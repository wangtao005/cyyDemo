package com.manhui.easyexp.entity.user.request;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("平台用户")
public class UserVO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1413060925259547553L;

	@ApiModelProperty("头像")
    private String headerUrl;
    
	@ApiModelProperty("登录账号")
    private String account;
    
	@ApiModelProperty("昵称")
    private String nickName;
	
    @ApiModelProperty("创建时间")
    private Date createDate;

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
}
