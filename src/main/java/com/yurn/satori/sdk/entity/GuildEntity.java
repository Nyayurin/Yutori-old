package com.yurn.satori.sdk.entity;

import lombok.Data;

/**
 * 群组
 *
 * @author Yurn
 */
@Data
public class GuildEntity {
    /**
     * 群组 ID
     * 不可为 null
     */
    private String id;

    /**
     * 群组名称
     * 可为 null
     */
    private String name;

    /**
     * 群组头像
     * 可为 null
     */
    private String avatar;
}
