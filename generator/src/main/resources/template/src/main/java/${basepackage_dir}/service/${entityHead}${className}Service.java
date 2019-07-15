<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>

package ${basepackage}.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ${basepackage}.${daopackage}.${entityHead}${className}Dao;
import ${basepackage}.${entitypackage}.${entityHead}${className};
import ${basepackage}.dao.query.CriteriaQuery;

import lombok.Getter;

/**
 *
 * ${table.remarks} Service
 * Generated automaticly
 * @version 1.0
 * @since 1.0
 *
 */
@Service
public class ${entityHead}${className}Service implements BaseBatisService<Long, ${entityHead}${className}>{

    @Resource
    private ${entityHead}${className}Dao ${classNameLower}Dao;


    /**
     * 根据id查询查询单条记录
     *
     * @param id
     * @return
     */
    public ${entityHead}${className} selectById(Long id){
        return this.${classNameLower}Dao.selectById(id);
    }

    /**
     * 插入数据，使用目标表所有字段
     *
     * @param target
     */
    @Override
    @Transactional
    public void insert(${entityHead}${className} target){
        this.${classNameLower}Dao.insert(target);
    }

    @Override
    @Transactional
    public int updateById(Long id, ${entityHead}${className} target) {
        return this.${classNameLower}Dao.updateById(id, target);
    }

    /**
     * 根据id删除单条记录
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(Long id){
        this.${classNameLower}Dao.deleteById(id);
    }

    /**
     * 根据动态查询条件计数
     *
     * @param criteriaQuery
     * @return
     */
    @Override
    public Long countByCriteriaQuery(CriteriaQuery criteriaQuery) {
        return this.${classNameLower}Dao.countByCriteriaQuery(criteriaQuery);
    }
    /**
     * 根据动态查询条件查询
     *
     * @param criteriaQuery
     * @return
     */
    @Override
    public List<${entityHead}${className}> selectByCriteriaQuery(CriteriaQuery criteriaQuery) {
        return this.${classNameLower}Dao.selectByCriteriaQuery(criteriaQuery);
    }

}
