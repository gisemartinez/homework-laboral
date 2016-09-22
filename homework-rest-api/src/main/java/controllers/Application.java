package controllers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Por todos los cambios que hice en el classpath, 
 * todos los annotations de acá los dejo para después
 */

@ImportResource("classpath:spring-webcontext.xml")
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

