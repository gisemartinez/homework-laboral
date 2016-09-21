package asinchronysm;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import dtos.TransactionDto;
import enums.TransactionStatus;
import fixedresources.Resources;
import services.AccountService;
import util.TransactionToText;

public class TransactionThreadManagement {

	private static ThreadPoolExecutor executeProductor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
	private static ThreadPoolExecutor executeConsumer = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
	public final static Logger LOGGER = LogManager.getLogger(TransactionThreadManagement.class);

	/*
	 * Gestión de transacciones ingresantes. Motivo: El ingreso, así como el
	 * procesamiento debe ser concurrente.
	 */
	@Autowired
	private AccountService accountService;

	public TransactionStatus queingTransactions(TransactionDto dto) throws InterruptedException, ExecutionException {

		TransactionProductorCallable transactionCallable;

		Future<TransactionStatus> result = null;

		TransactionStatus queuing = TransactionStatus.INICIA_ENCOLAMIENTO;

		TransactionToText transactionToText = new TransactionToText(dto.getOriginAccount(), dto.getDestinationAccount(),
				queuing);

		transactionToText.writeFile();

		try {

			transactionCallable = new TransactionProductorCallable(dto, accountService);

			result = executeProductor.submit(transactionCallable);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			transactionToText.setTransactionStatus(result.get());
			transactionToText.writeFile();
			LOGGER.info("Status : " + result.get() + " && Task done is " + result.isDone());

		}

		return result.get();
	}

	// TODO: Debería devolver una transaccionstatus?
	public void executeTransactions() throws InterruptedException, ExecutionException {

		TransactionConsumerCallable operation;

		Future<TransactionStatus> result = null;

		try {
			// get y take, esperan a que haya contenido en la queue
			operation = Resources.TRANSACTION_QUEUE.take();

			result = executeConsumer.submit(operation);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			LOGGER.info("Status : " + result.get() + " && Task done is " + result.isDone());

		}

		//executeConsumer.shutdown();
	}
}
