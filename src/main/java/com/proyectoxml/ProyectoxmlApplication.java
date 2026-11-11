package com.proyectoxml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ProyectoxmlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoxmlApplication.class, args);
	}

}
