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
import io.github.nyayurn.yutori.entity.MessageEntity;
import io.github.nyayurn.yutori.entity.PageResponseEntity;
import io.github.nyayurn.yutori.entity.PropertiesEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * 消息 API
 *
 * @author Yurn
 */
@Data
@Slf4j
@AllArgsConstructor
public class MessageApi {
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

    public MessageApi(String platform, String selfId, PropertiesEntity properties) {
        this.platform = platform;
        this.selfId = selfId;
        this.sendMessage = new SendMessage(platform, selfId, properties);
    }


    /**
     * 发送消息
     * 发送消息, 返回一个 Message 对象构成的数组
     *
     * @param channelId 频道 ID
     * @param content   消息内容
     * @return 输出
     */
    public List<MessageEntity> createMessage(String channelId, String content) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("content", content);
        String response = sendMessage.sendGenericMessage("message", "create", map.toString());
        try {
            return Optional.ofNullable(JSONArray.parse(response))
                    .map(objects -> objects.toList(MessageEntity.class))
                    .orElse(null);
        } catch (JSONException | NullPointerException e) {
            log.error("{}: {}", response, e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 获取消息
     * 获取特定消息, 返回一个 Message 对象
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @return 输出
     */
    public MessageEntity getMessage(String channelId, String messageId) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        String response = sendMessage.sendGenericMessage("message", "get", map.toString());
        try {
            return JSONObject.parseObject(response, MessageEntity.class);
        } catch (JSONException e) {
            log.error("{}: {}", response, e.toString());
        }
        return null;
    }

    /**
     * 撤回消息
     * 撤回特定消息
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     */
    public void deleteMessage(String channelId, String messageId) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        sendMessage.sendGenericMessage("message", "delete", map.toString());
    }

    /**
     * 编辑消息
     * 编辑特定消息
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param content   消息内容
     */
    public void updateMessage(String channelId, String messageId, String content) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        map.put("content", content);
        sendMessage.sendGenericMessage("message", "update", map.toString());
    }

    /**
     * 获取消息列表
     * 获取频道消息列表, 返回一个 Message 的 分页列表
     *
     * @param channelId 频道 ID
     * @param next      分页令牌
     * @return 输出
     */
    public List<PageResponseEntity<MessageEntity>> listMessage(String channelId, String next) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("next", next);
        String response = sendMessage.sendGenericMessage("message", "list", map.toString());
        try {
            //noinspection unchecked
            return Optional.ofNullable(JSONArray.parse(response))
                    .map(objects -> objects.stream().map(o -> ((PageResponseEntity<MessageEntity>) o)).toList())
                    .orElse(null);
        } catch (JSONException | NullPointerException e) {
            log.error("{}: {}", response, e.getLocalizedMessage());
        }
        return null;
    }
}
