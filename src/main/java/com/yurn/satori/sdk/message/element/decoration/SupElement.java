package com.yurn.satori.sdk.message.element.decoration;

import com.yurn.satori.sdk.message.element.basic.TextElement;

/**
 * 上标
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public class SupElement extends TextElement {
    public SupElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return "<sup>" + super.toString() + "</sup>";
    }
}