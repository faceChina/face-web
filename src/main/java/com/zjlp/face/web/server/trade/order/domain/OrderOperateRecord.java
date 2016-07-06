package com.zjlp.face.web.server.trade.order.domain;


import com.zjlp.face.web.constants.Constants;

import java.io.Serializable;
import java.util.Date;


public class OrderOperateRecord implements Serializable {
	
	private static final long serialVersionUID = -2439339743455593152L;

    //todo: 这里可以直接将operateType和operateDesc合并成一个enum类型的type字段
	//1.待付款 2已付款 3 发货 4 收货 5取消 6删除 7超时关闭 10交易成功
	/** 待付款 */
	public static final Integer create = 1;
	/** 已付款 */
	public static final Integer pay = 2;
	/** 发货 */
	public static final Integer send = 3;
	/** 收货 */
	public static final Integer recive = 4;
	/** 取消 */
	public static final Integer cancel = 5;
	/** 删除 */
	public static final Integer delete = 6;
	/** 超时关闭 */
	public static final Integer timeout = 7;
	/** 调整价格 */
	public static final Integer adjust = 8;
	/** 交易成功 */
	public static final Integer success = 10;

	private Long id;

    private String orderNo;

    private Long userId;

    private String userName;

    private Integer operationType;

    private String operationDesc;

    private Date createTime;

    public OrderOperateRecord () {

    }

    public OrderOperateRecord (String orderNo, Long userId,Integer operationType ,Integer operationDesc ,Date createTime) {
        setOrderNo(orderNo);
        setUserId(userId);
        setOperationType(operationType);
        if (Constants.BOOKORDER_STATUS_WAIT.equals(operationDesc)) {
            setOperationDesc("新增预约订单");
        } else if (Constants.BOOKORDER_STATUS_CONFIRM.equals(operationDesc)) {
            setOperationDesc("预约订单确认订单");
        } else if (Constants.BOOKORDER_STATUS_CANCEL.equals(operationDesc)) {
            setOperationDesc("预约订单取消订单");
        } else if (Constants.BOOKORDER_STATUS_REFUSE.equals(operationDesc)) {
            setOperationDesc("预约订单拒绝订单");
        }
        setCreateTime(createTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public String getOperationDesc() {
        return operationDesc;
    }

    public void setOperationDesc(String operationDesc) {
        this.operationDesc = operationDesc == null ? null : operationDesc.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}