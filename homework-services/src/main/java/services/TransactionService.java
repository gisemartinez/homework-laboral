package services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import asinchronysm.TransactionThreadManagement;
import dtos.TransactionDto;
import enums.TransactionStatus;

public class TransactionService {
	public final static Logger LOGGER = LogManager.getLogger(TransactionService.class);

	@Autowired
	private TransactionThreadManagement transactionThreadManagement;

	public TransactionStatus receiveTransactions(TransactionDto dto) {
		TransactionStatus status = TransactionStatus.INICIA_ENCOLAMIENTO;
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
