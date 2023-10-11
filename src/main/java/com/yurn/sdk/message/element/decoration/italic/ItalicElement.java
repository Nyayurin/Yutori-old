package com.yurn.sdk.message.element.decoration.italic;

import com.yurn.sdk.message.element.basic.TextElement;

/**
 * 斜体
 *
 * @author Yurn
 */
public class ItalicElement extends TextElement {
    public ItalicElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return "<i>" + super.toString() + "</i>";
    }
}
