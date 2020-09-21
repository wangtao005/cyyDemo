package com.manhui.easyexp.controller.feedback;

import com.manhui.easyexp.common.EasyexpExcelUtils;
import com.manhui.easyexp.service.feedback.FeedbackExperimentStimulateVoService;
import org.mics.core.response.OneDataResponse;
import org.mics.core.response.SimpleResponse;
import org.mics.lang.file.ExcelUtils;
import org.mics.token.annotation.LoginUser;
import org.mics.token.model.CurrentUser;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/experiment/stimulate")
public class FeedbackExperimentStimulateController {

    @Resource
    private FeedbackExperimentStimulateVoService service;


    /**
     * 导入实验试次
     */
    @RequestMapping("/importExcel")
    @ResponseBody
    public  Map<String,Object> importExcel(MultipartFile file, String type,String experimentType) {


        //   service.importExcel(file,currentUser);
        try {
            final InputStream inputStream = file.getInputStream();
            String[][] data = EasyexpExcelUtils.getData(inputStream, 2);
            Map<String,Object> map = service.importExcelDataFormat(data,experimentType);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String,Object> map = new HashMap<>();
            map.put("status", false);
            map.put("message", "解析excel出错");
            map.put("data", null);
            return map;
        }
    }


    /**
     * 删除
     */
    @RequestMapping("/del")
    @ResponseBody
    public SimpleResponse del(String id) {

        boolean del = service.del(id);
        if (del) {
            return new OneDataResponse<String>("删除成功!");
        } else {
            return new OneDataResponse<String>("删除失败!");
        }
    }


}


