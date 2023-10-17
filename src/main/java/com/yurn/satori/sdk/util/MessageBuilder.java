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

package com.yurn.satori.sdk.util;

import com.yurn.satori.sdk.message.element.BaseMessageElement;
import com.yurn.satori.sdk.message.element.basic.TextElement;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息构造器
 *
 * @author Yurn
 */
@Getter
public class MessageBuilder {
    private final List<BaseMessageElement> list = new LinkedList<>();

    public String build() {
        return list.stream().map(Object::toString).collect(Collectors.joining());
    }

    public String build(String delimiter) {
        return list.stream().map(Object::toString).collect(Collectors.joining(delimiter));
    }

    public MessageBuilder append(BaseMessageElement messageElement) {
        list.add(messageElement);
        return this;
    }

    public MessageBuilder append(String string) {
        list.add(new TextElement(string));
        return this;
    }

    public MessageBuilder append(Object object) {
        list.add(new TextElement(object.toString()));
        return this;
    }
}