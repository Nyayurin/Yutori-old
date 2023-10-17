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
