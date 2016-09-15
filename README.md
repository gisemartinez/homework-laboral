# Problema del consumidor y productor. 

##Hecho en Java 8, usando spring-boot, Futures, Callables y Executors. 

### Requerimiento: Procesar transacciones, nacionales o internacionales, que debiten/depositen en las cuentas intervinientes

	#### Almacenar 3 cuentas en memoria. Deben estar disponibles en un endpoint.
	#### Almacenar las transacciones, inmediatamente ingresan, en memoria.
	#### Ejecutar asincronicamente,en paralelo, el proceso de transacción.
	#### Hacer un unit test que permita probar el calculo de impuestos. 

	
