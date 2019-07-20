package com.guli.ucenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Administrator
 * @create 2019-07-20 10:42
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages={"com.guli.ucenter","com.guli.common"})
public class UcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class,args);
    }
}
