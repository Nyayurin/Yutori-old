package com.yurn.sdk.message.element.decoration;

import com.yurn.sdk.message.element.basic.TextElement;

/**
 * 代码
 *
 * @author Yurn
 */
public class CodeElement extends TextElement {
    public CodeElement(String text) {
        super(text);
    }

    @Override
    public String toXmlString() {
        return "<code>" + super.toXmlString() + "</code>";
    }
}