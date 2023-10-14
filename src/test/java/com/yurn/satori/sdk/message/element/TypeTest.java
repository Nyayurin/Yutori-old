package com.yurn.satori.sdk.message.element;

import com.yurn.satori.sdk.message.element.typesetting.BrElement;
import com.yurn.satori.sdk.message.element.typesetting.MessageElement;
import com.yurn.satori.sdk.message.element.typesetting.ParagraphElement;
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
                        <message id="123456" forward="true">message</message>""",
                new MessageElement("message", "123456", true).toString());
    }

    @Test
    public void paragraph() {
        Assertions.assertEquals("""
                        <p/>""",
                new ParagraphElement().toString());
    }
}
