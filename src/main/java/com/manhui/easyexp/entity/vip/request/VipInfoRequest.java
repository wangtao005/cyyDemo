package com.manhui.easyexp.entity.vip.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 会员信息
 * @author zls
 * 2020年6月30日
 */
@ApiModel("会员信息")
public class VipInfoRequest {
	 
    @NotBlank(message = "用户ID不能为空")
    @ApiModelProperty("用户ID")
    private String userId;
   
    @ApiModelProperty("用户账号")
    private String account;

    @ApiModelProperty("用户手机号")
    private String phone;

    @ApiModelProperty("用户名")
    private String userName;
    
    @NotNull(message = "会员级别编号不能为空")
    @ApiModelProperty("会员级别编号")
    private Byte level;
    
    @ApiModelProperty(name="0免费用户，1付费用户",hidden = true)
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Byte getLevel() {
		return level;
	}

	public void setLevel(Byte level) {
		this.level = level;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
    
	
	
    
    
}
