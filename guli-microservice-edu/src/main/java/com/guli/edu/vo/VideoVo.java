package com.guli.edu.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 * @create 2019-07-25 10:21
 */
@ApiModel(value = "课时信息")
@Data
public class VideoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private Boolean free;

    //视频播放的videoId
    private String videoSourceId;
}
