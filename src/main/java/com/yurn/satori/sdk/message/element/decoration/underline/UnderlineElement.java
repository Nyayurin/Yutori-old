package com.yurn.satori.sdk.message.element.decoration.underline;

import com.yurn.satori.sdk.message.element.basic.TextElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 下划线
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UnderlineElement extends TextElement {
    public UnderlineElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        String result = "<u>";
        if (text != null) {
            result += super.toString();
        }
        result += "</u>";
        return result;
    }
}