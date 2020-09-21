package com.manhui.easyexp.service.cusotmization.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.mics.core.page.PageInfo;
import org.mics.core.response.PageDataResponse;
import org.mics.lang.bean.BeanConvertUtil;
import org.mics.lang.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manhui.easyexp.dao.cusotmization.CusotmizationRepository;
import com.manhui.easyexp.entity.cusotmization.request.CusotmizationDO;
import com.manhui.easyexp.entity.cusotmization.request.CusotmizationQuery;
import com.manhui.easyexp.entity.cusotmization.request.CusotmizationRequest;
import com.manhui.easyexp.entity.cusotmization.request.CusotmizationVO;
import com.manhui.easyexp.service.cusotmization.ICusotmizationService;

/**
 * 个性化定制
 * @author zls
 * 2020年7月1日
 */
@Service
public class CusotmizationServiceImpl implements ICusotmizationService{

	@Resource
	private CusotmizationRepository cusotmizationRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CusotmizationServiceImpl.class);

	@Override
	public CusotmizationVO getInfo(String id) {
		// 查询信息
		CusotmizationDO cusotmizationVO =  cusotmizationRepository.getOne(id);
		
		return BeanConvertUtil.convert(cusotmizationVO, CusotmizationVO.class);
	}

	/**
	 * 添加数据
	 * author zls
	 * 2020年7月1日
	 */
	@Override
	@Transactional
	public String save(CusotmizationRequest entity) {
		CusotmizationDO obj = BeanConvertUtil.convert(entity, CusotmizationDO.class);
		obj = cusotmizationRepository.save(obj);
		return obj.getId();
	}

	/**
	 * 获取分页列表数据
	 * author zls
	 * 2020年7月1日
	 */
	@Override
	public PageDataResponse<CusotmizationVO> getListByPage(CusotmizationQuery pageQuery){
		
		//规格定义
        Specification<CusotmizationDO> specification = new Specification<CusotmizationDO>() {
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
            public Predicate toPredicate(Root<CusotmizationDO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	List<Predicate> predicates = new ArrayList<>(); //所有的断言
                if(StringUtils.isNotBlank(pageQuery.getStartDate())){ //添加断言
                	Date s = null;
					try {
						s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pageQuery.getStartDate()+" 00:00:00");
					} catch (ParseException e) {
						LOGGER.error("开始时间转换出错",e);
						throw new CustomException("时间查询出错",e);
					}
                    Predicate start = cb.greaterThanOrEqualTo(root.get("createDate").as(Date.class), s);
                    predicates.add(start);
                }
                if(StringUtils.isNotBlank(pageQuery.getEndDate())){ //添加断言
                	Date e = null;
					try {
						e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pageQuery.getEndDate()+" 23:59:59");
					} catch (ParseException e1) {
						LOGGER.error("结束时间转换出错",e);
						throw new CustomException("时间查询出错",e1);
					}
                    Predicate end = cb.lessThanOrEqualTo(root.get("createDate").as(Date.class), e);
                    predicates.add(end);
                }
                
                return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
        
       Pageable pageable = PageRequest.of(pageQuery.getPageNo(),pageQuery.getPageSize(), Sort.Direction.DESC, "createDate");
        //查询
       Page<CusotmizationDO> page = this.cusotmizationRepository.findAll(specification,pageable);
       PageInfo pageInfo = new PageInfo();
       pageInfo.setPageNo(page.getNumber());
       pageInfo.setPageSize(page.getSize());
       pageInfo.setTotalCount(page.getTotalElements());
       pageInfo.setTotalPage(page.getTotalPages());
       List<CusotmizationVO> pageList = BeanConvertUtil.convertList(page.getContent(), CusotmizationVO.class);
      return new PageDataResponse<>(pageInfo,pageList);
		
	}
}
