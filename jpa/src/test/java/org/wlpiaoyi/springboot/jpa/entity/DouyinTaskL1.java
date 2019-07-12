package org.wlpiaoyi.springboot.jpa.entity;


import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "douyin_task_l1")
public class DouyinTaskL1 implements Serializable {


    // alias
    public static final String END_TAG_HEAD = "end_task";

    public enum StatusEnum {

        E_NOKNOW(-999, "未知状态"),
        E_NO_ACCOUNTS(-3, "没有账号"),
        E_ERRO_STOP(-2, "强制停止任务"),
        E_EXCEPTION(-1, "未完成的任务"),
        E_UNSTART(0, "等待执行"),
        E_DOING(1, "任务进行中"),
        E_COMPLETE(2, "任务完成"),
        ;

        @Getter
        private int code;
        @Getter
        private String name;

        StatusEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public static final DouyinTaskL1.StatusEnum getEnum(int code){
            for (DouyinTaskL1.StatusEnum statusEnum : DouyinTaskL1.StatusEnum.values()) {
                if(statusEnum.getCode() == code)
                    return statusEnum;
            }
            return DouyinTaskL1.StatusEnum.E_NOKNOW;
        }
    }


    /**
     *
     * db_column: id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * 原任务ID
     */
    @Column
    private long originId;
    /**
     * 当前订单ID
     * db_column: order_id
     */
    @Column
    private long orderId;
    /**
     * 原始当前订单ID
     */
    @Column
    private long originOrderId;

    /**
     * 任务类型
     * db_column: task_type
     */
    @Column
    private String taskType;

    /**
     * 评论数量
     * db_column: count
     */
    private int count;

    /**
     * 执行评论数量
     * db_column: count
     */
    private int excutedCount;

    /**
     * 任务执行开始时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginTime;
    /**
     * 任务执行结束时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    /**
     * 任务的状态
     * db_column: status
     */
    @Column
    private int status;
    /**
     * 子任务的层次
     * db_column: depth
     */
    @Column
    private int depth;
    /**
     * 日志
     * db_column: log
     */
    @Column
    private String log;

    @OneToMany(targetEntity = DouyinTaskL2.class, cascade = CascadeType.ALL, mappedBy = "parent") //mappedBy = "one" 表示one是一对多管理的被维护端， 既当添加many时顺带添加一个one
    private List<DouyinTaskL2> childs;

    @OneToMany(targetEntity = DouyinTaskL2.class, cascade = CascadeType.ALL, mappedBy = "originParent") //mappedBy = "one" 表示one是一对多管理的被维护端， 既当添加many时顺带添加一个one
    private List<DouyinTaskL2> originChilds;


    // columns END

    public DouyinTaskL1.StatusEnum getStatusEnum(){
        return DouyinTaskL1.StatusEnum.getEnum(this.status);
    }

    @Override
    public String toString() {
        return this.getClass().toString() + "[id=" + id + "]";
    }


}
