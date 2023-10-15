package com.yurn.satori.sdk.message.element.meta;

import com.yurn.satori.sdk.message.element.basic.TextElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 引用
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class QuoteElement extends TextElement {

    public QuoteElement( String text) {
        super(text);
    }

    @Override
    public String toString() {
        Element element = DocumentHelper.createElement("quote");
        if (text != null) {
            element.setText(text);
        }
        return element.asXML();
    }
}
