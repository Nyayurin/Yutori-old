package com.yurn.satori.sdk.util;

/**
 * @author Yurn
 */
public class XmlUtil {
    public static String encode(String raw) {
        return raw.replace("&", "&amp;")
                .replace("\"", "&quot;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
