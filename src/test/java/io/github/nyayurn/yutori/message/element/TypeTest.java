package io.github.nyayurn.yutori.message.element;

import io.github.nyayurn.yutori.message.element.typesetting.BrElement;
import io.github.nyayurn.yutori.message.element.typesetting.MessageElement;
import io.github.nyayurn.yutori.message.element.typesetting.ParagraphElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TypeTest {
    @Test
    public void br() {
        Assertions.assertEquals("""
                        <br/>""",
                new BrElement().toString());
    }

    @Test
    public void message() {
        Assertions.assertEquals("""
                        <message id="123456" forward>message</message>""",
                new MessageElement("message", "123456", true).toString());
    }

    @Test
    public void paragraph() {
        Assertions.assertEquals("""
                        <p/>""",
                new ParagraphElement().toString());
    }
}
