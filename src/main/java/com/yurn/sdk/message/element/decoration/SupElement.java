package com.yurn.sdk.message.element.decoration;

import com.yurn.sdk.message.element.basic.TextElement;

/**
 * 上标
 *
 * @author Yurn
 */
public class SupElement extends TextElement {
    public SupElement(String text) {
        super(text);
    }

    @Override
    public String toXmlString() {
        return "<sup>" + super.toXmlString() + "</sup>";
    }
}