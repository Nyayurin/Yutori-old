package com.yurn.sdk.message.element.decoration.italic;

import com.yurn.sdk.message.element.basic.TextElement;

/**
 * 斜体
 *
 * @author Yurn
 */
public class EmElement extends TextElement {
    public EmElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return "<em>" + super.toString() + "</em>";
    }
}