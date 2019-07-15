package org.wlpiaoyi.dataSource.configuration;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.wlpiaoyi.dataSource.dao.BaseBatisDao;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:mybatis.properties")
@MapperScan(basePackages = {"org.wlpiaoyi.dataSource.dao.impl"},
        markerInterface = BaseBatisDao.class,
        sqlSessionFactoryRef = "DaoSqlSessionFactory-mybatis")
public class MybatisDaoConfig {

    @Value("${mybatis.dao-locations}")
    private String daoLocation;

    @Resource(name = "dataSource-druid")
    private DataSource dataSource;


    @Bean(name = "DaoSqlSessionFactory-mybatis")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(this.dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(this.daoLocation));

        return factoryBean.getObject();
    }

    @Bean(name = "DaoSqlSessionTemplate-mybatis")
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(this.sqlSessionFactory());
        return template;
    }

    @Bean(name = "TransactionManager-mybatis")
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(this.dataSource);
    }
}
