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
	
	/*
	 * Cada transacción inicia un nuevo thread para insertar en la lista de 
	 * transacciones a procesar
	 */
	@RequestMapping(value = "/transaction", method = RequestMethod.POST)
	public ResponseEntity<Void> receiveTransaction(@RequestBody TransactionDto transaction){
	
		ResponseEntity<Void> status;
		try{
			LOGGER.debug(transaction);
			
			enums.TransactionStatus response = transactionService.receiveTransactions(transaction);
			if(response.equals(enums.TransactionStatus.ERROR_INTERNO_ANTES_DE_ENCOLARSE)){
				status =  new ResponseEntity<Void>(HttpStatus.FORBIDDEN);				
			}else if(response.equals(enums.TransactionStatus.ERROR_EN_PARAMETROS_RECIBIDOS)){
				status =  new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);				
			}else if(response.equals(enums.TransactionStatus.INSERTADA_CORRECTAMENTE)){
				status =  new ResponseEntity<Void>(HttpStatus.ACCEPTED);				
			}else{
				status = new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);			
			}

			
		}catch(Exception e){
			status = new ResponseEntity<Void>(HttpStatus.INSUFFICIENT_STORAGE);
			LOGGER.error("Cachear mejor la excepción", e);
		}
        return status;
	
		
	}
	
	@RequestMapping(value = "/transaction", method = RequestMethod.GET)
	public ResponseEntity<TransactionDto> getTransactions(){
		//TODO: endpoint para mostrar transacciones encoladas
        return new ResponseEntity<TransactionDto>(HttpStatus.ACCEPTED);
		
	}
}
