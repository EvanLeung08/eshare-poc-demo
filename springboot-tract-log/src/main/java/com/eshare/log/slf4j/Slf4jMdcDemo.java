package com.eshare.log.slf4j;

import com.alibaba.ttl.TtlRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Slf4jMdcDemo {
    private static final Logger logger = LoggerFactory.getLogger(Slf4jMdcDemo.class);
    final static ExecutorService executorService = Executors.newFixedThreadPool(2);
    public void processSl4jLog() throws Exception {
        // Log in Main Thread
        logger.info("Slf4jMdcDemo-> Log in main!");

        // Run task in thread pool
        executorService.submit(createTask()).get();

        // Init Log Context, set TTL
        // More KV if needed
        final String TRACE_ID = "trace-id";
        final String TRACE_ID_VALUE = UUID.randomUUID().toString();
        MDC.put(TRACE_ID, TRACE_ID_VALUE);

        // Log in Main Thread
        logger.info("Slf4jMdcDemo-> Main thread starts to process!");

        executorService.submit(createTask()).get();

        logger.info("Slf4jMdcDemo-> Exit main");
        executorService.shutdown();
    }

    private static Runnable createTask() {
        final Runnable task = new Runnable() {
            @Override
            public void run() {
                // Log in thread pool
                MDC.put("task", new Date().toString());
                logger.info("Slf4jMdcDemo-> Sub thread starts to process!");
            }
        };
        return TtlRunnable.get(task);
    }
}
