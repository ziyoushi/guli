package com.guli.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Administrator
 * @create 2019-07-17 16:47
 */
@EnableEurekaClient
@SpringBootApplication
public class AliyunOssApplication {
    public static void main(String[] args) {
        SpringApplication.run(AliyunOssApplication.class,args);
    }
}
