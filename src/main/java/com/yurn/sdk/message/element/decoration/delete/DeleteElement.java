package com.yurn.sdk.message.element.decoration.delete;

import com.yurn.sdk.message.element.basic.TextElement;

/**
 * 删除线
 *
 * @author Yurn
 */
public class DeleteElement extends TextElement {
    public DeleteElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return "<del>" + super.toString() + "</del>";
    }
}