package com.yurn.satori.sdk.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yurn.satori.sdk.entity.ChannelEntity;
import com.yurn.satori.sdk.entity.PageResponseEntity;

import java.util.List;

/**
 * 频道的 API
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public final class ChannelApi {
    /**
     * 获取群组频道
     * 根据 ID 获取频道。返回一个 Channel 对象
     *
     * @param channelId 频道id
     * @param platform  平台名称
     * @param selfId    机器人 ID
     * @return 输出
     */
    public static ChannelEntity getChannel(String channelId, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        String response = SendMessage.sendGenericMessage(platform, selfId, "channel", "get", map.toString());
        return JSONObject.parseObject(response, ChannelEntity.class);
    }

    /**
     * 获取群组频道列表
     * 获取群组中的全部频道, 返回一个 Channel 的 分页列表
     *
     * @param guildId  群组 ID
     * @param next     分页令牌
     * @param platform 平台名称
     * @param selfId   机器人 ID
     * @return 输出
     */
    public static List<PageResponseEntity<ChannelEntity>> listChannel(String guildId, String next, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("next", next);
        String response = SendMessage.sendGenericMessage(platform, selfId, "channel", "list", map.toString());
        //noinspection unchecked
        return JSONArray.parse(response).stream().map(o -> (PageResponseEntity<ChannelEntity>) o).toList();
    }

    /**
     * 创建群组频道
     *
     * @param guildId  群组 ID
     * @param data     频道数据
     * @param platform 平台名称
     * @param selfId   机器人 ID
     * @return 输出
     */
    public static ChannelEntity createChannel(String guildId, ChannelEntity data, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("data", data);
        String response = SendMessage.sendGenericMessage(platform, selfId, "channel", "create", map.toString());
        return JSONObject.parseObject(response, ChannelEntity.class);
    }

    /**
     * 修改群组频道
     *
     * @param channelId 频道 ID
     * @param data      频道数据
     * @param platform  平台名称
     * @param selfId    机器人 ID
     * @return 输出
     */
    public static ChannelEntity updateChannel(String channelId, ChannelEntity data, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        map.put("data", data);
        String response = SendMessage.sendGenericMessage(platform, selfId, "channel", "update", map.toString());
        return JSONObject.parseObject(response, ChannelEntity.class);
    }

    /**
     * 删除群组频道
     *
     * @param channelId 频道 ID
     * @param platform  平台名称
     * @param selfId    机器人 ID
     */
    public static void deleteChannel(String channelId, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("channel_id", channelId);
        SendMessage.sendGenericMessage(platform, selfId, "channel", "delete", map.toString());
    }

    /**
     * 创建私聊频道
     *
     * @param userId   用户 ID
     * @param platform 平台名称
     * @param selfId   机器人 ID
     * @return 输出
     */
    public static ChannelEntity createUserChannel(String userId, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("user_id", userId);
        String response = SendMessage.sendGenericMessage(platform, selfId, "user.channel", "create", map.toString());
        return JSONObject.parseObject(response, ChannelEntity.class);
    }
}
