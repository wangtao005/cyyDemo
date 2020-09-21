package com.manhui.easyexp.entity.feedback;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.manhui.easyexp.common.EasyexpEnum;
import com.manhui.easyexp.entity.myData.MyDataInfo;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.mics.jpa.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 带反馈实验
 */
@Data
@Entity
@Table(name = "t_feedback")
@Where(clause = "bln_del = 0")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Feedback extends BaseEntity {
    /**
     * 实验类别(0:无反馈实验;1:有反馈)
     */
    @Column(name = "experiment_type", nullable = false, columnDefinition = "tinyint(2) comment '实验类别' ")
    private EasyexpEnum.experimentTypeEnum experimentType;
    /**
     * 实验名称
     */
    @Column(name = "experiment_name", columnDefinition = "varchar(120) COMMENT '实验名称'")
    private String experimentName;
    /**
     * 是否全屏(0:全屏;1:非全屏)
     */
    @Column(name = "whether_full_screen", nullable = false, columnDefinition = "tinyint(2) comment '是否全屏' ")
    private EasyexpEnum.whetherFullScreenEnum whetherFullScreen;

    /**
     * 是否显示进度条(1:是;0:否)
     */
    @Column(name = "yes_or_no_view_progress_bar", nullable = false, columnDefinition = "tinyint(2) comment '是否显示进度条' ")
    private EasyexpEnum.yesOrNoEnum yesOrNoViewProgressBar;

    /**
     * 是否随机(1:是;0:否)
     */
    @Column(name = "yes_or_no_random", nullable = false, columnDefinition = "tinyint(2) comment '是否随机' ")
    private EasyexpEnum.yesOrNoEnum yesOrNoRandom;

    /**
     * 实验刺激呈现完之后再出现选项(1:是;0:否)
     */
    @Column(name = "yes_or_no_show_first_stimulate", nullable = false, columnDefinition = "tinyint(2) comment '实验刺激呈现完之后再出现选项' ")
    private EasyexpEnum.yesOrNoEnum yesOrNoShowFirstStimulate;

    /**
     * 反应设置是否立即在按键后结束(1:是;0:否)
     */
    @Column(name = "immediately_over", nullable = false, columnDefinition = "tinyint(2) comment '反应设置' ")
    private EasyexpEnum.yesOrNoEnum immediatelyOver;

    /**
     *被试基本信息.
     */
    @Column(name = "participant_basic_info", columnDefinition = "varchar(500) COMMENT '被试基本信息'")
    private String participantBasicInfo;

    /**
     *屏幕颜色
     */
    @Column(name = "screen_color", columnDefinition = "varchar(10) COMMENT '屏幕颜色'")
    private String screenColor;

    /**
     *字体颜色
     */
    @Column(name = "font_color", columnDefinition = "varchar(10) COMMENT '字体颜色'")
    private String fontColor;

    /**
     *字体大小
     */
    @Column(name = "font_size", columnDefinition = "varchar(10) COMMENT '字体大小写'")
    private String fontSize;

    /**
     * 指导语
     */
    @Column(name = "instruction", columnDefinition = "varchar(2000) COMMENT '指导语'")
    private String instruction;

    /**
     * 指导语类别(0:固定时长(毫秒);1:随机时间,时间段;2:等待用户)
     */
    @Column(name = "instruction_type", nullable = false, columnDefinition = "tinyint(2) comment '指导语类别' ")
    private EasyexpEnum.waitTypeEnum instructionType;
    /**
     * 指导语时长
     */
    @Column(name = "instruction_duration", columnDefinition = "varchar(500) COMMENT '指导语时长'")
    private String instructionDuration;

    /**
     * 结束语
     */
    @Column(name = "conclusion", columnDefinition = "varchar(2000) COMMENT '结束语'")
    private String conclusion;

    /**
     * 结束语类别(0:固定时长(毫秒);1:随机时间,时间段;2:等待用户)
     */
    @Column(name = "conclusion_type", nullable = false, columnDefinition = "tinyint(2) comment '结束语类别' ")
    private EasyexpEnum.waitTypeEnum conclusionType;
    /**
     * 结束语时长
     */
    @Column(name = "conclusion_duration", columnDefinition = "varchar(500) COMMENT '结束语时长'")
    private String conclusionDuration;


    /**
     * 刺激实验
     */
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "feedback_id")
    private List<FeedbackExperimentStimulate> feedbackExperimentStimulate;


    @Transient
    private String childIds;//呆删除的子数据id
    @Transient
    private Boolean locking;//锁定

}
