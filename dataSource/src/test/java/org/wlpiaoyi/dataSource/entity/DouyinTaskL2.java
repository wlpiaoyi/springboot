package org.wlpiaoyi.dataSource.entity;


import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.persistence.*;

/**
 *
 *  Database Model
 * Generated automaticly
 * @version 1.0
 * @since 1.0
 *
 */
@Data

@Entity
@Table(name = "douyin_task_l2")
@SuppressWarnings("serial")
public class DouyinTaskL2 implements Serializable {

    /**
     *
     * db_column: id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     *
     * db_column: create_date
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    /**
     *
     * db_column: log
     */
    @Column
    private String log;
    /**
     * 上级任务ID
     * db_column: parent_id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private DouyinTaskL1 parent;
    /**
     * 原任务ID
     * db_column: origin_parent_id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_parent_id")
    private DouyinTaskL1 originParent;

    /**
     *  -1:任务失败,0:无效任务, 1:等待执行, 2:正在执行, 3:执行完成
     * db_column: status
     */
    @Column
    private short status;
    /**
     *
     * db_column: account_id
     */
    @Column
    private long accountId;
    /**
     * 任务数量
     * db_column: task_count
     */
    @Column
    private int taskCount;
    /**
     * 执行数量
     * db_column: excuted_count
     */
    @Column
    private int excutedCount;

    @Override
    public String toString() {
        return this.getClass().toString() + "[id=" + id + "]";
    }
}
