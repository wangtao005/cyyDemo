package com.manhui.easyexp.service.cloudDisk;

import java.util.List;

import org.mics.core.response.PageDataResponse;
import org.springframework.web.multipart.MultipartFile;

import com.manhui.easyexp.entity.cloudDisk.CloudDiskFile;
import com.manhui.easyexp.entity.cloudDisk.CloudDiskFileVO;

public interface ICloudDiskFileService {

	/**
	 * 查询详情
	 * author zls
	 * 2020年7月21日
	 */
	CloudDiskFileVO getInfo(String id);

	/**
	 * 添加数据
	 * author zls
	 * 2020年7月21日
	 */
	List<CloudDiskFileVO> save(CloudDiskFile entity,MultipartFile file);

	/**
	 * 根据父ID获取列表
	 * author zls
	 * 2020年7月21日
	 */
	List<CloudDiskFileVO> getListByParentId(String parentId, String userId);

	/**
	 * 获取分页所有文件列表数据
	 * author zls
	 * 2020年7月21日
	 */
	PageDataResponse<CloudDiskFileVO> getListByPage(Integer pageNo, Integer pageSize,String userId);

	void remove(String id, String userId);

}