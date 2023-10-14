package com.yurn.satori.sdk.message.element.decoration.underline;

import com.yurn.satori.sdk.message.element.basic.TextElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.lang.NonNull;

/**
 * 下划线
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UnderlineElement extends TextElement {
    public UnderlineElement(@NonNull String text) {
        super(text);
    }

    @Override
    public String toString() {
        Element element = DocumentHelper.createElement("u");
        if (text != null) {
            element.setText(text);
        }
        return element.asXML();
    }
}