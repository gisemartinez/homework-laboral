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
import enums.ErrorMessages;
import enums.TransactionStatus;
import services.TransactionThreadService;

@Controller
@RequestMapping("/api")
public class TransactionController {

	@Autowired
	private TransactionThreadService transactionThreadService;

	public final static Logger LOGGER = LogManager.getLogger(TransactionController.class);

	/*
	 * Cada transacción inicia un nuevo thread para insertar en la lista de
	 * transacciones a procesar
	 */
	@RequestMapping(value = "/transaction", method = RequestMethod.POST)
	public ResponseEntity<Void> receiveTransaction(@RequestBody TransactionDto transaction) {

		ResponseEntity<Void> status;
		try {
			LOGGER.debug(transaction);

			TransactionStatus response = transactionThreadService.receiveAndProcessTransactions(transaction);

			if (TransactionStatus.ERROR_INTERNO_ANTES_DE_ENCOLARSE.equals(response)) {

				status = new ResponseEntity<Void>(HttpStatus.FORBIDDEN);

			} else if (TransactionStatus.ERROR_EN_PARAMETROS_RECIBIDOS.equals(response)) {

				status = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

			} else if (TransactionStatus.FINALIZA_ENCOLAMIENTO.equals(response)) {

				status = new ResponseEntity<Void>(HttpStatus.ACCEPTED);

			} else {

				status = new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
			}

		} catch (Exception e) {
			status = new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
			LOGGER.error(ErrorMessages.TRANSACCION_INVALIDA, e);
		}
		return status;

	}
}
