package com.manhui.easyexp.common.file.entity;

import org.hibernate.annotations.Where;
import org.mics.jpa.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_file")
@Where(clause = "bln_del = 0")
public class FileEntity extends BaseEntity {

    /**
     * 表序号
     */
    @Column(name = "table_id", columnDefinition = "varchar(1) COMMENT '表序号'")
    private String tableId;
    /**
     * 表名
     */
    @Column(name = "table_name", columnDefinition = "varchar(1) COMMENT '表名'")
    private String tableName;
    /**
     * 附件地址
     */
    @Column(name = "file_url", columnDefinition = "varchar(1) COMMENT '附件地址'")
    private String fileUrl;

    /**
     * 附件类型
     */
    @Column(name = "file_type", columnDefinition = "varchar(1) COMMENT '附件类型'")
    private String fileType;
    /**
     * 附件大小
     */
    @Column(name = "file_size", columnDefinition = "varchar(1) COMMENT '附件大小'")
    private String fileSize;

    /**
     * 附件名称
     */
    @Column(name = "file_name", columnDefinition = "varchar(1) COMMENT '附件名称'")
    private String fileName;
    /**
     * 文件标识code
     */
    @Column(name = "code", columnDefinition = "varchar(1) COMMENT '文件标识code'")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}
