package com.open.capacity.common.test.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.open.capacity.common.util.ThreadTaskUtils;

@Service
public class LogService {
    Logger logger = LoggerFactory.getLogger(LogService.class);

    public void log() {
        logger.info("1111111111111================begin==============================");

        
        ThreadTaskUtils.run(() -> run1());
        FutureTask<String> futureTask = new FutureTask<String>(() -> call1());
        ThreadTaskUtils.run(futureTask);
        
        logger.info("1111111111111==================end============================");
        
        logger.info("2222222222222================begin==============================");

        
        new Thread(()-> run2() ).start();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(()->call2());
        
        logger.info("2222222222222==================end============================");
        

        
    }

    private String call1() {
        logger.info("11111111111");
        return "11111111111";
    }

    public void run1() {
        logger.info("11111111111");
    }
    private String call2() {
        logger.info("22222222222");
        return "22222222222";
    }

    public void run2() {
        logger.info("22222222222");
    }
}