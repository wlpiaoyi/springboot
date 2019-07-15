package org.wlpiaoyi.dataSource.dao.model.param;

import org.wlpiaoyi.dataSource.dao.query.Criteria;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过动态查询条件更新记录
 * 
 * @author wanglong(a)ikamobile.com
 *
 */
@Data
public class UpdateByCriteriaQueryParam {

    /**
     * 更新where条件
     */
    protected List<Criteria> criterias = new ArrayList<Criteria>();
    /**
     * 修改到目标模型
     */
    protected Object target;

    public UpdateByCriteriaQueryParam() {}

    public UpdateByCriteriaQueryParam(Object target) {
        this.target = target;
    }

    public void or(Criteria criteria) {
        criterias.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        criterias.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (criterias.size() == 0) {
            criterias.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        return new Criteria();
    }

    public void clear() {
        criterias.clear();
    }

}