package com.manhui.easyexp.service.user;

import com.manhui.easyexp.entity.user.request.UserRequest;
import com.manhui.easyexp.entity.user.request.UserVO;

public interface IUserService {

	/**
	 * 详情信息
	 * author zls
	 * 2020年7月2日
	 */
	UserVO getInfo(String account);

	/**
	 * 添加数据
	 * author zls
	 * 2020年6月30日
	 */
	UserVO update(UserRequest entity);

	UserVO login(String account, String password);

}