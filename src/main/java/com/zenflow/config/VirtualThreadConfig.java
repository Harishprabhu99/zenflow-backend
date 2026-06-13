package com.zenflow.config;

import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class VirtualThreadConfig {

    @Bean(name = "virtualThreadExecutor")
    public AsyncTaskExecutor virtualThreadExecutor() {
        return new TaskExecutorAdapter(
            Executors.newVirtualThreadPerTaskExecutor()
        );
    }

    // Replace Tomcat thread pool with virtual threads
    @Bean
    public TomcatProtocolHandlerCustomizer<?> tomcatVirtualThreads() {
        return handler -> handler.setExecutor(
            Executors.newVirtualThreadPerTaskExecutor()
        );
    }
}
