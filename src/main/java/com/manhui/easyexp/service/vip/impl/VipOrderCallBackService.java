package com.manhui.easyexp.service.vip.impl;

import javax.annotation.Resource;

import org.mics.enduser.service.EndUserService;
import org.mics.enduser.vo.EndUserInfoVO;
import org.mics.order.callback.OrderPayCallBack;
import org.mics.order.entity.OrderDO;
import org.mics.order.enums.OrderState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manhui.easyexp.entity.vip.request.VipInfoRequest;
import com.manhui.easyexp.entity.vip.request.VipLevelVO;
import com.manhui.easyexp.service.vip.IVipInfoService;
import com.manhui.easyexp.service.vip.IVipLevelService;

@Service
public class VipOrderCallBackService implements OrderPayCallBack {

	@Resource
	private IVipInfoService vipInfoService;
	@Resource
	private EndUserService endUserService;
	
	@Resource
	private IVipLevelService vipLevelService;
	
	@Transactional
	@Override
	public OrderDO payNotify(OrderDO orderDO) {
		if(OrderState.PAY_FINAL_SUCCESS!=orderDO.getState()) {
			return orderDO;
		}
		
		VipLevelVO level = vipLevelService.getById(orderDO.getGoodsId());
		EndUserInfoVO user = endUserService.getUserByUserId(orderDO.getUserId());
		VipInfoRequest entity = new VipInfoRequest();
		entity.setAccount(user.getAccount());
		entity.setLevel(level.getLevel());
		entity.setType(1);
		entity.setPhone(user.getPhone());
		entity.setUserId(user.getId());
		entity.setUserName(user.getName());
		vipInfoService.save(entity);
		return orderDO;
	}

}
