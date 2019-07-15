package org.wlpiaoyi.dataSource.dao;


import org.wlpiaoyi.dataSource.dao.model.param.DeleteByCriteriaQueryParam;
import org.wlpiaoyi.dataSource.dao.model.param.UpdateByCriteriaQueryParam;
import org.wlpiaoyi.dataSource.dao.query.CriteriaQuery;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * mybatis数据库访问接口基类。具体业务dao接口继承该接口，结合自动生成的mybatis配置文件，完成绝大多数功能。 主要封装动态查询、动态更新、动态插入、分页查询。
 * 
 * @author wanglong(a)ikamobile.com
 *
 */
public interface BaseBatisDao<PK extends Serializable, M> {

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
     * 根据动态查询条件删除数据
     * 
     * @param deleteByCriteriaQueryParam
     */
    void deleteByCriteriaQuery(DeleteByCriteriaQueryParam deleteByCriteriaQueryParam);

    /**
     * 插入数据，使用目标表所有字段
     * 
     * @param target
     */
    void insert(M target);

    /**
     * 插入数据，使用target所有非null字段
     * 
     * @param target
     */
    void insertSelective(M target);

    /**
     * 根据动态查询条件修改，使用目标表所有字段
     * 
     * @param updateByCriteriaQueryParam
     * @return
     */
    int updateByCriteriaQuery(UpdateByCriteriaQueryParam updateByCriteriaQueryParam);

    /**
     * 根据动态查询条件修改，使用target所有非空条件字段
     * 
     * @param updateByCriteriaQueryParam
     * @return
     */
    int updateByCriteriaQuerySelective(UpdateByCriteriaQueryParam updateByCriteriaQueryParam);

    /**
     * 根据id修改，使用target所有非空条件字段
     * 
     * @return
     */
    int updateByIdSelective(@Param("id") PK id, @Param("target") M target);

    /**
     * 根据id修改，使用target所有非空条件字段
     *
     * @return
     */
    int updateByIdSelective(Map<String, Object> params);


    /**
     * 根据id修改，使用目标表所有字段
     *
     *
     */
    int updateById(@Param("id") PK id, @Param("target") M target);
    
    
    /**
     * 根据id修改，使用目标表所有字段
     * 
     */
    int updateById(Map<String, Object> params);

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
