package com.yurn.sdk.entity;

import lombok.Data;

/**
 * 群组角色
 *
 * @author Yurn
 */
@Data
public class GuildRole {
    /**
     * 角色 ID
     * 不可为 null
     */
    private String id;

    /**
     * 角色名称
     * 可为 null
     */
    private String name;
}
