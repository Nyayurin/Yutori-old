package com.yurn.sdk.message.element.decoration.delete;

import com.yurn.sdk.message.element.basic.TextElement;

/**
 * 删除线
 *
 * @author Yurn
 */
public class StrikethroughElement extends TextElement {
    public StrikethroughElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return "<s>" + super.toString() + "</s>";
    }
}