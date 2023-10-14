package com.yurn.satori.sdk.message.element.basic;

import com.yurn.satori.sdk.message.element.BaseMessageElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.lang.NonNull;

/**
 * 链接
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class HrefElement extends BaseMessageElement {
    /**
     * 链接的 URL
     */
    protected String href;

    public HrefElement(@NonNull String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        Element element = DocumentHelper.createElement("a");
        if (href != null) {
            element.addAttribute("href", href);
        }
        return element.asXML();
    }
}
