package com.manhui.easyexp.entity.cusotmization.request;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 个性化定制
 * @author zls
 * 2020年7月1日
 */
@ApiModel("个性化定制")
public class CusotmizationVO {
	
	@ApiModelProperty("编号ID")
	private String id;

	@ApiModelProperty("联系人 ")
	private String userName;

	@ApiModelProperty("联系电话")
	private String userPhone;

	@ApiModelProperty("联系邮件 ")
	private String userEmail;

	@ApiModelProperty("需求描述 ")
	private String remark;

	@ApiModelProperty("登录账号(手机号)")
	private String account;
	
	@ApiModelProperty("创建时间")
	private Date createDate;

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
