package com.manhui.easyexp.common.file.dao;

import com.manhui.easyexp.common.file.entity.FileEntity;
import org.mics.jpa.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDao  extends BaseRepository<FileEntity> {

}
