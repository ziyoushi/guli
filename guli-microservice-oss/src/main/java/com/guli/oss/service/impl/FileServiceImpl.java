package com.guli.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.guli.oss.service.FileService;
import com.guli.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author Administrator
 * @create 2019-07-17 16:53
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upload(MultipartFile file) {


        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，
        // 请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        String fileHost = ConstantPropertiesUtil.FILE_HOST;

        String uploadUrl = null;

        try {
            // 上传文件流。
            InputStream inputStream = file.getInputStream();

            //构建日期路径
            String filePath = new DateTime().toString("yyyy/MM/dd");

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            String originalName = file.getOriginalFilename().toString();
            String fileType = originalName.substring(originalName.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString();
            String fileUrl = fileHost + "/" + filePath + "/"+ fileName + fileType;

            ossClient.putObject(bucketName, fileUrl, inputStream);

            //oss图片案例
            //https://cguli-190222.oss-cn-hangzhou.aliyuncs.com/avatar/timg.jpg
            uploadUrl = "https://" +bucketName +"." + endpoint + "/" + fileUrl;

            // 关闭OSSClient。
            ossClient.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadUrl;
    }
}
