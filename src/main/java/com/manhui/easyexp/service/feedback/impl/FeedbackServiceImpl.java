package com.manhui.easyexp.service.feedback.impl;

import com.manhui.easyexp.common.EasyexpEnum;
import com.manhui.easyexp.dao.feedback.FeedbackDao;
import com.manhui.easyexp.dao.myDataInfo.MyDataInfoDao;
import com.manhui.easyexp.entity.feedback.Feedback;
import com.manhui.easyexp.entity.feedback.FeedbackExperimentStimulate;
import com.manhui.easyexp.service.feedback.FeedbackExperimentStimulateService;
import com.manhui.easyexp.service.feedback.FeedbackService;
import com.manhui.easyexp.service.myDataInfo.MyDataInfoService;
import org.apache.commons.lang3.StringUtils;
import org.mics.core.page.PageInfo;
import org.mics.core.response.PageDataResponse;
import org.mics.log.annotation.OperatorLog;
import org.mics.token.annotation.LoginUser;
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
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackDao dao;
    @Resource
    private MyDataInfoService myDateService;
    @Resource
    private FeedbackExperimentStimulateService fess;

    @Override
    @OperatorLog("保存或修改对象")
    public Feedback saveAndEdit(Feedback entity, CurrentUser currentUser) {
        entity.setCreateBy(currentUser.getId());
        if (StringUtils.isNotBlank(entity.getId())) {
            Feedback byId = this.getById(entity.getId());
            if (byId != null) {
                List<FeedbackExperimentStimulate> list = byId.getFeedbackExperimentStimulate();
                for (FeedbackExperimentStimulate fs : list) {
                    fess.del(fs.getId());
                }
            }
        }
        if(StringUtils.isBlank(entity.getId())){
            entity.setCreateBy(currentUser.getId());
        }else{
            entity.setUpdateBy(currentUser.getId());
        }
        Feedback save = dao.save(entity);
        return save;
    }

    @Override
    @OperatorLog("根据id删除对象")
    public boolean del(String id) {
        try {
            boolean b = myDateService.delByFeedBackId(id);
            if (b) {
                dao.deleteById(id);
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @OperatorLog("根据id获取对象")
    public Feedback getById(String id) {
        Feedback one = dao.getOne(id);
        List<FeedbackExperimentStimulate> list = one.getFeedbackExperimentStimulate();
        return one;
    }

    @Override
    public PageDataResponse listByPage(Feedback entity, Integer pageNo, Integer pageSize, CurrentUser currentUser) {
        Specification<Feedback> spec = new Specification<Feedback>() {
            @Override
            public Predicate toPredicate(Root<Feedback> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> lstPredicates = new ArrayList<Predicate>();
                Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
                if (!StringUtils.isEmpty(entity.getId())) {
                    lstPredicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), entity.getId()));
                }
                if (entity.getExperimentType() != null) {
                    lstPredicates.add(criteriaBuilder.equal(root.get("experimentType"), entity.getExperimentType()));
                }
                lstPredicates.add(criteriaBuilder.equal(root.get("createBy"), currentUser.getId()));

                return criteriaBuilder.and(lstPredicates.toArray(arrayPredicates));
            }
        };

        Page<Feedback> page = dao.findAll(spec, PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, "createDate"));
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNo(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotalCount(page.getTotalElements());
        pageInfo.setTotalPage(page.getTotalPages());
        PageDataResponse pif = new PageDataResponse(pageInfo, page.getContent());
        return pif;
    }

}
