package com.manhui.easyexp.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.mics.core.response.OneDataResponse;
import org.mics.core.response.SimpleResponse;
import org.mics.lang.exception.CustomException;
import org.mics.token.annotation.IgnoreToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manhui.easyexp.entity.user.request.UserRequest;
import com.manhui.easyexp.entity.user.request.UserVO;
import com.manhui.easyexp.service.user.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 平台端用户
 * @author zls
 * 2020年7月7日
 */
@RestController
@RequestMapping("/platform/user")
@Api(tags = "[平台端]-平台用户")
public class UserController {
	
	
	@Resource
	private IUserService userService;
	
	
	@IgnoreToken
	@GetMapping("/getInfo")
    @ApiOperation(value = "详情")
	public SimpleResponse getInfo(String account) {
		return new OneDataResponse<UserVO>(userService.getInfo(account));
	}
	
	@IgnoreToken
	@GetMapping("/getCurrentInfo")
    @ApiOperation(value = "获取当前用户详情")
	public SimpleResponse getCurrentInfo(HttpServletRequest request) {
		UserVO entity =(UserVO)request.getSession().getAttribute("platformUser");
		if(entity==null) {
			throw new CustomException("用户登录已失败，请重新登录");	
		}
		return new OneDataResponse<UserVO>(entity);	
	}
	
	@IgnoreToken
	@PostMapping("/login")
    @ApiOperation(value = "登录")
	public SimpleResponse login(HttpServletRequest request,String account,String password) {
		UserVO entity  = userService.login(account, password);
		request.getSession().setAttribute("platformUser", entity);
		request.getSession().setAttribute("platformLogin", true);
		return new OneDataResponse<UserVO>(entity);	
	}
	@IgnoreToken
	@GetMapping("/logout")
    @ApiOperation(value = "退出登录")
	public SimpleResponse logout(HttpServletRequest request) {
		request.getSession().removeAttribute("platformUser");
		request.getSession().removeAttribute("platformLogin");
		return new SimpleResponse();
	}
	
	
	@IgnoreToken
	@PostMapping("/update")
    @ApiOperation(value = "修改用户信息")
	public SimpleResponse update(@Validated @RequestBody UserRequest entity,HttpServletRequest request) {
		UserVO obj = userService.update(entity);
		request.getSession().setAttribute("platformUser", obj);
		return new SimpleResponse();
	}
	
}
