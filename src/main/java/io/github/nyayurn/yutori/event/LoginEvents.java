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

package io.github.nyayurn.yutori.event;

/**
 * 登录的事件列表
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public final class LoginEvents {
    /**
     * 登录被创建时触发
     * 必需资源: login
     */
    public static final String LOGIN_ADDED = "login-added";

    /**
     * 登录被删除时触发
     * 必需资源: login
     */
    public static final String LOGIN_REMOVED = "login-removed";

    /**
     * 登录信息更新时触发
     * 必需资源: login
     */
    public static final String LOGIN_UPDATED = "login-updated";

    private LoginEvents() {}
}
