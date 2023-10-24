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

package io.github.nyayurn.yutori.message.element.decoration.italic;

import io.github.nyayurn.yutori.message.element.basic.TextElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 斜体
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EmElement extends TextElement {
    public EmElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        String result = "<em>";
        if (text != null) {
            result += super.toString();
        }
        result += "</em>";
        return result;
    }
}