package com.yurn.satori.sdk.message.element.decoration.italic;

import com.yurn.satori.sdk.message.element.basic.TextElement;

/**
 * 斜体
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public class EmElement extends TextElement {
    public EmElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return "<em>" + super.toString() + "</em>";
    }
}