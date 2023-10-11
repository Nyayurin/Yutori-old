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
    public String toString() {
        return "<strong>" + super.toString() + "</strong>";
    }
}
