package com.yurn.satori.sdk.message.element.decoration.delete;

import com.yurn.satori.sdk.message.element.basic.TextElement;

/**
 * 删除线
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public class StrikethroughElement extends TextElement {
    public StrikethroughElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return "<s>" + super.toString() + "</s>";
    }
}