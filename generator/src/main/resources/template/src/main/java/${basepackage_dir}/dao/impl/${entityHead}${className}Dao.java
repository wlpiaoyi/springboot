<#include "/java_copyright.include">
<#assign className = table.className>   
package ${basepackage}.${daopackage};

import java.util.List;
import java.util.Map;

import ${basepackage}.dao.BaseBatisDao;
import ${basepackage}.dao.model.param.DeleteByCriteriaQueryParam;
import ${basepackage}.dao.model.param.UpdateByCriteriaQueryParam;
import ${basepackage}.dao.query.CriteriaQuery;
import ${basepackage}.${entitypackage}.${entityHead}${className};

/**
 * 
 * ${table.remarks} mybatis数据库访问接口基类。具体业务dao接口继承该接口，结合自动生成的mybatis配置文件，完成绝大多数功能。 主要封装动态查询、动态更新、动态插入、分页查询。
 * 
 * Generated automaticly
 * @version 1.0
 * @since 1.0
 *
 */
public interface ${entityHead}${className}Dao extends BaseBatisDao<Long, ${entityHead}${className}> {

}
