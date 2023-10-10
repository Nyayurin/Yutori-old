package com.yurn.sdk.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yurn.sdk.entity.GuildMember;
import com.yurn.sdk.entity.Login;
import com.yurn.sdk.entity.PageResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 群组成员 API
 *
 * @author Yurn
 */
@Component
public class GuildMemberApi {
    @Resource
    private SendMessage sendMessage;

    /**
     * 获取群组成员
     * 获取群成员信息, 返回一个 GuildMember 对象
     *
     * @param guildId 群组 ID
     * @param userId  用户 ID
     * @param login   机器人信息
     * @return 输出
     */
    public GuildMember getGuildMember(String guildId, String userId, Login login) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("user_id", userId);
        String response = sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "guild.member", "get", map.toString());
        return JSONObject.parseObject(response, GuildMember.class);
    }

    /**
     * 获取群组成员列表
     * 获取群成员列表, 返回一个 GuildMember 的 分页列表
     *
     * @param guildId 群组 ID
     * @param next    分页令牌
     * @param login   机器人信息
     * @return 输出
     */
    public List<PageResponse<GuildMember>> listGuildMember(String guildId, String next, Login login) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("next", next);
        String response = sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "guild.member", "list", map.toString());
        //noinspection unchecked
        return JSONArray.parse(response).stream().map(o -> (PageResponse<GuildMember>)o).toList();
    }

    /**
     * 踢出群组成员
     * 将某个用户踢出群组
     *
     * @param guildId 群组 ID
     * @param userId  用户 ID
     * @param login   机器人信息
     */
    public void kickGuildMember(String guildId, String userId, Login login) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("user_id", userId);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "guild.member", "kick", map.toString());
    }

    /**
     * 踢出群组成员
     * 将某个用户踢出群组
     *
     * @param guildId   群组 ID
     * @param userId    用户 ID
     * @param permanent 是否永久踢出 (无法再次加入群组)
     * @param login     机器人信息
     */
    public void kickGuildMember(String guildId, String userId, boolean permanent, Login login) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        map.put("user_id", userId);
        map.put("permanent", permanent);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "guild.member", "kick", map.toString());
    }

    /**
     * 通过群组成员申请
     * 处理加群请求
     *
     * @param messageId 请求 ID
     * @param approve   是否通过请求
     * @param login     机器人信息
     */
    public void getGuildMember(String messageId, boolean approve, Login login) {
        JSONObject map = new JSONObject();
        map.put("message_id", messageId);
        map.put("approve", approve);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "guild.member", "approve", map.toString());
    }

    /**
     * 通过群组成员申请
     * 处理加群请求
     *
     * @param messageId 请求 ID
     * @param approve   是否通过请求
     * @param comment   备注信息
     * @param login     机器人信息
     */
    public void getGuildMember(String messageId, boolean approve, String comment, Login login) {
        JSONObject map = new JSONObject();
        map.put("message_id", messageId);
        map.put("approve", approve);
        map.put("comment", comment);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "guild.member", "approve", map.toString());
    }
}
