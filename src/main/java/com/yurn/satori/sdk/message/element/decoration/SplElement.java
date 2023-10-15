package com.yurn.satori.sdk.message.element.decoration;

import com.yurn.satori.sdk.message.element.basic.TextElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 剧透
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SplElement extends TextElement {
    public SplElement( String text) {
        super(text);
    }

    @Override
    public String toString() {
        Element element = DocumentHelper.createElement("spl");
        if (text != null) {
            element.setText(text);
        }
        return element.asXML();
    }
}