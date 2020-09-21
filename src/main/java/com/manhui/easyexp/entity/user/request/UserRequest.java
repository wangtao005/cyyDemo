package com.manhui.easyexp.entity.user.request;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 平台用户
 * @author zls
 * 2020年7月7日
 */
@ApiModel("平台用户")
public class UserRequest {
	 
    
    @ApiModelProperty("头像")
    private String headerUrl;
    
    @NotBlank(message = "登录账号不能为空")
	@ApiModelProperty("登录账号")
    private String account;
    
    @NotBlank(message = "昵称不能为空")
	@ApiModelProperty("昵称")
    private String nickName;
    
	@ApiModelProperty("旧密码")
    private String password1;
	
	@ApiModelProperty("新密码")
    private String password2;

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

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
    
    
}
