package asinchronysm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import enums.TransactionStatus;
import fixedresources.Resources;

public class TransactionThreadManagement {

	public static void main(String[] args) {
		
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

		List<Future<TransactionStatus>> resultList = new ArrayList<>();
		try{
			//resources me deberia avisar que tiene contenido para procesar
			while ( !Resources.TRANSACTION_QUEUE.isEmpty()) {
				
				TransactionCallable operation = Resources.TRANSACTION_QUEUE.removeFirst();
				
				Future<TransactionStatus> result = executor.submit(operation);
				
				resultList.add(result);
				
			}
		}catch( Exception e){
			e.printStackTrace();
		}

		for (Future<TransactionStatus> future : resultList) {
			try {
				System.out.println(
						"Future result is - " + " - " + future.get() + "; And Task done is " + future.isDone());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e){
				e.printStackTrace();
			}
		}
		
		executor.shutdown();
	}
}
