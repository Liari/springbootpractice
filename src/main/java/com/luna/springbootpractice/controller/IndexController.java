package com.luna.springbootpractice.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类描述: TODO<br>
 *
 * @author ctp
 * @date 2019/8/31 10:30
 */
@RestController("/")
public class IndexController {

    @RequestMapping
    public JSONObject index() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","xiaoming");
        return jsonObject;
    }


}
