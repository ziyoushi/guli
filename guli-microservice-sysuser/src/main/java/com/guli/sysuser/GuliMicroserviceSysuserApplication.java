package com.guli.sysuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.guli.sysuser.config"})
public class GuliMicroserviceSysuserApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliMicroserviceSysuserApplication.class, args);
    }

}
