package com.guli.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Administrator
 * @create 2019-07-24 20:26
 */
public interface VideoService {
    String uploadVideo(MultipartFile file);
}
