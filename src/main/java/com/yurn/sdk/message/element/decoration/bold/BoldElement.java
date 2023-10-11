package com.yurn.sdk.message.element.decoration.bold;

import com.yurn.sdk.message.element.basic.TextElement;

/**
 * 粗体
 *
 * @author Yurn
 */
public class BoldElement extends TextElement {
    public BoldElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return "<b>" + super.toString() + "</b>";
    }
}
