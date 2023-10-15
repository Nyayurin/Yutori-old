package com.yurn.satori.sdk.message.element.decoration;

import com.yurn.satori.sdk.message.element.basic.TextElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 上标
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SupElement extends TextElement {
    public SupElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        String result = "<sup>";
        if (text != null) {
            result += super.toString();
        }
        result += "</sup>";
        return result;
    }
}