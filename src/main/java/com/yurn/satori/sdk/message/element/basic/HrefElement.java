package com.yurn.satori.sdk.message.element.basic;

import com.yurn.satori.sdk.message.element.BaseMessageElement;
import com.yurn.satori.sdk.util.XmlUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    public HrefElement(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        String result = "<a";
        if (href != null) {
            result += " href=\"" + XmlUtil.encode(href) + "\"";
        }
        result += "/>";
        return result;
    }
}
