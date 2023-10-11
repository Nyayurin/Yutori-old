package com.yurn.sdk.message.element.decoration.underline;

import com.yurn.sdk.message.element.basic.TextElement;

/**
 * 下划线
 *
 * @author Yurn
 */
public class UnderlineElement extends TextElement {
    public UnderlineElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return "<u>" + super.toString() + "</u>";
    }
}