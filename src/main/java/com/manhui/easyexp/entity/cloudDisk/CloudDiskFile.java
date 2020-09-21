package com.manhui.easyexp.entity.cloudDisk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.mics.jpa.entity.BaseEntity;

/**
 * 用户云盘
 * 
 * @author zls 2020年7月21日
 */
@Entity
@Table(name = "t_cloud_disk_file")
@Where(clause = "bln_del = 0")
public class CloudDiskFile extends BaseEntity{


	/**
	 * 用户ID（关联用户表）
	 */
	@Column(name = "user_id",  columnDefinition = "varchar(30) comment '用户ID' ")
	private String userId;

	@Column(name = "type",  columnDefinition = "tinyint(2) comment '0 文件夹 1文件' ")
	private Byte type;
	
	@Column(name = "parentId",  columnDefinition = "varchar(32) comment '父节点' ")
	private String parentId;
	
	@Column(name = "file_name",  columnDefinition = "varchar(100) comment '名称' ")
	private String fileName;
	
	@Column(name = "file_url",  columnDefinition = "varchar(500) comment '路径' ")
	private String fileUrl;
	
	@Column(name = "size",  columnDefinition = "bigint(20) comment '文件大小byte' ")
	private Long size;
	
	@Column(name = "batch_name",  columnDefinition = "varchar(50) comment '批次名称' ")
	private String batchName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	
}
