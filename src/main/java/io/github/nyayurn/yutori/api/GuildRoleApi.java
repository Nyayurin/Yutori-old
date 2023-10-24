/*
Copyright (c) 2023 Yurn
yutori is licensed under Mulan PSL v2.
You can use this software according to the terms and conditions of the Mulan PSL v2.
You may obtain a copy of Mulan PSL v2 at:
         http://license.coscl.org.cn/MulanPSL2
THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
See the Mulan PSL v2 for more details.
 */

package io.github.nyayurn.yutori.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import io.github.nyayurn.yutori.entity.GuildRoleEntity;
import io.github.nyayurn.yutori.entity.PageResponseEntity;
import io.github.nyayurn.yutori.entity.PropertiesEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 群组角色 API
 *
 * @author Yurn
 */
@Data
@Slf4j
@AllArgsConstructor
public class GuildRoleApi {
    /**
     * 平台名称
     */
    private String platform;

    /**
     * 机器人 ID
     */
    private String selfId;

    /**
     * SendMessage 实例类
     */
    private SendMessage sendMessage;

    public GuildRoleApi(String platform, String selfId, PropertiesEntity properties) {
        this.platform = platform;
        this.selfId = selfId;
        this.sendMessage = new SendMessage(platform, selfId, properties);
    }


    /**
     * 设置群组成员角色
     * 设置群组内用户的角色
     *
     * @param guildId 群组 ID
     * @param userId  用户 ID
     * @param roleId  角色 ID
     */
    public void setGuildRole(String guildId, String userId, String roleId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("user_id", userId);
        map.put("role_id", roleId);
        sendMessage.sendGenericMessage("guild.member.role", "set", map.toString());
    }

    /**
     * 取消群组成员角色
     * 取消群组内用户的角色
     *
     * @param guildId 群组 ID
     * @param userId  用户 ID
     * @param roleId  角色 ID
     */
    public void unsetGuildRole(String guildId, String userId, String roleId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("user_id", userId);
        map.put("role_id", roleId);
        sendMessage.sendGenericMessage("guild.member.role", "unset", map.toString());
    }

    /**
     * 获取群组角色列表
     * 获取群组角色列表, 返回一个 GuildRole 的 分页列表
     *
     * @param guildId 群组 ID
     * @param next    分页令牌
     * @return 输出
     */
    public List<PageResponseEntity<GuildRoleEntity>> listGuildRole(String guildId, String next) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("next", next);
        String response = sendMessage.sendGenericMessage("guild.role", "list", map.toString());
        try {
            //noinspection unchecked
            return JSONArray.parse(response).stream().map(o -> ((PageResponseEntity<GuildRoleEntity>) o)).toList();
        } catch (JSONException e) {
            log.error("{}: {}", response, e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 创建群组角色
     * 创建群组角色, 返回一个 GuildRole 对象
     *
     * @param guildId 群组 ID
     * @param role    角色数据
     * @return 输出
     */
    public GuildRoleEntity createGuildRole(String guildId, GuildRoleEntity role) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("role", role);
        String response = sendMessage.sendGenericMessage("guild.role", "create", map.toString());
        try {
            return JSONObject.parseObject(response, GuildRoleEntity.class);
        } catch (JSONException e) {
            log.error("{}: {}", response, e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 修改群组角色
     *
     * @param guildId 群组 ID
     * @param roleId  角色 ID
     * @param role    角色数据
     */
    public void updateGuildRole(String guildId, String roleId, GuildRoleEntity role) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("role_id", roleId);
        map.put("role", role);
        sendMessage.sendGenericMessage("guild.role", "update", map.toString());
    }

    /**
     * 删除群组角色
     *
     * @param guildId 群组 ID
     * @param roleId  角色 ID
     */
    public void deleteGuildRole(String guildId, String roleId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("role_id", roleId);
        sendMessage.sendGenericMessage("guild.role", "delete", map.toString());
    }
}
