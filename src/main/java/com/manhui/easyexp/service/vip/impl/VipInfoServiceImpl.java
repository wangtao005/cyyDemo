package com.manhui.easyexp.service.vip.impl;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.HibernateValidator;
import org.mics.core.page.PageInfo;
import org.mics.core.page.PageQuery;
import org.mics.core.response.PageDataResponse;
import org.mics.lang.bean.BeanConvertUtil;
import org.mics.lang.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manhui.easyexp.dao.vip.VipInfoRepository;
import com.manhui.easyexp.entity.vip.request.VipInfoDO;
import com.manhui.easyexp.entity.vip.request.VipInfoRequest;
import com.manhui.easyexp.entity.vip.request.VipInfoVO;
import com.manhui.easyexp.entity.vip.request.VipLevelVO;
import com.manhui.easyexp.service.vip.IVipInfoService;
import com.manhui.easyexp.service.vip.IVipLevelService;

/**
 * 会员信息
 * 
 * @author zls 2020年6月30日
 */
@Service
public class VipInfoServiceImpl implements IVipInfoService {

	@Resource
	private VipInfoRepository vipInfoRepository;
	@Resource
	private IVipLevelService vipLevelService;

	private static final Logger LOGGER = LoggerFactory.getLogger(VipInfoServiceImpl.class);

	@Override
	public VipInfoVO getInfo(String userId) {
		// 查询信息
		VipInfoDO vipInfoDO =  vipInfoRepository.getByUserId(userId);
		VipInfoVO target = null;
		if(vipInfoDO!=null) {
			target = BeanConvertUtil.convert(vipInfoDO, VipInfoVO.class);
		}
		return target;

	}

	/**
	 * 添加数据
	 * author zls
	 * 2020年6月30日
	 */
	@Override
	@Transactional
	public void save(VipInfoRequest entity) {
		//验证
		String validMessage = "";
		//初始化检查器。
		   ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
		         .configure()
		         .failFast( false )
		         .buildValidatorFactory();
		   Validator validator = validatorFactory.getValidator();
		   //检查
		   Set<ConstraintViolation<Object>> set = validator.validate(entity);
		   //循环set，获取检查结果
		   for(ConstraintViolation<Object> voset : set){
		      validMessage = validMessage + voset.getMessage() +";";
		   }
		   if(!StringUtils.isEmpty(validMessage)){
			   LOGGER.error("添加会员信息出错{}",validMessage);
		      //抛出业务异常
		      throw new CustomException(validMessage);
		   }
		   
		   
		  //根据会员级别编号获取级别信息 
		  VipLevelVO level = vipLevelService.getInfo(entity.getLevel());
		  if(level==null) {
			  LOGGER.error("会员级别编号为:{}不存在",entity.getLevel());
		      //抛出业务异常
		      throw new CustomException("会员编号不存在");
		  }
		  VipInfoDO vipInfoDO = vipInfoRepository.getByUserId(entity.getUserId());
		 
		  Date date = Date.from(Instant.now());
		  Date startTime = new Date(date.getTime());
		  if(vipInfoDO!=null) {
			  Date endTime = new Date(vipInfoDO.getEndTime().getTime());
			  if(endTime.getTime()>date.getTime()) {//判断是否有会员时间未过期
				  date = endTime;
				  startTime = new Date(vipInfoDO.getStartTime().getTime());
			  }
		  }
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(date);
		  calendar.add(Calendar.DATE,level.getDuration().intValue());//添加天数
		  //保存前需删除以前的会员信息
		  if(vipInfoDO!=null) {
			  vipInfoDO.setBlnDel(true);//软删除对象
		  }
		  //实例化新数据
		  VipInfoDO obj = new VipInfoDO();
		  BeanUtils.copyProperties(entity, obj);
		  obj.setStartTime(startTime);//开始时间
		  obj.setEndTime(calendar.getTime());//结束时间
          vipInfoRepository.save(obj);
	}

	/**
	 * 获取分页会员信息
	 * author zls
	 * 2020年6月30日
	 */
	@Override
	public PageDataResponse<VipInfoVO> getListByPage(PageQuery pageQuery){
		
		//规格定义
        Specification<VipInfoDO> specification = new Specification<VipInfoDO>() {
            /**
			 * author zls
			 */
			private static final long serialVersionUID = -8785309034575411806L;

			/**
             * 构造断言
             * @param root 实体对象引用
             * @param query 规则查询对象
             * @param cb 规则构建对象
             * @return 断言
             */
            @Override
            public Predicate toPredicate(Root<VipInfoDO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if(StringUtils.isNotBlank(pageQuery.getKey())){ //添加断言
                	Predicate predicate = cb.disjunction();
                	predicate.getExpressions().add(cb.like(root.get("userName").as(String.class),"%".concat(pageQuery.getKey()).concat("%")));
                	predicate.getExpressions().add(cb.like(root.get("account").as(String.class),"%".concat(pageQuery.getKey()).concat("%")));
                	predicate.getExpressions().add(cb.like(root.get("phone").as(String.class),"%".concat(pageQuery.getKey()).concat("%")));
                	return predicate;
                }
                return cb.conjunction();
            }
        };
        
       Pageable pageable = PageRequest.of(pageQuery.getPageNo(),pageQuery.getPageSize(), Sort.Direction.DESC, "createDate");
        //查询
       Page<VipInfoDO> page = this.vipInfoRepository.findAll(specification,pageable);
       PageInfo pageInfo = new PageInfo();
       pageInfo.setPageNo(page.getNumber());
       pageInfo.setPageSize(page.getSize());
       pageInfo.setTotalCount(page.getTotalElements());
       pageInfo.setTotalPage(page.getTotalPages());
       List<VipInfoVO> pageList = BeanConvertUtil.convertList(page.getContent(), VipInfoVO.class);
      return new PageDataResponse<>(pageInfo,pageList);
		
	}
}
