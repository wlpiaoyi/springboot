package org.wlpiaoyi.dataSource.configuration;

import org.apache.http.client.utils.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.wlpiaoyi.dataSource.ApplicationLoader;
import org.wlpiaoyi.dataSource.entity.DouyinTaskL1;
import org.wlpiaoyi.dataSource.entity.MbtDouyinTaskL1;
import org.wlpiaoyi.dataSource.repository.DouyinTaskL1Repository;
import org.wlpiaoyi.dataSource.service.DouyinTaskL1Service;
import org.wlpiaoyi.dataSource.service.MbtDouyinTaskL1Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Configuration
@EnableJpaRepositories(basePackages={"org.wlpiaoyi.dataSource.repository"}, transactionManagerRef = "transactionManager-jpa")
//@EntityScan(basePackages={"org.wlpiaoyi.springboot.jpa.dao.entity"})
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
        taskL1.setLog(DateUtils.formatDate(new Date(), "yyyy/MM/dd HH:mm:ss"));
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
        taskL1.setLog(DateUtils.formatDate(new Date(), "yyyy/MM/dd HH:mm:ss"));
        try{
            ApplicationLoader.getBean(DouyinTaskL1Repository.class).save(taskL1);
            taskL1 = ApplicationLoader.getBean(DouyinTaskL1Repository.class).getOne(taskL1s.get(0).getId());
        }catch (Exception e){e.printStackTrace();}
        System.out.println("repository save=======================>\n" +
                taskL1.getLog()
                + "\n<=======================");


        MbtDouyinTaskL1Service mbtTaskL1Service =  ApplicationLoader.getBean(MbtDouyinTaskL1Service.class);
        List<MbtDouyinTaskL1> mbtDouyinTaskL1s = mbtTaskL1Service.selectByCriteriaQuery(null);
//        taskL1s =  taskL1Service.(null);
//        DouyinTaskL1 taskL1 = ApplicationLoader.getBean(DouyinTaskL1Repository.class).getOne(taskL1s.get(0).getId());
//        System.out.println("get=======================>\n" +
//                taskL1.getLog()
//                + "\n<=======================");
//        taskL1.setLog(DateUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss"));
//        try{
//            taskL1Service.save(taskL1);
//            taskL1 = ApplicationLoader.getBean(DouyinTaskL1Repository.class).getOne(taskL1s.get(0).getId());
//        }catch (Exception e){e.printStackTrace();}
//        System.out.println("service save=======================>\n" +
//                taskL1.getLog()
//                + "\n<=======================");
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        taskL1.setLog(DateUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss"));
//        try{
//            ApplicationLoader.getBean(DouyinTaskL1Repository.class).save(taskL1);
//            taskL1 = ApplicationLoader.getBean(DouyinTaskL1Repository.class).getOne(taskL1s.get(0).getId());
//        }catch (Exception e){e.printStackTrace();}
//        System.out.println("repository save=======================>\n" +
//                taskL1.getLog()
//                + "\n<=======================");

    }

    @After
    public void tearDown() throws Exception {

    }
}
