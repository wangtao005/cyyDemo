package com.manhui.easyexp.entity.feedback;

import com.manhui.easyexp.common.EasyexpEnum;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.mics.jpa.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_feedback_experiment_stimulate")
@Where(clause = "bln_del = 0")
public class FeedbackExperimentStimulate  extends BaseEntity {



    /**
     * code
     */
    @Column(name = "code", columnDefinition = "varchar(11) COMMENT '排序字段'")
    private String code;

    /**
     * 刺激类别(0:文字;1:图片;2:音视频)
     */
    @Column(name = "stimulate_type", nullable = false, columnDefinition = "tinyint(2) comment '刺激类别' ")
    private EasyexpEnum.stimulateTypeEnum stimulateType;

    /**
     * 实验刺激内容名称
     */
    @Column(name = "stimulate_content_name", columnDefinition = "varchar(300) COMMENT '实验刺激内容名称'")
    private String stimulateContentName;

    /**
     * 实验刺激内容url
     */
    @Column(name = "stimulate_content_url", columnDefinition = "varchar(3000) COMMENT '实验刺激内容url'")
    private String stimulateContentUrl;

    /**
     * 试次名称(试次说明)
     */
    @Column(name = "trial_name", columnDefinition = "varchar(300) COMMENT '试次名称'")
    private String trialName;

    /**
     *呈现时长类别(0:固定时长(毫秒);1:随机时间,时间段;2:等待被试看完)
     */
    @Column(name = "present_type", nullable = false, columnDefinition = "tinyint(2) comment '呈现类别' ")
    private EasyexpEnum.waitTypeEnum presentType;

    /**
     *反应时长类别(0:固定时长(毫秒);1:随机时间,时间段;2:等待被试看完)
     */
    @Column(name = "reaction_present_type", nullable = false, columnDefinition = "tinyint(2) comment '呈现类别' ")
    private EasyexpEnum.waitTypeEnum reactionPresentType;

    /**
     *反应最少时长
     */
    @Column(name = "reaction_least_duration", columnDefinition = "varchar(500) COMMENT '反应最少时长'")
    private String reactionLeastDuration;
    /**
     *反应最长时长
     */
    @Column(name = "reaction_longest_duration", columnDefinition = "varchar(500) COMMENT '反应最长时长'")
    private String reactionLongestDuration;

    /**
     *呈现位置(0:上左右);1:中横;2:中竖)
     */
    @Column(name = "position_type", nullable = false, columnDefinition = "tinyint(2) comment '呈现位置' ")
    private EasyexpEnum.positionTypeEnum positionType;

    /**
     *呈现时长
     */
    @Column(name = "present_duration", columnDefinition = "varchar(500) COMMENT '呈现时长'")
    private String presentDuration;
    /**
     *最少时长
     */
    @Column(name = "least_duration", columnDefinition = "varchar(500) COMMENT '最少时长'")
    private String leastDuration;
    /**
     *最长时长
     */
    @Column(name = "longest_duration", columnDefinition = "varchar(500) COMMENT '最长时长'")
    private String longestDuration;

    /**
     *正确答案
     */
    @Column(name = "correct_answer", columnDefinition = "varchar(500) COMMENT '正确答案'")
    private String correctAnswer;
    /**
     *规定反应时长
     */
    @Column(name = "specify_the_reaction_time", columnDefinition = "varchar(200) COMMENT '规定反应时长'")
    private String specifyTheReactionTime;

    /**
     * 试次间隔内容类别(0:十字注视点;1:空白屏幕)
     */
    @Column(name = "trial_content_type", nullable = false, columnDefinition = "tinyint(2) comment '试次间隔内容类别' ")
    private EasyexpEnum.trialContentTypeEnum trialContentType;

//    /**
//     * 试次间隔呈现类别(0:固定时长(毫秒);1:随机时间,时间段;2:等待用户)
//     */
//    @Column(name = "trial_present_type", nullable = false, columnDefinition = "tinyint(2) comment '试次间隔呈现类别' ")
//    private EasyexpEnum.waitTypeEnum trialPresentType;

    /**
     * 按键类别(0:带选项的按钮;1:键盘;2:滑块;3:文字输入;4:鼠标与鼠标轨迹)
     */
    @Column(name = "button_type", nullable = false, columnDefinition = "tinyint(2) comment '试按键类别' ")
    private EasyexpEnum.buttonTypeEnum buttonType;

    /**
     * 按键内容
     */
    @Column(name = "button_content", columnDefinition = "varchar(500) COMMENT '按键内容'")
    private String buttonContent;


    /**
     * 实验逻辑类别(0:根据反应时长来反馈;1:根据被试上一次的回答来给反馈)
     */
    @Column(name = "experimental_logic_type", columnDefinition = "tinyint(2) comment '实验逻辑类别' ")
    private EasyexpEnum.experimentalLogicTypeEnum experimentalLogicType;

    /**
     * 实验逻辑内容
     */
    @Column(name = "experimental_logic_content", columnDefinition = "varchar(5000) COMMENT '实验逻辑内容'")
    private String experimentalLogicContent;

}
