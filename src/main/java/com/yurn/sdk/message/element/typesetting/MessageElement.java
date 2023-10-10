package com.yurn.sdk.message.element.typesetting;

import com.yurn.sdk.message.element.basic.TextElement;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 消息
 *
 * @author Yurn
 */
public class MessageElement extends TextElement {
    /**
     * 消息的 ID
     */
    private final String id;
    /**
     * 是否为转发消息
     */
    private final Boolean forward;

    public MessageElement(@NonNull String text) {
        this(text, null, null);
    }

    public MessageElement(@NonNull String text,
                          @Nullable String id,
                          @Nullable Boolean forward) {
        super(text);
        this.id = id;
        this.forward = forward;
    }

    @Override
    public String toXmlString() {
        String str = "<message";
        if (id != null) {
            str += " id=&quot;" + id + "&quot;";
        }
        if (forward != null) {
            str += " forward=&quot;" + forward + "&quot;";
        }
        str += ">" + super.toXmlString() + "</message>";
        return str;
    }
}
