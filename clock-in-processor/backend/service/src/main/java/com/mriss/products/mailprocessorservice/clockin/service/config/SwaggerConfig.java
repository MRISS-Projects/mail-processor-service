package com.mriss.products.mailprocessorservice.clockin.service.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author Marcelo Riss (marcelo.riss@gmail.com)
 * @version 2020, Sep 11.
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfig.class);

    @Value("${app.version}")
    private String appVersion;

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket api() {
        LOGGER.info("Enabling swagger...");
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/backend/service/**")).build().useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, defaultResponseMessages())
                .globalResponseMessage(RequestMethod.POST, defaultResponseMessages()).apiInfo(apiInfo());
    }

    private List<ResponseMessage> defaultResponseMessages() {
        return new ArrayList<ResponseMessage>() {
            {
                add(new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value())
                        .message(HttpStatus.BAD_REQUEST.getReasonPhrase()).build());
                add(new ResponseMessageBuilder().code(HttpStatus.UNAUTHORIZED.value())
                        .message(HttpStatus.UNAUTHORIZED.getReasonPhrase()).build());
                add(new ResponseMessageBuilder().code(HttpStatus.FORBIDDEN.value())
                        .message(HttpStatus.FORBIDDEN.getReasonPhrase()).build());
                add(new ResponseMessageBuilder().code(HttpStatus.NOT_FOUND.value())
                        .message(HttpStatus.NOT_FOUND.getReasonPhrase()).build());
                add(new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).build());
            }
        };
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Mail Processing Service REST API")
                .description("Cloud based API used to calculate clock-in data and store results at FireStore.")
                .version(appVersion).license("Apache 2.0").licenseUrl("https://www.apache.org/licenses/LICENSE-2.0.txt")
                .contact(new Contact("Marcelo Riss", "https://github.com/MRISS-Projects/mail-processor-service",
                        "marcelo.riss@gmail.com"))
                .build();
    }
}