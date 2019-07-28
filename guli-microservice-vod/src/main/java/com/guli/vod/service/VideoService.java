package com.guli.vod.service;

import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Administrator
 * @create 2019-07-24 20:26
 */
public interface VideoService {

    String uploadVideo(MultipartFile file);

    void removeVideo(String videoId);

    //创建视频播放对象 前端调用可以直接播放视频
    //获取视频上传地址和凭证
    CreateUploadVideoResponse getUploadAuthAndAddress(String title, String fileName);

    //刷新上传凭证
    RefreshUploadVideoResponse refreshUploadAuthAndAddress(String videoId);
}
