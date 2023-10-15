package com.yurn.satori.sdk.message.element.decoration.delete;

import com.yurn.satori.sdk.message.element.basic.TextElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 删除线
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class StrikethroughElement extends TextElement {
    public StrikethroughElement(String text) {
        super(text);
    }

    @Override
    public String toString() {
        String result = "<s>";
        if (text != null) {
            result += super.toString();
        }
        result += "</s>";
        return result;
    }
}