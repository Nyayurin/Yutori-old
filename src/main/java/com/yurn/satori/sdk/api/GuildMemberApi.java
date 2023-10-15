package com.yurn.satori.sdk.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yurn.satori.sdk.entity.GuildMemberEntity;
import com.yurn.satori.sdk.entity.PageResponseEntity;

import java.util.List;

/**
 * 群组成员 API
 *
 * @author Yurn
 */
public final class GuildMemberApi {
    /**
     * 获取群组成员
     * 获取群成员信息, 返回一个 GuildMember 对象
     *
     * @param guildId  群组 ID
     * @param userId   用户 ID
     * @param platform 平台名称
     * @param selfId   机器人 ID
     * @return 输出
     */
    public static GuildMemberEntity getGuildMember(String guildId, String userId, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("user_id", userId);
        String response = SendMessage.sendGenericMessage(platform, selfId, "guild.member", "get", map.toString());
        return JSONObject.parseObject(response, GuildMemberEntity.class);
    }

    /**
     * 获取群组成员列表
     * 获取群成员列表, 返回一个 GuildMember 的 分页列表
     *
     * @param guildId  群组 ID
     * @param next     分页令牌
     * @param platform 平台名称
     * @param selfId   机器人 ID
     * @return 输出
     */
    public static List<PageResponseEntity<GuildMemberEntity>> listGuildMember(String guildId, String next, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("next", next);
        String response = SendMessage.sendGenericMessage(platform, selfId, "guild.member", "list", map.toString());
        //noinspection unchecked
        return JSONArray.parse(response).stream().map(o -> (PageResponseEntity<GuildMemberEntity>) o).toList();
    }

    /**
     * 踢出群组成员
     * 将某个用户踢出群组
     *
     * @param guildId  群组 ID
     * @param userId   用户 ID
     * @param platform 平台名称
     * @param selfId   机器人 ID
     */
    public static void kickGuildMember(String guildId, String userId, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("user_id", userId);
        SendMessage.sendGenericMessage(platform, selfId, "guild.member", "kick", map.toString());
    }

    /**
     * 踢出群组成员
     * 将某个用户踢出群组
     *
     * @param guildId   群组 ID
     * @param userId    用户 ID
     * @param permanent 是否永久踢出 (无法再次加入群组)
     * @param platform  平台名称
     * @param selfId    机器人 ID
     */
    public static void kickGuildMember(String guildId, String userId, boolean permanent, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("user_id", userId);
        map.put("permanent", permanent);
        SendMessage.sendGenericMessage(platform, selfId, "guild.member", "kick", map.toString());
    }

    /**
     * 通过群组成员申请
     * 处理加群请求
     *
     * @param messageId 请求 ID
     * @param approve   是否通过请求
     * @param platform  平台名称
     * @param selfId    机器人 ID
     */
    public static void getGuildMember(String messageId, boolean approve, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("message_id", messageId);
        map.put("approve", approve);
        SendMessage.sendGenericMessage(platform, selfId, "guild.member", "approve", map.toString());
    }

    /**
     * 通过群组成员申请
     * 处理加群请求
     *
     * @param messageId 请求 ID
     * @param approve   是否通过请求
     * @param comment   备注信息
     * @param platform  平台名称
     * @param selfId    机器人 ID
     */
    public static void getGuildMember(String messageId, boolean approve, String comment, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("message_id", messageId);
        map.put("approve", approve);
        map.put("comment", comment);
        SendMessage.sendGenericMessage(platform, selfId, "guild.member", "approve", map.toString());
    }
}
