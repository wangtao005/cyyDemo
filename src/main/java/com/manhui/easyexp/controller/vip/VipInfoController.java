package com.manhui.easyexp.controller.vip;

import javax.annotation.Resource;

import org.mics.core.page.PageQuery;
import org.mics.core.response.OneDataResponse;
import org.mics.core.response.SimpleResponse;
import org.mics.token.annotation.IgnoreToken;
import org.mics.token.annotation.LoginUser;
import org.mics.token.model.CurrentUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manhui.easyexp.entity.vip.request.VipInfoVO;
import com.manhui.easyexp.service.vip.IVipInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 会员信息
 * @author zls
 * 2020年6月30日
 */
@RestController
@RequestMapping("/platform/vipInfo")
@Api(tags = "[平台端]-会员")
public class VipInfoController {
	
	
	@Resource
	private IVipInfoService vipInfoService;
	
	@IgnoreToken
	@PostMapping("/getListByPage")
    @ApiOperation(value = "会员分页列表")
	public SimpleResponse getListByPage(@RequestBody PageQuery query) {
		return vipInfoService.getListByPage(query);
	}
	
	@IgnoreToken
	@PostMapping("/getInfo")
    @ApiOperation(value = "查询会员信息")
	public SimpleResponse getInfo(@LoginUser CurrentUser currentUser) {
		VipInfoVO info = vipInfoService.getInfo(currentUser.getId());
		info.setUserName(currentUser.getName());
		return new OneDataResponse<VipInfoVO>(info);
	}
	
	
}
