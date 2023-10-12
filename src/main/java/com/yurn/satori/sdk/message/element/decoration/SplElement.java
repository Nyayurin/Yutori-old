package com.yurn.satori.sdk.message.element.decoration;

import com.yurn.satori.sdk.message.element.basic.TextElement;

/**
 * 剧透
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public class SplElement extends TextElement {
    public SplElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return "<spl>" + super.toString() + "</spl>";
    }
}