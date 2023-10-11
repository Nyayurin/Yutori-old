package com.yurn.sdk.message.element.decoration.underline;

import com.yurn.sdk.message.element.basic.TextElement;

/**
 * 下划线
 *
 * @author Yurn
 */
public class InsElement extends TextElement {
    public InsElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return "<ins>" + super.toString() + "</ins>";
    }
}