package com.manhui.easyexp.service.cloudDisk.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.mics.core.page.PageInfo;
import org.mics.core.response.PageDataResponse;
import org.mics.lang.bean.BeanConvertUtil;
import org.mics.lang.calculate.SnowflakeIdWorker;
import org.mics.lang.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.manhui.easyexp.dao.cloudDisk.CloudDiskFileDao;
import com.manhui.easyexp.entity.cloudDisk.CloudDiskFile;
import com.manhui.easyexp.entity.cloudDisk.CloudDiskFileVO;
import com.manhui.easyexp.service.cloudDisk.ICloudDiskFileService;

/**
 * 云盘管理
 * @author zls
 * 2020年7月21日
 */
@Service
public class CloudDiskFileServiceImpl implements ICloudDiskFileService {

	@Resource
	private CloudDiskFileDao cloudDiskFileDao;
	

	 @Value("${file_upload_path}")
	 private String uploadPath;
	/**
	 * 查询详情
	 * author zls
	 * 2020年7月21日
	 */
	@Override
	public CloudDiskFileVO getInfo(String id) {
		// 查询信息
		CloudDiskFile entity =  cloudDiskFileDao.getOne(id);
		if(entity==null) {
			throw new CustomException("文件不存在");
		}
		return BeanConvertUtil.convert(entity, CloudDiskFileVO.class);
	}

	/**
	 * 删除文件
	 * author zls
	 * 2020年7月22日
	 */
	@Override
	@Transactional
	public void remove(String id,String userId) {
		CloudDiskFile entity =  cloudDiskFileDao.getOne(id);
		if(entity==null) {
			throw new CustomException("文件不存在");
		}
		if(!entity.getUserId().equals(userId)) {
			throw new CustomException("没有权限删除该文件");
		}
		cloudDiskFileDao.deleteById(id);
		//删除子文件及文件夹数据
		deleteChildren(id);
		String name = entity.getFileUrl().replaceAll("/",Matcher.quoteReplacement(File.separator));
		File file = new File(uploadPath,name);
		//删除子文件及文件夹
		deleteDirectory(file);
	}
	/**
	 * 删除子文件及文件夹数据
	 * author zls
	 * 2020年7月22日
	 */
	private void deleteChildren(String parentId) {
		List<CloudDiskFile> list =  cloudDiskFileDao.getByParentId(parentId);
		if(!CollectionUtils.isEmpty(list)) {
			for(int i=0;i<list.size();i++) {
				deleteChildren(list.get(i).getId());
				cloudDiskFileDao.delete(list.get(i));
			}
		}
		
	}
	/**
	 * 删除子文件及文件夹
	 * author zls
	 * 2020年7月22日
	 */
	private void deleteDirectory(File file) {
		if(file.isDirectory()) {
			File[] f = file.listFiles();
			for(int i=0;i<f.length;i++) {
				deleteDirectory(f[i]);
			}
			
		}
		file.delete();
		
	}
	
