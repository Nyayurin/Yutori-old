package com.yurn.satori.sdk.message.element.decoration;

import com.yurn.satori.sdk.message.element.basic.TextElement;

/**
 * 下标
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public class SubElement extends TextElement {
    public SubElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return "<sub>" + super.toString() + "</sub>";
    }
}