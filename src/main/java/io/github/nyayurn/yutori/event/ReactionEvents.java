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
 * 表态的事件列表
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public final class ReactionEvents {
    /**
     * 当表态被添加时触发
     */
    public static final String REACTION_ADDED = "reaction-added";

    /**
     * 当表态被移除时触发
     */
    public static final String REACTION_REMOVED = "reaction-removed";

    private ReactionEvents() {}
}
