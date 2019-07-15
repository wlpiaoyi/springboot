/*
 * Powered By wlpiaoyi
 */
package org.wlpiaoyi.dataSource.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;

/**
 * 
 *  Database Model
 * Generated automaticly
 * @version 1.0
 * @since 1.0
 *
 */
@Data
@SuppressWarnings("serial")
public class MbtDouyinTaskL1 implements Serializable {
    
    // alias
    public static final String TABLE = "douyin_task_l1";
    // column name of id
    public static final String FIELD_ID = "id";
    // column name of originId
    public static final String FIELD_ORIGIN_ID = "origin_id";
    // column name of orderId
    public static final String FIELD_ORDER_ID = "order_id";
    // column name of originOrderId
    public static final String FIELD_ORIGIN_ORDER_ID = "origin_order_id";
    // column name of taskType
    public static final String FIELD_TASK_TYPE = "task_type";
    // column name of beginTime
    public static final String FIELD_BEGIN_TIME = "begin_time";
    // column name of endTime
    public static final String FIELD_END_TIME = "end_time";
    // column name of status
    public static final String FIELD_STATUS = "status";
    // column name of depth
    public static final String FIELD_DEPTH = "depth";
    // column name of log
    public static final String FIELD_LOG = "log";
    // column name of count
    public static final String FIELD_COUNT = "count";
    // column name of excutedCount
    public static final String FIELD_EXCUTED_COUNT = "excuted_count";

    // columns START
    /**
     * 
     * db_column: id 
     */    
    private long id;
    /**
     * 
     * db_column: origin_id 
     */    
    private long originId;
    /**
     * 
     * db_column: order_id 
     */    
    private long orderId;
    /**
     * 原始订单ID
     * db_column: origin_order_id 
     */    
    private long originOrderId;
    /**
     * 
     * db_column: task_type 
     */    
    private String taskType;
    /**
     * 
     * db_column: begin_time 
     */    
    private Date beginTime;
    /**
     * 
     * db_column: end_time 
     */    
    private Date endTime;
    /**
     * 
     * db_column: status 
     */    
    private int status;
    /**
     * 
     * db_column: depth 
     */    
    private int depth;
    /**
     * 
     * db_column: log 
     */    
    private String log;
    /**
     * 
     * db_column: count 
     */    
    private int count;
    /**
     * 
     * db_column: excuted_count 
     */    
    private int excutedCount;
    // columns END


}
