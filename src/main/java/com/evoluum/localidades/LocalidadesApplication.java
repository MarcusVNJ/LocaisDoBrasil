package com.evoluum.localidades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LocalidadesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalidadesApplication.class, args);
	}

}
