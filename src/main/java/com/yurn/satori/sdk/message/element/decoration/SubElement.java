package com.yurn.satori.sdk.message.element.decoration;

import com.yurn.satori.sdk.message.element.basic.TextElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 下标
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SubElement extends TextElement {
    public SubElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        String result = "<sub>";
        if (text != null) {
            result += super.toString();
        }
        result += "</sub>";
        return result;
    }
}