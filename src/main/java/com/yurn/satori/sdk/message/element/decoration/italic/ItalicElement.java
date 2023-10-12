package com.yurn.satori.sdk.message.element.decoration.italic;

import com.yurn.satori.sdk.message.element.basic.TextElement;

/**
 * 斜体
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public class ItalicElement extends TextElement {
    public ItalicElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return "<i>" + super.toString() + "</i>";
    }
}
