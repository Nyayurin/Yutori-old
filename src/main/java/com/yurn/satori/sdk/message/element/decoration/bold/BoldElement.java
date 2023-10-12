package com.yurn.satori.sdk.message.element.decoration.bold;

import com.yurn.satori.sdk.message.element.basic.TextElement;

/**
 * 粗体
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public class BoldElement extends TextElement {
    public BoldElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return "<b>" + super.toString() + "</b>";
    }
}
