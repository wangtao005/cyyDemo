package com.manhui.easyexp.service.user.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.mics.lang.bean.BeanConvertUtil;
import org.mics.lang.exception.CustomException;
import org.mics.lang.security.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manhui.easyexp.dao.user.UserRepository;
import com.manhui.easyexp.entity.user.request.UserDO;
import com.manhui.easyexp.entity.user.request.UserRequest;
import com.manhui.easyexp.entity.user.request.UserVO;
import com.manhui.easyexp.service.user.IUserService;

/**
 * 平台用户管理
 * @author zls
 * 2020年7月7日
 */
@Service
public class UserServiceImpl implements IUserService {

	@Resource
	private UserRepository userRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	/**
	 * 详情信息
	 * author zls
	 * 2020年7月2日
	 */
	@Override
	public UserVO getInfo(String account) {
		// 查询信息
		UserDO userDO =  userRepository.getByAccount(account);
		return BeanConvertUtil.convert(userDO, UserVO.class);
		
	}
	
	@Override
	public UserVO login(String account,String password) {
		// 查询信息
		UserDO userDO =  userRepository.getByAccount(account);
		if(userDO==null) {
			throw new CustomException("用户名错误");
		}
		if(!PasswordUtil.validatePassword(password, userDO.getSalt(), userDO.getPassword())) {
			throw new CustomException("密码错误");
		}
		userDO.setLastLoginTime(new Date());
		userRepository.save(userDO);
		return BeanConvertUtil.convert(userDO, UserVO.class);
		
	}
	
	/**
	 * 添加数据
	 * author zls
	 * 2020年6月30日
	 */
	@Override
	@Transactional
	public UserVO update(UserRequest entity) {
		
		UserDO userDO =  userRepository.getByAccount(entity.getAccount());
		
		if(StringUtils.isNotBlank(entity.getPassword2())) {
			if(PasswordUtil.validatePassword(entity.getPassword1(), userDO.getSalt(), userDO.getPassword())) {
				userDO.setPassword(PasswordUtil.encryptPassword(entity.getPassword2(), userDO.getSalt()));	
			}else {
				LOGGER.error("账号:{} 修改密码，旧密码错误",userDO.getAccount());
				throw new CustomException("旧密码错误");
			}
		}
		
		if(StringUtils.isNotBlank(entity.getHeaderUrl())) {
			userDO.setHeaderUrl(entity.getHeaderUrl());
		}
		userDO.setNickName(entity.getNickName());
		userDO.setModifyDate(new Date());
		userRepository.save(userDO);
		return BeanConvertUtil.convert(userDO, UserVO.class);
	}

}
