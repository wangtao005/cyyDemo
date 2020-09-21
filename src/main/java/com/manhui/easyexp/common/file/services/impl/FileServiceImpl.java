package com.manhui.easyexp.common.file.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.manhui.easyexp.common.file.dao.FileDao;
import com.manhui.easyexp.common.file.entity.FileEntity;
import com.manhui.easyexp.common.file.services.FileService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FileDao fileDao;

    @Override
    public FileEntity getFileEntityById(String id) {
        return this.getById(id);
    }

    private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public void delFileEntity(String id) {
        fileDao.deleteById(id);
    }

    @Override
    public List<FileEntity> listFileByTableIdAndName(String tableId, String tableName) {
        if(StringUtils.isEmpty(tableId)) {
            return new ArrayList<>(0);
        }
        Specification<FileEntity> spec = new Specification<FileEntity>() {
            @Override
            public Predicate toPredicate(Root<FileEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> lstPredicates = new ArrayList<Predicate>();
                if (!StringUtils.isEmpty(tableId)) {
                    Predicate p1 = criteriaBuilder.equal(root.get("tableId").as(String.class), tableId);
                    lstPredicates.add(p1);
                }
                if (!StringUtils.isEmpty(tableName)) {
                    Predicate p1 = criteriaBuilder.equal(root.get("tableName").as(String.class), tableName);
                    lstPredicates.add(p1);
                }
                Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
                return criteriaBuilder.and(lstPredicates.toArray(arrayPredicates));
            }
        };
        List<FileEntity> list= fileDao.findAll(spec);
        return list;
    }



    @Override
    public boolean addOrEdit(String files, String tableId, String tableName, String delIds) {

        boolean save = true;
        boolean editSave = true;
        // 删除
        if (!StringUtils.isEmpty(tableName)) {
            if (!StringUtils.isEmpty(delIds)) {
                String[] split = delIds.split(",");
                Set<FileEntity> delFiles = new HashSet<FileEntity>();
                for (String str : split) {
//                    String parseString = String.parseString(str);
                    FileEntity byId = this.getById(str);
                    String tableName2 = byId.getTableName();
                    if (!tableName.equals(tableName2)) {
                        return false;
                    }
                    delFiles.add(byId);
                }
                fileDao.deleteInBatch(delFiles);
            }
        }
        // 如果fileID为空就新添加
        if (!StringUtils.isEmpty(files)) {
            JSONObject jsStr = JSONObject.parseObject(files);
            Set<String> keySet = jsStr.keySet();
            List<Integer> listS = new ArrayList<Integer>();
            LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
            for (String str : keySet) {
                int indexOf = files.indexOf(str);
                map.put(indexOf, str);
                listS.add(indexOf);
            }
            Collections.sort(listS);
            Iterator<Integer> iterator = listS.listIterator();
            List<FileEntity> list = new ArrayList<FileEntity>();
            List<FileEntity> editlist = new ArrayList<FileEntity>();
            while (iterator.hasNext()) {
                Integer mynext = iterator.next();
                String next = map.get(mynext);
                String str = jsStr.get(next).toString();
                JSONObject parseObject = JSONObject.parseObject(str);
                Set<String> keySet2 = parseObject.keySet();
                boolean flag = true;
                FileEntity fileEntity = new FileEntity();
                fileEntity.setTableId(tableId);
                fileEntity.setTableName(tableName);
                for (String string : keySet2) {
                    if ("fileId".equals(string)) {
                        String object = parseObject.get(string).toString();
                        if (object != null) {
                            fileEntity.setId(object);
                            flag = false;
                        }
                    } else if ("fileName".equals(string)) {
                        String object = parseObject.get(string).toString();
                        fileEntity.setFileName(object);
                    } else if ("fileType".equals(string)) {
                        String object = parseObject.get(string).toString();
                        fileEntity.setFileType(object);
                    } else if ("fileSize".equals(string)) {
                        String object = parseObject.get(string).toString();
                        fileEntity.setFileSize(object);
                    } else if ("fileUrl".equals(string)) {
                        String object = parseObject.get(string).toString();
                        fileEntity.setFileUrl(object);
                    } else if ("code".equals(string)) {
                        String object = parseObject.get(string).toString();
                        fileEntity.setCode(object);
                    }
                }
                if (flag) {
                    list.add(fileEntity);
                } else {
                    editlist.add(fileEntity);
                }

            }
            if (!editlist.isEmpty()) {
                List<FileEntity> fileEntities = fileDao.saveAll(editlist);
                if(fileEntities==null){
                    editSave = false;
                }
            }
            if (!list.isEmpty()) {

                List<FileEntity> fileEntities = fileDao.saveAll(list);
                if(fileEntities==null){
                    save = false;
                }
            }

            return editSave && save;

        }
        return save;
    }

    @Override
    public FileEntity getById(String id) {
        return fileDao.getOne(id);
    }



}
