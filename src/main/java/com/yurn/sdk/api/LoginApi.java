package com.yurn.sdk.api;

import com.alibaba.fastjson2.JSONObject;
import com.yurn.sdk.entity.Login;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * 登录信息 API
 *
 * @author Yurn
 */
@Component
public class LoginApi {
    @Resource
    private SendMessage sendMessage;

    /**
     * 获取登录信息
     * 获取登录信息, 返回一个 Login 对象
     *
     * @return 输出
     */
    public Login getLogin() {
        String response = sendMessage.sendGenericMessage(null, null, "login", "get", null);
        return JSONObject.parseObject(response, Login.class);
    }
}
