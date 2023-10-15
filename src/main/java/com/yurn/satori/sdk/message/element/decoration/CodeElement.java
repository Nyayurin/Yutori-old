package com.yurn.satori.sdk.message.element.decoration;

import com.yurn.satori.sdk.message.element.basic.TextElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 代码
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CodeElement extends TextElement {
    public CodeElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        String result = "<code>";
        if (text != null) {
            result += super.toString();
        }
        result += "</code>";
        return result;
    }
}