package com.yurn.satori.sdk.message.element.decoration.bold;

import com.yurn.satori.sdk.message.element.basic.TextElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 粗体
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BoldElement extends TextElement {
    public BoldElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        String result = "<b>";
        if (text != null) {
            result += super.toString();
        }
        result += "</b>";
        return result;
    }
}
