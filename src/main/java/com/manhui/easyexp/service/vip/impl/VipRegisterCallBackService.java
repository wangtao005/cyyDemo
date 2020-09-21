package com.manhui.easyexp.service.vip.impl;

import javax.annotation.Resource;

import org.mics.enduser.entity.EndUserDO;
import org.mics.enduser.service.EndUserService;
import org.mics.enduser.service.RegisterCallBack;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manhui.easyexp.entity.vip.request.VipInfoRequest;
import com.manhui.easyexp.entity.vip.request.VipLevelVO;
import com.manhui.easyexp.service.vip.IVipInfoService;
import com.manhui.easyexp.service.vip.IVipLevelService;

@Service
public class VipRegisterCallBackService implements RegisterCallBack {

	@Resource
	private IVipInfoService vipInfoService;
	@Resource
	private EndUserService endUserService;
	@Resource
	private IVipLevelService vipLevelService;
	
	@Transactional
	@Override
	public void registerBack(EndUserDO user) {
		
		VipLevelVO level = vipLevelService.getFeeInfo();
		VipInfoRequest entity = new VipInfoRequest();
		entity.setAccount(user.getAccount());
		entity.setLevel(level.getLevel());
		entity.setType(0);
		entity.setPhone(user.getPhone());
		entity.setUserId(user.getId());
		entity.setUserName(user.getName());
		vipInfoService.save(entity);
	}

}
