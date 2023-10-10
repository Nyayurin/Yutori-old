package com.yurn.sdk.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yurn.sdk.entity.Login;
import com.yurn.sdk.entity.PageResponse;
import com.yurn.sdk.entity.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 表态 API (实验性)
 *
 * @author Yurn
 */
@Component
public class ReactionApi {
    @Resource
    private SendMessage sendMessage;

    /**
     * 添加表态
     * 向特定消息添加表态
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param emoji     表态名称
     * @param login     机器人信息
     */
    public void createReaction(String channelId, String messageId, String emoji, Login login) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        map.put("emoji", emoji);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "reaction", "create", map.toString());
    }

    /**
     * 删除表态
     * 从特定消息删除自己添加的特定表态
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param emoji     表态名称
     * @param login     机器人信息
     */
    public void deleteReaction(String channelId, String messageId, String emoji, Login login) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        map.put("emoji", emoji);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "reaction", "delete", map.toString());
    }

    /**
     * 删除表态
     * 从特定消息删除某个用户添加的特定表态
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param emoji     表态名称
     * @param userId    用户 ID
     * @param login     机器人信息
     */
    public void deleteReaction(String channelId, String messageId, String emoji, String userId, Login login) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        map.put("emoji", emoji);
        map.put("user_id", userId);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "reaction", "delete", map.toString());
    }

    /**
     * 清除表态
     * 从特定消息清除所有表态
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param login     机器人信息
     */
    public void clearReaction(String channelId, String messageId, Login login) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "reaction", "clear", map.toString());
    }

    /**
     * 清除表态
     * 从特定消息清除某个特定表态, 如果没有传入表态名称则表示清除所有表态
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param emoji     表态名称
     * @param login     机器人信息
     */
    public void clearReaction(String channelId, String messageId, String emoji, Login login) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        map.put("emoji", emoji);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "reaction", "clear", map.toString());
    }

    /**
     * 获取表态列表
     * 获取添加特定消息的特定表态的用户列表, 返回一个 User 的 分页列表
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param emoji     表态名称
     * @param next      分页令牌
     * @param login     机器人信息
     */
    public List<PageResponse<User>> listReaction(String channelId, String messageId, String emoji, String next, Login login) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        map.put("emoji", emoji);
        map.put("next", next);
        String response = sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "reaction", "list", map.toString());
        //noinspection unchecked
        return JSONArray.parse(response).stream().map(o -> ((PageResponse<User>) o)).toList();
    }
}
