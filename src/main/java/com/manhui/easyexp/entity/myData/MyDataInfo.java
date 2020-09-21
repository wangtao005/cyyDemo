package com.manhui.easyexp.entity.myData;

import lombok.Data;
import org.hibernate.annotations.Where;
import org.mics.jpa.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 *  我的数据
 */
@Data
@Entity
@Table(name = "t_my_data_info")
@Where(clause = "bln_del = 0")
public class MyDataInfo extends BaseEntity {

    /**
     * 提交时间
     */
    @Column(name = "submit_time", columnDefinition = "varchar(500) COMMENT '提交时间'")
    @Temporal(TemporalType.DATE)
    private Date submitTime;
    /**
     * 所用时长
     */
    @Column(name = "spent_time", columnDefinition = "varchar(50) COMMENT '所用时间'")
    private String spentTime;
    /**
     * 来源
     */
    @Column(name = "source", columnDefinition = "varchar(500) COMMENT '来源'")
    private String source;
    /**
     * 来源详情
     */
    @Column(name = "source_info", columnDefinition = "varchar(500) COMMENT '来源详情'")
    private String sourceInfo;
    /**
     * 来自IP
     */
    @Column(name = "from_ip", columnDefinition = "varchar(500) COMMENT '来自IP'")
    private String fromIp;

    /**
     * 实验Id
     */
    @Column(name = "feedback_id", columnDefinition = "varchar(64) COMMENT '实验Id'")
    private String feedbackId;
    /**
     * 姓名
     */
    @Column(name = "name", columnDefinition = "varchar(64) COMMENT '姓名'")
    private String name;
    /**
     * 性别
     */
    @Column(name = "sex", columnDefinition = "varchar(64) COMMENT '性别'")
    private String sex;
    /**
     * 年龄
     */
    @Column(name = "age", columnDefinition = "varchar(64) COMMENT '年龄'")
    private String age;
    /**
     * 受教育年限
     */
    @Column(name = "yearsOfEducation", columnDefinition = "varchar(64) COMMENT '受教育年限'")
    private String yearsOfEducation;
    /**
     * 性取向
     */
    @Column(name = "sexualOrientation", columnDefinition = "varchar(120) COMMENT '性取向'")
    private String sexualOrientation;
    /**
     * 身高
     */
    @Column(name = "height", columnDefinition = "varchar(64) COMMENT '身高体重'")
    private String height;
    /**
     * 体重
     */
    @Column(name = "bodyWeight", columnDefinition = "varchar(64) COMMENT '体重'")
    private String bodyWeight;

    /**
     * 答题详情
     */
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "my_data_info_id")
    private List<AnswerContent> answerContents;





}
