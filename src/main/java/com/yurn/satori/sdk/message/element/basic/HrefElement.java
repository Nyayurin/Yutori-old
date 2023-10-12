package com.yurn.satori.sdk.message.element.basic;

import com.yurn.satori.sdk.message.element.BaseMessageElement;
import org.springframework.lang.NonNull;

/**
 * 链接
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public class HrefElement extends BaseMessageElement {
    /**
     * 链接的 URL
     */
    private final String href;

    public HrefElement(@NonNull String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "<sharp href=&quot;" + href + "&quot;/>";
    }
}
