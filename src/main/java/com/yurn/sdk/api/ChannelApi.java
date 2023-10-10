package com.yurn.sdk.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yurn.sdk.entity.Channel;
import com.yurn.sdk.entity.Login;
import com.yurn.sdk.entity.PageResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 频道的 API
 *
 * @author Yurn
 */
@Component
public class ChannelApi {
    @Resource
    private SendMessage sendMessage;

    /**
     * 获取群组频道
     * 根据 ID 获取频道。返回一个 Channel 对象
     *
     * @param channelId 频道id
     * @param login     机器人信息
     * @return 输出
     */
    public Channel getChannel(String channelId, Login login) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        String response = sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "channel", "get", map.toString());
        return JSONObject.parseObject(response, Channel.class);
    }

    /**
     * 获取群组频道列表
     * 获取群组中的全部频道, 返回一个 Channel 的 分页列表
     *
     * @param guildId 群组 ID
     * @param next    分页令牌
     * @param login   机器人信息
     * @return 输出
     */
    public List<PageResponse<Channel>> listChannel(String guildId, String next, Login login) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("next", next);
        String response = sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "channel", "list", map.toString());
        //noinspection unchecked
        return JSONArray.parse(response).stream().map(o -> (PageResponse<Channel>) o).toList();
    }

    /**
     * 创建群组频道
     *
     * @param guildId 群组 ID
     * @param data    频道数据
     * @return 输出
     */
    public Channel createChannel(String guildId, Channel data, Login login) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("data", data);
        String response = sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "channel", "create", map.toString());
        return JSONObject.parseObject(response, Channel.class);
    }

    /**
     * 修改群组频道
     *
     * @param channelId 频道 ID
     * @param data      频道数据
     * @return 输出
     */
    public Channel updateChannel(String channelId, Channel data, Login login) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("data", data);
        String response = sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "channel", "update", map.toString());
        return JSONObject.parseObject(response, Channel.class);
    }

    /**
     * 删除群组频道
     *
     * @param channelId 频道 ID
     */
    public void deleteChannel(String channelId, Login login) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "channel", "delete", map.toString());
    }

    /**
     * 创建私聊频道
     *
     * @param userId 用户 ID
     * @return 输出
     */
    public Channel createUserChannel(String userId, Login login) {
        JSONObject map = new JSONObject();
        map.put("user_id", userId);
        String response = sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "user.channel", "create", map.toString());
        return JSONObject.parseObject(response, Channel.class);
    }
}
