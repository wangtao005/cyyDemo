package com.manhui.easyexp.entity.vip.request;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("会员等级信息")
public class VipLevelVO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1413060925259547553L;


	@ApiModelProperty("级别ID")
    private String id;
  
    @ApiModelProperty("级别编号")
    private Byte level;
    
    @ApiModelProperty("等级名")
    private String levelName;
    
    @ApiModelProperty("时间")
    private Integer duration;
    
    @ApiModelProperty("金额")
    private BigDecimal amount;
    
    private Byte type;

	public Byte getLevel() {
		return level;
	}

	public void setLevel(Byte level) {
		this.level = level;
	}

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}
    
   

}
