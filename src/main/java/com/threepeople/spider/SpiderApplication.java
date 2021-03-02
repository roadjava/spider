package com.threepeople.spider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = {"com.threepeople.spider.mapper"})
@EnableTransactionManagement
@EnableScheduling
public class SpiderApplication {
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
