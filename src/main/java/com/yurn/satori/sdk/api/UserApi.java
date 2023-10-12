package com.yurn.satori.sdk.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yurn.satori.sdk.entity.PageResponseEntity;
import com.yurn.satori.sdk.entity.UserEntity;

import java.util.List;

/**
 * 用户 API
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public final class UserApi {
    /**
     * 获取用户信息
     * 获取用户信息, 返回一个 User 对象
     *
     * @param userId   用户 ID
     * @param platform 平台名称
     * @param selfId   机器人 ID
     * @return 输出
     */
    public static UserEntity getUser(String userId, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("user_id", userId);
        String response = SendMessage.sendGenericMessage(platform, selfId, "user", "get", map.toString());
        return JSONObject.parseObject(response, UserEntity.class);
    }

    /**
     * 获取好友列表
     * 获取好友列表。返回一个 User 的 分页列表
     *
     * @param platform 平台名称
     * @param selfId   机器人 ID
     * @return 输出
     */
    public static List<PageResponseEntity<UserEntity>> listFriend(String platform, String selfId) {
        String response = SendMessage.sendGenericMessage(platform, selfId, "friend", "list", null);
        //noinspection unchecked
        return JSONArray.parse(response).stream().map(o -> (PageResponseEntity<UserEntity>) o).toList();
    }

    /**
     * 获取好友列表
     * 获取好友列表。返回一个 User 的 分页列表
     *
     * @param next     分页令牌
     * @param platform 平台名称
     * @param selfId   机器人 ID
     * @return 输出
     */
    public static List<PageResponseEntity<UserEntity>> listFriend(String next, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("next", next);
        String response = SendMessage.sendGenericMessage(platform, selfId, "friend", "list", map.toString());
        //noinspection unchecked
        return JSONArray.parse(response).stream().map(o -> (PageResponseEntity<UserEntity>) o).toList();
    }

    /**
     * 处理好友申请
     *
     * @param messageId 消息 ID
     * @param approve   是否通过请求
     * @param platform  平台名称
     * @param selfId    机器人 ID
     */
    public static void approveFriend(String messageId, boolean approve, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("message_id", messageId);
        map.put("approve", approve);
        SendMessage.sendGenericMessage(platform, selfId, "friend", "approve", map.toString());
    }

    /**
     * 处理好友申请
     *
     * @param messageId 消息 ID
     * @param approve   是否通过请求
     * @param comment   备注信息
     * @param platform  平台名称
     * @param selfId    机器人 ID
     */
    public static void approveFriend(String messageId, boolean approve, String comment, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("message_id", messageId);
        map.put("approve", approve);
        map.put("comment", comment);
        SendMessage.sendGenericMessage(platform, selfId, "friend", "approve", map.toString());
    }
}
