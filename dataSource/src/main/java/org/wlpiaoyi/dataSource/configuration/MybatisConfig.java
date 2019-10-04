package org.wlpiaoyi.dataSource.configuration;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:mybatis.properties")
@MapperScan(basePackages = {"org.wlpiaoyi.dataSource.dao.impl","org.wlpiaoyi.dataSource.entity"},sqlSessionFactoryRef = "SqlSessionFactory-mybatis", sqlSessionTemplateRef = "SqlSessionTemplate-mybatis")
public class MybatisConfig {

    @Value("${mybatis.map-locations}")
    private String mapLocation;

    @Resource(name = "dataSource")
    private DataSource dataSource;

    @Bean(name = "SqlSessionFactory-mybatis")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(this.dataSource);
        factoryBean.setConfigLocation(new ClassPathResource(this.mapLocation));
        return factoryBean.getObject();
    }


    @Bean(name = "SqlSessionTemplate-mybatis")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("SqlSessionFactory-mybatis")SqlSessionFactory sqlSessionFactory) {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
        return template;
    }

}
