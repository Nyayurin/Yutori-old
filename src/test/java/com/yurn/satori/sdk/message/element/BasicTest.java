package com.yurn.satori.sdk.message.element;

import com.yurn.satori.sdk.message.element.basic.AtElement;
import com.yurn.satori.sdk.message.element.basic.HrefElement;
import com.yurn.satori.sdk.message.element.basic.SharpElement;
import com.yurn.satori.sdk.message.element.basic.TextElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BasicTest {
    @Test
    public void at() {
        Assertions.assertEquals("""
                        <at id="114514" name="田所浩二" role="admin" type="all"/>""",
                new AtElement("114514", "田所浩二", "admin", "all").toString());
    }

    @Test
    public void href() {
        Assertions.assertEquals("""
                        <a href="https://www.baidu.com"/>""",
                new HrefElement("https://www.baidu.com").toString());
    }

    @Test
    public void sharp() {
        Assertions.assertEquals("""
                        <sharp id="1919810" name="大粪交流群"/>""",
                new SharpElement("1919810", "大粪交流群").toString());
    }

    @Test
    public void text() {
        Assertions.assertEquals(
                "&quot;&amp;&lt;&gt;",
                new TextElement("\"&<>").toString());
    }
}
