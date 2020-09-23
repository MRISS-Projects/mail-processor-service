package com.mriss.products.mailprocessorservice.clockin.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = { "com.mriss.products.mailprocessorservice.clockin.app.config" })
public class ClockInApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClockInApplication.class, args);
    }
}