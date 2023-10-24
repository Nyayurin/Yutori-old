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

/**
 * 登录信息
 *
 * @author Yurn
 */
@Data
public class LoginEntity {
    /**
     * 用户对象
     * 可为 null
     */
    private UserEntity user;

    /**
     * 平台帐号
     * 可为 null
     */
    @JSONField(name = "self_id")
    private String selfId;

    /**
     * 平台名称
     * 可为 null
     */
    private String platform;

    /**
     * 登录状态
     * 不可为 null
     */
    private Integer status;

    /**
     * 离线
     */
    public static final int OFFLINE = 0;
    /**
     * 在线
     */
    public static final int ONLINE = 1;
    /**
     * 连接中
     */
    public static final int CONNECT = 2;
    /**
     * 断开连接
     */
    public static final int DISCONNECT = 3;
    /**
     * 重新连接
     */
    public static final int RECONNECT = 4;
}
