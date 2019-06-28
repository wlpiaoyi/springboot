package org.wlpiaoyi.springboot.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@PropertySource(value = "classpath:application-detail/cors-filter.properties")
public class CorsFilterConfiguration {

    //1 设置你要允许的网站域名，如果全允许则设为 *
    @Value("${org.wlpiaoyi.cors.allowed-origins}")
    private List<String> allowedOrigins;
    @Value("${org.wlpiaoyi.cors.allowed-headers}")
    private List<String> allowedHeaders;
    @Value("${org.wlpiaoyi.cors.allowed-methods}")
    private List<String> allowedMethods;
    //4 对接口配置跨域设置
    @Value("${org.wlpiaoyi.cors.allowed-credential}")
    private boolean allowedCredential;
    //4 对接口配置跨域设置
    @Value("${org.wlpiaoyi.cors.url-config-source}")
    private String urlConfigSource;


    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        if(this.allowedOrigins != null && this.allowedOrigins.size() > 0)
            corsConfiguration.setAllowedOrigins(this.allowedOrigins);
        if(this.allowedHeaders != null && this.allowedHeaders.size() > 0)
            corsConfiguration.setAllowedHeaders(this.allowedHeaders);
        if(this.allowedMethods != null && this.allowedMethods.size() > 0)
            corsConfiguration.setAllowedMethods(this.allowedMethods);
        corsConfiguration.setAllowCredentials(this.allowedCredential);
        return corsConfiguration;
    }

    @Bean
    public org.springframework.web.filter.CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(this.urlConfigSource, buildConfig());
        return new org.springframework.web.filter.CorsFilter(source);
    }
}
