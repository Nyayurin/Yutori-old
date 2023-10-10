package com.yurn.sdk.message.element.resource;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 文件
 *
 * @author Yurn
 */
public class FileElement extends BaseResourceElement {

    public FileElement(@NonNull String src) {
        super(src);
    }

    public FileElement(@NonNull String src,
                       @Nullable Boolean cache,
                       @Nullable String timeout) {
        super(src, cache, timeout);
    }

    @Override
    public String toXmlString() {
        return "<file" + super.toXmlString() + "/>";
    }
}
