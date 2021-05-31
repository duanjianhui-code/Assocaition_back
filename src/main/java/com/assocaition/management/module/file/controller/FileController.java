package com.assocaition.management.module.file.controller;


import com.assocaition.management.commons.ResponseResult;
import com.assocaition.management.commons.aliyun.OSSUtils;
import com.assocaition.management.module.authority.entity.AuthSysUser;
import com.assocaition.management.module.file.entity.AssocaitionFile;
import com.assocaition.management.module.file.service.FileService;
import com.assocaition.management.utils.UUIDUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author Duanjianhui
 * @date 2021-04-11 3:18 下午
 * @describe
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private OSSUtils ossUtils;

    @Autowired
    FileService fileService;

    //格式化时间
    private static SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");

    //文件上传
    @PostMapping("/uploadFile.do")
    @ResponseBody
    public Map<String,Object> uploadFile(MultipartFile file, HttpSession session, String businessType,String assId){

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        AuthSysUser userInfo = (AuthSysUser)session.getAttribute("userinfo");
        String ossUrl = ossUtils.uploadFile(file, businessType);
        //http://duanjianhui1998.oss-cn-beijing.aliyuncs.com/imager/20210501/941adaff-f80f-448f-ae00-aba98f1eb9c0.jpg
        String fileName = file.getOriginalFilename();
        String id = UUIDUtils.getUUID("file");
        AssocaitionFile assocaitionFile = new AssocaitionFile();
        assocaitionFile.setId(id);
        assocaitionFile.setFileName(fileName);
        assocaitionFile.setUploadTime(new Date());
        assocaitionFile.setType("1");
        assocaitionFile.setUploadUser(userInfo.getName());
        assocaitionFile.setIsDelete("1");
        assocaitionFile.setOssUrl(ossUrl);
        if (!StringUtils.isEmpty(assId)){
            assocaitionFile.setAssId(assId);
        }else {
            assocaitionFile.setAssId("default");
        }
        int i = fileService.insertFile(assocaitionFile);
        if (i==0){
            result.put("code",1);
            result.put("msg","上传失败");
            result.put("data",data);
            return result;
        }
        result.put("code",0);
        result.put("msg","上传成功");
        data.put("src",ossUrl);
        data.put("fileName",fileName);
        result.put("data",data);
        return result;
    }


    //文件下载
    @GetMapping("/downloadFile.do")
    public void downloadFile(String id, HttpServletResponse response){
        log.info("---------开始文件下载--------");

        /**
         * 数据库操作
         *
         *
         *
         */
        String url = "https://duanjianhui1998.oss-cn-beijing.aliyuncs.com/docx/20210416/dd1c78fb-a5e6-41e5-a17f-875a3d13feca.docx";
        String fileName = "测试文件.docx";
        byte[] bytes = ossUtils.downloadFile(url);
        ServletOutputStream outputStream = null;
        try {
            response.setHeader("Content-Type", "application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes(), "ISO8859-1"));

            outputStream = response.getOutputStream();
            outputStream.flush();
            outputStream.write(bytes, 0, bytes.length);

            outputStream.flush();
            outputStream.close();
            log.info("-------文件下载完成-------");

        } catch (Exception e){
            e.printStackTrace();
            log.info("-------文件下载失败，发生异常-------");
        } finally {

        }
    }

    @GetMapping("/downloadFileByUrl.do")
    public void downloadFileByUrl(String url,HttpServletResponse response) {
        int i = url.lastIndexOf("/");
        ResponseResult result = new ResponseResult();
        fileService.addCount();
        String fileName = url.substring(i+1, url.length());
        byte[] bytes = ossUtils.downloadFile(url);
        ServletOutputStream outputStream = null;
        try {
            response.setHeader("Content-Type", "application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes(), "ISO8859-1"));

            outputStream = response.getOutputStream();
            outputStream.flush();
            outputStream.write(bytes, 0, bytes.length);

            outputStream.flush();
            outputStream.close();
            log.info("-------文件下载完成-------");

        } catch (Exception e) {
            e.printStackTrace();
            log.info("-------文件下载失败，发生异常-------");
        } finally {
        }
    }

    /**
     * 删除文件
     * @param ids 文件的ID编号
     * @return
     */
    @GetMapping("/deleteFile.do")
    public ResponseResult deleteFile(String ids){
        ResponseResult result = new ResponseResult();
        boolean flag = false;
        try {
            if (ids.contains(",")) {
                List<String> idArray = Lists.newArrayList(ids.split(","));
                /**
                 * 数据库操作
                 *
                 *
                 */
                List<String> list = new ArrayList<>();
                flag = ossUtils.deleteFiles(list);
            } else {
                /**
                 * 数据库操作
                 *
                 *
                 */
                String url = "https://duanjianhui1998.oss-cn-beijing.aliyuncs.com/docx/20210416/b0279e68-fd81-4274-9233-b5862863a9ba.docx";
                flag = ossUtils.deleteFile(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(10000);
            result.setStatus("fail");
            log.info("------文件删除失败-------");
            return result;

        }
        if (!flag){
            result.setCode(10000);
            result.setStatus("fail");
            log.info("------文件删除失败-------");
        }else {
            log.info("------文件删除成功-------");
        }
        return result;
    }

    @PostMapping("/selectFiles.do")
    public ResponseResult selectFiles(){
        ResponseResult result = new ResponseResult();


        return result;
    }


    private String getFileName(String businessType, String ext) {
        //上传oss文件夹，通过fileName来指定
        String date = sd.format(new Date());
        if (StringUtils.isEmpty(businessType)){
            businessType = "default";
        }
        //生成唯一的ID
        String uuid = UUID.randomUUID().toString().replace("/","-");
        //组合fileName
        String fileName = businessType + "/" + date +"/" + uuid+ ext;
        return fileName;
    }
}
