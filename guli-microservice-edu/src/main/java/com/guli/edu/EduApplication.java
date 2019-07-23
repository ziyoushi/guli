package com.guli.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Administrator
 * @create 2019-07-12 11:24
 */
@EnableTransactionManagement
@EnableEurekaClient
@SpringBootApplication
@ComponentScan(basePackages={"com.guli.edu","com.guli.common"})
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
