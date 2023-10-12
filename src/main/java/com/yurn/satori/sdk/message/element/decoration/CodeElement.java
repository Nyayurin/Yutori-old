package com.yurn.satori.sdk.message.element.decoration;

import com.yurn.satori.sdk.message.element.basic.TextElement;

/**
 * 代码
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public class CodeElement extends TextElement {
    public CodeElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return "<code>" + super.toString() + "</code>";
    }
}