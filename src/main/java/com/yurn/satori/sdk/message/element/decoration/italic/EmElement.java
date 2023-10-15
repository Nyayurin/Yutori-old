package com.yurn.satori.sdk.message.element.decoration.italic;

import com.yurn.satori.sdk.message.element.basic.TextElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 斜体
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EmElement extends TextElement {
    public EmElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        String result = "<em>";
        if (text != null) {
            result += super.toString();
        }
        result += "</em>";
        return result;
    }
}