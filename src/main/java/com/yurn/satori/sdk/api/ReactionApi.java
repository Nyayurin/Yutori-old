package com.yurn.satori.sdk.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yurn.satori.sdk.entity.PageResponseEntity;
import com.yurn.satori.sdk.entity.UserEntity;

import java.util.List;

/**
 * 表态 API (实验性)
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public final class ReactionApi {
    /**
     * 添加表态
     * 向特定消息添加表态
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param emoji     表态名称
     * @param platform  平台名称
     * @param selfId    机器人 ID
     */
    public static void createReaction(String channelId, String messageId, String emoji, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        map.put("emoji", emoji);
        SendMessage.sendGenericMessage(platform, selfId, "reaction", "create", map.toString());
    }

    /**
     * 删除表态
     * 从特定消息删除自己添加的特定表态
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param emoji     表态名称
     * @param platform  平台名称
     * @param selfId    机器人 ID
     */
    public static void deleteReaction(String channelId, String messageId, String emoji, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        map.put("emoji", emoji);
        SendMessage.sendGenericMessage(platform, selfId, "reaction", "delete", map.toString());
    }

    /**
     * 删除表态
     * 从特定消息删除某个用户添加的特定表态
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param emoji     表态名称
     * @param userId    用户 ID
     * @param platform  平台名称
     * @param selfId    机器人 ID
     */
    public static void deleteReaction(String channelId, String messageId, String emoji, String userId, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        map.put("emoji", emoji);
        map.put("user_id", userId);
        SendMessage.sendGenericMessage(platform, selfId, "reaction", "delete", map.toString());
    }

    /**
     * 清除表态
     * 从特定消息清除所有表态
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param platform  平台名称
     * @param selfId    机器人 ID
     */
    public static void clearReaction(String channelId, String messageId, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        SendMessage.sendGenericMessage(platform, selfId, "reaction", "clear", map.toString());
    }

    /**
     * 清除表态
     * 从特定消息清除某个特定表态, 如果没有传入表态名称则表示清除所有表态
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param emoji     表态名称
     * @param platform  平台名称
     * @param selfId    机器人 ID
     */
    public static void clearReaction(String channelId, String messageId, String emoji, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        map.put("emoji", emoji);
        SendMessage.sendGenericMessage(platform, selfId, "reaction", "clear", map.toString());
    }

    /**
     * 获取表态列表
     * 获取添加特定消息的特定表态的用户列表, 返回一个 User 的 分页列表
     *
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param emoji     表态名称
     * @param next      分页令牌
     * @param platform  平台名称
     * @param selfId    机器人 ID
     */
    public static List<PageResponseEntity<UserEntity>> listReaction(String channelId, String messageId, String emoji, String next,
                                                                    String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("message_id", messageId);
        map.put("emoji", emoji);
        map.put("next", next);
        String response = SendMessage.sendGenericMessage(platform, selfId, "reaction", "list", map.toString());
        //noinspection unchecked
        return JSONArray.parse(response).stream().map(o -> ((PageResponseEntity<UserEntity>) o)).toList();
    }
}
