package com.manhui.easyexp.entity.cusotmization.request;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.mics.jpa.entity.BaseEntity;


/**
 * 客户定制化信息
 * @author zls
 * 2020年7月1日
 */
@Entity
@Table(name = "t_cusotmization")
@Where(clause = "bln_del = 0")
public class CusotmizationDO extends BaseEntity implements Serializable {

    /**
	 * author zls
	 */
	private static final long serialVersionUID = 1L;

	 
    @Column(name = "user_name", columnDefinition = "varchar(30) comment '联系人' ")
    private String userName;
	
    @Column(name = "user_phone", columnDefinition = "varchar(25) comment '联系电话' ")
    private String userPhone;
    
    @Column(name = "user_email", columnDefinition = "varchar(50) comment '联系邮件' ")
    private String userEmail;
    
    @Column(name = "remark", columnDefinition = "varchar(1000) comment '需求描述' ")
    private String remark;
    
    @Column(name = "account", columnDefinition = "varchar(30) comment '登录账号(手机号)'")
    private String account;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
    
    
    
}

