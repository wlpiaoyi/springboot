package org.wlpiaoyi.dataSource.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
@PropertySource(value = "classpath:jpa.properties")
public class JPAManagerConfig {


    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String HIBERNATE_DDL_AUTO = "hibernate.ddl-auto";
    private static final String HIBERNATE_ENABLE_LAZY_LOAD_NO_TRANS = "hibernate.enable_lazy_load_no_trans";
    private static final String HIBERNATE_PHYSICAL_NAME_STRATEGY = "hibernate.physical_naming_strategy";


    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String dialect;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String hbm2ddlAuto;

    @Value("${spring.jpa.show-sql}")
    private Boolean showSql;

    @Value("${spring.jpa.format-sql}")
    private Boolean formatSql;

    @Value("${spring.jpa.enable-lazy}")
    private Boolean enabelLazy;

    @Value("${spring.jpa.physical-naming-strategy}")
    private String physicalNamingStrategy;

    @Value("${spring.jpa.entity.packages-to-scan}")
    private String packagesToScan;

    @Bean
    PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        String[] args = this.packagesToScan.split(",");
        //实体存放的package位置
        entityManagerFactoryBean.setPackagesToScan(args);
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);

        Properties jpaProperties = new Properties();
        jpaProperties.put(HIBERNATE_DIALECT, dialect);
        jpaProperties.put(HIBERNATE_DDL_AUTO, hbm2ddlAuto);
        jpaProperties.put(HIBERNATE_SHOW_SQL, this.showSql);
        jpaProperties.put(HIBERNATE_FORMAT_SQL, this.formatSql);
        jpaProperties.put(HIBERNATE_ENABLE_LAZY_LOAD_NO_TRANS, this.enabelLazy);
        jpaProperties.put(HIBERNATE_PHYSICAL_NAME_STRATEGY, this.physicalNamingStrategy);

        jpaProperties.put("use_query_cache", "true");
        jpaProperties.put("use_second_level_cache", "false");
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        return entityManagerFactoryBean;
    }
}
