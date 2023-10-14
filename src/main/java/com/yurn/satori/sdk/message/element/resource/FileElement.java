package com.yurn.satori.sdk.message.element.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 文件
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FileElement extends BaseResourceElement {
    public FileElement(@NonNull String src) {
        super(src);
    }

    public FileElement(@NonNull String src, @Nullable Boolean cache, @Nullable String timeout) {
        super(src, cache, timeout);
    }

    @Override
    public String toString() {
        Element element = DocumentHelper.createElement("file");
        if (src != null) {
            element.addAttribute("src", src);
        }
        if (cache != null) {
            element.addAttribute("cache", String.valueOf(cache));
        }
        if (timeout != null) {
            element.addAttribute("timeout", timeout);
        }
        return element.asXML();
    }
}
