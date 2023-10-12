package com.yurn.satori.sdk.message.element.resource;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 视频
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public class VideoElement extends BaseResourceElement {

    public VideoElement(@NonNull String src) {
        super(src);
    }

    public VideoElement(@NonNull String src,
                        @Nullable Boolean cache,
                        @Nullable String timeout) {
        super(src, cache, timeout);
    }

    @Override
    public String toString() {
        return "<video" + super.toString() + "/>";
    }
}