	/**
	 * 添加数据
	 * author zls
	 * 2020年7月21日
	 */
	@Override
	@Transactional
	public List<CloudDiskFileVO> save(CloudDiskFile entity,MultipartFile file) {
		
		List<CloudDiskFileVO> voList = new ArrayList<>();
		String parentDocument = null;
		//保存文件
		if(StringUtils.isNotBlank(entity.getParentId())) {
			CloudDiskFile obj = cloudDiskFileDao.getOne(entity.getParentId());
			if(obj==null) {
				throw new CustomException("父文件夹不存在");
			}
			parentDocument = obj.getFileUrl();
		}else {
			parentDocument = entity.getUserId();
		}
		File document = new File(uploadPath+parentDocument.replaceAll("/",Matcher.quoteReplacement(File.separator)));
		if(!document.exists()) {
			document.mkdirs();
		}
		File fileObject = null;
		if(entity.getType()==1) {
			fileObject = new File(document, file.getOriginalFilename());
			//判断是否是ZIP
			if(file.getOriginalFilename().toLowerCase().endsWith("zip")) {
				try {
					 try (RandomAccessFile randomFile = new RandomAccessFile(fileObject.getPath(), "rw")) {// 防止断点续传
			                randomFile.write(file.getBytes());
			            }
					List<Map<String,Object>> list = CloudDiskFileServiceImpl.zipUncompress(fileObject,fileObject.getParent(),true);
					for(int i=0;i<list.size();i++) {
						CloudDiskFile cdf = BeanConvertUtil.convert(entity, CloudDiskFile.class);
						cdf.setFileUrl(parentDocument+"/"+list.get(i).get("fileName"));
						cdf.setSize((long)(list.get(i).get("size")));
						cdf.setFileName(list.get(i).get("originalFileName").toString());
						cdf = cloudDiskFileDao.save(cdf);
						voList.add(BeanConvertUtil.convert(cdf, CloudDiskFileVO.class));
					}
					
				} catch (Exception e) {
					throw new CustomException("解压文件出错",e);
				}
				return voList;
			}else {
				
				
				try (RandomAccessFile randomFile = new RandomAccessFile(fileObject.getPath(), "rw")) {// 防止断点续传
			                randomFile.write(file.getBytes());
			    } catch (IOException e) {
					throw new CustomException("上传文件出错",e);
				}
				entity.setFileName(fileObject.getName());
				entity.setSize(fileObject.length());
			}
			
		}else {
			fileObject = new File(document, entity.getFileName());
			if(!fileObject.exists()) {
				fileObject.mkdirs();
			}
			entity.setSize(0L);
		}
		entity.setFileUrl(parentDocument+"/"+fileObject.getName());
		
		CloudDiskFile obj =  cloudDiskFileDao.save(entity);
		voList.add(BeanConvertUtil.convert(obj, CloudDiskFileVO.class));
		
		return voList;
	}

	/**
	 * 根据父ID获取列表
	 * author zls
	 * 2020年7月21日
	 */
	@Override
	public List<CloudDiskFileVO> getListByParentId(String parentId,String userId){
		//规格定义
        Specification<CloudDiskFile> specification = new Specification<CloudDiskFile>() {
            /**
			 * author zls
			 */
			private static final long serialVersionUID = -8785309034575411806L;

			/**
             * 构造断言
             * @param root 实体对象引用
             * @param query 规则查询对象
             * @param cb 规则构建对象
             * @return 断言
             */
            @Override
            public Predicate toPredicate(Root<CloudDiskFile> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	List<Predicate> predicates = new ArrayList<>(); //所有的断言
            	if(StringUtils.isNotBlank(parentId)) {
            		Predicate where1 = cb.equal(root.get("parentId").as(String.class), parentId);
            		predicates.add(where1);
            	}else {
            		cb.isNull(root.get("parentId").as(String.class));
            	}
            	Predicate where2 = cb.equal(root.get("userId").as(String.class), userId);
				predicates.add(where2);
                return query.where(predicates.toArray(new Predicate[predicates.size()])).orderBy(new OrderImpl(root.get("type").as(Byte.class)),new OrderImpl(root.get("createDate").as(Date.class),false)).getRestriction();
            }
        };
        
        List<CloudDiskFile> list = this.cloudDiskFileDao.findAll(specification);
        //转换
        return BeanConvertUtil.convertList(list, CloudDiskFileVO.class);
	}
	
