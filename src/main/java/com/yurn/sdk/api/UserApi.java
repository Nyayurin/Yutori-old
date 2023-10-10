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
 * 用户 API
 *
 * @author Yurn
 */
@Component
public class UserApi {
    @Resource
    private SendMessage sendMessage;

    /**
     * 获取用户信息
     * 获取用户信息, 返回一个 User 对象
     *
     * @param userId 用户 ID
     * @param login  机器人信息
     * @return 输出
     */
    public User getUser(String userId, Login login) {
        JSONObject map = new JSONObject();
        map.put("user_id", userId);
        String response = sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "user", "get", map.toString());
        return JSONObject.parseObject(response, User.class);
    }

    /**
     * 获取好友列表
     * 获取好友列表。返回一个 User 的 分页列表
     *
     * @param login 机器人信息
     * @return 输出
     */
    public List<PageResponse<User>> listFriend(Login login) {
        String response = sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "friend", "list", null);
        //noinspection unchecked
        return JSONArray.parse(response).stream().map(o -> (PageResponse<User>) o).toList();
    }

    /**
     * 获取好友列表
     * 获取好友列表。返回一个 User 的 分页列表
     *
     * @param next  分页令牌
     * @param login 机器人信息
     * @return 输出
     */
    public List<PageResponse<User>> listFriend(String next, Login login) {
        JSONObject map = new JSONObject();
        map.put("next", next);
        String response = sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "friend", "list", map.toString());
        //noinspection unchecked
        return JSONArray.parse(response).stream().map(o -> (PageResponse<User>) o).toList();
    }

    /**
     * 处理好友申请
     *
     * @param messageId 消息 ID
     * @param approve   是否通过请求
     * @param login     机器人信息
     */
    public void approveFriend(String messageId, boolean approve, Login login) {
        JSONObject map = new JSONObject();
        map.put("message_id", messageId);
        map.put("approve", approve);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "friend", "approve", map.toString());
    }

    /**
     * 处理好友申请
     *
     * @param messageId 消息 ID
     * @param approve   是否通过请求
     * @param comment   备注信息
     * @param login     机器人信息
     */
    public void approveFriend(String messageId, boolean approve, String comment, Login login) {
        JSONObject map = new JSONObject();
        map.put("message_id", messageId);
        map.put("approve", approve);
        map.put("comment", comment);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "friend", "approve", map.toString());
    }
}
