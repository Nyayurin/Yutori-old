package com.yurn.satori.sdk.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * 群组成员
 *
 * @author Yurn
 */
@Data
public class GuildMemberEntity {
    /**
     * 用户对象
     * 可为 null
     */
    private UserEntity user;

    /**
     * 用户在群组中的名称
     * 可为 null
     */
    private String name;

    /**
     * 用户在群组中的头像
     * 可为 null
     */
    private String avatar;

    /**
     * 加入时间
     * 可为 null
     */
    @JSONField(name = "joined_at")
    private Long joinedAt;
}
