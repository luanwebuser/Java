package com.microsservico.geobaseconsult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.microsservico.geobaseconsult.controller.GeobaseConsultController;

@SpringBootApplication
public class GeobaseConsultApplication implements CommandLineRunner {

	@Autowired
	private GeobaseConsultController controller;

	public static void main(String[] args) {
		SpringApplication.run(GeobaseConsultApplication.class, args);
	}

	@Override
	public void run(String... args) {

		controller.APIConsult(-25.450103792520423, -49.2530206021666);

	}

}
