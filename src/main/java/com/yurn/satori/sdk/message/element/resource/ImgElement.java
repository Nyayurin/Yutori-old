package com.yurn.satori.sdk.message.element.resource;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 图片
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public class ImgElement extends BaseResourceElement {
    /**
     * 图片的宽度
     */
    private final Long width;
    /**
     * 图片的高度
     */
    private final Long height;

    public ImgElement(@NonNull String src) {
        this(src, null, null);
    }

    public ImgElement(@NonNull String src,
                      @Nullable Long width,
                      @Nullable Long height) {
        super(src);
        this.width = width;
        this.height = height;
    }

    public ImgElement(@NonNull String src,
                      @Nullable Boolean cache,
                      @Nullable String timeout,
                      @Nullable Long width,
                      @Nullable Long height) {
        super(src, cache, timeout);
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        String str = "<img" + super.toString();
        if (width != null) {
            str += " width=&quot;" + width + "&quot;";
        }
        if (height != null) {
            str += " height=&quot;" + height + "&quot;";
        }
        str += "/>";
        return str;
    }
}
