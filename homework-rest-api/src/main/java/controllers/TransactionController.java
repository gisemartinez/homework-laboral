package controllers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dtos.TransactionDto;
import services.TransactionService;


@Controller
@RequestMapping("/api")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	public final static Logger LOGGER = LogManager.getLogger(TransactionController.class);
	
	
	@RequestMapping(value = "/transaction", method = RequestMethod.POST)
	public ResponseEntity<Void> receiveTransaction(@RequestBody TransactionDto transaction){
	
		ResponseEntity<Void> status;
		try{
			LOGGER.debug(transaction);
			transactionService.putInQueue(transaction);
			transactionService.operate();
			status = new ResponseEntity<Void>(HttpStatus.ACCEPTED);
			
		}catch(Exception e){
			status = new ResponseEntity<Void>(HttpStatus.INSUFFICIENT_STORAGE);
			LOGGER.error("Cachear mejor la excepción", e);
		}
        return status;
	
		
	}
	
	@RequestMapping(value = "/transaction", method = RequestMethod.GET)
	public ResponseEntity<Void> getTransactions(){
		LOGGER.debug("Entrando a metodo GET");
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		
	}
}