	/**
	 * 获取分页所有文件列表数据
	 * author zls
	 * 2020年7月21日
	 */
	@Override
	public PageDataResponse<CloudDiskFileVO> getListByPage(Integer pageNo, Integer pageSize,String userId){
		
		//规格定义
        Specification<CloudDiskFile> specification = new Specification<CloudDiskFile>() {
            /**
			 * author zls
			 */
			private static final long serialVersionUID = -8785309034575411806L;

			/**
             * 构造断言
             * @param root 实体对象引用
             * @param query 规则查询对象
             * @param cb 规则构建对象
             * @return 断言
             */
            @Override
            public Predicate toPredicate(Root<CloudDiskFile> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	List<Predicate> predicates = new ArrayList<>(); //所有的断言
            	Predicate end = cb.equal(root.get("type").as(Byte.class), 1);
                predicates.add(end);
                Predicate user = cb.equal(root.get("userId").as(String.class), userId);
                predicates.add(user);
                
                return query.where(predicates.toArray(new Predicate[predicates.size()])).orderBy(new OrderImpl(root.get("createDate").as(Date.class),false)).getRestriction();
            }
        };
        
       Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.Direction.DESC, "createDate");
        //查询
       Page<CloudDiskFile> page = this.cloudDiskFileDao.findAll(specification,pageable);
       PageInfo pageInfo = new PageInfo();
       pageInfo.setPageNo(page.getNumber());
       pageInfo.setPageSize(page.getSize());
       pageInfo.setTotalCount(page.getTotalElements());
       pageInfo.setTotalPage(page.getTotalPages());
       List<CloudDiskFileVO> pageList = BeanConvertUtil.convertList(page.getContent(), CloudDiskFileVO.class);
      return new PageDataResponse<>(pageInfo,pageList);
		
	}
	
	public static List<Map<String,Object>> zipUncompress(File srcFile,String destDirPath,boolean delSrc) throws Exception {
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new CustomException("解压文件不存在");
        }
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        String fileEncode = EncodeUtil.getEncode(srcFile.getPath(),true);
        try(ZipFile zipFile = new ZipFile(srcFile,Charset.forName(fileEncode))){//创建压缩文件对象
	        //开始解压
	        Enumeration<?> entries = zipFile.entries();
	        while (entries.hasMoreElements()) {
	            ZipEntry entry = (ZipEntry) entries.nextElement();
	            // 如果是文件夹，就创建个文件夹
	            String fileName = entry.getName();
	            
	            if(fileName.lastIndexOf("/")>-1) {
            		fileName  = fileName.substring(fileName.lastIndexOf("/")+1);
	            }
	            if(fileName.lastIndexOf("\\")>-1) {
            		fileName  = fileName.substring(fileName.lastIndexOf("\\")+1);
	            }
	            
	            if (!entry.isDirectory()) {
	            	Map<String,Object> map = new HashMap<String, Object>();
	                // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
	                File targetFile = new File(destDirPath + File.separator + SnowflakeIdWorker.getInstance().nextId()+fileName.substring(fileName.lastIndexOf(".")));
	                targetFile.createNewFile();
	                // 将压缩文件内容写入到这个文件中
	                InputStream is = null;
	                FileOutputStream fos = null;
	                try {
		                is = zipFile.getInputStream(entry);
		                fos = new FileOutputStream(targetFile);
		                int len;
		                byte[] buf = new byte[1024];
		                while ((len = is.read(buf)) != -1) {
		                    fos.write(buf, 0, len);
		                }
	                }finally {
	                	if(fos!=null)
	                	fos.close();
	                	if(is!=null)
	                    is.close();
	                }
	                map.put("size",targetFile.length());
	                map.put("fileName", targetFile.getName());
	                map.put("originalFileName", fileName);
	                list.add(map);
	            }
	        }
        }
        if(delSrc) {
        	srcFile.delete();
        }
        return list;
    }


}

class EncodeUtil {
	private static Logger logger = LoggerFactory.getLogger(EncodeUtil.class);	
 
	private static int BYTE_SIZE = 8;
    public static String CODE_UTF8 = "UTF-8";
    public static String CODE_UTF8_BOM = "UTF-8_BOM";
    public static String CODE_GBK = "GBK";
 
