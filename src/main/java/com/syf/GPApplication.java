package com.syf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@ServletComponentScan(basePackages = "com.syf.filter")
@SpringBootApplication
public class GPApplication {
    public static void main(String[] args) {
        SpringApplication.run(GPApplication.class, args);
    }


}
