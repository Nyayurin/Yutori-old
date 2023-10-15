package com.yurn.satori.sdk.message.element.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 文件
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FileElement extends BaseResourceElement {
    public FileElement( String src) {
        super(src);
    }

    public FileElement( String src,  Boolean cache,  String timeout) {
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
