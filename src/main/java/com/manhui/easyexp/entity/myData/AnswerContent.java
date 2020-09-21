package com.manhui.easyexp.entity.myData;

import lombok.Data;
import org.hibernate.annotations.Where;
import org.mics.jpa.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 *  答题内容
 */
@Data
@Entity
@Table(name = "t_answer_content")
@Where(clause = "bln_del = 0")
public class AnswerContent extends BaseEntity {


    /**
     * 所用时长
     */
    @Column(name = "spent_time", columnDefinition = "varchar(50) COMMENT '所用时间'")
    private String spentTime;
    /**
     * 所用时长
     */
    @Column(name = "title_presentation_time", columnDefinition = "varchar(50) COMMENT '题目呈现时间'")
    private String titlePresentationTime;

    /**
     * 做题题目
     */
    @Column(name = "topic", columnDefinition = "varchar(500) COMMENT '做题题目'")
    private String topic;

    /**
     * 答案
     */
    @Column(name = "answer", columnDefinition = "varchar(10000) COMMENT '答案'")
    private String answer;

    /**
     * 小题Id
     */
    @Column(name = "fes_id", columnDefinition = "varchar(64) COMMENT '小题Id'")
    private String fesId;
    /**
     * 试次间呈现内容
     */
    @Column(name = "between_Content", columnDefinition = "varchar(64) COMMENT '试次间呈现内容'")
    private String betweenContent;
    /**
     * 试次间呈现内容时间点
     */
    @Column(name = "between_Content_time", columnDefinition = "varchar(64) COMMENT '试次间呈现内容时间点'")
    private String betweenContentTime;


}
