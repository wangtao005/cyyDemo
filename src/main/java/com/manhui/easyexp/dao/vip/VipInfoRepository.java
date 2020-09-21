package com.manhui.easyexp.dao.vip;

import org.mics.jpa.repository.BaseRepository;

import com.manhui.easyexp.entity.vip.request.VipInfoDO;

public interface VipInfoRepository extends BaseRepository<VipInfoDO> {

	
	/**
	 * 根据用户ID获取会员信息
	 * author zls
	 * 2020年6月30日
	 */
	VipInfoDO getByUserId(String userId);
	
	
	
	
	
}
