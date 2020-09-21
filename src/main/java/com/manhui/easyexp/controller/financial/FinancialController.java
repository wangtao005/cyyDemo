package com.manhui.easyexp.controller.financial;

import javax.annotation.Resource;

import org.mics.core.response.OneDataResponse;
import org.mics.core.response.SimpleResponse;
import org.mics.token.annotation.IgnoreToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manhui.easyexp.entity.financial.request.FinancialQuery;
import com.manhui.easyexp.entity.financial.request.FinancialVO;
import com.manhui.easyexp.service.financial.IFinancialService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 财务充值流水
 * @author zls
 * 2020年7月2日
 */
@RestController
@RequestMapping("/platform/financial")
@Api(tags = "[平台端]-财务管理")
public class FinancialController {
	
	
	@Resource
	private IFinancialService financialService;
	
	@IgnoreToken
	@PostMapping("/getListByPage")
    @ApiOperation(value = "分页列表")
	public SimpleResponse getListByPage(@RequestBody FinancialQuery pageQuery) {
		return financialService.getListByPage(pageQuery);
	}
	
	@IgnoreToken
	@PostMapping("/getInfo")
    @ApiOperation(value = "信息")
	public SimpleResponse getInfo(String id) {
		return new OneDataResponse<FinancialVO>(financialService.getInfo(id));
		
	}
	
}
