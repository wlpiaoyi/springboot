/*
 * Powered By wlpiaoyi
 */
package org.wlpiaoyi.dataSource.mybatis.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.wlpiaoyi.dataSource.mybatis.entity.MbtDouyinTaskL1;

/**
 * 
 * mybatis数据库访问接口基类。具体业务dao接口继承该接口，结合自动生成的mybatis配置文件，完成绝大多数功能。 主要封装动态查询、动态更新、动态插入、分页查询。
 * 
 * Generated automaticly
 * @version 1.0
 * @since 1.0
 *
 */
@Mapper
public interface MbtDouyinTaskL1Dao {

    @Select("select * from douyin_task_l1 where id = #{id}")
    MbtDouyinTaskL1 findById(@Param("id") long id);

}
