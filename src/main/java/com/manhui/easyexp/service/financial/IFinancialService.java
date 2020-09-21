package com.manhui.easyexp.service.financial;

import org.mics.core.response.PageDataResponse;

import com.manhui.easyexp.entity.financial.request.FinancialQuery;
import com.manhui.easyexp.entity.financial.request.FinancialRequest;
import com.manhui.easyexp.entity.financial.request.FinancialVO;

public interface IFinancialService {

	/**
	 * 详情信息
	 * author zls
	 * 2020年7月2日
	 */
	FinancialVO getInfo(String id);

	/**
	 * 添加数据
	 * author zls
	 * 2020年6月30日
	 */
	void save(FinancialRequest entity);

	/**
	 * 获取分页信息
	 * author zls
	 * 2020年7月2日
	 */
	PageDataResponse<FinancialVO> getListByPage(FinancialQuery financialQuery);

}