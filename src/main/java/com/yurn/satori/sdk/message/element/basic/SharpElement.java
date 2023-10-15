package com.yurn.satori.sdk.message.element.basic;

import com.yurn.satori.sdk.message.element.BaseMessageElement;
import com.yurn.satori.sdk.util.XmlUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
        String result = "<sharp";
        if (id != null) {
            result += " id=\"" + id + "\"";
        }
        if (name != null) {
            result += " name=\"" + XmlUtil.encode(name) + "\"";
        }
        result += "/>";
        return result;
    }
}
