package com.yurn.sdk.message.element.resource;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 语音
 *
 * @author Yurn
 */
public class AudioElement extends BaseResourceElement {

    public AudioElement(@NonNull String src) {
        super(src);
    }

    public AudioElement(@NonNull String src,
                        @Nullable Boolean cache,
                        @Nullable String timeout) {
        super(src, cache, timeout);
    }

    @Override
    public String toXmlString() {
        return "<audio" + super.toXmlString() + "/>";
    }
}
