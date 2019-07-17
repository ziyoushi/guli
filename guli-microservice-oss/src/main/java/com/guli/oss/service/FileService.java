package com.guli.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Administrator
 * @create 2019-07-17 16:51
 */
public interface FileService {
    String upload(MultipartFile file);
}
