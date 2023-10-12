package com.yurn.satori.sdk.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yurn.satori.sdk.entity.GuildRoleEntity;
import com.yurn.satori.sdk.entity.PageResponseEntity;

import java.util.List;

/**
 * 群组角色 API
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public final class GuildRoleApi {
    /**
     * 设置群组成员角色
     * 设置群组内用户的角色
     *
     * @param guildId  群组 ID
     * @param userId   用户 ID
     * @param roleId   角色 ID
     * @param platform 平台名称
     * @param selfId   机器人 ID
     */
    public static void setGuildRole(String guildId, String userId, String roleId, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("user_id", userId);
        map.put("role_id", roleId);
        SendMessage.sendGenericMessage(platform, selfId, "guild.member.role", "set", map.toString());
    }

    /**
     * 取消群组成员角色
     * 取消群组内用户的角色
     *
     * @param guildId  群组 ID
     * @param userId   用户 ID
     * @param roleId   角色 ID
     * @param platform 平台名称
     * @param selfId   机器人 ID
     */
    public static void unsetGuildRole(String guildId, String userId, String roleId, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("user_id", userId);
        map.put("role_id", roleId);
        SendMessage.sendGenericMessage(platform, selfId, "guild.member.role", "unset", map.toString());
    }

    /**
     * 获取群组角色列表
     * 获取群组角色列表, 返回一个 GuildRole 的 分页列表
     *
     * @param guildId  群组 ID
     * @param next     分页令牌
     * @param platform 平台名称
     * @param selfId   机器人 ID
     * @return 输出
     */
    public static List<PageResponseEntity<GuildRoleEntity>> listGuildRole(String guildId, String next, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("next", next);
        String response = SendMessage.sendGenericMessage(platform, selfId, "guild.role", "list", map.toString());
        //noinspection unchecked
        return JSONArray.parse(response).stream().map(o -> ((PageResponseEntity<GuildRoleEntity>) o)).toList();
    }

    /**
     * 创建群组角色
     * 创建群组角色, 返回一个 GuildRole 对象
     *
     * @param guildId  群组 ID
     * @param role     角色数据
     * @param platform 平台名称
     * @param selfId   机器人 ID
     * @return 输出
     */
    public static GuildRoleEntity createGuildRole(String guildId, GuildRoleEntity role, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("role", role);
        String response = SendMessage.sendGenericMessage(platform, selfId, "guild.role", "create", map.toString());
        return JSONObject.parseObject(response, GuildRoleEntity.class);
    }

    /**
     * 修改群组角色
     *
     * @param guildId  群组 ID
     * @param roleId   角色 ID
     * @param role     角色数据
     * @param platform 平台名称
     * @param selfId   机器人 ID
     */
    public static void updateGuildRole(String guildId, String roleId, GuildRoleEntity role, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("role_id", roleId);
        map.put("role", role);
        SendMessage.sendGenericMessage(platform, selfId, "guild.role", "update", map.toString());
    }

    /**
     * 删除群组角色
     *
     * @param guildId  群组 ID
     * @param roleId   角色 ID
     * @param platform 平台名称
     * @param selfId   机器人 ID
     */
    public static void deleteGuildRole(String guildId, String roleId, String platform, String selfId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("role_id", roleId);
        SendMessage.sendGenericMessage(platform, selfId, "guild.role", "delete", map.toString());
    }
}
