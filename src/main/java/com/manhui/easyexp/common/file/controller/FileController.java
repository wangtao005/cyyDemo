package com.manhui.easyexp.common.file.controller;

import com.alibaba.fastjson.JSONObject;
import com.manhui.easyexp.EasyexpApplication;
import com.manhui.easyexp.common.file.services.FileService;
import org.apache.commons.codec.binary.Base64;
import org.mics.core.response.OneDataResponse;
import org.mics.core.response.SimpleResponse;
import org.mics.token.annotation.IgnoreToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/file")
public class FileController {

    private static final String FILEPREVIEW = "FILEPREVIEW";
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @Resource
    private FileService fileservice;
    private static String fileUploadPath;


    @Value("${file_upload_path}")
    public void setFileUploadPath(String fileUploadPath) {
        FileController.fileUploadPath = fileUploadPath;
    }


    /**
     * 下载
     *
     * @throws IOException
     */
    @IgnoreToken
    @RequestMapping("/downloadExcel")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {

        InputStream inputStream = null;
        byte[] buffer = null;
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {//判断系统是windows还是linux
            URL url = new ClassPathResource("static/excelFile/exp_template.xls").getURL();
            File file = new File(url.getFile());
            inputStream = new FileInputStream(file);
        } else {
            InputStream inst = EasyexpApplication.class.getClassLoader().getResourceAsStream("static/excelFile/exp_template.xls");
            buffer = new byte[inst.available()];
            inst.read(buffer);
            inst.close();
            // 清空response
            response.reset();
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download;charset=utf-8");
        response.setHeader("Content-disposition", URLEncoder.encode("easyxp", "UTF-8"));
        //mime类型
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("exp_template", "UTF-8"));
        response.setHeader("Pragma", "No-cache");
        //从内存中写出来
        OutputStream outputStream = response.getOutputStream();
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            int len = 0;
            byte[] b = new byte[1024 * 100];
            while ((len = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, len);
            }
        } else {
            outputStream.write(buffer);
        }
        //释放inputstream
        try {
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 通过路径下载
     *
     * @param response
     * @throws IOException
     * @作者:wt
     * @日期:2019年3月12日 上午10:45:01
     */
    @RequestMapping("/downloadByUrl")
    public void downloadByUrl(HttpServletRequest request, HttpServletResponse response, String name, String url) throws IOException {

        url = new String(new Base64().decode(url));
        name = new String(new Base64().decode(name));
        long length = 0L;
        //判断是本地路径还是网上路径
        String regEx = "[a-zA-z]+://[^\\s]*";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(url);
        boolean rs = matcher.matches();
        InputStream inputStream = null;
        if (rs) {
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            inputStream = conn.getInputStream();
            length = conn.getContentLengthLong();
        } else {//本地文件
            inputStream = new FileInputStream(url);
            length = inputStream.available();
        }

        // 清空response
        response.reset();
        // 设置response的Header
        response.addHeader("Content-Disposition",
                "attachment;filename=" + setFileDownloadHeader(request, name));
        response.addHeader("Content-Length", "" + length);
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        int len = 0;
        byte[] b = new byte[1024 * 100];
        while ((len = inputStream.read(b)) != -1) {
            toClient.write(b, 0, len);
        }
        //释放inputstream
        try {
            inputStream.close();
        } catch (Exception e) {
        }
        toClient.flush();
        toClient.close();
    }

    /**
     * 实现文件上传
     *
     */
    @RequestMapping("/editorFileUpload")
    @ResponseBody
    public Map<String, Object> editorFileUpload(MultipartFile file, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> upload = upload(file, request);
        ArrayList<String> list = new ArrayList<>();
        Object status = upload.get("status");
        if (status.equals(false)) {
            map.put("errno", 1);
        } else {
            map.put("errno", 0);//成功
            String path = upload.get("path").toString();
            // String url2=request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+"/upload"+path;
            list.add(path);
        }
        map.put("data", list);
        return map;
    }

    /**
     * 实现文件上传
     *
     */
    @IgnoreToken
    @RequestMapping("/fileUpload")
    @ResponseBody
    public Map<String, Object> fileUpload(MultipartFile file, HttpServletRequest request) {
        Map<String, Object> upload = upload(file, request);
        Object status = upload.get("status");
        if (status.equals(true)) {
            String path = upload.get("path").toString();
            //   String url2=request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+"/upload"+path;
            upload.put("path", path);
        }
        return upload;
    }

    /**
     * 实现文件上传
     */
    public static Map<String, Object> upload(MultipartFile file, HttpServletRequest request) {
//		Properties properties = new Properties();
//		properties.load(FileController.class.getResourceAsStream("/application.properties"));
        String fileName = file.getOriginalFilename();
        int lastIndexOf = fileName.lastIndexOf(".");
        String fileType = fileName.substring(lastIndexOf + 1);

        double fileSize = Math.round(file.getSize() / 1024);// kb

//		String fileUploadPath = fileUploadPath;
        Map<String, Object> map = new HashMap<String, Object>();
        if (file.isEmpty()) {
            map.put("status", false);
            map.put("msg", "上传文件为空");
            map.put("path", "");
            map.put("fileType", "");
            map.put("fileSize", "");
            map.put("fileName", "");
            return map;
        }
        String separator = File.separator;

        Calendar cal = Calendar.getInstance();
        String dstr = "/" + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DATE);
        File dir = new File(fileUploadPath + dstr);
        if (!dir.exists()) {
            dir.mkdirs();
        }

//        File path1 = new File(fileUploadPath);
//        if (!path1.exists()) {
//            path1 = new File("");
//        }
        fileName = fileName.substring(fileName.lastIndexOf(separator) + 1);
        if (fileName.lastIndexOf(".") == -1) {
            map.put("status", false);
            map.put("msg", "上传文件为空");
            map.put("path", "");
            map.put("fileType", "");
            map.put("fileSize", "");
            map.put("fileName", "");
            return map;
        }
        // String newFileName = getUUID() + extName;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        String dateStr = sdf.format(new Date());
//        String realPath2 = fileUploadPath + dateStr + File.separator +"."+fileType;
        String strLong = System.currentTimeMillis() + "." + fileType;
        String realPath2 = fileUploadPath + dstr + File.separator + strLong;
        File dest = new File(realPath2);
        File parent = dest.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        try {

            try (RandomAccessFile randomFile = new RandomAccessFile(realPath2, "rw")) {// 防止断点续传
                randomFile.seek(randomFile.length());
                randomFile.write(file.getBytes());
                randomFile.close();
            }


            map.put("status", true);
            map.put("msg", "上传文件成功");
            map.put("fileType", fileType);
            map.put("fileSize", fileSize);
            map.put("path", dstr + "/" + strLong);
            map.put("fileName", fileName);
            return map;
        } catch (IOException e) {
            logger.error("上传文件出错", e);
            map.put("status", false);
            map.put("msg", e.getMessage());
            map.put("path", "");
            map.put("fileType", "");
            map.put("fileSize", "");
            map.put("fileName", fileName);
        }
        throw new RuntimeException("upload error");
    }

    /**
     * 删除服务器上指定路径的文件
     */
    @RequestMapping("/delFile")
    @ResponseBody
    public boolean delFile(String fileList) {
        try {
            JSONObject jsStr = JSONObject.parseObject(fileList);
            Set<String> keySet = jsStr.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                String str = jsStr.get(next).toString();
                JSONObject parseObject = JSONObject.parseObject(str);
                Set<String> keySet2 = parseObject.keySet();
                for (String string : keySet2) {
                    if ("fileUrl".equals(string)) {
                        String fileUrl = parseObject.get(string).toString();
                        File file = new File(fileUrl);
                        boolean delete = file.delete();
                    }
                }
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除文件信息
     */
    @RequestMapping("/delFileInfo")
    @ResponseBody
    public SimpleResponse idelFileInfo(String id) {
        try {
            fileservice.delFileEntity(id);
            return new OneDataResponse<String>("删除成功!");
        } catch (Exception e) {
            return new OneDataResponse<String>("删除失败!");
        }
    }

    /**
     * UUid
     *
     * @return
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

    public static String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}


