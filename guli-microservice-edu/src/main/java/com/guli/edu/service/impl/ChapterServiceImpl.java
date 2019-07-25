package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.edu.entity.Chapter;
import com.guli.edu.entity.Video;
import com.guli.edu.mapper.ChapterMapper;
import com.guli.edu.mapper.VideoMapper;
import com.guli.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.edu.vo.ChapterVo;
import com.guli.edu.vo.VideoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author changchen
 * @since 2019-07-12
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoMapper videoMapper;

    //因为video表中关联章节
    @Override
    public void removeChapterById(String id) {

        //根据章节id删除video
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id",id);
        videoMapper.delete(queryWrapper);

        baseMapper.deleteById(id);

    }

    //业务理解 章节(chapter)中有课时(video)
    @Override
    public List<ChapterVo> nestedList(String courseId) {

        List<ChapterVo> chapterVoList = new ArrayList<>();

        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        chapterQueryWrapper.orderByAsc("sort","id");
        List<Chapter> chapters = baseMapper.selectList(chapterQueryWrapper);


        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        videoQueryWrapper.orderByAsc("sort","id");
        List<Video> videos = videoMapper.selectList(videoQueryWrapper);

        //数据封装
        for (Chapter chapter : chapters) {

            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);

            List<VideoVo> videoVoList = new ArrayList<>();

            for (Video video : videos) {

                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(video,videoVo);

                if (video.getChapterId().equals(chapter.getId())){
                    videoVoList.add(videoVo);
                }

            }
            chapterVo.setChildren(videoVoList);

            chapterVoList.add(chapterVo);

        }

        return chapterVoList;
    }
}
