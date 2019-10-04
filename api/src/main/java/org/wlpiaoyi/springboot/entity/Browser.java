package org.wlpiaoyi.springboot.entity;

import lombok.Data;

@Data
public class Browser {
    /**
     * 订单号
     */
    private String dillCode;
    /**
     * 订单生成时间
     */
    private String dillDate;
    /**
     * 订单发货时间
     */
    private String outDate;
    /**
     * 产品图片
     */
    private String imgurl;
    /**
     * 买家ID
     */
    private String userId;
    /**
     * 买家名称
     */
    private String userName;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 购买数量
     */
    private String productNum;
    /**
     * 订单状态
     */
    private String statusName;
    /**
     * 总价格
     */
    private String totalPrice;
    /**
     * 支付价格
     */
    private String payPrice;

}
