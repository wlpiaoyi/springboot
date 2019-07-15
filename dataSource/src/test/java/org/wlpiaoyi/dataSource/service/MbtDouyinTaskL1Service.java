/*
 * Powered By wlpiaoyi
 */

package org.wlpiaoyi.dataSource.service;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wlpiaoyi.dataSource.dao.impl.MbtDouyinTaskL1Dao;
import org.wlpiaoyi.dataSource.entity.MbtDouyinTaskL1;
import org.wlpiaoyi.dataSource.dao.query.CriteriaQuery;

import lombok.Getter;

/**
 *
 *  Service
 * Generated automaticly
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class MbtDouyinTaskL1Service implements BaseBatisService<Long, MbtDouyinTaskL1>{

    @Resource
    private MbtDouyinTaskL1Dao douyinTaskL1Dao;


    /**
     * 根据id查询查询单条记录
     *
     * @param id
     * @return
     */
    public MbtDouyinTaskL1 selectById(Long id){
        return this.douyinTaskL1Dao.selectById(id);
    }

    /**
     * 插入数据，使用目标表所有字段
     *
     * @param target
     */
    @Override
    @Transactional
    public void insert(MbtDouyinTaskL1 target){
        this.douyinTaskL1Dao.insert(target);
    }

    @Override
    @Transactional
    public int updateById(Long id, MbtDouyinTaskL1 target) {
        return this.douyinTaskL1Dao.updateById(id, target);
    }

    /**
     * 根据id删除单条记录
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(Long id){
        this.douyinTaskL1Dao.deleteById(id);
    }

    /**
     * 根据动态查询条件计数
     *
     * @param criteriaQuery
     * @return
     */
    @Override
    public Long countByCriteriaQuery(CriteriaQuery criteriaQuery) {
        return this.douyinTaskL1Dao.countByCriteriaQuery(criteriaQuery);
    }
    /**
     * 根据动态查询条件查询
     *
     * @param criteriaQuery
     * @return
     */
    @Override
    public List<MbtDouyinTaskL1> selectByCriteriaQuery(CriteriaQuery criteriaQuery) {
        return this.douyinTaskL1Dao.selectByCriteriaQuery(criteriaQuery);
    }

}
