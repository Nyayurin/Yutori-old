package com.yurn.satori.sdk.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * 内部事件
 *
 * @author Yurn
 */
@Data
public class InternalEventEntity {
    /**
     * 事件 ID
     * 不可为 null
     */
    private Integer id;

    /**
     * 事件类型
     * 固定为 internal
     */
    private String type;

    /**
     * 接收者的平台名称
     * 不可为 null
     */
    private String platform;

    /**
     * 接收者的平台账号
     * 不可为 null
     */
    @JSONField(name = "self_id")
    private String selfId;

    /**
     * 事件的时间戳
     * 不可为 null
     */
    private Long timestamp;

    /**
     * 内部事件类型
     * 不可为 null
     */
    @JSONField(name = "_type")
    private String internalType;

    /**
     * 内部事件数据
     * 不可为 null
     */
    @JSONField(name = "_data")
    private Object internalData;
}
