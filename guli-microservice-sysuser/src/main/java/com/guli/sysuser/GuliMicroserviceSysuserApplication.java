package com.guli.sysuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GuliMicroserviceSysuserApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliMicroserviceSysuserApplication.class, args);
    }

}
