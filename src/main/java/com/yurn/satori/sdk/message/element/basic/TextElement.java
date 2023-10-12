package com.yurn.satori.sdk.message.element.basic;

import com.yurn.satori.sdk.message.element.BaseMessageElement;

/**
 * 纯文本
 *
 * @author Yurn
 */
public class TextElement extends BaseMessageElement {
    private final String text;

    public TextElement(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
