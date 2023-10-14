package com.yurn.satori.sdk.message.element;

import com.yurn.satori.sdk.message.element.meta.AuthorElement;
import com.yurn.satori.sdk.message.element.meta.QuoteElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MetaTest {
    @Test
    public void author() {
        Assertions.assertEquals("""
                        <author user-id="2.5" nickname="鸡王" avatar="https://th.bing.com/th/id/OIP.0Ld_Qg_bBOkzJzphqBHWBAHaEK"/>""",
                new AuthorElement("2.5", "鸡王", "https://th.bing.com/th/id/OIP.0Ld_Qg_bBOkzJzphqBHWBAHaEK").toString());
    }

    @Test
    public void quote() {
        Assertions.assertEquals("""
                        <quote>引用</quote>""",
                new QuoteElement("引用").toString());
    }
}
