package com.manhui.easyexp.controller.feedback;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.mics.core.response.OneDataResponse;
import org.mics.core.response.PageDataResponse;
import org.mics.core.response.SimpleResponse;
import org.mics.token.annotation.IgnoreToken;
import org.mics.token.annotation.LoginUser;
import org.mics.token.model.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manhui.easyexp.common.EasyexpEnum;
import com.manhui.easyexp.entity.feedback.Feedback;
import com.manhui.easyexp.entity.feedback.FeedbackExperimentStimulate;
import com.manhui.easyexp.entity.myData.MyDataInfo;
import com.manhui.easyexp.service.feedback.FeedbackService;
import com.manhui.easyexp.service.myDataInfo.MyDataInfoService;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Resource
    private FeedbackService service;
    @Resource
    private MyDataInfoService myDataInfoservice;


    /**
     * 列表页面
     */
    @RequestMapping("/listByPage")
    @ResponseBody
    public PageDataResponse listByPage( Feedback entity, Integer pageNo, Integer pageSize,@LoginUser CurrentUser currentUser) {
        if (pageNo == null) {
            pageNo = 0;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 20;
        }
        PageDataResponse pageData = service.listByPage(entity, pageNo, pageSize,currentUser);

        List<Feedback> data = pageData.getData();
        for (Feedback datum : data) {
            if (StringUtils.isNotBlank(datum.getId())){
                List<MyDataInfo> list = myDataInfoservice.getByFeedbackId(datum.getId());
                if (list.size()>0){
                    datum.setLocking(true);
                }else{
                    datum.setLocking(false);
                }
            }
        }

        return pageData;
    }
    /**
     * 新增和修改
     */
    @RequestMapping("/saveAndEdit")
    @ResponseBody
    public SimpleResponse saveAndEdit( @RequestBody Feedback entity,@LoginUser CurrentUser currentUser) {

        if (StringUtils.isNotBlank(entity.getId())){
            List<MyDataInfo> list = myDataInfoservice.getByFeedbackId(entity.getId());
            if (list.size()>0){
                return new OneDataResponse<String>("进行中,无法再次编辑");
            }
        }
        try{
            Feedback Feedback = service.saveAndEdit(entity,currentUser);
            return new OneDataResponse<Feedback>(Feedback);
        }catch (Exception e){
            e.printStackTrace();
            return new OneDataResponse<String>("操作失败!");
        }
    }

    /**
     * 详情
     */
    @RequestMapping("/info")
    @ResponseBody
    public OneDataResponse info( String id) {
        Feedback Feedback = service.getById(id);
        if (Feedback != null) {
            return new OneDataResponse<Feedback>(Feedback);
        } else {
            return new OneDataResponse<String>("操作失败!");
        }
    }

    /**
     * 试次随机
     */
    @IgnoreToken //忽略登录
    @RequestMapping("/random/info")
    @ResponseBody
    public OneDataResponse randomInfo( String id) {
        Feedback Feedback = service.getById(id);
        EasyexpEnum.yesOrNoEnum yesOrNoRandom = Feedback.getYesOrNoRandom();
        if(yesOrNoRandom == EasyexpEnum.yesOrNoEnum.YES){
            List<FeedbackExperimentStimulate> list = Feedback.getFeedbackExperimentStimulate();
            Collections.shuffle(list);
        }
        if (Feedback != null) {
            return new OneDataResponse<Feedback>(Feedback);
        } else {
            return new OneDataResponse<String>("操作失败!");
        }
    }

    /**
     * 删除
     */
    @RequestMapping("/del")
    @ResponseBody
    public SimpleResponse del( String id) {
//        if (StringUtils.isNotBlank(id)){
//            List<MyDataInfo> list = myDataInfoservice.getByFeedbackId(id);
//            if (list.size()>0){
//                return new OneDataResponse<String>("进行中,无法删除");
//            }
//        }
        boolean del = service.del(id);
        if (del) {
            return new OneDataResponse<String>("删除成功!");
        } else {
            return new OneDataResponse<String>("删除失败!");
        }
    }


}


