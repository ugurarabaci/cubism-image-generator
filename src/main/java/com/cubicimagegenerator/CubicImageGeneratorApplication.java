package com.cubicimagegenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CubicImageGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CubicImageGeneratorApplication.class, args);
	}

}
