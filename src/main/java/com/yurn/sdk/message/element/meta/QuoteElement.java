package com.yurn.sdk.message.element.meta;

import com.yurn.sdk.message.element.basic.TextElement;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 引用
 *
 * @author Yurn
 */
public class QuoteElement extends TextElement {
    /**
     * 消息的 ID
     */
    private final String id;
    /**
     * 是否为转发消息
     */
    private final Boolean forward;

    public QuoteElement(@NonNull String text) {
        this(text, null, null);
    }

    public QuoteElement(@NonNull String text,
                        @Nullable String id,
                        @Nullable Boolean forward) {
        super(text);
        this.id = id;
        this.forward = forward;
    }

    @Override
    public String toString() {
        String str = "<quote";
        if (id != null) {
            str += " id=&quot;" + id + "&quot;";
        }
        if (forward != null) {
            str += " forward=&quot;" + forward + "&quot;";
        }
        str += ">" + super.toString() + "</quote>";
        return str;
    }
}
