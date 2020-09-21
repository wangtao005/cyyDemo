package com.manhui.easyexp.controller.vip;

import javax.annotation.Resource;

import org.mics.core.response.MultipleDataResponse;
import org.mics.core.response.OneDataResponse;
import org.mics.core.response.SimpleResponse;
import org.mics.token.annotation.IgnoreToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manhui.easyexp.entity.vip.request.VipLevelRequest;
import com.manhui.easyexp.entity.vip.request.VipLevelVO;
import com.manhui.easyexp.service.vip.IVipLevelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 会员级别
 * @author zls
 * 2020年6月30日
 */
@RestController
@RequestMapping("/platform/vipLevel")
@Api(tags = "[平台端]-会员级别")
public class VipLevelController {
	
	
	@Resource
	private IVipLevelService vipLevelService;
	
	@IgnoreToken
	@GetMapping("/getList")
    @ApiOperation(value = "会员级别列表")
	public SimpleResponse getList() {
		return new MultipleDataResponse<VipLevelVO>(vipLevelService.getList());
	}
	
	@IgnoreToken
	@GetMapping("/getInfo")
    @ApiOperation(value = "查询会员级别信息")
	public SimpleResponse getInfo(Byte level) {
		return new OneDataResponse<VipLevelVO>(vipLevelService.getInfo(level));
	}
	
	@IgnoreToken
	@PostMapping("/update")
    @ApiOperation(value = "修改会员级别信息")
	public SimpleResponse update(@Validated @RequestBody VipLevelRequest request) {
		vipLevelService.update(request);
		return new SimpleResponse();
	}
	
	
}
