package com.manhui.easyexp.controller.customization;

import javax.annotation.Resource;

import org.mics.core.response.OneDataResponse;
import org.mics.core.response.SimpleResponse;
import org.mics.token.annotation.IgnoreToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manhui.easyexp.entity.cusotmization.request.CusotmizationQuery;
import com.manhui.easyexp.entity.cusotmization.request.CusotmizationVO;
import com.manhui.easyexp.service.cusotmization.ICusotmizationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 个性化定制
 * @author zls
 * 2020年6月30日
 */
@RestController
@RequestMapping("/platform/cusotmization")
@Api(tags = "[平台端]-个性化定制")
public class CusotmizationController {
	
	
	@Resource
	private ICusotmizationService cusotmizationService;
	
	@IgnoreToken
	@PostMapping("/getListByPage")
    @ApiOperation(value = "分页列表")
	public SimpleResponse getListByPage(@RequestBody CusotmizationQuery pageQuery) {
		return cusotmizationService.getListByPage(pageQuery);
	}
	
	@IgnoreToken
	@PostMapping("/getInfo")
    @ApiOperation(value = "定制信息")
	public SimpleResponse getInfo(String id) {
		return new OneDataResponse<CusotmizationVO>(cusotmizationService.getInfo(id));
		
	}
	/*
	 * @IgnoreToken
	 * 
	 * @PostMapping("/save")
	 * 
	 * @ApiOperation(value = "添加定制信息") public SimpleResponse
	 * save(@Validated @RequestBody CusotmizationRequest request) { return new
	 * SaveOrUpdateResponse(cusotmizationService.save(request));
	 * 
	 * }
	 */
	
}
