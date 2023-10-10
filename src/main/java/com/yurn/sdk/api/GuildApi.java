package com.yurn.sdk.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yurn.sdk.entity.Guild;
import com.yurn.sdk.entity.Login;
import com.yurn.sdk.entity.PageResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 群组 API
 *
 * @author Yurn
 */
@Component
public class GuildApi {
    @Resource
    private SendMessage sendMessage;

    /**
     * 获取群组
     * 根据 ID 获取, 返回一个 Guild 对象
     *
     * @param guildId 群组 ID
     * @param login   机器人信息
     * @return 输出
     */
    public Guild getGuild(String guildId, Login login) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        String response = sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "guild", "get", map.toString());
        return JSONObject.parseObject(response, Guild.class);
    }

    /**
     * 获取群组列表
     * 获取当前用户加入的全部群组, 返回一个 Guild 的 分页列表。
     *
     * @param next  分页令牌
     * @param login 机器人信息
     * @return 输出
     */
    public List<PageResponse<Guild>> listGuild(String next, Login login) {
        JSONObject map = new JSONObject();
        map.put("next", next);
        String response = sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "guild", "list", map.toString());
        //noinspection unchecked
        return JSONArray.parse(response).stream().map(o -> (PageResponse<Guild>)o).toList();
    }

    /**
     * 处理群组邀请
     * 处理来自群组的邀请
     *
     * @param messageId 请求 ID
     * @param approve   是否通过请求
     * @param comment   备注信息
     * @param login     机器人信息
     */
    public void approveGuild(String messageId, boolean approve, String comment, Login login) {
        JSONObject map = new JSONObject();
        map.put("message_id", messageId);
        map.put("approve", approve);
        map.put("comment", comment);
        sendMessage.sendGenericMessage(
                login.getPlatform(), login.getSelfId(), "guild", "approve", map.toString());
    }
}
