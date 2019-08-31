package com.luna.springbootpractice.demo.myfegin;

import com.alibaba.fastjson.JSONObject;
import com.luna.springbootpractice.utils.HttpClientPoolUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述: TODO<br>
 *
 * @author ctp
 * @date 2019/8/30 15:32
 */
@Component
public class DefaultMyFeginClientSupport implements MyFeginClientSupport {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        MyFeginClientScanner myFeginClientScanner = method.getDeclaringClass().getAnnotation(MyFeginClientScanner.class);
        MyFeginClientRequestMapping myFeginClientRequestMapping = method.getAnnotation(MyFeginClientRequestMapping.class);

        if (myFeginClientRequestMapping == null) {
            return null;
        }

        String value = myFeginClientRequestMapping.value();
        String url = StringUtils.join(myFeginClientScanner.url(), value);

        String result = null;
        if (args != null && args.length == 1) {

            //发送http请求
            //Map<String,Object> params = new HashMap<String, Object>(1);
            //params.put("param",args[0]);
            //JSONObject jsonObject = HttpClientPoolUtil.doGet(url,params);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("test", args[0]);

            result = jsonObject.toJSONString();
        }

        if (method.getReturnType() == String.class) {
            return result;
        }
        return null;

    }
}
