package com.guli.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.common.vo.R;
import com.guli.statistics.client.UcenterClient;
import com.guli.statistics.entity.Daily;
import com.guli.statistics.mapper.DailyMapper;
import com.guli.statistics.service.DailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author changchen
 * @since 2019-07-22
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void createStatisticsByDay(String day) {
        //删除已存在的统计对象
        //根据统计日期(date_calculated)筛选数据
        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated",day);
        baseMapper.delete(queryWrapper);

        //获取统计信息 远程调用
        R r = ucenterClient.registerCount(day);
        Integer countRegister = (Integer) r.getData().get("countRegister");

        //创建统计对象
        Daily daily = new Daily();
        //注册人数
        daily.setRegisterNum(countRegister);
        //登录人数
        daily.setLoginNum(RandomUtils.nextInt(100,200));
        //每日播放视频数
        daily.setVideoViewNum(RandomUtils.nextInt(100,200));
        //每日新增课程数
        daily.setCourseNum(RandomUtils.nextInt(100,200));
        daily.setDateCalculated(day);

        //保存到数据库
        baseMapper.insert(daily);

    }

    @Override
    public Map<String, Object> getChartData(String begin, String end, String type) {

        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("date_calculated",type);
        queryWrapper.between("date_calculated",begin,end);
        List<Daily> dailyList = baseMapper.selectList(queryWrapper);

        HashMap<String, Object> map = new HashMap<>();
        List<String> dateList = new ArrayList<>();
        List<Integer> dataList = new ArrayList<>();
        map.put("dateList",dateList);
        map.put("dataList",dataList);

        for (Daily daily : dailyList) {

            String dateCalculated = daily.getDateCalculated();
            dateList.add(dateCalculated);

            switch (type){

                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;

            }

        }
        return map;
    }
}
