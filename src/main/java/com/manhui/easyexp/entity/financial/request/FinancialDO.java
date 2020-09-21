package com.manhui.easyexp.entity.financial.request;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.mics.jpa.entity.BaseEntity;


/**
 * 财务管理
 * @author zls
 * 2020年7月2日
 */
@Entity
@Table(name = "t_financial")
@Where(clause = "bln_del = 0")
public class FinancialDO extends BaseEntity implements Serializable {

    /**
	 * author zls
	 */
	private static final long serialVersionUID = 1L;

	 
    @Column(name = "order_no", columnDefinition = "varchar(225) comment '订单号' ")
    private String orderNo;
    
    @Column(name = "amount", columnDefinition = "decimal(18,2) comment '充值金额' ")
    private BigDecimal amount;
    
    
    @Column(name = "user_id", columnDefinition = "varchar(30) comment '用户ID' ")
    private String userId;
    
    
    @Column(name = "user_name", columnDefinition = "varchar(30) comment '用户名' ")
    private String userName;
    
	
    @Column(name = "type", columnDefinition = "tinyint(2) comment '充值平台 0支付宝 1微信 2银行卡 3其他' ")
    private Byte type;


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


	public Byte getType() {
		return type;
	}


	public void setType(Byte type) {
		this.type = type;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
    
    
    
}

