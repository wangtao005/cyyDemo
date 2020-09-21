package com.manhui.easyexp.entity.financial.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("财务流程")
public class FinancialVO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1413060925259547553L;

    @ApiModelProperty("编号ID")
    private String id;
	
    @ApiModelProperty("订单号 ")
    private String orderNo;
    
    @ApiModelProperty("充值金额")
    private BigDecimal amount;
    
    
    @ApiModelProperty("用户ID")
    private String userId;
    
    
    @ApiModelProperty("用户名")
    private String userName;
    
	
    @ApiModelProperty("充值平台 0支付宝 1微信 2银行卡 3其他")
    private Byte type;
	
    @ApiModelProperty("创建时间")
    private Date createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
   
    
}
