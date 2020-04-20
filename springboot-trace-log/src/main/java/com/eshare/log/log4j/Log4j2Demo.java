package com.eshare.log.log4j;

import com.alibaba.ttl.TtlRunnable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Log4j2Demo {
    private static Logger logger = LogManager.getLogger(Log4j2Demo.class);
    final static ExecutorService executorService = Executors.newFixedThreadPool(2);
    public void processLog4jLog() throws Exception {
        // Log in Main Thread
        logger.info("Log4j2Demo-> Log in main!");

        // Run task in thread pool
        executorService.submit(createTask()).get();

        // Init Log Context, set TTL
        // More KV if needed
        final String TRACE_ID = "trace-id";
        final String TRACE_ID_VALUE = UUID.randomUUID().toString();
        ThreadContext.put(TRACE_ID, TRACE_ID_VALUE);

        // Log in Main Thread
        logger.info("Log4j2Demo-> Main thread starts to process!");

        executorService.submit(createTask()).get();

        logger.info("Log4j2Demo-> Exit main");
        executorService.shutdown();
    }

    private static Runnable createTask() {
        final Runnable task = new Runnable() {
            @Override
            public void run() {
                // Log in thread pool
                ThreadContext.put("task", new Date().toString());
                logger.info("Log4j2Demo-> Sub thread starts to process!");
            }
        };
        return TtlRunnable.get(task);
    }
}
