package com.yurn.satori.sdk.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yurn.satori.sdk.entity.GuildEntity;
import com.yurn.satori.sdk.entity.PageResponseEntity;

import java.util.List;

/**
 * 群组 API
 *
 * @author Yurn
 */
public final class GuildApi {
    /**
     * 获取群组
     * 根据 ID 获取, 返回一个 Guild 对象
     *
     * @param guildId  群组 ID
     * @param platform 平台名称
     * @param selfId   机器人 ID
     * @return 输出
     */
    public static GuildEntity getGuild(String guildId, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        String response = SendMessage.sendGenericMessage(platform, selfId, "guild", "get", map.toString());
        return JSONObject.parseObject(response, GuildEntity.class);
    }

    /**
     * 获取群组列表
     * 获取当前用户加入的全部群组, 返回一个 Guild 的 分页列表。
     *
     * @param next     分页令牌
     * @param platform 平台名称
     * @param selfId   机器人 ID
     * @return 输出
     */
    public static List<PageResponseEntity<GuildEntity>> listGuild(String next, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("next", next);
        String response = SendMessage.sendGenericMessage(platform, selfId, "guild", "list", map.toString());
        //noinspection unchecked
        return JSONArray.parse(response).stream().map(o -> (PageResponseEntity<GuildEntity>) o).toList();
    }

    /**
     * 处理群组邀请
     * 处理来自群组的邀请
     *
     * @param messageId 请求 ID
     * @param approve   是否通过请求
     * @param comment   备注信息
     * @param platform  平台名称
     * @param selfId    机器人 ID
     */
    public static void approveGuild(String messageId, boolean approve, String comment, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("message_id", messageId);
        map.put("approve", approve);
        map.put("comment", comment);
        SendMessage.sendGenericMessage(platform, selfId, "guild", "approve", map.toString());
    }
}
