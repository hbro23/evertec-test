/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dizquestudios.evertectest.apps.debts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Sebastian
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String BASE_PACKAGE_API = "com.dizquestudios.evertectest.apps.debts.api";
    public static final String TITLE_API = "Evertec-test Debts";
    public static final String DESCRIPTION_API = "RESTFul demo application for managing client account debts.";
    public static final String VERSION_API = "0.0.1";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE_API))
                .paths(PathSelectors.regex("/.*"))
                .build().apiInfo(info());

    }
    
    private ApiInfo info(){
        return new ApiInfoBuilder().title(TITLE_API)
                .description(DESCRIPTION_API)
                .version(VERSION_API)
                .build();
    }
}
