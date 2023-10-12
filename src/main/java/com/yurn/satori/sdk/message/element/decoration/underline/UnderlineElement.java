package com.yurn.satori.sdk.message.element.decoration.underline;

import com.yurn.satori.sdk.message.element.basic.TextElement;

/**
 * 下划线
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public class UnderlineElement extends TextElement {
    public UnderlineElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return "<u>" + super.toString() + "</u>";
    }
}