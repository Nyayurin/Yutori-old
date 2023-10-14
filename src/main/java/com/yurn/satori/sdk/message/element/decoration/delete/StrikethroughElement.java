package com.yurn.satori.sdk.message.element.decoration.delete;

import com.yurn.satori.sdk.message.element.basic.TextElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.lang.NonNull;

/**
 * 删除线
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class StrikethroughElement extends TextElement {
    public StrikethroughElement(@NonNull String text) {
        super(text);
    }

    @Override
    public String toString() {
        Element element = DocumentHelper.createElement("s");
        if (text != null) {
            element.setText(text);
        }
        return element.asXML();
    }
}