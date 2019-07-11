package org.wlpiaoyi.springboot.jpa.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wlpiaoyi.springboot.jpa.entity.DouyinTaskL1;
import org.wlpiaoyi.springboot.jpa.repository.DouyinTaskL1Repository;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class DouyinTaskL1Service {


    @Autowired
    private DouyinTaskL1Repository taskL1Repository;

    public List<DouyinTaskL1>  findAll() {
        List<DouyinTaskL1> taskL1s =  taskL1Repository.findAll();
        return taskL1s;
    }

    @Transactional
    public void save(DouyinTaskL1 taskL1){
        this.taskL1Repository.save(taskL1);
    }

}
