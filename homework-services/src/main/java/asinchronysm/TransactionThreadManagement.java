package asinchronysm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;

import builders.BuilderFactory;
import dtos.TransactionDto;
import enums.TransactionStatus;
import fixedresources.Resources;

public class TransactionThreadManagement {

	private static ThreadPoolExecutor executeProductor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
	private static ThreadPoolExecutor executeConsumer = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

	/*
	 * Gestión de transacciones ingresantes. Motivo: El ingreso, así como el
	 * procesamiento debe ser concurrente.
	 */
	@Autowired
	private BuilderFactory builderFactory;

	public TransactionStatus queingTransactions(TransactionDto dto) {

		TransactionProductorCallable transactionCallable;
		List<Future<TransactionStatus>> resultList = new ArrayList<>();
		TransactionStatus queuing = TransactionStatus.INICIADA;

		try {
			transactionCallable = new TransactionProductorCallable(dto, builderFactory);

			Future<TransactionStatus> result = executeProductor.submit(transactionCallable);

			resultList.add(result);

		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Future<TransactionStatus> future : resultList) {
			try {
				queuing = future.get();
				System.out.println("And Task done is " + future.isDone());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		// Revisar: El shutdown debería estar temporizado, e incluso
		// podría no tener que estar
		executeProductor.shutdown();
		return queuing;
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
