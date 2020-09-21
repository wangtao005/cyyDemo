package com.manhui.easyexp.service.vip;

import java.util.List;

import com.manhui.easyexp.entity.vip.request.VipLevelRequest;
import com.manhui.easyexp.entity.vip.request.VipLevelVO;

public interface IVipLevelService {

	/**
	 * 根据等级编号获取等给信息
	 * author zls
	 * 2020年6月30日
	 */
	VipLevelVO getInfo(Byte level);

	void update(VipLevelRequest request);

	List<VipLevelVO> getList();

	VipLevelVO getFeeInfo();

	VipLevelVO getById(String id);

}