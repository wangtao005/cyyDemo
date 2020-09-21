package com.manhui.easyexp.entity.vip.request;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("会员信息")
public class VipInfoVO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1413060925259547553L;

    @ApiModelProperty("编号ID")
    private String id;
	
    @ApiModelProperty("账号")
    private String account;

    
    @ApiModelProperty("联系电话")
    private String phone;
    
    @ApiModelProperty("账户类型:0,免费1:付费")
    private Integer type;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("用户名")
    private String userName;
    
    @ApiModelProperty("开始时间")
    private Date startTime;
    
    @ApiModelProperty("到期时间")
    private Date endTime;
    

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