    /**
     * 通过文件全名称获取编码集名称
     *
     * @param fullFileName
     * @param ignoreBom
     * @return
     * @throws Exception
     */
    public static String getEncode(String fullFileName, boolean ignoreBom) throws Exception {
    	logger.debug("fullFileName ; {}", fullFileName);
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fullFileName))){
        	return getEncode(bis, ignoreBom);
        }
    }
 
    /**
     * 通过文件缓存流获取编码集名称，文件流必须为未曾
     *
     * @param bis
     * @param ignoreBom 是否忽略utf-8 bom
     * @return
     * @throws Exception
     */
    public static String getEncode(BufferedInputStream bis, boolean ignoreBom) throws Exception {
        bis.mark(0);
 
        String encodeType = "未识别";
        byte[] head = new byte[3];
        bis.read(head);
        if (head[0] == -1 && head[1] == -2) {
            encodeType = "UTF-16";
        } else if (head[0] == -2 && head[1] == -1) {
            encodeType = "Unicode";
        } else if (head[0] == -17 && head[1] == -69 && head[2] == -65) { //带BOM
            if (ignoreBom) {
                encodeType = CODE_UTF8;
            } else {
                encodeType = CODE_UTF8_BOM;
            }
        } else if ("Unicode".equals(encodeType)) {
            encodeType = "UTF-16";
        } else if (isUTF8(bis)) {
            encodeType = CODE_UTF8;
        } else {
            encodeType = CODE_GBK;
        }
        logger.info("result encode type : " + encodeType);
        return encodeType;
    }
 
    /**
     * 是否是无BOM的UTF8格式，不判断常规场景，只区分无BOM UTF8和GBK
     *
     * @param bis
     * @return
     */
    private static boolean isUTF8( BufferedInputStream bis) throws Exception {
        bis.reset();
 
        //读取第一个字节
        int code = bis.read();
        do {
            BitSet bitSet = convert2BitSet(code);
            //判断是否为单字节
            if (bitSet.get(0)) {//多字节时，再读取N个字节
                if (!checkMultiByte(bis, bitSet)) {//未检测通过,直接返回
                    return false;
                }
            } else {
                //单字节时什么都不用做，再次读取字节
            }
            code = bis.read();
        } while (code != -1);
        return true;
    }
 
    /**
     * 检测多字节，判断是否为utf8，已经读取了一个字节
     *
     * @param bis
     * @param bitSet
     * @return
     */
    private static boolean checkMultiByte(BufferedInputStream bis, BitSet bitSet) throws Exception {
        int count = getCountOfSequential(bitSet);
        byte[] bytes = new byte[count - 1];//已经读取了一个字节，不能再读取
        bis.read(bytes);
        for (byte b : bytes) {
            if (!checkUtf8Byte(b)) {
                return false;
            }
        }
        return true;
    }
 
    /**
     * 检测单字节，判断是否为utf8
     *
     * @param b
     * @return
     */
    private static boolean checkUtf8Byte(byte b) {
        BitSet bitSet = convert2BitSet(b);
        return bitSet.get(0) && !bitSet.get(1);
    }
 
    /**
     * 检测bitSet中从开始有多少个连续的1
     *
     * @param bitSet
     * @return
     */
    private static int getCountOfSequential( BitSet bitSet) {
        int count = 0;
        for (int i = 0; i < BYTE_SIZE; i++) {
            if (bitSet.get(i)) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }
 
 
    /**
     * 将整形转为BitSet
     *
     * @param code
     * @return
     */
    private static BitSet convert2BitSet(int code) {
        BitSet bitSet = new BitSet(BYTE_SIZE);
 
        for (int i = 0; i < BYTE_SIZE; i++) {
            int tmp3 = code >> (BYTE_SIZE - i - 1);
            int tmp2 = 0x1 & tmp3;
            if (tmp2 == 1) {
                bitSet.set(i);
            }
        }
        return bitSet;
    }
 
    /**
     * 将一指定编码的文件转换为另一编码的文件
     *
     * @param oldFullFileName
     * @param oldCharsetName
     * @param newFullFileName
     * @param newCharsetName
     */
    public static void convert(String oldFullFileName, String oldCharsetName, String newFullFileName, String newCharsetName) throws Exception {
    	logger.info("the old file name is : {}, The oldCharsetName is : {}", oldFullFileName, oldCharsetName);
    	logger.info("the new file name is : {}, The newCharsetName is : {}", newFullFileName, newCharsetName);
 
        StringBuffer content = new StringBuffer();
 
        try(BufferedReader bin = new BufferedReader(new InputStreamReader(new FileInputStream(oldFullFileName), oldCharsetName))){
	        String line;
	        while ((line = bin.readLine()) != null) {
	            content.append(line);
	            content.append(System.getProperty("line.separator"));
	        }
        }
        newFullFileName = newFullFileName.replace("\\", "/");
        File dir = new File(newFullFileName.substring(0, newFullFileName.lastIndexOf("/")));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try(Writer out = new OutputStreamWriter(new FileOutputStream(newFullFileName), newCharsetName)){
        	out.write(content.toString());
        }
    }
 
}

