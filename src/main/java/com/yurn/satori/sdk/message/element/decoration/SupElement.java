package com.yurn.satori.sdk.message.element.decoration;

import com.yurn.satori.sdk.message.element.basic.TextElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.lang.NonNull;

/**
 * 上标
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SupElement extends TextElement {
    public SupElement(@NonNull String text) {
        super(text);
    }

    @Override
    public String toString() {
        Element element = DocumentHelper.createElement("sup");
        if (text != null) {
            element.setText(text);
        }
        return element.asXML();
    }
}