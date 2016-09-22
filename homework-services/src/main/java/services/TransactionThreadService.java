package services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import asinchronysm.TransactionThreadManagement;
import dtos.TransactionDto;
import enums.TransactionStatus;

public class TransactionThreadService {
	public final static Logger LOGGER = LogManager.getLogger(TransactionThreadService.class);

	@Autowired
	private TransactionThreadManagement transactionThreadManagement;

	public TransactionStatus receiveAndProcessTransactions(TransactionDto dto) {
		TransactionStatus status = null;
		
		try {
			status = transactionThreadManagement.queingTransactions(dto);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		
		if(TransactionStatus.FINALIZA_ENCOLAMIENTO.equals(status)){
			try {
				transactionThreadManagement.executeTransactions();
			} catch (Exception e) {
				status = TransactionStatus.FINALIZADA_NOK;
				LOGGER.error(e);
			}		
		}
		 
		return status;
	}
}
