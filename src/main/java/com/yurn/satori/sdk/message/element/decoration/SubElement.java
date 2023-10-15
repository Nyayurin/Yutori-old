package com.yurn.satori.sdk.message.element.decoration;

import com.yurn.satori.sdk.message.element.basic.TextElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 下标
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SubElement extends TextElement {
    public SubElement( String text) {
        super(text);
    }

    @Override
    public String toString() {
        Element element = DocumentHelper.createElement("sub");
        if (text != null) {
            element.setText(text);
        }
        return element.asXML();
    }
}