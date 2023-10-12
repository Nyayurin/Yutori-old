package com.yurn.satori.sdk.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * 频道
 *
 * @author Yurn
 */
@Data
public class ChannelEntity {
    /**
     * 频道 ID
     * 不可为 null
     */
    private String id;

    /**
     * 频道类型
     * 不可为 null
     */
    private Integer type;

    /**
     * 频道名称
     * 可为 null
     */
    private String name;

    /**
     * 父频道 ID
     * 可为 null
     */
    @JSONField(name = "parent_id")
    private String parentId;

    /**
     * 文本频道
     */
    public static final int TEXT = 0;
    /**
     * 语音频道
     */
    public static final int VOICE = 1;
    /**
     * 分类频道
     */
    public static final int CATEGORY = 2;
    /**
     * 私聊频道
     */
    public static final int DIRECT = 3;
}
