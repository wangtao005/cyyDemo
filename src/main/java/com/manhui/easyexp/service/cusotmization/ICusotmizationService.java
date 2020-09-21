package com.manhui.easyexp.service.cusotmization;



import org.mics.core.response.PageDataResponse;

import com.manhui.easyexp.entity.cusotmization.request.CusotmizationQuery;
import com.manhui.easyexp.entity.cusotmization.request.CusotmizationRequest;
import com.manhui.easyexp.entity.cusotmization.request.CusotmizationVO;

public interface ICusotmizationService {

	CusotmizationVO getInfo(String id);

	/**
	 * 添加数据
	 * author zls
	 * 2020年7月1日
	 */
	String save(CusotmizationRequest entity);

	/**
	 * 获取分页列表数据
	 * author zls
	 * 2020年7月1日
	 */
	PageDataResponse<CusotmizationVO> getListByPage(CusotmizationQuery pageQuery);

}