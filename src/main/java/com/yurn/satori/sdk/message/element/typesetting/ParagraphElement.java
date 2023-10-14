package com.yurn.satori.sdk.message.element.typesetting;

import com.yurn.satori.sdk.message.element.BaseMessageElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 段落
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ParagraphElement extends BaseMessageElement {
    @Override
    public String toString() {
        return "<p/>";
    }
}
