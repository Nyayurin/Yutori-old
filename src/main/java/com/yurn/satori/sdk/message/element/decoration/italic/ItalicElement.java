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
public class ItalicElement extends TextElement {
    public ItalicElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        String result = "<i>";
        if (text != null) {
            result += super.toString();
        }
        result += "</i>";
        return result;
    }
}
