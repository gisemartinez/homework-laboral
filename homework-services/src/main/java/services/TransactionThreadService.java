package services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import asinchronysm.TransactionThreadManagement;
import dtos.TransactionDto;
import enums.TransactionStatus;
import util.TransactionToText;

public class TransactionThreadService {
	public static final Logger LOGGER = LogManager.getLogger(TransactionThreadService.class);

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
				status = transactionThreadManagement.executeTransactions();
			} catch (Exception e) {
				status = TransactionStatus.FINALIZADA_NOK;
				LOGGER.error(e);
			}		
		}
		
		TransactionToText transactionToText = new TransactionToText(dto.getOriginAccount(), dto.getDestinationAccount(), status);
		
		transactionToText.writeFile();
		
		return status;
	}
}
