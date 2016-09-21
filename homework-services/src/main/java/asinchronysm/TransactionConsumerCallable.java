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
	
	public final static Logger LOGGER = LogManager.getLogger(TransactionConsumerCallable.class);
	
	private AccountService accountService;

	Transaction transaction;

	public TransactionConsumerCallable(Transaction transaction,AccountService accountService) {
		this.transaction = transaction;
		this.accountService = accountService;
	}

	@Override
	public TransactionStatus call() throws Exception {

		return succesfullOperation(transaction) ? TransactionStatus.FINALIZADA_OK : TransactionStatus.FINALIZADA_NOK;
	}
	//TODO: Pulir la lógica. Es poco legible.
	private synchronized boolean succesfullOperation(Transaction transaction) {
		
		
		TransactionStatus operation = TransactionStatus.INICIA_PROCESAMIENTO;

		TransactionToText transactionToText = new TransactionToText(transaction.getOriginAccount(), transaction.getDestinationAccount(), operation);

		try {
			transactionToText.writeFile();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		
		boolean successfull = false;

		BigDecimal substraction = transaction.calcularImpuesto().add(transaction.getMonto());
		BigDecimal addition = transaction.getMonto();
		
		try {

			Account originAccount = transaction.getOriginAccount();
			Account destinyAccount = transaction.getDestinationAccount();

			if (originAccount.isDebitLessThanActualCredit(substraction)) {
				accountService.updateCredit(originAccount.getId(), substraction.negate());
				accountService.updateCredit(destinyAccount.getId(), addition);
				successfull = true;
			}

		} catch (Exception e) {
			e.getMessage();
			throw e;
		}

		return successfull;
	}

}
