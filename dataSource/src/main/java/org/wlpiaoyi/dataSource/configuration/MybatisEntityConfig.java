package org.wlpiaoyi.dataSource.configuration;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"org.wlpiaoyi.dataSource.dao.pojo"},sqlSessionFactoryRef = "mybatisPojoSqlSessionFactory")
public class MybatisEntityConfig {


    @Value("${mybatis.map-locations}")
    private String mapLocation;

    @Resource(name = "dataSource-druid")
    private DataSource dataSource;

    @Bean(name = "mybatisPojoSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(this.dataSource);
        factoryBean.setConfigLocation(new ClassPathResource(this.mapLocation));
        return factoryBean.getObject();
    }
}
