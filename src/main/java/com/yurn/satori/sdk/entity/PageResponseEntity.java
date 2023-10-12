package com.yurn.satori.sdk.entity;

import lombok.Data;

/**
 * 分页列表单个对象
 *
 * @author Yurn
 */
@Data
public class PageResponseEntity<T> {
    /**
     * 数据
     * 不可为 null
     */
    private T[] data;
    /**
     * 下一页的令牌
     * 可为 null
     */
    private String next;
}
