package org.wlpiaoyi.springboot.jpa.configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.wlpiaoyi.springboot.jpa.ApplicationLoader;
import org.wlpiaoyi.springboot.jpa.entity.DouyinTaskL1;
import org.wlpiaoyi.springboot.jpa.repository.DouyinTaskL1Repository;
import org.wlpiaoyi.springboot.jpa.service.DouyinTaskL1Service;
import org.wlpiaoyi.utile.DateUtile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Configuration
@EnableJpaRepositories(basePackages={"org.wlpiaoyi.springboot.jpa.repository"}, transactionManagerRef = "transactionManager-jpa")
//@EntityScan(basePackages={"org.wlpiaoyi.springboot.jpa.entity"})
public class DataSourceConfigTest {

    @Before
    public void setUp() throws Exception {}

    @Test
    public void test() throws IOException {
        String[] args = new String[]{};
        ApplicationLoader.load(args);

        DouyinTaskL1Service taskL1Service =  ApplicationLoader.getBean(DouyinTaskL1Service.class);
        List<DouyinTaskL1> taskL1s =  taskL1Service.findAll();
        DouyinTaskL1 taskL1 = ApplicationLoader.getBean(DouyinTaskL1Repository.class).getOne(taskL1s.get(0).getId());
        System.out.println("get=======================>\n" +
                taskL1.getLog()
                + "\n<=======================");
        taskL1.setLog(DateUtile.format(new Date(), "yyyy/MM/dd HH:mm:ss"));
        try{
            taskL1Service.save(taskL1);
            taskL1 = ApplicationLoader.getBean(DouyinTaskL1Repository.class).getOne(taskL1s.get(0).getId());
        }catch (Exception e){e.printStackTrace();}
        System.out.println("service save=======================>\n" +
                taskL1.getLog()
                + "\n<=======================");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        taskL1.setLog(DateUtile.format(new Date(), "yyyy/MM/dd HH:mm:ss"));
        try{
            ApplicationLoader.getBean(DouyinTaskL1Repository.class).save(taskL1);
            taskL1 = ApplicationLoader.getBean(DouyinTaskL1Repository.class).getOne(taskL1s.get(0).getId());
        }catch (Exception e){e.printStackTrace();}
        System.out.println("repository save=======================>\n" +
                taskL1.getLog()
                + "\n<=======================");

    }

    @After
    public void tearDown() throws Exception {

    }
}
