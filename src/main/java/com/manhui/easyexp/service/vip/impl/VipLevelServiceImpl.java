package com.manhui.easyexp.service.vip.impl;

import java.util.List;

import javax.annotation.Resource;

import org.mics.lang.bean.BeanConvertUtil;
import org.mics.lang.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.manhui.easyexp.dao.vip.VipLevelRepository;
import com.manhui.easyexp.entity.vip.request.VipLevelDO;
import com.manhui.easyexp.entity.vip.request.VipLevelRequest;
import com.manhui.easyexp.entity.vip.request.VipLevelVO;
import com.manhui.easyexp.service.vip.IVipLevelService;

/**
 * 会员信息
 * 
 * @author zls 2020年6月30日
 */
@Service
public class VipLevelServiceImpl implements IVipLevelService {

	@Resource
	private VipLevelRepository vipLevelRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(VipLevelServiceImpl.class);

	/**
	 * 根据等级编号获取等给信息
	 * author zls
	 * 2020年6月30日
	 */
	@Override
	public VipLevelVO getInfo(Byte level) {
		// 查询信息
		VipLevelDO entity = vipLevelRepository.getByLevel(level);
		VipLevelVO obj = null;
		if(entity!=null) {
			obj = BeanConvertUtil.convert(entity, VipLevelVO.class);
		}
		return obj;
	}
	
	/**
	 * 获取会员信息列表
	 * author zls
	 * 2020年6月30日
	 */
	@Override
	public List<VipLevelVO> getList(){
		List<VipLevelDO> list =  vipLevelRepository.findAll();
		List<VipLevelVO> listVO = null;
		if(!CollectionUtils.isEmpty(list)) {
			listVO = BeanConvertUtil.convertList(list, VipLevelVO.class);
		}
		return listVO;
	}
	/**
	 * 修改某会员信息
	 * author zls
	 * 2020年6月30日
	 */
	@Override
	@Transactional
	public void update(VipLevelRequest request) {
		VipLevelDO entity = vipLevelRepository.getByLevel(request.getLevel());
		if(entity==null) {
			LOGGER.error("级别编号为:{}会员级别不存在",request.getLevel());
			throw new CustomException("会员级别不存在");
		}
		entity.setAmount(request.getAmount());
		entity.setDuration(request.getDuration());
		entity.setLevelName(request.getLevelName());
	}
	
	@Override
	public VipLevelVO getFeeInfo() {
		// 查询信息
		VipLevelDO entity = vipLevelRepository.getByType((byte)0);
		VipLevelVO obj = null;
		if(entity!=null) {
			obj = BeanConvertUtil.convert(entity, VipLevelVO.class);
		}
		return obj;
	}
	
	@Override
	public VipLevelVO getById(String id) {
		// 查询信息
		VipLevelDO entity = vipLevelRepository.getOne(id);
		VipLevelVO obj = null;
		if(entity!=null) {
			obj = BeanConvertUtil.convert(entity, VipLevelVO.class);
		}
		return obj;
	}
}
