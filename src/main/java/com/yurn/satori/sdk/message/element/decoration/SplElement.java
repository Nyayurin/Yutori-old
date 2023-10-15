package com.yurn.satori.sdk.message.element.decoration;

import com.yurn.satori.sdk.message.element.basic.TextElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 剧透
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SplElement extends TextElement {
    public SplElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        String result = "<spl>";
        if (text != null) {
            result += super.toString();
        }
        result += "</spl>";
        return result;
    }
}