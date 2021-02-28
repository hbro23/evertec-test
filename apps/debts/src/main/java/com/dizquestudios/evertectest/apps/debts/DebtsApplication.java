package com.dizquestudios.evertectest.apps.debts;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.dizquestudios.evertectest.core.debts.shared.API;

@SpringBootApplication
@ComponentScan(
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = API.class),
        value = {"com.dizquestudios.evertectest.core.debts","com.dizquestudios.evertectest.apps.debts.api"})
@EnableJpaRepositories("com.dizquestudios.evertectest.core.debts.infrastructure")
@EntityScan("com.dizquestudios.evertectest.core.debts.infrastructure")
public class DebtsApplication {

    public static void main(String[] args) {
        System.out.println("Prueba");
        SpringApplication.run(DebtsApplication.class, args);
    }

}
