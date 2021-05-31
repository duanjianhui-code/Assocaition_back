package com.assocaition.management.commons.aliyun;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * @author Duanjianhui
 * @date 2021-04-15 9:04 上午
 * @describe
 */
@Data
@Configuration
@Component
@PropertySource({"classpath:config/ali/oss-config.properties"})
public class OSSUtils {

    private static final Logger log = LoggerFactory.getLogger(OSSUtils.class);
    /**
     * 配置阿里云对象存储信息.配置文件路径：src/main/resources/config/aliyun/oss-config.properties
     */
    @Value("${oss.Endpoint}")
    private String ENDPOINT;

    @Value("${oss.AccessKeyId}")
    private String ACCESSKEYID;

    @Value("${oss.AccessKeySecret}")
    private String ACCESSKEYSECRET;

    @Value("${oss.BucketName}")
    private String BUCKETNAME;

    @Value("${oss.SufferUrl}")
    private String SUFFER_URL;

    //格式化时间
    private static SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");

    /**
     * 获取OSS连接
     * @return
     */
    public OSSClient getOSSClient(){
        //创建OSSClient对象
        OSSClient ossClient = new OSSClient(ENDPOINT,ACCESSKEYID,ACCESSKEYSECRET);
        if (ossClient.doesBucketExist(BUCKETNAME)){
            log.info("------------连接Bucket成功---------");
        }else {
            log.info("------------无Bucket，开始创建连接Bucket成功---------");
            //创建oss仓库
            CreateBucketRequest bucketRequest = new CreateBucketRequest(null);
            //设置仓库名称
            bucketRequest.setBucketName(BUCKETNAME);
            //设置权限，公共只读
            bucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
            ossClient.createBucket(bucketRequest);
            log.info("------------无Bucket，创建连接Bucket成功---------");
        }
        return ossClient;
    }

    /**
     *上传文件到oss
     * @param file 上传文件
     * @param businessType  文件类型
     * @return
     */
    public String uploadFile(MultipartFile file,String businessType){
        //获取连接
        OSSClient ossClient = this.getOSSClient();
        //获取文件的后缀名
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = getFileName(businessType, ext);
        String url = "";
        try {
            //通过ossClient 获取返回的URL
            ossClient.putObject(BUCKETNAME,fileName,new ByteArrayInputStream(file.getBytes()));
            url = SUFFER_URL + fileName;
            log.info("---------文件上传oss成功,地址为："+url);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("---------文件上传oss失败,发生异常------");
            return "";
        } finally {
            ossClient.shutdown();
        }
        return url;
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

    /**
     * 下载文件
     * @param url 文件的URL地址
     * @return
     */
    public byte[] downloadFile(String url) {
        byte[] result = null;
        String bucketUrl = getBucketUrl(url);
        OSSClient ossClient = this.getOSSClient();
        OSSObject ossObject= ossClient.getObject(BUCKETNAME, bucketUrl);
        if (ossObject != null) {
            InputStream is = ossObject.getObjectContent();
            result = InputStreamToByteArray(is);
        }
        ossClient.shutdown();
        return result;
    }

    private static String getBucketUrl(String url) {
        String BucketUrl= url.substring(url.lastIndexOf("oss-cn-beijing.aliyuncs.com/")+28,url.length());
        return BucketUrl;
    }


    public static byte[] InputStreamToByteArray(InputStream is) {
        // 1.创建源与目的的
        byte[] dest = null;// 在字节数组输出的时候是不需要源的。
        // 2.选择流，选择文件输入流
        ByteArrayOutputStream os = null;// 新增方法
        try {
            os = new ByteArrayOutputStream();
            // 3.操作,读文件
            byte[] flush = new byte[1024 * 10];// 10k，创建读取数据时的缓冲，每次读取的字节个数。
            int len = -1;// 接受长度；
            while ((len = is.read(flush)) != -1) {
                // 表示当还没有到文件的末尾时
                // 字符数组-->字符串，即是解码。
                os.write(flush, 0, len);// 将文件内容写出字节数组
            }
            os.flush();
            return os.toByteArray();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // 4.释放资源
            try {
                if (is != null) {// 表示当文打开时，才需要通知操作系统关闭
                    is.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 删除一个文件
     * @param url
     * @return
     */
    public boolean deleteFile(String url){
        boolean flag = false;
        String bucketUrl = getBucketUrl(url);
        OSSClient ossClient = this.getOSSClient();
        try {
            ossClient.deleteObject(BUCKETNAME, bucketUrl);
            flag = true;
        } catch (Exception e){
            e.printStackTrace();
            return flag;
        }
        ossClient.shutdown();
        return flag;
    }

    /**
     * 批量删除
     * @param urls
     * @return
     */
    public boolean deleteFiles(List<String> urls) {
        boolean flag = false;
        List<String> keys = new ArrayList<String>();
        for (String url :urls){
            keys.add(getBucketUrl(url));
        }
        OSSClient ossClient = this.getOSSClient();
        try {
            DeleteObjectsResult deleteObjectsResult =ossClient.deleteObjects(new DeleteObjectsRequest(BUCKETNAME).withKeys(keys));
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
            if (deletedObjects.size()>0){
                flag = true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        ossClient.shutdown();
        return flag;
    }
}
