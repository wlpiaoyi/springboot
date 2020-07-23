package org.wlpiaoyi.springboot;


import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * keytool -genkey -alias tomcat -keyalg RSA -keystore ~/springboot.keystore
 */
@Slf4j
@EnableCaching
@EnableScheduling
@SpringBootApplication
@PropertySource(value = "classpath:application.properties")
public class ApplicationLoader implements ApplicationContextAware {


    @Value("${server.port}")
    private int httpPort;
    //4 对接口配置跨域设置
    @Value("${server.port-sec}")
    private int httpsPort;

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

    @Bean
    public TomcatServletWebServerFactory servletContainer(Connector connector) {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(connector);
        return tomcat;
    }

    @Bean
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        //Connector监听的http的端口号
        connector.setPort(httpPort);
        connector.setSecure(false);
        //监听到http的端口号后转向到的https的端口号
        connector.setRedirectPort(httpsPort);
        return connector;
    }
}
