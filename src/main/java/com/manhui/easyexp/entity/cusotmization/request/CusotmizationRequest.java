package com.manhui.easyexp.entity.cusotmization.request;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 个性化定制
 * @author zls
 * 2020年7月1日
 */
@ApiModel("个性化定制")
public class CusotmizationRequest {
	 

    @NotBlank(message = "联系人不能为空")
    @ApiModelProperty("联系人")
    private String userName;
	
    @NotBlank(message = "联系电话不能为空")
    @ApiModelProperty("联系电话")
    private String userPhone;
    
    @ApiModelProperty("联系邮件")
    private String userEmail;
    
    @NotBlank(message = "需求描述不能为空")
    @ApiModelProperty("需求描述")
    private String remark;
    
    @ApiModelProperty("登录账号(手机号)")
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
