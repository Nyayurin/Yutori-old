package com.yurn.sdk.message;

import com.yurn.sdk.message.element.BaseMessageElement;
import org.springframework.lang.NonNull;

/**
 * 消息构造器
 *
 * @author Yurn
 */
public class MessageBuilder {
    private final StringBuilder builder = new StringBuilder();

    public String build() {
        return builder.toString();
    }

    public MessageBuilder append(@NonNull String string) {
        builder.append(string);
        return this;
    }

    public MessageBuilder append(@NonNull BaseMessageElement messageElement) {
        builder.append(messageElement.toXmlString());
        return this;
    }


}
