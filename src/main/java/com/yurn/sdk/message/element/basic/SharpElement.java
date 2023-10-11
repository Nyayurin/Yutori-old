package com.yurn.sdk.message.element.basic;

import com.yurn.sdk.message.element.BaseMessageElement;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 提及频道
 *
 * @author Yurn
 */
public class SharpElement extends BaseMessageElement {
    /**
     * 目标频道的 ID
     */
    private final String id;
    /**
     * 目标频道的名称
     */
    private final String name;

    public SharpElement(@NonNull String id,
                        @Nullable String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        String str = "<sharp id=&quot;" + id + "&quot;";
        if (name != null) {
            str += " name=&quot;" + name + "&quot;";
        }
        str += "/>";
        return str;
    }
}
