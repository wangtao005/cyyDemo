package com.manhui.easyexp.entity.vip.request;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.mics.jpa.entity.BaseEntity;


/**
 * 会员信息
 * @author zls
 * 2020年6月29日
 */
@Entity
@Table(name = "t_vip_info")
@Where(clause = "bln_del = 0")
public class VipInfoDO extends BaseEntity implements Serializable {

    /**
	 * author zls
	 */
	private static final long serialVersionUID = 1L;

	 /**
	  * 用户ID（关联用户表）
	  */
    @Column(name = "user_id", unique = false, columnDefinition = "varchar(30) comment '用户ID' ")
    private String userId;
	
	 /**
     * 登录账号(手机号)
     */
    @Column(name = "account", unique = false, columnDefinition = "varchar(30) comment '登录账号(手机号)' ")
    private String account;

    /**
     * 手机号
     */
    @Column(name = "phone", unique = false, columnDefinition = "varchar(11) comment '手机号' ")
    private String phone;

    /**
     * 姓名
     */
    @Column(name = "user_name", columnDefinition = "varchar(30) comment '姓名' ")
    private String userName;

    /**
     * 会员开始时间
     */
    @Column(name = "start_time", unique = false, columnDefinition = "datetime comment '会员开始时间' ")
    private Date startTime;
    
    /**
     * 会员结束时间
     */
    @Column(name = "end_time", unique = false, columnDefinition = "datetime comment '会员结束时间' ")
    private Date endTime;
    
    @Column(name = "type",  columnDefinition = "int(11) comment '账户类型:0,免费1:付费'")
    private Integer type;
    
    
    

	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}



	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}




    
}

