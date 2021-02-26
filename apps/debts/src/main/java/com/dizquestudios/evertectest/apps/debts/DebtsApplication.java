package com.dizquestudios.evertectest.apps.debts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.dizquestudios.evertectest.core.debts.shared.API;

@SpringBootApplication
@ComponentScan(
    includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = API.class),
    value = {"com.dizquestudios.evertectest.core.debts", "com.dizquestudios.evertectest.apps.debts.api"}
)
public class DebtsApplication {

	public static void main(String[] args) {
                System.out.println("Prueba");
		SpringApplication.run(DebtsApplication.class, args);
	}

}
