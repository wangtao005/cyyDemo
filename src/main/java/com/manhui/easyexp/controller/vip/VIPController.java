package com.manhui.easyexp.controller.vip;

import java.util.List;

import javax.annotation.Resource;

import org.mics.core.response.OneDataResponse;
import org.mics.token.annotation.LoginUser;
import org.mics.token.model.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manhui.easyexp.entity.vip.request.VipInfoVO;
import com.manhui.easyexp.entity.vip.request.VipLevelVO;
import com.manhui.easyexp.service.vip.IVipInfoService;
import com.manhui.easyexp.service.vip.IVipLevelService;

/**
 *vip升级
 */
@Controller
@RequestMapping("/vipinfo")
public class VIPController {

    @Resource
    private IVipLevelService service;
    @Resource
    private IVipInfoService infoservice;

    /**
     *
     */
    @RequestMapping("/getList")
    @ResponseBody
    public OneDataResponse getList() {
        List<VipLevelVO> list = service.getList();
        if (list != null) {
        	for(int i=0;i<list.size();i++) {
        		if(0==list.get(i).getType()){
        			list.remove(i);
        			break;
        		}
        	}
            return new OneDataResponse<List<VipLevelVO>>(list);
        } else {
            return new OneDataResponse<String>("操作失败!");
        }
    }
    @RequestMapping("/getInfo")
    @ResponseBody
    public OneDataResponse getInfo(@LoginUser CurrentUser currentUser) {
        VipInfoVO info = infoservice.getInfo(currentUser.getId());
        if (info != null) {
            return new OneDataResponse<VipInfoVO>(info);
        } else {
            return new OneDataResponse<String>("操作失败!");
        }
    }

}


