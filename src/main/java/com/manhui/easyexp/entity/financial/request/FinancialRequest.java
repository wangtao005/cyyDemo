package com.manhui.easyexp.entity.financial.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 财务充值流水
 * @author zls
 * 2020年7月2日
 */
@ApiModel("财务充值流水")
public class FinancialRequest {
	 
    @NotBlank(message = "用户ID不能为空")
    @ApiModelProperty("用户ID")
    private String userId;
    
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String userName;
   
    @NotBlank(message = "订单号不能为空")
    @ApiModelProperty("订单号")
    private String orderNo;
    
    @NotBlank(message = "充值金额不能为空")
    @ApiModelProperty("充值金额")
    private BigDecimal amount;
    
    @NotBlank(message = "充值类型不能为空")
    @ApiModelProperty("充值平台 0支付宝 1微信 2银行卡 3其他")
    private Byte type;

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

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}
	
	
    
    
}
