package com.soltelec.consola;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class DemoApplication {

    public final static String MODEL_PACKAGE = "com.soltelec.consola.model";

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
