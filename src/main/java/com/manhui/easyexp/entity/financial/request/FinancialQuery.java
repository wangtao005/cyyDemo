package com.manhui.easyexp.entity.financial.request;

import org.mics.core.page.PageQuery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("财务流水搜索")
public class FinancialQuery extends PageQuery {

	@ApiModelProperty("开始时间")
	private String startDate;
	
	@ApiModelProperty("结束时间")
	private String endDate;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	
}
