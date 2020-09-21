package com.manhui.easyexp.service.feedback.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.mics.log.annotation.OperatorLog;
import org.mics.token.model.CurrentUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manhui.easyexp.dao.feedback.FeedbackExperimentStimulateDao;
import com.manhui.easyexp.entity.feedback.FeedbackExperimentStimulate;
import com.manhui.easyexp.service.feedback.FeedbackExperimentStimulateService;

@Service
@Transactional
public class FeedbackExperimentStimulateServiceImpl implements FeedbackExperimentStimulateService {

    @Resource
    private FeedbackExperimentStimulateDao dao;

    @Override
    @OperatorLog("保存或修改对象")
    public FeedbackExperimentStimulate saveAndEdit(FeedbackExperimentStimulate entity,CurrentUser currentUser) {
        if(StringUtils.isBlank(entity.getId())){
            entity.setCreateBy(currentUser.getId());
        }else{
            entity.setUpdateBy(currentUser.getId());
        }
        FeedbackExperimentStimulate save = dao.save(entity);
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
    public FeedbackExperimentStimulate getById(String id) {
        FeedbackExperimentStimulate one = dao.getOne(id);
        return one;
    }

}
