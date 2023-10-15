package com.yurn.satori.sdk.message.element.decoration.underline;

import com.yurn.satori.sdk.message.element.basic.TextElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 下划线
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class InsElement extends TextElement {
    public InsElement( String text) {
        super(text);
    }

    @Override
    public String toString() {
        Element element = DocumentHelper.createElement("ins");
        if (text != null) {
            element.setText(text);
        }
        return element.asXML();
    }
}