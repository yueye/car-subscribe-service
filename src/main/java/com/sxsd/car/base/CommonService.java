/**
 * Copyright (C), 2015-2019, 联想（北京）有限公司
 * FileName: CommonService
 * Author:   liujx
 * Date:     2019/5/7 14:22
 * Description:
 * History:
 * 描述
 */
package com.sxsd.car.base;

import com.sxsd.base.SuperLog;
import com.sxsd.car.redis.RedisPay;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 〈〉
 *
 * @author liujx
 * @create 2019/5/7
 */
public class CommonService {
    @Autowired
    protected SuperLog superLog;
    @Autowired
    protected RedisPay redisPay;

}