/*
Copyright (c) 2023 Yurn
YurnSatoriSdk is licensed under Mulan PSL v2.
You can use this software according to the terms and conditions of the Mulan PSL v2.
You may obtain a copy of Mulan PSL v2 at:
         http://license.coscl.org.cn/MulanPSL2
THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
See the Mulan PSL v2 for more details.
 */

package com.yurn.satori.sdk.entity.events;

/**
 * 登录的事件列表
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public final class MessageEvents {
    /**
     * 当消息被创建时触发
     * 必需资源: channel, message, user
     */
    public static final String MESSAGE_CREATED = "message-created";

    /**
     * 当消息被编辑时触发
     * 必需资源: channel, message, user
     */
    public static final String MESSAGE_UPDATED = "message-updated";

    /**
     * 当消息被删除时触发
     * 必需资源: channel, message, user
     */
    public static final String MESSAGE_DELETED = "message-deleted";

    private MessageEvents() {}
}
