package asinchronysm;

import java.util.ArrayList;
import java.util.List;
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

		TransactionToText transactionToText = new TransactionToText(dto.getDestinationAccount(), dto.getOriginAccount(), queuing);

		try {
			transactionToText.writeFile();
		} catch (Exception e) {
			LOGGER.error(e);
		}

		try {

			transactionCallable = new TransactionProductorCallable(dto, accountService);

			result = executeProductor.submit(transactionCallable);

		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			transactionToText.setTransactionStatus(result.get());
			LOGGER.info("Status : " + result.get() +" && Task done is " + result.isDone());
		}
		return result.get();
		
		// // Revisar: El shutdown debería estar temporizado, e incluso
		// // podría no tener que estar
		// executeProductor.shutdown();
		// return queuing;
	}

	public void executeTransactions() {

		List<Future<TransactionStatus>> resultList = new ArrayList<>();

		try {
			// resources me deberia avisar que tiene contenido para procesar
			// while (!Resources.TRANSACTION_QUEUE.isEmpty()) {

			TransactionConsumerCallable operation = Resources.TRANSACTION_QUEUE.take();

			Future<TransactionStatus> result = executeConsumer.submit(operation);

			resultList.add(result);

			// }
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Future<TransactionStatus> future : resultList) {
			try {
				System.out.println(
						"Future result is - " + " - " + future.get() + "; And Task done is " + future.isDone());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		executeConsumer.shutdown();
	}
}
