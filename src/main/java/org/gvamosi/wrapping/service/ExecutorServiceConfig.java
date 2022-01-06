package org.gvamosi.wrapping.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorServiceConfig {

	@Value("${thread.pool.size}")
	private int threadPoolSize;
	
	@Value("${thread.keepalive.seconds}")
	private int threadKeepAliveSeconds;
	
    @Bean("fixedThreadPool")
    public ExecutorService fixedThreadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        ((ThreadPoolExecutor) executorService).setKeepAliveTime(threadKeepAliveSeconds, TimeUnit.SECONDS);
        return executorService;
    }
    
}