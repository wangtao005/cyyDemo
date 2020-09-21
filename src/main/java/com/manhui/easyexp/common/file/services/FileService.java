package com.manhui.easyexp.common.file.services;

import com.manhui.easyexp.common.file.entity.FileEntity;

import java.util.List;

public interface FileService {


    /**
     * 保存
     * @作者:wt
     * @日期:2019年4月23日 上午11:37:09
     * @return
     */
    FileEntity getById(String id);

    /**
     * 根据id查询
     * @作者:wt
     * @日期:2019年4月23日 上午11:37:09
     * @param id
     * @return
     */
    FileEntity getFileEntityById(String id);
    /**
     * 根据id删除
     * @作者:wt
     * @日期:2019年4月23日 上午11:37:09
     * @param id
     * @return
     */
    void delFileEntity(String id);

    /**
     * 通过表的id和表名查询表对应数据附件集合
     * @作者:wt
     * @日期:2019年4月23日 下午12:11:19
     * @param tableId
     * @param tableName
     * @return
     */
    List<FileEntity> listFileByTableIdAndName(String tableId, String tableName);


    /**
     * 添加或修改
     * 作者:wt
     * 日期:2019年5月9日
     * @param files
     * @param tableId
     * @param tableName
     * @param delIds
     * @return
     */
    boolean addOrEdit(String files,String tableId,String tableName,String delIds);

}
