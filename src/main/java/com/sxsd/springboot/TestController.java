/**
 * Copyright (C), 2015-2019, 联想（北京）有限公司
 * FileName: TestController
 * Author:   liujx
 * Date:     2019/6/18 16:23
 * Description:
 * History:
 * 描述
 */
package com.sxsd.springboot;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 〈〉
 *
 * @author liujx
 * @create 2019/6/18
 */
@RestController
public class TestController {

    @RequestMapping(value = "/getAllStackTraces")
    public void getAllStackTraces() {
        System.out.println("getAllStackTraces");
        Map<Thread, StackTraceElement[]> maps = Thread.getAllStackTraces();
        System.out.println(JSON.toJSONString(maps));
    }
}