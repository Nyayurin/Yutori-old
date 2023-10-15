package com.yurn.satori.sdk.message.element.resource;

import com.yurn.satori.sdk.util.XmlUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 文件
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FileElement extends BaseResourceElement {
    public FileElement(String src) {
        super(src);
    }

    public FileElement(String src, Boolean cache, String timeout) {
        super(src, cache, timeout);
    }

    @Override
    public String toString() {
        String result = "<file";
        if (src != null) {
            result += " src=\"" + XmlUtil.encode(src) + "\"";
        }
        if (cache != null) {
            result += " cache";
        }
        if (timeout != null) {
            result += " timeout=\"" + timeout + "\"";
        }
        result += "/>";
        return result;
    }
}
