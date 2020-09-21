package com.manhui.easyexp.entity.vip.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 会员信息
 * @author zls
 * 2020年6月30日
 */
@ApiModel("会员级别信息")
public class VipLevelRequest {
	
	 @NotNull(message = "级别编号不能为空")
	 @ApiModelProperty("级别编号")
	 private Byte level;
	   
	 @NotBlank(message = "等级名不能为空")
	 @ApiModelProperty("等级名")
	 private String levelName;
	 
	 @NotNull(message = "时间不能为空")
	 @ApiModelProperty("时间(天)")
	 private Integer duration;
	 
	 @NotNull(message = "金额不能为空")
	 @ApiModelProperty("金额(元)")
	 private BigDecimal amount;


	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Byte getLevel() {
		return level;
	}

	public void setLevel(Byte level) {
		this.level = level;
	}
	
    
    
}
