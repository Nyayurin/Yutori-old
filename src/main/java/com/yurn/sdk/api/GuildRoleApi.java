package com.yurn.sdk.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yurn.sdk.entity.GuildRole;
import com.yurn.sdk.entity.Login;
import com.yurn.sdk.entity.PageResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 群组角色 API
 *
 * @author Yurn
 */
@Component
public class GuildRoleApi {
    @Resource
    private SendMessage sendMessage;

    /**
     * 设置群组成员角色
     * 设置群组内用户的角色
     *
     * @param guildId 群组 ID
     * @param userId  用户 ID
     * @param roleId  角色 ID
     * @param login   机器人信息
     */
    public void setGuildRole(String guildId, String userId, String roleId, Login login) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("user_id", userId);
        map.put("role_id", roleId);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "guild.member.role", "set", map.toString());
    }

    /**
     * 取消群组成员角色
     * 取消群组内用户的角色
     *
     * @param guildId 群组 ID
     * @param userId  用户 ID
     * @param roleId  角色 ID
     * @param login   机器人信息
     */
    public void unsetGuildRole(String guildId, String userId, String roleId, Login login) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("user_id", userId);
        map.put("role_id", roleId);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "guild.member.role", "unset", map.toString());
    }

    /**
     * 获取群组角色列表
     * 获取群组角色列表, 返回一个 GuildRole 的 分页列表
     *
     * @param guildId 群组 ID
     * @param next    分页令牌
     * @param login   机器人信息
     * @return 输出
     */
    public List<PageResponse<GuildRole>> listGuildRole(String guildId, String next, Login login) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("next", next);
        String response = sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "guild.role", "list", map.toString());
        //noinspection unchecked
        return JSONArray.parse(response).stream().map(o -> ((PageResponse<GuildRole>) o)).toList();
    }

    /**
     * 创建群组角色
     * 创建群组角色, 返回一个 GuildRole 对象
     *
     * @param guildId 群组 ID
     * @param role    角色数据
     * @param login   机器人信息
     * @return 输出
     */
    public GuildRole createGuildRole(String guildId, GuildRole role, Login login) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("role", role);
        String response = sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "guild.role", "create", map.toString());
        return JSONObject.parseObject(response, GuildRole.class);
    }

    /**
     * 修改群组角色
     *
     * @param guildId 群组 ID
     * @param roleId  角色 ID
     * @param role    角色数据
     * @param login   机器人信息
     */
    public void updateGuildRole(String guildId, String roleId, GuildRole role, Login login) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("role_id", roleId);
        map.put("role", role);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "guild.role", "update", map.toString());
    }

    /**
     * 删除群组角色
     *
     * @param guildId 群组 ID
     * @param roleId  角色 ID
     * @param login   机器人信息
     */
    public void deleteGuildRole(String guildId, String roleId, Login login) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("role_id", roleId);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "guild.role", "delete", map.toString());
    }
}
