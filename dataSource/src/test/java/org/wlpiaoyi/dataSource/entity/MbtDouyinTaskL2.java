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
public class MbtDouyinTaskL2 implements Serializable {
    
    // alias
    public static final String TABLE = "douyin_task_l2";
    // column name of id
    public static final String FIELD_ID = "id";
    // column name of createDate
    public static final String FIELD_CREATE_DATE = "create_date";
    // column name of log
    public static final String FIELD_LOG = "log";
    // column name of parentId
    public static final String FIELD_PARENT_ID = "parent_id";
    // column name of status
    public static final String FIELD_STATUS = "status";
    // column name of accountId
    public static final String FIELD_ACCOUNT_ID = "account_id";
    // column name of taskCount
    public static final String FIELD_TASK_COUNT = "task_count";
    // column name of originParentId
    public static final String FIELD_ORIGIN_PARENT_ID = "origin_parent_id";
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
     * db_column: create_date 
     */    
    private Date createDate;
    /**
     * 
     * db_column: log 
     */    
    private String log;
    /**
     * 上级任务ID
     * db_column: parent_id 
     */    
    private long parentId;
    /**
     *  -1:任务失败,0:无效任务, 1:等待执行, 2:正在执行, 3:执行完成
     * db_column: status 
     */    
    private short status;
    /**
     * 
     * db_column: account_id 
     */    
    private long accountId;
    /**
     * 任务数量
     * db_column: task_count 
     */    
    private int taskCount;
    /**
     * 原任务ID
     * db_column: origin_parent_id 
     */    
    private long originParentId;
    /**
     * 执行数量
     * db_column: excuted_count 
     */    
    private int excutedCount;
    // columns END


}
