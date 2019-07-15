package org.wlpiaoyi.dataSource.dao.query;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 动态查询条件，内部所有criterions之间采用and结构， 该类不会直接用到，应当通过CriteriaQuery访问
 * 
 * @author wanglong(a)ikamobile.com
 *
 */
@Data
public class Criteria {

    /**
     * 查询条件集
     */
    protected List<Criterion> criterions;

    /**
     * 通过判断是否有criterions条件来判断当前查询条件组是否生效
     * 
     * @return
     */
    public boolean isValid() {
        return criterions != null && !criterions.isEmpty();
    }

    /**
     * 向查询条件组中增加条件
     * 
     * @param condition
     */
    protected void addCriterion(final String condition) {
        if (condition == null) {
            throw new RuntimeException("Value for condition cannot be null");
        }
        if (criterions == null) {
            criterions = new ArrayList<Criterion>();
        }
        criterions.add(new Criterion(condition));
    }

    /**
     * 增加单值查询条件，通过mybatis配置模板直接拼接
     * 
     * @param condition
     * @param value
     * @param property
     */
    protected void addCriterion(final String condition, final Object value, final String property) {
        if (value == null) {
            throw new RuntimeException("Value for " + property + " cannot be null");
        }
        if (criterions == null) {
            criterions = new ArrayList<Criterion>();
        }
        criterions.add(new Criterion(condition, value));
    }

    /**
     * 增加between双值查询条件，通过mybatis配置模板直接拼接
     * 
     * @param condition
     * @param value1
     * @param value2
     * @param property
     */
    protected void addCriterion(final String condition, final Object value1, final Object value2, final String property) {
        if (value1 == null || value2 == null) {
            throw new RuntimeException("Between values for " + property + " cannot be null");
        }
        if (criterions == null) {
            criterions = new ArrayList<Criterion>();
        }
        criterions.add(new Criterion(condition, value1, value2));
    }

    /**
     * 生成条件：fieldName is null
     * 
     * @param fieldName
     * @return
     */
    public Criteria andIsNull(final String fieldName) {
        addCriterion(fieldName + " is null");
        return (Criteria) this;
    }

    /**
     * 生成条件：fieldName is not null
     * 
     * @param fieldName
     * @return
     */
    public Criteria andIsNotNull(final String fieldName) {
        addCriterion(fieldName + " is not null");
        return (Criteria) this;
    }

    /**
     * 生成条件：fieldName = value
     * 
     * @param fieldName
     * @param value
     * @return
     */
    public Criteria andEqualTo(final String fieldName, final Object value) {
        addCriterion(fieldName + " =", value, fieldName);
        return (Criteria) this;
    }

    /**
     * 生成条件：fieldName <> value
     * 
     * @param fieldName
     * @param value
     * @return
     */
    public Criteria andNotEqualTo(final String fieldName, final Object value) {
        addCriterion(fieldName + " <>", value, fieldName);
        return (Criteria) this;
    }

    /**
     * 生成条件：fieldName > value
     * 
     * @param fieldName
     * @param value
     * @return
     */
    public Criteria andGreaterThan(final String fieldName, final Object value) {
        addCriterion(fieldName + " >", value, fieldName);
        return (Criteria) this;
    }

    /**
     * 生成条件：fieldName >= value
     * 
     * @param fieldName
     * @param value
     * @return
     */
    public Criteria andGreaterThanOrEqualTo(final String fieldName, final Object value) {
        addCriterion(fieldName + " >=", value, fieldName);
        return (Criteria) this;
    }

    /**
     * 生成条件：fieldName < value
     * 
     * @param fieldName
     * @param value
     * @return
     */
    public Criteria andLessThan(final String fieldName, final Object value) {
        addCriterion(fieldName + " <", value, fieldName);
        return (Criteria) this;
    }

    /**
     * 生成条件：fieldName <= value
     * 
     * @param fieldName
     * @param value
     * @return
     */
    public Criteria andLessThanOrEqualTo(final String fieldName, final Object value) {
        addCriterion(fieldName + " <=", value, fieldName);
        return (Criteria) this;
    }

    /**
     * 生成条件：fieldName like value
     * 
     * @param fieldName
     * @param value
     * @return
     */
    public Criteria andLike(final String fieldName, final Object value) {
        addCriterion(fieldName + " like", value, fieldName);
        return (Criteria) this;
    }

    /**
     * 生成条件：fieldName not like value
     * 
     * @param fieldName
     * @param value
     * @return
     */
    public Criteria andNotLike(final String fieldName, final Object value) {
        addCriterion(fieldName + " not like", value, fieldName);
        return (Criteria) this;
    }

    /**
     * 
     * 生成条件：fieldName in values
     * 
     * @param fieldName
     * @param values
     * @return
     */
    public Criteria andIn(final String fieldName, final Object... values) {
        if (values != null && values.length != 0) {
            addCriterion(fieldName + " in", toList(values), fieldName);
        }
        return (Criteria) this;
    }

    public List<Object> toList(Object[] values) {
        List<Object> valueList = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            Object param = values[i];
            if (param instanceof Collection<?>) {
            	Collection<?> list = (Collection<?>) param;
                valueList.addAll(list);
            } else if (param instanceof Object[]) {
                Object[] arr = (Object[]) param;
                valueList.addAll(Arrays.asList(arr));
            } else {
                valueList.add(param);
            }
        }
        return valueList;
    }

    /**
     * 生成条件：fieldName not in values
     * 
     * @param fieldName
     * @param values
     * @return
     */
    public Criteria andNotIn(final String fieldName, final Object ... values) {
        if (values != null && values.length != 0) {
            addCriterion(fieldName + " not in", toList(values), fieldName);
        }
        return (Criteria) this;
    }

    /**
     * 生成条件：fieldName between value1 and value2
     * 
     * @param fieldName
     * @param value1
     * @param value2
     * @return
     */
    public Criteria andBetween(final String fieldName, final String value1, final String value2) {
        addCriterion(fieldName + " between", value1, value2, fieldName);
        return (Criteria) this;
    }

    /**
     * 生成条件：fieldName not between value1 and value2
     * 
     * @param fieldName
     * @param value1
     * @param value2
     * @return
     */
    public Criteria andNotBetween(final String fieldName, final String value1, final String value2) {
        addCriterion(fieldName + " not between", value1, value2, fieldName);
        return (Criteria) this;
    }

}
