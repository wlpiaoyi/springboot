package org.wlpiaoyi.dataSource.service;

import org.apache.ibatis.annotations.Param;
import org.wlpiaoyi.dataSource.dao.query.CriteriaQuery;

import java.io.Serializable;
import java.util.List;

public interface BaseBatisService<PK extends Serializable, M> {
    /**
     * 根据id查询查询单条记录
     *
     * @param id
     * @return
     */
    M selectById(PK id);


    /**
     * 根据id删除单条记录
     *
     * @param id
     */
    void deleteById(PK id);

    /**
     * 插入数据，使用目标表所有字段
     *
     * @param target
     */
    void insert(M target);

    /**
     * 根据id修改，使用目标表所有字段
     *
     *
     */
    int updateById(PK id, M target);

    /**
     * 根据动态查询条件计数
     *
     * @param criteriaQuery
     * @return
     */
    Long countByCriteriaQuery(CriteriaQuery criteriaQuery);

    /**
     * 根据动态查询条件查询
     *
     * @param criteriaQuery
     * @return
     */
    List<M> selectByCriteriaQuery(CriteriaQuery criteriaQuery);
}
