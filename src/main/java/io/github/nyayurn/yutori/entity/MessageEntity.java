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

package io.github.nyayurn.yutori.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.lang.reflect.Member;

/**
 * 消息
 *
 * @author Yurn
 */
@Data
public class MessageEntity {
    /**
     * 消息 ID
     * 不可为 null
     */
    private String id;

    /**
     * 消息内容
     * 不可为 null
     */
    private String content;

    /**
     * 频道对象
     * 可为 null
     */
    private ChannelEntity channel;

    /**
     * 群组对象
     * 可为 null
     */
    private GuildEntity guild;

    /**
     * 成员对象
     * 可为 null
     */
    private Member member;

    /**
     * 用户对象
     * 可为 null
     */
    private UserEntity user;

    /**
     * 消息发送的时间戳
     * 可为 null
     */
    @JSONField(name = "created_at")
    private Long createdAt;

    /**
     * 消息修改的时间戳
     * 可为 null
     */
    @JSONField(name = "updated_at")
    private Long updatedAt;
}
