package com.yurn.sdk.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yurn.sdk.entity.Login;
import com.yurn.sdk.entity.Message;
import com.yurn.sdk.entity.PageResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消息 API
 *
 * @author Yurn
 */
@Component
public class MessageApi {
    @Resource
    private SendMessage sendMessage;

    /**
     * 发送消息
     * 发送消息, 返回一个 Message 对象构成的数组
     *
     * @param channelId 频道 ID
     * @param content   消息内容
     * @param login     机器人信息
     * @return 输出
     */
    public Message[] createMessage(String channelId, String content, Login login) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("content", content);
        String response = sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "message", "create", map.toString());
        return JSONArray.parse(response).toArray(Message.class);
    }

    /**
     * 获取消息
     * 获取特定消息, 返回一个 Message 对象
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param login     机器人信息
     * @return 输出
     */
    public Message getMessage(String channelId, String messageId, Login login) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        String response = sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "message", "get", map.toString());
        return JSONObject.parseObject(response, Message.class);
    }

    /**
     * 撤回消息
     * 撤回特定消息
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param login     机器人信息
     */
    public void deleteMessage(String channelId, String messageId, Login login) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "message", "delete", map.toString());
    }

    /**
     * 编辑消息
     * 编辑特定消息
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param content   消息内容
     * @param login     机器人信息
     */
    public void updateMessage(String channelId, String messageId, String content, Login login) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        map.put("content", content);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "message", "update", map.toString());
    }

    /**
     * 获取消息列表
     * 获取频道消息列表, 返回一个 Message 的 分页列表
     *
     * @param channelId 频道 ID
     * @param next      分页令牌
     * @param login     机器人信息
     * @return 输出
     */
    public List<PageResponse<Message>> listMessage(String channelId, String next, Login login) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("next", next);
        String response = sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "message", "list", map.toString());
        //noinspection unchecked
        return JSONArray.parse(response).stream().map(o -> ((PageResponse<Message>) o)).toList();
    }
}
