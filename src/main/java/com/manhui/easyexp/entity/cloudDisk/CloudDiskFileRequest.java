package com.manhui.easyexp.entity.cloudDisk;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户云盘
 * 
 * @author zls 2020年7月21日
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class CloudDiskFileRequest  {

	@NotNull(message = "文件类型不能为空")
	@ApiModelProperty("0 文件夹 1文件' ")
	private Byte type;
	
	@ApiModelProperty("父节点")
	private String parentId;
	
	@ApiModelProperty("文件夹名称（type=0时有效）")
	private String fileName;
	
	@NotBlank(message = "实验批次名称不能为空")
	@ApiModelProperty("实验批次名称")
	private String batchName;
	
}
