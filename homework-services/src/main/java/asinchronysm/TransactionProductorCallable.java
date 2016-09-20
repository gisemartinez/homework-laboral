package asinchronysm;

import java.util.concurrent.Callable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import builders.BuilderFactory;
import builders.TransactionBuilder;
import businessobjects.Transaction;
import dtos.TransactionDto;
import enums.TransactionStatus;
import fixedresources.Resources;

public class TransactionProductorCallable implements Callable<TransactionStatus> {

	public final static Logger LOGGER = LogManager.getLogger(TransactionProductorCallable.class);

	private Transaction transaction;

	private BuilderFactory builderFactory;

	public TransactionProductorCallable(TransactionDto transactionDto,BuilderFactory builderFactory) {
		this.builderFactory = builderFactory;
		try {
			this.transaction = buildTransaction(transactionDto);
		} catch (Exception e) {
			this.transaction = null;
		}
	}

	@Override
	public TransactionStatus call() throws Exception {

		TransactionConsumerCallable callable;
		TransactionStatus status;
		try {

			callable = new TransactionConsumerCallable(this.transaction);
			Resources.TRANSACTION_QUEUE.offer(callable);
			status = TransactionStatus.FINALIZA_ENCOLAMIENTO;

		} catch (NullPointerException e) {
			LOGGER.error(e);
			status = TransactionStatus.ERROR_EN_PARAMETROS_RECIBIDOS;
		} catch (Exception e) {
			LOGGER.error(e);
			status = TransactionStatus.ERROR_INTERNO_ANTES_DE_ENCOLARSE;
		}

		return status;
	}

	/*
	 * Implementación de builder para construir la transacción que arroja
	 * excepciones si las cuentas involucradas no existen
	 */
	private synchronized Transaction buildTransaction(TransactionDto transactionDto) throws Exception {
		
		TransactionBuilder transactionBuilder = builderFactory.getObject();
		transactionBuilder.buildWithAmount(transactionDto.getMonto());

		try {
			transactionBuilder.buildWithDestinyAccount(transactionDto.getDestinationAccount().getId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}

		try {
			transactionBuilder.buildWithOriginAccount(transactionDto.getOriginAccount().getId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}

		return transactionBuilder.build();
	}

}
