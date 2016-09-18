package asinchronysm;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;

import businessobjects.Account;
import businessobjects.Transaction;
import enums.TransactionStatus;
import services.AccountService;

public class TransactionConsumerCallable implements Callable<TransactionStatus> {

	@Autowired
	private AccountService accountService;

	Transaction transaction;

	public TransactionConsumerCallable(Transaction transaction) {
		this.transaction = transaction;
	}

	@Override
	public TransactionStatus call() throws Exception {

		return succesfullOperation(transaction) ? TransactionStatus.FINALIZADA_OK : TransactionStatus.FINALIZADA_NOK;
	}
	//TODO: Pulir la lógica. Es poco legible.
	private synchronized boolean succesfullOperation(Transaction transaction) {

		boolean successfull = false;

		BigDecimal substraction = transaction.calcularImpuesto().add(transaction.getMonto());
		BigDecimal addition = transaction.getMonto();
		
		try {

			Account originAccount = accountService.getAccountById(transaction.getOriginAccount().getId());
			Account destinyAccount = accountService.getAccountById(transaction.getOriginAccount().getId());

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
