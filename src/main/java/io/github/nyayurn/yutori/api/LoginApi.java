/*
Copyright (c) 2023 Yurn
yutori is licensed under Mulan PSL v2.
You can use this software according to the terms and conditions of the Mulan PSL v2.
You may obtain a copy of Mulan PSL v2 at:
         http://license.coscl.org.cn/MulanPSL2
THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
See the Mulan PSL v2 for more details.
 */

package io.github.nyayurn.yutori.api;

import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import io.github.nyayurn.yutori.entity.LoginEntity;
import io.github.nyayurn.yutori.entity.PropertiesEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录信息 API
 *
 * @author Yurn
 */
@Data
@Slf4j
@AllArgsConstructor
public class LoginApi {
    /**
     * SendMessage 实例类
     */
    private SendMessage sendMessage;

    public LoginApi(PropertiesEntity properties) {
        this.sendMessage = new SendMessage(null, null, properties);
    }

    /**
     * 获取登录信息
     * 获取登录信息, 返回一个 Login 对象
     *
     * @return 输出
     */
    public LoginEntity getLogin() {
        String response = sendMessage.sendGenericMessage("login", "get", null);
        try {
            return JSONObject.parseObject(response, LoginEntity.class);
        } catch (JSONException e) {
            log.error("{}: {}", response, e.getLocalizedMessage());
        }
        return null;
    }
}
