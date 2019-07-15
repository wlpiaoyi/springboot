package org.wlpiaoyi.dataSource.dao.query;

import lombok.Data;

import java.util.List;

/**
 * 动态查询条件单元，一个对象表示一个判断分支。如a=1
 * 
 * @author wanglong(a)ikamobile.com
 *
 */
@Data
public class Criterion {

    /**
     * 查询条件，如fieldName =
     */
    private String condition;

    /**
     * 查询条件对应值
     */
    private Object value = true;

    /**
     * 查询条件对应第二值，如between中的第二个值
     */
    private Object secondValue;

    /**
     * 是否是无值查询条件
     */
    private boolean noValue;

    /**
     * 是否是单值查询条件
     */
    private boolean singleValue;

    /**
     * 是否是between查询条件
     */
    private boolean betweenValue;

    /**
     * 是否是多值查询条件
     */
    private boolean listValue;

    private String typeHandler;

    /**
     * 基本构造方法
     * 
     * @param condition
     */
    public Criterion(String condition) {
        this.condition = condition;
        this.noValue = true;
    }

    public Criterion(String condition, Object value, String typeHandler) {
        this.condition = condition;
        this.value = value;
        this.typeHandler = typeHandler;
        if (value instanceof List<?>) {
            this.listValue = true;
        } else {
            this.singleValue = true;
        }
    }

    public Criterion(String condition, Object value) {
        this(condition, value, null);
    }

    public Criterion(String condition, Object value, Object secondValue, String typeHandler) {
        this.condition = condition;
        this.value = value;
        this.secondValue = secondValue;
        this.typeHandler = typeHandler;
        this.betweenValue = true;
    }

    public Criterion(String condition, Object value, Object secondValue) {
        this(condition, value, secondValue, null);
    }
}