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

package io.github.nyayurn.yutori.message.element.meta;

import io.github.nyayurn.yutori.message.element.BaseMessageElement;
import io.github.nyayurn.yutori.util.XmlUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 作者
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AuthorElement extends BaseMessageElement {
    /**
     * 用户 ID
     */
    protected String userId;

    /**
     * 昵称
     */
    protected String nickname;

    /**
     * 头像 URL
     */
    protected String avatar;

    public AuthorElement(String userId, String nickname, String avatar) {
        this.userId = userId;
        this.nickname = nickname;
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        String result = "<author";
        if (userId != null) {
            result += " user-id=\"" + userId + "\"";
        }
        if (nickname != null) {
            result += " nickname=\"" + XmlUtil.encode(nickname) + "\"";
        }
        if (avatar != null) {
            result += " avatar=\"" + XmlUtil.encode(avatar) + "\"";
        }
        result += "/>";
        return result;
    }
}
