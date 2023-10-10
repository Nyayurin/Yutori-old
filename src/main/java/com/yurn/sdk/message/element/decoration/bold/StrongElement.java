package com.yurn.sdk.message.element.decoration.bold;

import com.yurn.sdk.message.element.basic.TextElement;

/**
 * 粗体
 *
 * @author Yurn
 */
public class StrongElement extends TextElement {
    public StrongElement(String text) {
        super(text);
    }

    @Override
    public String toXmlString() {
        return "<strong>" + super.toXmlString() + "</strong>";
    }
}
