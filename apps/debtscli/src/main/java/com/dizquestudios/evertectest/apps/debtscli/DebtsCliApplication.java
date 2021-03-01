package com.dizquestudios.evertectest.apps.debtscli;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.dizquestudios.evertectest.core.debts.shared.API;

@SpringBootApplication
@ComponentScan(
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = API.class),
        value = {"com.dizquestudios.evertectest.core.debts", "com.dizquestudios.evertectest.apps.debtscli"})
@EnableJpaRepositories("com.dizquestudios.evertectest.core.debts.infrastructure")
@EntityScan("com.dizquestudios.evertectest.core.debts.infrastructure")
public class DebtsCliApplication {

    public static void main(String[] args) {
        logger().log(Level.INFO, "STARTING THE APPLICATION");
        SpringApplication.run(DebtsCliApplication.class, args);
        logger().log(Level.INFO, "APPLICATION FINISHED");
    }

    private static Logger logger() {
        return Logger.getLogger(DebtsCliApplication.class.getSimpleName());
    }
}
