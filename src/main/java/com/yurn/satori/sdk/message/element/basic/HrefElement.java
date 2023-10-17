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

package com.yurn.satori.sdk.message.element.basic;

import com.yurn.satori.sdk.message.element.BaseMessageElement;
import com.yurn.satori.sdk.util.XmlUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 链接
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class HrefElement extends BaseMessageElement {
    /**
     * 链接的 URL
     */
    protected String href;

    public HrefElement(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        String result = "<a";
        if (href != null) {
            result += " href=\"" + XmlUtil.encode(href) + "\"";
        }
        result += "/>";
        return result;
    }
}
