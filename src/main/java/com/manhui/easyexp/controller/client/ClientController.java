package com.manhui.easyexp.controller.client;

import javax.annotation.Resource;

import org.mics.core.page.PageQuery;
import org.mics.core.response.OneDataResponse;
import org.mics.core.response.SimpleResponse;
import org.mics.enduser.service.EndUserService;
import org.mics.enduser.vo.EndUserInfoVO;
import org.mics.token.annotation.IgnoreToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 会员信息
 * @author zls
 * 2020年6月30日
 */
@RestController
@RequestMapping("/platform/client")
@Api(tags = "[平台端]-客户")
public class ClientController {
	
	
	@Resource
	private EndUserService endUserService;
	
	@IgnoreToken
	@PostMapping("/getListByPage")
    @ApiOperation(value = "用户分页列表")
	public SimpleResponse getListByPage(@RequestBody PageQuery pageQuery) {
		return endUserService.getEndUserListByPage(pageQuery);
	}
	
	@IgnoreToken
	@PostMapping("/getInfo")
    @ApiOperation(value = "用户信息")
	public SimpleResponse getInfo(String userId) {
		return new OneDataResponse<EndUserInfoVO>(endUserService.getUserByUserId(userId));
		
	}
	
	
}
