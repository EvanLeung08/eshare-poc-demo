package com.eshare.log;

import com.eshare.log.log4j.Log4j2Demo;
import com.eshare.log.slf4j.Slf4jMdcDemo;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootTractLogApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringbootTractLogApplication.class, args);
        //Run log process
        new Log4j2Demo().processLog4jLog();
        new Slf4jMdcDemo().processSl4jLog();
    }

}
