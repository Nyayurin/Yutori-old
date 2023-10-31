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
import io.github.nyayurn.yutori.entity.GuildMemberEntity;
import io.github.nyayurn.yutori.entity.PageResponseEntity;
import io.github.nyayurn.yutori.entity.PropertiesEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * 群组成员 API
 *
 * @author Yurn
 */
@Data
@Slf4j
@AllArgsConstructor
public class GuildMemberApi {
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

    public GuildMemberApi(String platform, String selfId, PropertiesEntity properties) {
        this.platform = platform;
        this.selfId = selfId;
        this.sendMessage = new SendMessage(platform, selfId, properties);
    }


    /**
     * 获取群组成员
     * 获取群成员信息, 返回一个 GuildMember 对象
     *
     * @param guildId 群组 ID
     * @param userId  用户 ID
     * @return 输出
     */
    public GuildMemberEntity getGuildMember(String guildId, String userId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("user_id", userId);
        String response = sendMessage.sendGenericMessage("guild.member", "get", map.toString());
        try {
            return JSONObject.parseObject(response, GuildMemberEntity.class);
        } catch (JSONException e) {
            log.error("{}: {}", response, e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 获取群组成员列表
     * 获取群成员列表, 返回一个 GuildMember 的 分页列表
     *
     * @param guildId 群组 ID
     * @param next    分页令牌
     * @return 输出
     */
    public List<PageResponseEntity<GuildMemberEntity>> listGuildMember(String guildId, String next) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("next", next);
        String response = sendMessage.sendGenericMessage("guild.member", "list", map.toString());
        try {
            //noinspection unchecked
            return Optional.ofNullable(JSONArray.parse(response))
                    .map(objects -> objects.stream().map(o -> (PageResponseEntity<GuildMemberEntity>) o).toList())
                    .orElse(null);
        } catch (JSONException e) {
            log.error("{}: {}", response, e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 踢出群组成员
     * 将某个用户踢出群组
     *
     * @param guildId 群组 ID
     * @param userId  用户 ID
     */
    public void kickGuildMember(String guildId, String userId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("user_id", userId);
        sendMessage.sendGenericMessage("guild.member", "kick", map.toString());
    }

    /**
     * 踢出群组成员
     * 将某个用户踢出群组
     *
     * @param guildId   群组 ID
     * @param userId    用户 ID
     * @param permanent 是否永久踢出 (无法再次加入群组)
     */
    public void kickGuildMember(String guildId, String userId, boolean permanent) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("user_id", userId);
        map.put("permanent", permanent);
        sendMessage.sendGenericMessage("guild.member", "kick", map.toString());
    }

    /**
     * 通过群组成员申请
     * 处理加群请求
     *
     * @param messageId 请求 ID
     * @param approve   是否通过请求
     */
    public void approveGuildMember(String messageId, boolean approve) {
        JSONObject map = new JSONObject();
        map.put("message_id", messageId);
        map.put("approve", approve);
        sendMessage.sendGenericMessage("guild.member", "approve", map.toString());
    }

    /**
     * 通过群组成员申请
     * 处理加群请求
     *
     * @param messageId 请求 ID
     * @param approve   是否通过请求
     * @param comment   备注信息
     */
    public void approveGuildMember(String messageId, boolean approve, String comment) {
        JSONObject map = new JSONObject();
        map.put("message_id", messageId);
        map.put("approve", approve);
        map.put("comment", comment);
        sendMessage.sendGenericMessage("guild.member", "approve", map.toString());
    }
}
