package com.yurn.satori.sdk.api;

import com.alibaba.fastjson2.JSONObject;
import com.yurn.satori.sdk.entity.LoginEntity;

/**
 * 登录信息 API
 *
 * @author Yurn
 */
public final class LoginApi {
    /**
     * 获取登录信息
     * 获取登录信息, 返回一个 Login 对象
     *
     * @return 输出
     */
    public static LoginEntity getLogin() {
        String response = SendMessage.sendGenericMessage(null, null, "login", "get", null);
        return JSONObject.parseObject(response, LoginEntity.class);
    }
}
