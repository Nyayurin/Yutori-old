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
 * 用户
 *
 * @author Yurn
 */
@Data
public class UserEntity {
    /**
     * 用户 ID
     * 不可为 null
     */
    private String id;

    /**
     * 用户名称
     * 可为 null
     */
    private String name;

    /**
     * 用户头像
     * 可为 null
     */
    private String avatar;

    /**
     * 是否为机器人
     * 可为 null
     */
    @JSONField(name = "is_bot")
    private Boolean isBot;
}
