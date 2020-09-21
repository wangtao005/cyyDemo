package com.manhui.easyexp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages= {"org.mics","com.manhui.easyexp"})
@EnableJpaRepositories(basePackages = {"org.mics","com.manhui.easyexp"})
@EntityScan(basePackages= {"org.mics","com.manhui.easyexp"})
@EnableTransactionManagement
public class EasyexpApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyexpApplication.class, args);
        System.out.println("=======================================启动完成=====================================");
    }

}
