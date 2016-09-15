package services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import asinchronysm.TransactionCallable;
import asinchronysm.TransactionThreadManagement;
import builders.TransactionBuilder;
import dtos.TransactionDto;
import fixedresources.Resources;

@Component
public class TransactionService {
	public final static Logger LOGGER = LogManager.getLogger(TransactionService.class);

	public void putInQueue(TransactionDto transactionDto) {

		TransactionBuilder transactionBuilder = new TransactionBuilder(transactionDto.getMonto());

		try {
			transactionBuilder.buildWithDestinyAccount(transactionDto.getDestinationAccount().getId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		
		try {
			transactionBuilder.buildWithOriginAccount(transactionDto.getOriginAccount().getId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		TransactionCallable callable = new TransactionCallable(transactionBuilder.build());
		Resources.TRANSACTION_QUEUE.addLast(callable);
		LOGGER.debug(Resources.TRANSACTION_QUEUE.size());

	}

	public void operate() {
		TransactionThreadManagement.main(null);
	}

}
