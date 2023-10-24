package io.github.nyayurn.yutori.message.element;

import io.github.nyayurn.yutori.message.element.basic.AtElement;
import io.github.nyayurn.yutori.message.element.basic.HrefElement;
import io.github.nyayurn.yutori.message.element.basic.SharpElement;
import io.github.nyayurn.yutori.message.element.basic.TextElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BasicTest {
    @Test
    public void at() {
        Assertions.assertEquals("""
                        <at id="114514" name="田所浩二" role="admin" type="all"/>""",
                new AtElement("114514", "田所浩二", "admin", "all").toString());
        Assertions.assertEquals("""
                        <at id="114514" name="&quot;&amp;&lt;&gt;" role="admin" type="all"/>""",
                new AtElement("114514", "\"&<>", "admin", "all").toString());
    }

    @Test
    public void href() {
        Assertions.assertEquals("""
                        <a href="https://www.baidu.com"/>""",
                new HrefElement("https://www.baidu.com").toString());
        Assertions.assertEquals("""
                        <a href="https://www.baidu.com/&quot;&amp;&lt;&gt;"/>""",
                new HrefElement("https://www.baidu.com/\"&<>").toString());
    }

    @Test
    public void sharp() {
        Assertions.assertEquals("""
                        <sharp id="1919810" name="大粪交流群"/>""",
                new SharpElement("1919810", "大粪交流群").toString());
        Assertions.assertEquals("""
                        <sharp id="1919810" name="&quot;&amp;&lt;&gt;"/>""",
                new SharpElement("1919810", "\"&<>").toString());
    }

    @Test
    public void text() {
        Assertions.assertEquals(
                "&quot;&amp;&lt;&gt;",
                new TextElement("\"&<>").toString());
    }
}
