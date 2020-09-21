package com.manhui.easyexp.entity.cloudDisk;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户云盘
 * 
 * @author zls 2020年7月21日
 */
@ApiModel("用户云盘")
@EqualsAndHashCode(callSuper = false)
@Data
public class CloudDiskFileVO {

	@ApiModelProperty("编号ID")
	private String id;
	
	@ApiModelProperty("创建 时间")
	private Date createDate;
	
	@ApiModelProperty("用户ID")
	private String userId;

	@ApiModelProperty("类型 0文件夹 1文件")
	private Byte type;
	
	@ApiModelProperty("父节点Id")
	private String parentId;
	
	@ApiModelProperty("文件名称 ")
	private String fileName;
	
	@ApiModelProperty("文件路径")
	private String fileUrl;
	
	@ApiModelProperty("文件大小byte ")
	private Long size;
	
	@ApiModelProperty("批次名称 ")
	private String batchName;
	
	
	
}
