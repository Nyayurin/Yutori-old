package com.yurn.satori.sdk.message.element.basic;

import com.yurn.satori.sdk.message.element.BaseMessageElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 提及频道
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SharpElement extends BaseMessageElement {
    /**
     * 目标频道的 ID
     */
    protected String id;

    /**
     * 目标频道的名称
     */
    protected String name;

    public SharpElement(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        Element element = DocumentHelper.createElement("sharp");
        if (id != null) {
            element.addAttribute("id", id);
        }
        if (name != null) {
            element.addAttribute("name", name);
        }
        return element.asXML();
    }
}
