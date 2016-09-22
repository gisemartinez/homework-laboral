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

	public static final Logger LOGGER = LogManager.getLogger(TransactionController.class);

	/*
	 * Cada transacción inicia un nuevo thread para insertar en la lista de
	 * transacciones a procesar
	 */
	@RequestMapping(value = "/transaction", method = RequestMethod.POST)
	public ResponseEntity<TransactionStatus> receiveTransaction(@RequestBody TransactionDto transaction) {

		ResponseEntity<TransactionStatus> status;
		try {
			LOGGER.debug(transaction);

			TransactionStatus response = transactionThreadService.receiveAndProcessTransactions(transaction);

			if (TransactionStatus.FINALIZADA_OK.equals(response)) {

				status = new ResponseEntity<>(response,HttpStatus.OK);
			} else if (TransactionStatus.ERROR_INTERNO_ANTES_DE_ENCOLARSE.equals(response)) {

				status = new ResponseEntity<>(response,HttpStatus.CONFLICT);

			} else if (TransactionStatus.ERROR_EN_PARAMETROS_RECIBIDOS.equals(response)) {

				status = new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

			} else if (TransactionStatus.FINALIZA_ENCOLAMIENTO.equals(response)) {

				status = new ResponseEntity<>(response,HttpStatus.ACCEPTED);

			} else if (TransactionStatus.INICIA_PROCESAMIENTO.equals(response)) {

				status = new ResponseEntity<>(response,HttpStatus.PROCESSING);

			} else if (TransactionStatus.INTERRUPCION_AL_PROCESAR.equals(response)) {

				status = new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);

			} else if (TransactionStatus.FINALIZADA_NOK.equals(response)) {

				status = new ResponseEntity<>(response,HttpStatus.NO_CONTENT);

			} else {

				status = new ResponseEntity<>(response,HttpStatus.NOT_IMPLEMENTED);
			}

		} catch (Exception e) {
			status = new ResponseEntity<>(TransactionStatus.ERROR_DESCONOCIDO,HttpStatus.INTERNAL_SERVER_ERROR);
			LOGGER.error(ErrorMessages.TRANSACCION_INVALIDA, e);
		}
		return status;

	}
}
