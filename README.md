# Problema del consumidor y productor. 



### Requerimiento: Procesar transacciones, nacionales o internacionales, que debiten/depositen en las cuentas intervinientes

	* Almacenar 3 cuentas en memoria. Deben estar disponibles en un endpoint.
	* Almacenar las transacciones, inmediatamente ingresan, en memoria.
	* Ejecutar asincronicamente,en paralelo, el proceso de transacción.
	* Hacer un unit test que permita probar el calculo de impuestos. 
### Java 8, Spring-boot, Maven 3, Jersey
	$ git clone gisemartinez/homework-laboral
	$ cd homework-laboral
	$ mvn clean install
	$ cd homework-rest-api
	$ spring-boot:run

### Links útiles

####Motivos para usar LinkedBlockigQueue: 
		*http://www.concretepage.com/java/linkedblockingqueue_java
		*https://examples.javacodegeeks.com/core-java/util/concurrent/linkedblockingqueue/java-util-concurrent-linkedblockingqueue-example/
####Usos de Java 8 para concurrencia
		*http://winterbe.com/posts/2015/04/07/java8-concurrency-tutorial-thread-executor-examples/
####Spring Non-managed-beans
		*
	
