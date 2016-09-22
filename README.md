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
	
	#Endpoints
	GET : http://localhost:8080/api/accounts/
	POST: http://localhost:8080/api/transaction
		Exmaple payload : 
		{
		"originAccount":{
			"id": 0
		},
		"destinationAccount":{
			"id": 1
		},
			"monto":100
		}

### Links útiles

####Motivos para usar LinkedBlockigQueue: 
		*http://www.concretepage.com/java/linkedblockingqueue_java
		*https://examples.javacodegeeks.com/core-java/util/concurrent/linkedblockingqueue/java-util-concurrent-linkedblockingqueue-example/
####Ejemplos útiles
		*
####Spring Non-managed-beans
		*
	
####Mejoras
	El guardado en el archivo debería ser un poco más elegante
	Usar un poco más Java 8, incluyendo las clases Consumer y Productor
	Exponer en swagger los endpoints