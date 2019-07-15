package org.wlpiaoyi.dataSource.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.wlpiaoyi.dataSource.repository.DouyinTaskL1Repository;
import org.wlpiaoyi.dataSource.entity.DouyinTaskL1;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class DouyinTaskL1Service{



    @Autowired
    private EntityManager em;

    @Autowired
    private DouyinTaskL1Repository taskL1Repository;

    public List<DouyinTaskL1>  findAll() {
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching();
        List<DouyinTaskL1> taskL1s =  taskL1Repository.findAll();
        return taskL1s;
    }

    @Transactional
    public void save(DouyinTaskL1 taskL1){
        this.taskL1Repository.save(taskL1);
    }

}
