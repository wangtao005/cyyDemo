package com.manhui.easyexp.service.vip;

import org.mics.core.page.PageQuery;
import org.mics.core.response.PageDataResponse;

import com.manhui.easyexp.entity.vip.request.VipInfoRequest;
import com.manhui.easyexp.entity.vip.request.VipInfoVO;

public interface IVipInfoService {

	VipInfoVO getInfo(String userId);

	/**
	 * 添加数据
	 * author zls
	 * 2020年6月30日
	 */
	void save(VipInfoRequest entity);

	/**
	 * 获取分页会员信息
	 * author zls
	 * 2020年6月30日
	 */
	PageDataResponse<VipInfoVO> getListByPage(PageQuery pageQuery);

}