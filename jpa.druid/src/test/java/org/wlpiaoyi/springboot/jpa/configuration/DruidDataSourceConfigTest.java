package org.wlpiaoyi.springboot.jpa.configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.wlpiaoyi.springboot.jpa.ApplicationLoader;
import org.wlpiaoyi.springboot.jpa.entity.DouyinTaskL1;
import org.wlpiaoyi.springboot.jpa.repository.DouyinTaskL1Repository;
import org.wlpiaoyi.springboot.jpa.service.DouyinTaskL1Service;

import java.io.IOException;
import java.util.List;

@Configuration
@PropertySource(value = "classpath:druid-data-source.properties")
//@EnableJpaRepositories(basePackages={"org.wlpiaoyi.springboot.jpa"}, transactionManagerRef = "transactionManager")
//@EntityScan(basePackages={"org.wlpiaoyi.springboot.jpa"})
public class DruidDataSourceConfigTest extends DruidDataSourceConfig {

    @Before
    public void setUp() throws Exception {}

    @Test
    public void test() throws IOException {
        String[] args = new String[]{};
        ApplicationLoader.load(args);

        DouyinTaskL1Service taskL1Service =  ApplicationLoader.getBean(DouyinTaskL1Service.class);
        List<DouyinTaskL1> taskL1s =  taskL1Service.findAll();
        taskL1s.get(0).setLog("========>");
        ApplicationLoader.getBean(DouyinTaskL1Repository.class).save(taskL1s.get(0));
        DouyinTaskL1 taskL1 = ApplicationLoader.getBean(DouyinTaskL1Repository.class).getOne(taskL1s.get(0).getId());
        System.out.println();

//        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        }
    }

    @After
    public void tearDown() throws Exception {

    }
}
