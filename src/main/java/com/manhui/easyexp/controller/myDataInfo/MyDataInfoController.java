package com.manhui.easyexp.controller.myDataInfo;

import com.manhui.easyexp.common.Utils;
import com.manhui.easyexp.common.ZipUtils;
import com.manhui.easyexp.entity.feedback.Feedback;
import com.manhui.easyexp.entity.myData.AnswerContent;
import com.manhui.easyexp.entity.myData.MyDataInfo;
import com.manhui.easyexp.service.feedback.FeedbackService;
import com.manhui.easyexp.service.myDataInfo.MyDataInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;

@Controller
@RequestMapping("/my/data")
public class MyDataInfoController {

    @Resource
    private MyDataInfoService service;

    @Resource
    private FeedbackService feedbackservice;


    /**
     * 列表页面
     */
    @RequestMapping("/listByPage")
    @ResponseBody
    public PageDataResponse listByPage(MyDataInfo entity, Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            pageNo = 0;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 20;
        }
        if (entity.getFeedbackId() == null) {
            return null;
        }
        PageDataResponse pageData = service.listByPage(entity, pageNo, pageSize);
        List<MyDataInfo> data = pageData.getData();
        for (MyDataInfo datum : data) {
            List<AnswerContent> answerContents = datum.getAnswerContents();
            answerContents.sort(new Comparator<AnswerContent>() {
                @Override
                public int compare(AnswerContent o1, AnswerContent o2) {
                    Long id1 = Long.parseLong(o1.getFesId());
                    Long id2 = Long.parseLong(o2.getFesId());
                    return id1.compareTo(id2);
                }
            });
        }
        return pageData;
    }

    /**
     * 导出数据整理
     */
    @IgnoreToken
    @RequestMapping("/listData")
    @ResponseBody
    public OneDataResponse listData(MyDataInfo entity, Integer size) {

        List<String> tHeader = new ArrayList<>();
        List<String> filterVal = new ArrayList<>();
        List<Map<String, Object>> list = new ArrayList<>();
        String[] tHeaderArr = {"提交时间", "所用时长", "来源", "来源详情", "来自IP", "实验Id"};
        String[] filterValArr = {"submitTime", "spentTime", "source", "sourceInfo", "fromIp", "feedbackId"};
        Collections.addAll(tHeader, tHeaderArr);
        Collections.addAll(filterVal, filterValArr);
        Feedback feedback = feedbackservice.getById(entity.getFeedbackId());
        String participantBasicInfo = feedback.getParticipantBasicInfo();
        if (StringUtils.isNotBlank(participantBasicInfo)) {
            if (participantBasicInfo.contains("name")) {
                tHeader.add("姓名");
                filterVal.add("name");
            }
            if (participantBasicInfo.contains("sex")) {
                tHeader.add("性别");
                filterVal.add("sex");
            }
            if (participantBasicInfo.contains("age")) {
                tHeader.add("年龄");
                filterVal.add("age");
            }
            if (participantBasicInfo.contains("yearsOfEducation")) {
                tHeader.add("受教育年限");
                filterVal.add("yearsOfEducation");
            }
            if (participantBasicInfo.contains("sexualOrientation")) {
                tHeader.add("性取向");
                filterVal.add("sexualOrientation");
            }
            if (participantBasicInfo.contains("height")) {
                tHeader.add("身高");
                filterVal.add("height");
            }
            if (participantBasicInfo.contains("bodyWeight")) {
                tHeader.add("体重");
                filterVal.add("bodyWeight");
            }
        }

        List<MyDataInfo> listData = service.listData(entity, size);

        for (int j = 0; j < listData.size(); j++) {
            MyDataInfo li = listData.get(j);
            Map<String, Object> map = new HashMap<>();
            List<AnswerContent> answerContents = li.getAnswerContents();
            answerContents.sort(new Comparator<AnswerContent>() {
                @Override
                public int compare(AnswerContent o1, AnswerContent o2) {
                    Long id1 = Long.parseLong(o1.getFesId());
                    Long id2 = Long.parseLong(o2.getFesId());
                    return id1.compareTo(id2);
                }
            });
            for (int i = 0; i < answerContents.size(); i++) {
                AnswerContent answerContent = answerContents.get(i);
                String topic = answerContent.getTopic();//题目
                String answer = answerContent.getAnswer();//答案
                // String spentTime = answerContent.getSpentTime();//用时
                if (j == 0) {
                    tHeader.add(topic);
                    filterVal.add("序号" + i);
                }
                map.put("序号" + i, answer);//+("   作答用时:"+spentTime));
            }
            Field[] declaredFields = li.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                try {
                    Object value = field.get(li);
                    map.put(fieldName, value);
                } catch (Exception e) {
                }
            }
            list.add(map);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tHeader", tHeader);
        map.put("filterVal", filterVal);
        map.put("list", list);

        return new OneDataResponse(map);
    }

    /**
     * 添加和修改页面
     */
    @IgnoreToken //忽略登录
    @RequestMapping("/saveAndEdit")
    @ResponseBody
    public SimpleResponse saveAndEdit(@RequestBody MyDataInfo entity, HttpServletRequest request,@LoginUser CurrentUser currentUser) {

        entity.setSubmitTime(new Date());
        String addresses = Utils.getAddresses(Utils.getIpAddr(request));
        entity.setSource(Utils.mobileOrPC(request));
        entity.setSourceInfo(addresses);
        MyDataInfo MyDataInfo = service.saveAndEdit(entity,currentUser);
        if (MyDataInfo != null) {
            return new OneDataResponse<MyDataInfo>(MyDataInfo);
        } else {
            return null;
        }
    }

    /**
     * 删除文件信息
     */
    @RequestMapping("/info")
    @ResponseBody
    public SimpleResponse info(String id) {
        MyDataInfo MyDataInfo = service.getById(id);
        if (MyDataInfo != null) {
            return new OneDataResponse<MyDataInfo>(MyDataInfo);
        } else {
            return new OneDataResponse<String>("操作失败!");
        }
    }

    /**
     * 删除文件信息
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

    /**
     * 单人答题导出详细
     */
    @IgnoreToken //忽略登录
    @RequestMapping("/single/listDate")
    public void fileDownload(HttpServletRequest request, HttpServletResponse response, String ids) {
        ids = ids.replaceAll(" ", "");
        String[] split = ids.split(",");
        List<File> waitDel = new ArrayList<>();
        String basePath = request.getSession().getServletContext().getRealPath("");
        File fileDir = new File(basePath + "waitDel");//创建一个文件夹用来装创建的excel文件
        File[] files = fileDir.listFiles();//看看这个文件是否有数据,有就清空
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        for (String s : split) {//生成excel在waitDel文件夹中
            if (StringUtils.isBlank(s)) {
                continue;
            }
            MyDataInfo myDataInfo = service.getById(s);
            String fileName = System.currentTimeMillis() + ".xls";
            if (!fileDir.isDirectory() && !fileDir.exists()) {
                //创建单层目录
                fileDir.mkdir();
            }
            String path = basePath + "/waitDel/" + fileName;
            HSSFWorkbook wb = fileDownloadBook(myDataInfo);
            File file = new File(path);
            try {
                /*生成excel 在服务器*/
                FileOutputStream os = new FileOutputStream(path);
                wb.write(os);
                os.close();
                waitDel.add(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            OutputStream out = response.getOutputStream();
            ZipUtils.toZip(waitDel, out);
            /*下载excel*/
//            response.addHeader("Content-Disposition", "attachment;filename=" + new String("123" + ".zip"));
//             response.setContentType("application/octet-stream");
//            InputStream in = new FileInputStream(fileDir);
//            int b;
//            while ((b = in.read()) != -1) out.write(b);
//            in.close();
            out.close();
            /*删除生成的excel文件*/
            for (File fl : waitDel) {
                fl.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*构建一个excel表格*/
    public static HSSFWorkbook fileDownloadBook(MyDataInfo myDataInfo) {

        HSSFWorkbook wb = new HSSFWorkbook();/*首先创建字体样式*/
        HSSFFont font = wb.createFont();/*创建字体样式*/
        font.setFontName("宋体");/*使用宋体*/
        font.setFontHeightInPoints((short) 12);/*字体大小*/
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);/* 加粗*/
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment((short) 2);/*居中*/
        style.setVerticalAlignment((short) 2);/*垂直居中*/
        style.setFont(font);/*设置字体样式*/
        style.setFillForegroundColor((short) 41);
        style.setFillPattern((short) 1);
        style.setBorderTop((short) 1);/*上边框*/
        style.setBorderBottom((short) 1);/*下边框*/
        style.setBorderLeft((short) 1);/*左边框*/
        style.setBorderRight((short) 1);/*右边框*/

        HSSFCellStyle styleInfo = wb.createCellStyle();
        styleInfo.setVerticalAlignment((short) 2);/*垂直居中*/
        styleInfo.setFont(font);/*设置字体样式*/
        styleInfo.setFillForegroundColor((short) 41);
        styleInfo.setFillPattern((short) 1);
        styleInfo.setBorderTop((short) 1);/*上边框*/
        styleInfo.setBorderBottom((short) 1);/*下边框*/
        styleInfo.setBorderLeft((short) 1);/*左边框*/
        styleInfo.setBorderRight((short) 1);/*右边框*/
        HSSFSheet sheet = wb.createSheet("被试作答详情");
        sheet.setDisplayGridlines(false);
        HSSFRow rowinfo = sheet.createRow(0);
        rowinfo.setHeight((short) (24 * 20));
//合并单元格
        CellRangeAddress cra = new CellRangeAddress(0, 0, 6, 7); // 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(cra);
// 使用RegionUtil类为合并后的单元格添加边框
        RegionUtil.setBorderBottom(1, cra, sheet, wb); // 下边框
        RegionUtil.setBorderLeft(1, cra, sheet, wb); // 左边框
        RegionUtil.setBorderRight(1, cra, sheet, wb); // 有边框
        RegionUtil.setBorderTop(1, cra, sheet, wb); // 上边框
        String[] arrinfo = {"姓名", "性别", "年龄", "受教育年限", "性取向", "身高", "体重"};
        for (int w = 0; w < arrinfo.length; w++) {
            HSSFCell cell = rowinfo.createCell(w);
            String value = "";
            if (w == 0) {
                value = myDataInfo.getName();
            } else if (w == 1) {
                value = myDataInfo.getSex();
            } else if (w == 2) {
                value = myDataInfo.getAge();
            } else if (w == 3) {
                value = myDataInfo.getYearsOfEducation();
            } else if (w == 4) {
                value = myDataInfo.getSexualOrientation();
            } else if (w == 5) {
                value = myDataInfo.getHeight();
            } else if (w == 6) {
                value = myDataInfo.getBodyWeight();
            }
            cell.setCellValue(arrinfo[w] + " : " + value);
            cell.setCellStyle(styleInfo);
        }
        HSSFRow row = sheet.createRow(1);
        row.setHeight((short) (24 * 20));
        String[] arr = {"ID", "创建时间", "试次", "回答", "试次呈现的时间点", "反应时", "试次间呈现内容", "试次间呈现内容时间点"};
        for (int i = 0; i < arr.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(arr[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, i == 0 ? (25 * 256) : (30 * 256));/*设置第一列的宽度是31个字符宽度*/
        }
        HSSFCellStyle styledata = wb.createCellStyle();
        styledata.setBorderTop((short) 1);/*上边框*/
        styledata.setBorderBottom((short) 1);/*下边框*/
        styledata.setBorderLeft((short) 1);/*左边框*/
        styledata.setBorderRight((short) 1);/*右边框*/
        List<AnswerContent> answerContents = myDataInfo.getAnswerContents();
        for (int n = 0; n < answerContents.size(); n++) {
            HSSFRow rows = sheet.createRow(n + 2);
            rows.setHeight((short) (20 * 20));
            AnswerContent ac = answerContents.get(n);
            for (int j = 0; j < arr.length; j++) {
                HSSFCell cell = rows.createCell(j);
                String values = "";
                if (j == 0) {
                    values = ac.getId();
                } else if (j == 1) {
                    values = ac.getCreateDate().toString();
                } else if (j == 2) {
                    values = ac.getTopic();
                } else if (j == 3) {
                    values = ac.getAnswer();
                } else if (j == 4) {
                    values = ac.getTitlePresentationTime();
                } else if (j == 5) {
                    values = ac.getSpentTime();
                } else if (j == 6) {
                    values = ac.getBetweenContent();
                } else if (j == 7) {
                    values = ac.getBetweenContentTime();
                }
                cell.setCellValue(values);
                cell.setCellStyle(styledata);
            }
        }
        return wb;
    }


}


