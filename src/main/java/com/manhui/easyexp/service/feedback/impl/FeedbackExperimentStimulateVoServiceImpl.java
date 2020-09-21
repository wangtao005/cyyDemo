package com.manhui.easyexp.service.feedback.impl;

import com.alibaba.fastjson.JSON;
import com.manhui.easyexp.common.EasyexpEnum;
import com.manhui.easyexp.dao.feedback.FeedbackExperimentStimulateDao;
import com.manhui.easyexp.entity.feedback.FeedbackExperimentStimulate;
import com.manhui.easyexp.service.feedback.FeedbackExperimentStimulateVoService;
import org.apache.commons.lang3.StringUtils;
import org.mics.log.annotation.OperatorLog;
import org.mics.token.model.CurrentUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class FeedbackExperimentStimulateVoServiceImpl implements FeedbackExperimentStimulateVoService {

    @Resource
    private FeedbackExperimentStimulateDao dao;

    @Override
    @OperatorLog("保存或修改对象")
    public FeedbackExperimentStimulate saveAndEdit(FeedbackExperimentStimulate entity, CurrentUser currentUser) {
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

    private static String DURATION = "6000";//固定呈现时长
    private static String LEAST_DURATION = "1000";//最短呈现时长
    private static String LONGEST_DURATION = "6000";//最长呈现时长

    @Override
    public Map<String, Object> importExcelDataFormat(String[][] data, String experimentType) {


        Map<String, Object> mapData = new HashMap<>();
        mapData.put("status", false);
        mapData.put("message", "");
        List<FeedbackExperimentStimulate> dataList = new ArrayList<>();

        for (int i = 0; i < data.length; i++) {
            String[] datum = data[i];
            FeedbackExperimentStimulate entity = new FeedbackExperimentStimulate();
            Map<String, Object> map = new HashMap<>();
            for (int j = 0; j < datum.length; j++) {
                String s = datum[j];
                s = s.replaceAll(" ", "");
                if (j == 0) {                                //刺激类型
                    if (StringUtils.isNotBlank(s)) {
                        EasyexpEnum.stimulateTypeEnum[] values = EasyexpEnum.stimulateTypeEnum.values();
                        for (EasyexpEnum.stimulateTypeEnum value : values) {
                            String name = value.name();
                            if (name.equals(s)) {
                                entity.setStimulateType(value);
                            }
                        }
                    } else {
                        String str = "第" + (i + 1) + "行第" + (j + 1) + "列不能为空";
                        mapData.put("message", str);
                        return mapData;
                    }
                }
                if (j == 1) {                                   //实验刺激
                    if (EasyexpEnum.stimulateTypeEnum.TEXT == entity.getStimulateType()) {
                        if (StringUtils.isBlank(s)) {
                            String str = "第" + (i + 1) + "行第" + (j + 1) + "列刺激类型为TEXT时不能为空";
                            mapData.put("message", str);
                            return mapData;
                        }
                    }
                    entity.setStimulateContentName(s);
                } else if (j == 2) {                             //url地址
                    if (EasyexpEnum.stimulateTypeEnum.IMG == entity.getStimulateType()) {
                        if (StringUtils.isBlank(s)) {
                            String str = "第" + (i + 1) + "行第" + (j + 1) + "列" + "地址不能为空";
                            mapData.put("message", str);
                            return mapData;
                        }
                        List<Map<String, String>> urlLisrt = new ArrayList<>();
                        String strarr[] = {".png", ".jpg", ".jpeg", ".bmp", ".gif"};
                        String[] split = s.split(",");
                        for (String s1 : split) {
                            Map<String, String> map1 = new HashMap<>();
                            strArr:
                            for (int k = 0; k < strarr.length; k++) {
                                String s2 = strarr[k];
                                boolean b = s1.endsWith(s2);
                                if (b) {
                                    map1.put("path", s1);
                                    urlLisrt.add(map1);
                                    break strArr;
                                }
                                if (k == strarr.length - 1) {
                                    String str = "第" + (i + 1) + "行第" + (j + 1) + "列" + "图片后缀名只能是{'.png', '.jpg', '.jpeg', '.bmp', '.gif'}";
                                    mapData.put("message", str);
                                    return mapData;
                                }
                            }
                        }
                        entity.setStimulateContentUrl(JSON.toJSONString(urlLisrt));
                    } else if (EasyexpEnum.stimulateTypeEnum.AUDIO_AND_VIDEO == entity.getStimulateType()) {
                        if (StringUtils.isBlank(s)) {
                            String str = "第" + (i + 1) + "行第" + (j + 1) + "列" + "地址不能为空";
                            mapData.put("message", str);
                            return mapData;
                        }

                        String strarr[] = {".mp4", ".m2v", ".mkv", ".rmvb", ".wmv", ".avi", ".flv", ".mov", ".m4v", ".mp3", ".wav", ".wmv"};
                        for (int k = 0; k < strarr.length; k++) {
                            String s1 = strarr[k];
                            if (s.endsWith(s1)) {
                                entity.setStimulateContentUrl(s);
                                break;
                            }
                            if (k == strarr.length - 1) {
                                String str = "第" + (i + 1) + "行第" + (j + 1) + "列" + "后缀名只能是{'.mp4', '.m2v', '.mkv', '.rmvb', '.wmv', '.avi', '.flv', '.mov', '.m4v','.mp3', '.wav', '.wmv'}";
                                mapData.put("message", str);
                                return mapData;
                            }
                        }
                    }
                } else if (j == 3) {                             //图片排列
                    if (EasyexpEnum.stimulateTypeEnum.IMG == entity.getStimulateType()) {
                        EasyexpEnum.positionTypeEnum[] values = EasyexpEnum.positionTypeEnum.values();
                        for (EasyexpEnum.positionTypeEnum value : values) {
                            String name = value.name();
                            if (name.equals(s)) {
                                entity.setPositionType(value);
                            }
                        }
                        EasyexpEnum.positionTypeEnum buttonType = entity.getPositionType();
                        if (buttonType == null) {
                            entity.setPositionType(EasyexpEnum.positionTypeEnum.UP_LEFT_RIGHT);
                        }
                    } else {
                        entity.setPositionType(EasyexpEnum.positionTypeEnum.UP_LEFT_RIGHT);
                    }
                } else if (j == 4) {                             //试次说明
                    entity.setTrialName(s);
                } else if (j == 5) {                            //反应时长类别
                    if (StringUtils.isBlank(s)) {
                        String str = "第" + (i + 1) + "行第" + (j + 1) + "列不能为空";
                        mapData.put("message", str);
                        return mapData;
                    }
                    EasyexpEnum.waitTypeEnum[] values = EasyexpEnum.waitTypeEnum.values();
                    for (EasyexpEnum.waitTypeEnum value : values) {
                        String name = value.name();
                        if (name.equals(s)) {
                            entity.setReactionPresentType(value);
                            continue;
                        }
                    }
                    EasyexpEnum.waitTypeEnum presentType = entity.getReactionPresentType();
                    if (presentType == null) {
                        String str = "第" + (i + 1) + "行第" + (j + 1) + "列参数不正确,请重新选择";
                        mapData.put("message", str);
                        return mapData;
                    }
                } else if (j == 6) {                                //反应时长设置
                    EasyexpEnum.waitTypeEnum reactionPresentType = entity.getReactionPresentType();
                    if (EasyexpEnum.waitTypeEnum.WAITING_FOR_COMPLETION != reactionPresentType) {
                        if (StringUtils.isBlank(s)) {
                            String str = "第" + (i + 1) + "行第" + (j + 1) + "列不能为空";
                            mapData.put("message", str);
                            return mapData;
                        } else {
                            String s1 = s.replaceAll(" ", "");
                            if (EasyexpEnum.waitTypeEnum.FIXED_DURATION == reactionPresentType) {
                                if (!s.matches("^[0-9]*$")) {
                                    String str = "第" + (i + 1) + "行第" + (j + 1) + "列请数字";
                                    mapData.put("message", str);
                                    return mapData;
                                }
                                long l = Long.parseLong(s1);
                                if (l <= 0) {
                                    String str = "第" + (i + 1) + "行第" + (j + 1) + "列请填写大于0的整数";
                                    mapData.put("message", str);
                                    return mapData;
                                } else {
                                    entity.setSpecifyTheReactionTime(s);
                                }
                            } else {
                                String[] split = null;
                                s1 = s1.replaceAll("，", ",");
                                if (s1.indexOf(",") > -1) {
                                    split = s1.split(",");
                                } else if (s1.indexOf("~") > -1) {
                                    split = s1.split("~");
                                } else {
                                    String str = "第" + (i + 1) + "行第" + (j + 1) + "列请填写大于0的整数,两个整数用','或'~'隔开";
                                    mapData.put("message", str);
                                    return mapData;
                                }
                                if (split.length == 2) {
                                    if (StringUtils.isNotBlank(split[0]) && StringUtils.isNotBlank(split[0])) {
                                        if (split[0].matches("^[0-9]*$") && split[1].matches("^[0-9]*$")) {
                                            long l1s = Long.parseLong(split[0]);
                                            long l2s = Long.parseLong(split[1]);
                                            if (l2s - l1s > 0) {
                                                entity.setReactionLeastDuration(split[0]);
                                                entity.setReactionLongestDuration(split[1]);
                                            } else {
                                                entity.setReactionLeastDuration(split[1]);
                                                entity.setReactionLongestDuration(split[0]);
                                            }
                                        } else {
                                            String str = "第" + (i + 1) + "行第" + (j + 1) + "列请填写大于0的整数";
                                            mapData.put("message", str);
                                            return mapData;
                                        }
                                    } else {
                                        String str = "第" + (i + 1) + "行第" + (j + 1) + "列请填写大于0的整数,两个整数用','或'~'隔开";
                                        mapData.put("message", str);
                                        return mapData;
                                    }
                                } else {
                                    String str = "第" + (i + 1) + "行第" + (j + 1) + "列填写两个数字并且用','或'~'隔开";
                                    mapData.put("message", str);
                                    return mapData;
                                }
                            }
                        }
                    }
                } else if (j == 7) {                             //呈现时长类型
                    if (StringUtils.isBlank(s)) {
                        String str = "第" + (i + 1) + "行第" + (j + 1) + "列不能为空";
                        mapData.put("message", str);
                        return mapData;
                    }
                    EasyexpEnum.waitTypeEnum[] values = EasyexpEnum.waitTypeEnum.values();
                    for (EasyexpEnum.waitTypeEnum value : values) {
                        String name = value.name();
                        if (name.equals(s)&&!"WAITING_FOR_COMPLETION".equals(s)) {
                            entity.setPresentType(value);
                            continue;
                        }
                    }
                    EasyexpEnum.waitTypeEnum presentType = entity.getPresentType();
                    if (presentType == null) {
                        String str = "第" + (i + 1) + "行第" + (j + 1) + "列参数不正确,请重新选择";
                        mapData.put("message", str);
                        return mapData;
                    }
                } else if (j == 8) {
                    EasyexpEnum.waitTypeEnum presentType = entity.getPresentType();
                    String s1 = s.replaceAll(" ", "");
                    if (EasyexpEnum.waitTypeEnum.FIXED_DURATION == presentType) {
                        if (!s.matches("^[0-9]*$")) {
                            String str = "第" + (i + 1) + "行第" + (j + 1) + "列请数字";
                            mapData.put("message", str);
                            return mapData;
                        }
                        long l = Long.parseLong(s1);
                        if (l <= 0) {
                            String str = "第" + (i + 1) + "行第" + (j + 1) + "列请填写大于0的整数";
                            mapData.put("message", str);
                            return mapData;
                        } else {
                            entity.setPresentDuration(s);
                        }

                    } else {
                        if (StringUtils.isNotBlank(s)) {
                            String[] split = null;
                            s = s.replaceAll("，", ",");
                            if (s.indexOf("~") > -1) {
                                split = s.split("~");
                            } else if (s.indexOf(",") > -1) {
                                split = s.split(",");
                            } else {
                                String str = "第" + (i + 1) + "行第" + (j + 1) + "列填写大于0的整数,两个整数用','或'~'隔开";
                                mapData.put("message", str);
                                return mapData;
                            }
                            if (split.length == 2) {
                                if (StringUtils.isNotBlank(split[0]) && StringUtils.isNotBlank(split[0])) {
                                    if (split[0].matches("^[0-9]*$") && split[1].matches("^[0-9]*$")) {
                                        long l1s = Long.parseLong(split[0]);
                                        long l2s = Long.parseLong(split[1]);
                                        if (l2s - l1s > 0) {
                                            entity.setLeastDuration(split[0]);
                                            entity.setLongestDuration(split[1]);
                                        } else {
                                            entity.setLeastDuration(split[1]);
                                            entity.setLongestDuration(split[0]);
                                        }
                                    } else {
                                        String str = "第" + (i + 1) + "行第" + (j + 1) + "列请填写大于0的整数";
                                        mapData.put("message", str);
                                        return mapData;
                                    }
                                } else {
                                    String str = "第" + (i + 1) + "行第" + (j + 1) + "列请填写大于0的整数,两个整数用','或'~'隔开";
                                    mapData.put("message", str);
                                    return mapData;
                                }
                            } else {
                                String str = "第" + (i + 1) + "行第" + (j + 1) + "列填写两个数字并且用','或'~'隔开";
                                mapData.put("message", str);
                                return mapData;
                            }
                        } else {
                            entity.setLeastDuration(LEAST_DURATION);
                            entity.setLongestDuration(LONGEST_DURATION);
                        }
                    }
                } else if (j == 9) {                             //呈现内容类型(十字注视,空白屏)
                    EasyexpEnum.trialContentTypeEnum[] values = EasyexpEnum.trialContentTypeEnum.values();
                    for (EasyexpEnum.trialContentTypeEnum value : values) {
                        String name = value.name();
                        if (name.equals(s)) {
                            entity.setTrialContentType(value);
                        }
                    }
                    EasyexpEnum.trialContentTypeEnum trialContentType = entity.getTrialContentType();
                    if (trialContentType == null) {
                        entity.setTrialContentType(EasyexpEnum.trialContentTypeEnum.CROSS_GAZE);
                    }
                } else if (j == 10) {                                //按键类型(待选项按钮,键盘,滑块,文字输入,鼠标轨迹)
                    EasyexpEnum.buttonTypeEnum[] values = EasyexpEnum.buttonTypeEnum.values();
                    for (EasyexpEnum.buttonTypeEnum value : values) {
                        String name = value.name();
                        if (name.equals(s)) {
                            entity.setButtonType(value);
                        }
                    }
                    EasyexpEnum.buttonTypeEnum buttonType = entity.getButtonType();
                    if (buttonType == null) {
                        entity.setButtonType(EasyexpEnum.buttonTypeEnum.BUTTON_WITH_OPTIONS);
                    }
                } else if (j == 11) {                                //按钮内容
                    EasyexpEnum.buttonTypeEnum buttonType = entity.getButtonType();

                    if (buttonType == EasyexpEnum.buttonTypeEnum.BUTTON_WITH_OPTIONS) {
                        List<Map<String, String>> list = new ArrayList<>();
                        if (StringUtils.isNotBlank(s)) {
                            s = s.replaceAll("，", ",");
                            String[] split = s.split(",");
                            for (String s1 : split) {
                                Map<String, String> map1 = new HashMap<>();
                                map1.put("name", s1);
                                list.add(map1);
                            }
                        } else {
                            Map<String, String> map1 = new HashMap<>();
                            map1.put("name", "选项1");
                            Map<String, String> map2 = new HashMap<>();
                            map2.put("name", "选项2");
                            list.add(map1);
                            list.add(map2);
                        }
                        String s1 = JSON.toJSONString(list);
                        entity.setButtonContent(s1);
                    } else if (buttonType == EasyexpEnum.buttonTypeEnum.KEYBOARD) {
                        List<Map<String, String>> list = new ArrayList<>();
                        if (StringUtils.isNotBlank(s)) {
                            s = s.replaceAll("，", ",");
                            if (s.indexOf(":") > -1) {
                                String[] split = s.split(",");
                                for (String s1 : split) {
                                    String[] strings = s1.split(":");
                                    if (strings[0].length() == 1) {
                                        if (Character.isDigit(strings[0].charAt(0)) || Character.isLowerCase(strings[0].charAt(0))) {
                                            Map<String, String> map1 = new HashMap<>();
                                            map1.put("name", strings[1]);
                                            map1.put("keyUp", strings[0]);
                                            list.add(map1);
                                        }
                                    } else {
                                        list.clear();
                                        defaultValue(list);
                                        continue;
                                    }
                                }
                            } else {
                                defaultValue(list);
                            }
                        } else {
                            defaultValue(list);
                        }
                        entity.setButtonContent(JSON.toJSONString(list));
                    } else if (buttonType == EasyexpEnum.buttonTypeEnum.SLIDER) {
                        Map<String, String> map1 = new HashMap<>();
                        if (StringUtils.isNotBlank(s)) {
                            s = s.replaceAll("，", ",");
                            if (s.indexOf(",") > -1) {
                                String[] split = s.split(",");
                                map1.put("min", split[0]);
                                map1.put("max", split[1]);
                            } else {
                                map1.put("min", "0");
                                map1.put("max", "100");
                            }
                        } else {
                            map1.put("min", "0");
                            map1.put("max", "100");
                        }
                        String s1 = JSON.toJSONString(map1);
                        entity.setButtonContent(s1);
                    }
                } else if (j == 12) {                                // 正确答案
                    if (StringUtils.isNotBlank(s)) {
                        entity.setCorrectAnswer(s);
                    }
                } else if (j == 13) {  // 实验逻辑类别

                    if(EasyexpEnum.experimentTypeEnum.NO_FEEDBACK.name().equals(experimentType)){
                        continue;
                    }

                    if (EasyexpEnum.buttonTypeEnum.MOUSE_AND_MOUSE_TRACK == entity.getButtonType()) { //获取按键类型
                        if (!EasyexpEnum.experimentalLogicTypeEnum.REACTION_TIME.name().equals(s)) { //如果按键类型是鼠标轨迹那么实验逻辑只能 REACTION_TIME根据反应时长来反馈
                            String str = "第" + (i + 1) + "行第" + (j + 1) + "列按键类型是'MOUSE_AND_MOUSE_TRACK'那么实验逻辑类型只能是'REACTION_TIME'";
                            mapData.put("message", str);
                            return mapData;
                        }
                    }
                    EasyexpEnum.experimentalLogicTypeEnum[] values = EasyexpEnum.experimentalLogicTypeEnum.values();
                    for (EasyexpEnum.experimentalLogicTypeEnum value : values) {
                        String name = value.name();
                        if (name.equals(s)) {
                            entity.setExperimentalLogicType(value);
                        }
                    }
                    if (StringUtils.isBlank(s)) {
                        String str = "第" + (i + 1) + "行第" + (j + 1) + "列不能为空";
                        mapData.put("message", str);
                        return mapData;
                    }

                } else if (j == 19 || j == 14 || j == 15 || j == 16 || j == 17 || j == 18) {
                    if (StringUtils.isNotBlank(experimentType)) {                                                     //有反馈实验需要验证的内容
                        if (EasyexpEnum.experimentTypeEnum.WITH_FEEDBACK.name().equals(experimentType)) {
                            if (StringUtils.isBlank(s)) {
                                String str = "第" + (i + 1) + "行第" + (j + 1) + "列在有反馈实验中不能为空";
                                mapData.put("message", str);
                                return mapData;
                            } else {
                                if (EasyexpEnum.experimentalLogicTypeEnum.REACTION_TIME == entity.getExperimentalLogicType()) {//根据反映时长做出反馈
                                    //比较符号只能是<>
                                    if (j == 14) {
                                        if ("if<".equals(s)) {
                                            map.put("value", false);
                                        } else if ("if>".equals(s)) {
                                            map.put("value", true);
                                        } else {
                                            String str = "第" + (i + 1) + "行第" + (j + 1) + "列比较符号错误,只能是'if<'或者'if>'";
                                            mapData.put("message", str);
                                            return mapData;
                                        }
                                    } else if (j == 15) {
                                        if (StringUtils.isNotBlank(s)) {
                                            String regEx1 = "\\d+";
                                            Pattern compile = Pattern.compile(regEx1);
                                            Matcher matcher = compile.matcher(s);
                                            if (matcher.matches()) {
                                                long l = Long.parseLong(s);
                                                map.put("timeCost", l);
                                            } else {
                                                String str = "第" + (i + 1) + "行第" + (j + 1) + "列只能是正整数";
                                                mapData.put("message", str);
                                                return mapData;
                                            }
                                        } else {
                                            String str = "第" + (i + 1) + "行第" + (j + 1) + "列只能是正整数";
                                            mapData.put("message", str);
                                            return mapData;
                                        }
                                    }
                                } else {
                                    //比较符号只能是=
                                    if (j == 14) {
                                        if (!"if=".equals(s)) {
                                            String str = "第" + (i + 1) + "行第" + (j + 1) + "列比较符号错误,只能是'if=''";
                                            mapData.put("message", str);
                                            return mapData;
                                        }

                                    } else if (j == 15) {
                                        map.put("answer", s);
                                    }
                                }

                                if (j == 16 || j == 18) {
                                    if (StringUtils.isNotBlank(s)) {
                                        if (j == 18) {
                                            map.put("type2", s);
                                        } else {
                                            map.put("type", s);
                                        }
                                    } else {
                                        String str = "第" + (i + 1) + "行第" + (j + 1) + "列只能是'(TEXT)'或者'(IMG)'或者'AUDIO_AND_VIDEO'";
                                        mapData.put("message", str);
                                        return mapData;
                                    }

                                } else if (j == 17) {
                                    String type = (String) map.get("type");
                                    if ("IMG".equals(type)) {
                                        s = s.replaceAll("，", ",");
                                        String[] strings = s.split(",");
                                        List<Map<String, String>> lm = getList(strings);
                                        map.put("imgUrl", JSON.toJSONString(lm));
                                    } else if ("AUDIO_AND_VIDEO".equals(type)) {
                                        map.put("audioAndVideoUrl", s);
                                    } else {
                                        map.put("text", s);
                                    }
                                } else if (j == 19) {
                                    String type = (String) map.get("type2");
                                    if ("IMG".equals(type)) {
                                        s = s.replaceAll("，", ",");
                                        String[] strings = s.split(",");
                                        List<Map<String, String>> lm = getList(strings);
                                        map.put("imgUrl2", JSON.toJSONString(lm));
                                    } else if ("AUDIO_AND_VIDEO".equals(type)) {
                                        map.put("audioAndVideoUrl2", s);
                                    } else {
                                        map.put("text2", s);
                                    }
                                }
                            }
                        }
                    }
                    if (j == 19) {
                        if (EasyexpEnum.experimentTypeEnum.WITH_FEEDBACK.name().equals(experimentType)) {
                            entity.setExperimentalLogicContent(JSON.toJSONString(map));
                        }
                    }
                }
            }
            dataList.add(entity);
            entity.setCode(i + "");
        }
        mapData.put("status", true);
        mapData.put("data", dataList);
        return mapData;
    }

    private List<Map<String, String>> getList(String[] strings) {
        List<Map<String, String>> lm = new ArrayList<>();
        for (String string : strings) {
            if (StringUtils.isNotBlank(string)) {
                Map<String, String> lmap = new HashMap<>();
                lmap.put("path", string);
                lm.add(lmap);
            }
        }
        return lm;
    }

    private void defaultValue(List<Map<String, String>> list) {
        Map<String, String> map = new HashMap<>();
        map.put("name", "是");
        map.put("keyUp", "k");
        Map<String, String> map1 = new HashMap<>();
        map1.put("name", "否");
        map1.put("keyUp", "j");
        list.add(map);
        list.add(map1);
    }

}
