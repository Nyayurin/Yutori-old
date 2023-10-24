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
import io.github.nyayurn.yutori.entity.GuildEntity;
import io.github.nyayurn.yutori.entity.PageResponseEntity;
import io.github.nyayurn.yutori.entity.PropertiesEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 群组 API
 *
 * @author Yurn
 */
@Data
@Slf4j
@AllArgsConstructor
public class GuildApi {
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

    public GuildApi(String platform, String selfId, PropertiesEntity properties) {
        this.platform = platform;
        this.selfId = selfId;
        this.sendMessage = new SendMessage(platform, selfId, properties);
    }

    /**
     * 获取群组
     * 根据 ID 获取, 返回一个 Guild 对象
     *
     * @param guildId 群组 ID
     * @return 输出
     */
    public GuildEntity getGuild(String guildId) {
        JSONObject map = new JSONObject();
        map.put("guild_id", guildId);
        String response = sendMessage.sendGenericMessage("guild", "get", map.toString());
        try {
            return JSONObject.parseObject(response, GuildEntity.class);
        } catch (JSONException e) {
            log.error("{}: {}", response, e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 获取群组列表
     * 获取当前用户加入的全部群组, 返回一个 Guild 的 分页列表。
     *
     * @param next 分页令牌
     * @return 输出
     */
    public List<PageResponseEntity<GuildEntity>> listGuild(String next) {
        JSONObject map = new JSONObject();
        map.put("next", next);
        String response = sendMessage.sendGenericMessage("guild", "list", map.toString());
        try {
            //noinspection unchecked
            return JSONArray.parse(response).stream().map(o -> (PageResponseEntity<GuildEntity>) o).toList();
        } catch (JSONException e) {
            log.error("{}: {}", response, e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 处理群组邀请
     * 处理来自群组的邀请
     *
     * @param messageId 请求 ID
     * @param approve   是否通过请求
     * @param comment   备注信息
     */
    public void approveGuild(String messageId, boolean approve, String comment) {
        JSONObject map = new JSONObject();
        map.put("message_id", messageId);
        map.put("approve", approve);
        map.put("comment", comment);
        sendMessage.sendGenericMessage("guild", "approve", map.toString());
    }
}
