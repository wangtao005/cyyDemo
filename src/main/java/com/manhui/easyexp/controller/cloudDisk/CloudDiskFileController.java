package com.manhui.easyexp.controller.cloudDisk;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.mics.core.response.OneDataResponse;
import org.mics.core.response.SimpleResponse;
import org.mics.lang.bean.BeanConvertUtil;
import org.mics.token.annotation.LoginUser;
import org.mics.token.model.CurrentUser;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.manhui.easyexp.entity.cloudDisk.CloudDiskFile;
import com.manhui.easyexp.entity.cloudDisk.CloudDiskFileRequest;
import com.manhui.easyexp.entity.cloudDisk.CloudDiskFileVO;
import com.manhui.easyexp.service.cloudDisk.ICloudDiskFileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/cloudDisk")
@Api(tags = "[客户端]-云盘")
public class CloudDiskFileController {

	@Resource
	private ICloudDiskFileService cloudDiskFileService;

	/**
	 * 添加文件或文件夹
	 * author zls
	 * 2020年7月22日
	 */
	@PostMapping("/save")
	@ApiOperation(value = "添加文件或文件夹")
	public SimpleResponse save(@Validated CloudDiskFileRequest request,BindingResult bindingResult,@ApiParam("文件") MultipartFile file,@LoginUser CurrentUser currentUser) {
		
		List<ObjectError> allErrors = bindingResult.getAllErrors();
		StringBuilder sb = new StringBuilder();
        for (ObjectError error : allErrors) {
            String defaultMessage = error.getDefaultMessage();
            sb.append(defaultMessage);
        }
		if(sb.length()>0) {
			return new OneDataResponse<String>(200,sb.toString(),"操作失败");
		}	
        if(request.getType()==0&&StringUtils.isBlank(request.getFileName())) {
        	return new OneDataResponse<String>(200,"文件夹名称不能为空","操作失败");
        }
		CloudDiskFile entity = BeanConvertUtil.convert(request, CloudDiskFile.class);
		entity.setUserId(currentUser.getId());
		
		return new OneDataResponse<>(cloudDiskFileService.save(entity, file));

	}
	
	@PostMapping("/remove")
    @ApiOperation(value = "删除")
	public SimpleResponse remove(String id,@LoginUser CurrentUser currentUser) {
		cloudDiskFileService.remove(id,currentUser.getId());
		return new OneDataResponse<>("删除成功");
	}
	
	
	@PostMapping("/getListByPage")
    @ApiOperation(value = "分页列表")
	public SimpleResponse getListByPage(Integer pageNo, Integer pageSize,@LoginUser CurrentUser currentUser) {
		return cloudDiskFileService.getListByPage(pageNo,pageSize,currentUser.getId());
	}
	
	@PostMapping("/getInfo")
    @ApiOperation(value = "文件信息")
	public SimpleResponse getInfo(String id) {
		return new OneDataResponse<CloudDiskFileVO>(cloudDiskFileService.getInfo(id));
		
	}
	
	@PostMapping("/getListByParentId")
    @ApiOperation(value = "根据父ID获取列表")
	public SimpleResponse getListByParentId(String parentId,@LoginUser CurrentUser currentUser) {
		return new OneDataResponse<>(cloudDiskFileService.getListByParentId(parentId,currentUser.getId()));
	}

}
