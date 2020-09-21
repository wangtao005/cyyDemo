package com.manhui.easyexp.entity.vip.request;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.mics.jpa.entity.BaseEntity;


/**
 * 会员信息
 * @author zls
 * 2020年6月29日
 */
@Entity
@Table(name = "t_vip_level")
@Where(clause = "bln_del = 0")
public class VipLevelDO extends BaseEntity implements Serializable {

    /**
	 * author zls
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	    * 会员等级编号
	    */
	@Column(name = "level", nullable = false, columnDefinition = "tinyint(2) comment '会员等级' ")
	private Byte level;
    
	
	/**
	 * 会员等级名称
	 */
    @Column(name = "level_name", columnDefinition = "varchar(30) comment '会员等级名' ")
    private String levelName;
    

    /**
     * 会员时间
     */
    @Column(name = "duration", columnDefinition = "int(11) comment '时长' ")
    private Integer duration;
    
    /**
     * 该等级的金额
     */
    @Column(name = "amount", columnDefinition = "decimal(18,2) comment '金额（元）' ")
    private BigDecimal amount;
    
    @Column(name = "type", columnDefinition = "tinyint(4) comment '0 免费 1付费' ")
    private Byte type;

	public Byte getLevel() {
		return level;
	}

	public void setLevel(Byte level) {
		this.level = level;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}
    
   
    
}

