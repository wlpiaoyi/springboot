package org.wlpiaoyi.dataSource.dao.model.param;

import org.wlpiaoyi.dataSource.dao.query.Criteria;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据动态查询条件删除数据
 * 
 * @author wanglong(a)ikamobile.com
 *
 */
@Data
public class DeleteByCriteriaQueryParam {

    /**
     * 动态查询目标数据的查询条件
     */
    protected List<Criteria> criterias = new ArrayList<Criteria>();

    public DeleteByCriteriaQueryParam() {}

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