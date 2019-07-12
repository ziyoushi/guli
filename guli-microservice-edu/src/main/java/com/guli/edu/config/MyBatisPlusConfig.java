package com.guli.edu.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Administrator
 * @create 2019-07-12 11:26
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.guli.edu.mapper")
public class MyBatisPlusConfig {

}
