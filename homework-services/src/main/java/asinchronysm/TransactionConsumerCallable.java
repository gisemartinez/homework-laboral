package asinchronysm;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import businessobjects.Account;
import businessobjects.Transaction;
import enums.TransactionStatus;
import services.AccountService;
import util.TransactionToText;

public class TransactionConsumerCallable implements Callable<TransactionStatus> {

	public static final Logger LOGGER = LogManager.getLogger(TransactionConsumerCallable.class);

	private AccountService accountService;

	Transaction transaction;

	public TransactionConsumerCallable(Transaction transaction, AccountService accountService) {
		this.transaction = transaction;
		this.accountService = accountService;
	}

	@Override
	public TransactionStatus call() throws Exception {

		TransactionStatus operation = TransactionStatus.INICIA_PROCESAMIENTO;

		TransactionToText transactionToText = new TransactionToText(transaction.getOriginAccount(),
				transaction.getDestinationAccount(), operation);

		transactionToText.writeFile();

		operation = updateAccountsCredit(transaction);

		transactionToText.setTransactionStatus(operation).writeFile();

		return operation;
	}

	private synchronized TransactionStatus updateAccountsCredit(Transaction transaction) {

		BigDecimal substraction = transaction.calcularImpuesto().add(transaction.getMonto());
		BigDecimal addition = transaction.getMonto();
		TransactionStatus operation;
		Account originAccount = transaction.getOriginAccount();
		Account destinyAccount = transaction.getDestinationAccount();

		try {

			if (originAccount.isDebitLessThanActualCredit(substraction)) {
				
				accountService.updateCredit(originAccount.getId(), substraction.negate());
				accountService.updateCredit(destinyAccount.getId(), addition);
				operation = TransactionStatus.FINALIZADA_OK;
				
			} else {
				operation = TransactionStatus.FONDOS_INSUFICIENTES;
			}

		} catch (Exception e) {
			LOGGER.error(e);
			operation = TransactionStatus.ERROR_DE_ACTUALIZACION_A_CUENTAS;
		}

		return operation;
	}

}
