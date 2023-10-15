package com.yurn.satori.sdk.message.element.decoration;

import com.yurn.satori.sdk.message.element.basic.TextElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 代码
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CodeElement extends TextElement {
    public CodeElement( String text) {
        super(text);
    }

    @Override
    public String toString() {
        Element element = DocumentHelper.createElement("code");
        if (text != null) {
            element.setText(text);
        }
        return element.asXML();
    }
}