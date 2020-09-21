package com.manhui.easyexp.dao.cloudDisk;

import java.util.List;

import org.mics.jpa.repository.BaseRepository;

import com.manhui.easyexp.entity.cloudDisk.CloudDiskFile;

public interface CloudDiskFileDao extends BaseRepository<CloudDiskFile> {

	List<CloudDiskFile> getByParentId(String parentId);
}
