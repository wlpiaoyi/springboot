package org.wlpiaoyi.springboot;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.wlpiaoyi.springboot.service.WebSocketService;
import org.wlpiaoyi.utile.websocket.WebSocketListener;

import java.io.IOException;


@Slf4j
@EnableCaching
@EnableScheduling
@SpringBootApplication
public class ApplicationLoader implements ApplicationContextAware {

    private static ApplicationContext APPLICATION_CONTEXT;
    public static void main(String[] args) {
        SpringApplication.run(ApplicationLoader.class, args);
    }

    public static <T> T getBean(Class<T> tClass) {
        return APPLICATION_CONTEXT.getBean(tClass);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;
    }
}
