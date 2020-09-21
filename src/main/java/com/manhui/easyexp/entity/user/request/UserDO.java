package com.manhui.easyexp.entity.user.request;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.mics.jpa.entity.BaseEntity;


/**
 * 后台用户
 * @author zls
 * 2020年7月7日
 */
@Entity
@Table(name = "t_user")
@Where(clause = "bln_del = 0")
public class UserDO extends BaseEntity implements Serializable {
    /**
	 * author zls
	 */
	private static final long serialVersionUID = 1L;
    @Column(name = "header_url", columnDefinition = "varchar(500) comment '头像' ")
    private String headerUrl;
    
    @Column(name = "account", columnDefinition = "varchar(50) comment '登录账号' ")
    private String account;
    
    @Column(name = "nick_name", columnDefinition = "varchar(50) comment '昵称' ")
    private String nickName;
    
    @Column(name = "password", columnDefinition = "varchar(256) comment '密码' ")
    private String password;
    
    @Column(name = "salt", columnDefinition = "varchar(256) comment '盐值' ")
    private String salt;
    
    @Column(name = "last_login_time", columnDefinition = "datetime comment '最后登录时间' ")
    private Date lastLoginTime;
    
    

	public String getHeaderUrl() {
		return headerUrl;
	}

	public void setHeaderUrl(String headerUrl) {
		this.headerUrl = headerUrl;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
    
    
}

