package com.guli.statistics.client;

import com.guli.common.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Administrator
 * @create 2019-07-22 11:06
 */
@Component
@FeignClient("guli-ucenter")
public interface UcenterClient {

    @GetMapping(value = "/admin/ucenter/member/count-register/{day}")
    public R registerCount(@PathVariable("day") String day);
}
