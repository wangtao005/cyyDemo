package com.manhui.easyexp.service.myDataInfo.impl;

import com.manhui.easyexp.dao.myDataInfo.MyDataInfoDao;
import com.manhui.easyexp.entity.myData.MyDataInfo;
import com.manhui.easyexp.service.myDataInfo.MyDataInfoService;
import org.apache.commons.lang3.StringUtils;
import org.mics.core.page.PageInfo;
import org.mics.core.response.PageDataResponse;
import org.mics.log.annotation.OperatorLog;
import org.mics.token.model.CurrentUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MyDataInfoServiceImpl implements MyDataInfoService {

    @Resource
    private MyDataInfoDao dao;

    @Override
    @OperatorLog("保存或修改对象")
    public MyDataInfo saveAndEdit(MyDataInfo entity,CurrentUser currentUser) {
        if(StringUtils.isBlank(entity.getId())){
            entity.setCreateBy(currentUser.getId());
        }else{
            entity.setUpdateBy(currentUser.getId());
        }
        MyDataInfo save = dao.save(entity);
        return save;
    }

    @Override
    @OperatorLog("根据id删除对象")
    public boolean del(String id) {
        try {
            dao.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @OperatorLog("根据id获取对象")
    public MyDataInfo getById(String id) {
        MyDataInfo one = dao.getOne(id);
        return one;
    }

    @Override
    public PageDataResponse listByPage(MyDataInfo entity, Integer pageNo, Integer pageSize) {
        Specification<MyDataInfo> spec = new Specification<MyDataInfo>() {
            @Override
            public Predicate toPredicate(Root<MyDataInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> lstPredicates = new ArrayList<Predicate>();
                Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
                if (!StringUtils.isEmpty(entity.getFeedbackId())) {
                    lstPredicates.add(criteriaBuilder.equal(root.get("feedbackId").as(Long.class), entity.getFeedbackId()));
                }
                return criteriaBuilder.and(lstPredicates.toArray(arrayPredicates));
            }
        };

        Page<MyDataInfo> page = dao.findAll(spec, PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "id"));
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNo(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotalCount(page.getTotalElements());
        pageInfo.setTotalPage(page.getTotalPages());
        PageDataResponse pif = new PageDataResponse(pageInfo, page.getContent());
        return pif;
    }

    @Override
    public List<MyDataInfo> listData(MyDataInfo entity,Integer size) {
    if(StringUtils.isBlank(entity.getFeedbackId())){
        return null;
    }
        Specification<MyDataInfo> spec = new Specification<MyDataInfo>() {
            @Override
            public Predicate toPredicate(Root<MyDataInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> lstPredicates = new ArrayList<Predicate>();
                Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
                if (StringUtils.isNotBlank(entity.getFeedbackId())) {
                    lstPredicates.add(criteriaBuilder.equal(root.get("feedbackId").as(Long.class), entity.getFeedbackId()));
                }
                return criteriaBuilder.and(lstPredicates.toArray(arrayPredicates));
            }
        };
        List<MyDataInfo> all =null;
        if(size==null){
            all = dao.findAll(spec);
        }else{
            Page<MyDataInfo> page = dao.findAll(spec, PageRequest.of(0, size, Sort.Direction.DESC, "createDate"));
             all = page.getContent();
        }
        return all;
    }

    @Override
    public List<MyDataInfo> getByFeedbackId(String id) {
        if(StringUtils.isBlank(id)){
            return null;
        }
        Specification<MyDataInfo> spec = new Specification<MyDataInfo>() {
            @Override
            public Predicate toPredicate(Root<MyDataInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> lstPredicates = new ArrayList<Predicate>();
                Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
                if (StringUtils.isNotBlank(id)) {
                    lstPredicates.add(criteriaBuilder.equal(root.get("feedbackId").as(Long.class), id));
                }
                return criteriaBuilder.and(lstPredicates.toArray(arrayPredicates));
            }
        };
        return dao.findAll(spec);
    }

    @Override
    public boolean delByFeedBackId(String id) {
        try{
            List<MyDataInfo> byFeedbackId = this.getByFeedbackId(id);
            dao.deleteAll(byFeedbackId);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}
