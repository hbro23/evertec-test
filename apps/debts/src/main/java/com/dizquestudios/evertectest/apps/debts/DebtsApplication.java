package com.dizquestudios.evertectest.apps.debts;

import com.dizquestudios.evertectest.core.debts.domain.Debt;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.dizquestudios.evertectest.core.debts.shared.API;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javax.money.Monetary;
import org.javamoney.moneta.FastMoney;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.zalando.jackson.datatype.money.MoneyModule;

@SpringBootApplication
@ComponentScan(
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = API.class),
        value = {"com.dizquestudios.evertectest.core.debts", "com.dizquestudios.evertectest.apps.debts.api"})
@EnableJpaRepositories("com.dizquestudios.evertectest.core.debts.infrastructure")
@EntityScan("com.dizquestudios.evertectest.core.debts.infrastructure")
public class DebtsApplication {

    static {
        Monetary.getCurrency(Debt.CURRENCY);
    }

    public static void main(String[] args) {
        SpringApplication.run(DebtsApplication.class, args);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new MoneyModule()
                        .withMonetaryAmount(FastMoney::of));
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }
}
