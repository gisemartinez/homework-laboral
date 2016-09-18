package services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import asinchronysm.TransactionThreadManagement;
import dtos.TransactionDto;
import enums.TransactionStatus;

@Component
public class TransactionService {
	public final static Logger LOGGER = LogManager.getLogger(TransactionService.class);

	public TransactionStatus receiveTransactions(TransactionDto dto) {
		
		TransactionThreadManagement transactionThreadManagement = new TransactionThreadManagement();
		
		try {
			transactionThreadManagement.queingTransactions(dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//TODO: Return some useful object
		return null;
		
		
	}

}
