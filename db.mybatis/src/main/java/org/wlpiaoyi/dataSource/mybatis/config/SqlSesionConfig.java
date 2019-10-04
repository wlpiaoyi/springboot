package org.wlpiaoyi.dataSource.mybatis.config;


import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.wlpiaoyi.utile.PackageUtile;
import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:mybatis.properties")
@MapperScan(basePackages = {"org.wlpiaoyi.dataSource.dao.impl"}, sqlSessionFactoryRef = "SqlSessionFactory-mybatis", sqlSessionTemplateRef = "SqlSessionTemplate-mybatis")
public class SqlSesionConfig {


    @Value("${org.wlpiaoyi.db.mybatis.mapper-package}")
    private String mapperPackage;

    @Bean(name = "SqlSessionFactory-mybatis")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource){
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(environment);
        for (Class clazz : PackageUtile.getClasses(this.mapperPackage)){
            configuration.addMapper(clazz);
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return sqlSessionFactory;

    }

    @Bean(name = "SqlSessionTemplate-mybatis")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("SqlSessionFactory-mybatis")SqlSessionFactory sqlSessionFactory) {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
        return template;
    }

}
