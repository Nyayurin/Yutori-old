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

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import io.github.nyayurn.yutori.entity.PageResponseEntity;
import io.github.nyayurn.yutori.entity.PropertiesEntity;
import io.github.nyayurn.yutori.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * 用户 API
 *
 * @author Yurn
 */
@Data
@Slf4j
@AllArgsConstructor
public class UserApi {
    /**
     * 平台名称
     */
    private String platform;

    /**
     * 机器人 ID
     */
    private String selfId;

    /**
     * SendMessage 实例类
     */
    private SendMessage sendMessage;

    public UserApi(String platform, String selfId, PropertiesEntity properties) {
        this.platform = platform;
        this.selfId = selfId;
        this.sendMessage = new SendMessage(platform, selfId, properties);
    }

    /**
     * 获取用户信息
     * 获取用户信息, 返回一个 User 对象
     *
     * @param userId 用户 ID
     * @return 输出
     */
    public UserEntity getUser(String userId) {
        JSONObject map = new JSONObject();
        map.put("user_id", userId);
        String response = sendMessage.sendGenericMessage("user", "get", map.toString());
        try {
            return JSONObject.parseObject(response, UserEntity.class);
        } catch (JSONException e) {
            log.error("{}: {}", response, e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 获取好友列表
     * 获取好友列表。返回一个 User 的 分页列表
     *
     * @return 输出
     */
    public List<PageResponseEntity<UserEntity>> listFriend() {
        String response = sendMessage.sendGenericMessage("friend", "list", null);
        try {
            //noinspection unchecked
            return Optional.ofNullable(JSONArray.parse(response))
                    .map(objects -> objects.stream().map(o -> (PageResponseEntity<UserEntity>) o).toList())
                    .orElse(null);
        } catch (JSONException | NullPointerException e) {
            log.error("{}: {}", response, e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 获取好友列表
     * 获取好友列表。返回一个 User 的 分页列表
     *
     * @param next 分页令牌
     * @return 输出
     */
    public List<PageResponseEntity<UserEntity>> listFriend(String next) {
        JSONObject map = new JSONObject();
        map.put("next", next);
        String response = sendMessage.sendGenericMessage("friend", "list", map.toString());
        try {
            //noinspection unchecked
            return Optional.ofNullable(JSONArray.parse(response))
                    .map(objects -> objects.stream().map(o -> (PageResponseEntity<UserEntity>) o).toList())
                    .orElse(null);
        } catch (JSONException | NullPointerException e) {
            log.error("{}: {}", response, e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 处理好友申请
     *
     * @param messageId 消息 ID
     * @param approve   是否通过请求
     */
    public void approveFriend(String messageId, boolean approve) {
        JSONObject map = new JSONObject();
        map.put("message_id", messageId);
        map.put("approve", approve);
        sendMessage.sendGenericMessage("friend", "approve", map.toString());
    }

    /**
     * 处理好友申请
     *
     * @param messageId 消息 ID
     * @param approve   是否通过请求
     * @param comment   备注信息
     */
    public void approveFriend(String messageId, boolean approve, String comment) {
        JSONObject map = new JSONObject();
        map.put("message_id", messageId);
        map.put("approve", approve);
        map.put("comment", comment);
        sendMessage.sendGenericMessage("friend", "approve", map.toString());
    }
}
