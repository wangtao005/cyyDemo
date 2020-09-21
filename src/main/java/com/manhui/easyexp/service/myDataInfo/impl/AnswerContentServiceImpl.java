package com.manhui.easyexp.service.myDataInfo.impl;

import com.manhui.easyexp.dao.myDataInfo.AnswerContentDao;
import com.manhui.easyexp.entity.myData.AnswerContent;
import com.manhui.easyexp.service.AnswerContent.AnswerContentService;
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
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AnswerContentServiceImpl implements AnswerContentService {

    @Resource
    private AnswerContentDao dao;

    @Override
    @OperatorLog("保存或修改对象")
    public AnswerContent saveAndEdit(AnswerContent entity, CurrentUser currentUser) {
        if(org.apache.commons.lang3.StringUtils.isBlank(entity.getId())){
            entity.setCreateBy(currentUser.getId());
        }else{
            entity.setUpdateBy(currentUser.getId());
        }
        AnswerContent save = dao.save(entity);
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
    public AnswerContent getById(String id) {
        AnswerContent one = dao.getOne(id);
        return one;
    }

    @Override
    public PageDataResponse listByPage(AnswerContent entity, Integer pageNo, Integer pageSize) {
        Specification<AnswerContent> spec = new Specification<AnswerContent>() {
            @Override
            public Predicate toPredicate(Root<AnswerContent> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> lstPredicates = new ArrayList<Predicate>();
                Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
                if (!StringUtils.isEmpty(entity.getId())) {
                    lstPredicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), entity.getId()));
                }
                return criteriaBuilder.and(lstPredicates.toArray(arrayPredicates));
            }
        };

        Page<AnswerContent> page = dao.findAll(spec, PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "id"));
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNo(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotalCount(page.getTotalElements());
        pageInfo.setTotalPage(page.getTotalPages());
        PageDataResponse pif = new PageDataResponse(pageInfo, page.getContent());
        return pif;
    }

}
