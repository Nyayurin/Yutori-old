package com.yurn.sdk.message.element.typesetting;

import com.yurn.sdk.message.element.BaseMessageElement;

/**
 * 段落
 *
 * @author Yurn
 */
public class ParagraphElement extends BaseMessageElement {
    @Override
    public String toXmlString() {
        return "<p/>";
    }
}
