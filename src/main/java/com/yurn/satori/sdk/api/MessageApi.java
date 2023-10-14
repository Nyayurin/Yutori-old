package com.yurn.satori.sdk.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yurn.satori.sdk.entity.MessageEntity;
import com.yurn.satori.sdk.entity.PageResponseEntity;

import java.util.List;

/**
 * 消息 API
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public final class MessageApi {
    /**
     * 发送消息
     * 发送消息, 返回一个 Message 对象构成的数组
     *
     * @param channelId 频道 ID
     * @param content   消息内容
     * @param platform  平台名称
     * @param selfId    机器人 ID
     * @return 输出
     */
    public static List<MessageEntity> createMessage(String channelId, String content, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("content", content);
        String response = SendMessage.sendGenericMessage(platform, selfId, "message", "create", map.toString());
        return JSONArray.parse(response).toList(MessageEntity.class);
    }

    /**
     * 获取消息
     * 获取特定消息, 返回一个 Message 对象
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param platform  平台名称
     * @param selfId    机器人 ID
     * @return 输出
     */
    public static MessageEntity getMessage(String channelId, String messageId, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        String response = SendMessage.sendGenericMessage(platform, selfId, "message", "get", map.toString());
        return JSONObject.parseObject(response, MessageEntity.class);
    }

    /**
     * 撤回消息
     * 撤回特定消息
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param platform  平台名称
     * @param selfId    机器人 ID
     */
    public static void deleteMessage(String channelId, String messageId, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        SendMessage.sendGenericMessage(platform, selfId, "message", "delete", map.toString());
    }

    /**
     * 编辑消息
     * 编辑特定消息
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param content   消息内容
     * @param platform  平台名称
     * @param selfId    机器人 ID
     */
    public static void updateMessage(String channelId, String messageId, String content, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        map.put("content", content);
        SendMessage.sendGenericMessage(platform, selfId, "message", "update", map.toString());
    }

    /**
     * 获取消息列表
     * 获取频道消息列表, 返回一个 Message 的 分页列表
     *
     * @param channelId 频道 ID
     * @param next      分页令牌
     * @param platform  平台名称
     * @param selfId    机器人 ID
     * @return 输出
     */
    public static List<PageResponseEntity<MessageEntity>> listMessage(String channelId, String next, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("next", next);
        String response = SendMessage.sendGenericMessage(platform, selfId, "message", "list", map.toString());
        //noinspection unchecked
        return JSONArray.parse(response).stream().map(o -> ((PageResponseEntity<MessageEntity>) o)).toList();
    }
}
