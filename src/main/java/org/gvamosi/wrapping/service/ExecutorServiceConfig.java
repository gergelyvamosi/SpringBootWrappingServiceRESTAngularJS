package org.gvamosi.wrapping.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorServiceConfig {
	
    @Bean("cachedThreadPool")
    public ExecutorService cachedThreadPool() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        return executorService;
    }
    
}