package com.manhui.easyexp.controller.personalise;

import javax.annotation.Resource;

import org.mics.core.response.OneDataResponse;
import org.mics.token.annotation.IgnoreToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manhui.easyexp.entity.cusotmization.request.CusotmizationRequest;
import com.manhui.easyexp.entity.cusotmization.request.CusotmizationVO;
import com.manhui.easyexp.service.cusotmization.ICusotmizationService;

/**
 * 个性化定制
 */
@Controller
@RequestMapping("/personalise")
public class PersonaliseController {

    @Resource
    private ICusotmizationService service;


    /**
     * 新增和修改
     */
    @IgnoreToken //忽略登录
    @RequestMapping("/save")
    @ResponseBody
    public OneDataResponse save(CusotmizationRequest entity) {

        String save = service.save(entity);
        if (save != null) {
            return new OneDataResponse<String>(save);
        } else {
            return new OneDataResponse<String>("操作失败!");
        }
    }

    /**
     * 详情
     */
    @RequestMapping("/info")
    @ResponseBody
    public OneDataResponse info(String id) {
        CusotmizationVO info = service.getInfo(id);
        if (info != null) {
            return new OneDataResponse<CusotmizationVO>(info);
        } else {
            return new OneDataResponse<String>("操作失败!");
        }
    }

}


