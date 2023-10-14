package com.yurn.satori.sdk.message.element.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 图片
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ImgElement extends BaseResourceElement {
    /**
     * 图片的宽度
     */
    protected Long width;

    /**
     * 图片的高度
     */
    protected Long height;

    public ImgElement(@NonNull String src) {
        this(src, null, null);
    }

    public ImgElement(@NonNull String src, @Nullable Long width, @Nullable Long height) {
        super(src);
        this.width = width;
        this.height = height;
    }

    public ImgElement(@NonNull String src, @Nullable Boolean cache, @Nullable String timeout, @Nullable Long width, @Nullable Long height) {
        super(src, cache, timeout);
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        Element element = DocumentHelper.createElement("img");
        if (src != null) {
            element.addAttribute("src", src);
        }
        if (cache != null) {
            element.addAttribute("cache", String.valueOf(cache));
        }
        if (timeout != null) {
            element.addAttribute("timeout", timeout);
        }
        if (width != null) {
            element.addAttribute("width", String.valueOf(width));
        }
        if (height != null) {
            element.addAttribute("height", String.valueOf(height));
        }
        return element.asXML();
    }
}
