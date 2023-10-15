package com.yurn.satori.sdk.message.element.meta;

import com.yurn.satori.sdk.message.element.basic.TextElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 引用
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class QuoteElement extends TextElement {

    public QuoteElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        String result = "<quote>";
        if (text != null) {
            result += super.toString();
        }
        result += "</quote>";
        return result;
    }
}
