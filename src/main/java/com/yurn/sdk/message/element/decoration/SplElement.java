package com.yurn.sdk.message.element.decoration;

import com.yurn.sdk.message.element.basic.TextElement;

/**
 * 剧透
 *
 * @author Yurn
 */
public class SplElement extends TextElement {
    public SplElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return "<spl>" + super.toString() + "</spl>";
    }
}