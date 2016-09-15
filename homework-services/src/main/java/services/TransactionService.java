package services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import asinchronysm.TransactionThreadManagement;
import builders.TransactionBuilder;
import businessobjects.InternationalTransaction;
import businessobjects.NationalTransaction;
import businessobjects.Transaction;
import dtos.TransactionDto;
import fixedresources.Resources;

@Component
public class TransactionService {
	public final static Logger LOGGER = LogManager.getLogger(TransactionService.class);

	public void putInQueue(TransactionDto transactionDto) {

		Resources.TRANSACTION_QUEUE.addLast(obtainNationalInternational(transactionDto));
		LOGGER.debug(Resources.TRANSACTION_QUEUE.size());
		
	}

	public void operate() {
		TransactionThreadManagement.main(null);
	}

	private Transaction obtainNationalInternational(TransactionDto transactionDto) {

		Transaction transactionResponse;

		if (isNationalTransaction(transactionDto)) {

			transactionResponse = copyTransaction(new NationalTransaction(), transactionDto);

		} else {

			transactionResponse = copyTransaction(new InternationalTransaction(), transactionDto);
		}
		return transactionResponse;
	}

	private Transaction copyTransaction(Transaction transaction, TransactionDto transactionDto) {
		return new TransactionBuilder(transaction, transactionDto).buildWithAccounts().build();
	}

	private boolean isNationalTransaction(TransactionDto transaction) {

		return Resources.MY_COUNTRY.equals(transaction.getOriginAccount().getOriginCountry())
				&& Resources.MY_COUNTRY.equals(transaction.getDestinationAccount().getOriginCountry());

	}

}
