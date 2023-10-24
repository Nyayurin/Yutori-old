package io.github.nyayurn.yutori.message.element;

import io.github.nyayurn.yutori.message.element.decoration.CodeElement;
import io.github.nyayurn.yutori.message.element.decoration.SplElement;
import io.github.nyayurn.yutori.message.element.decoration.SubElement;
import io.github.nyayurn.yutori.message.element.decoration.SupElement;
import io.github.nyayurn.yutori.message.element.decoration.bold.BoldElement;
import io.github.nyayurn.yutori.message.element.decoration.bold.StrongElement;
import io.github.nyayurn.yutori.message.element.decoration.delete.DeleteElement;
import io.github.nyayurn.yutori.message.element.decoration.delete.StrikethroughElement;
import io.github.nyayurn.yutori.message.element.decoration.italic.EmElement;
import io.github.nyayurn.yutori.message.element.decoration.italic.ItalicElement;
import io.github.nyayurn.yutori.message.element.decoration.underline.InsElement;
import io.github.nyayurn.yutori.message.element.decoration.underline.UnderlineElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DecoTest {
    @Test
    public void bold() {
        Assertions.assertEquals("""
                        <b>胖的</b>""",
                new BoldElement("胖的").toString());
        Assertions.assertEquals("""
                        <strong>胖的它哥</strong>""",
                new StrongElement("胖的它哥").toString());
    }

    @Test
    public void delete() {
        Assertions.assertEquals("""
                        <del>中分线</del>""",
                new DeleteElement("中分线").toString());
        Assertions.assertEquals("""
                        <s>删除线</s>""",
                new StrikethroughElement("删除线").toString());
    }

    @Test
    public void italic() {
        Assertions.assertEquals("""
                        <em>MJ Peek</em>""",
                new EmElement("MJ Peek").toString());
        Assertions.assertEquals("""
                        <i>Annie are you okay?</i>""",
                new ItalicElement("Annie are you okay?").toString());
    }

    @Test
    public void underline() {
        Assertions.assertEquals("""
                        <ins>我一个滑铲</ins>""",
                new InsElement("我一个滑铲").toString());
        Assertions.assertEquals("""
                        <u>填空题</u>""",
                new UnderlineElement("填空题").toString());
    }

    @Test
    public void code() {
        Assertions.assertEquals("""
                        <code>&lt;C&gt;+C &lt;C&gt;+V</code>""",
                new CodeElement("<C>+C <C>+V").toString());
    }

    @Test
    public void spl() {
        Assertions.assertEquals("""
                        <spl>透透你</spl>""",
                new SplElement("透透你").toString());
    }

    @Test
    public void su() {
        Assertions.assertEquals("""
                        <sub>啥B</sub>""",
                new SubElement("啥B").toString());
        Assertions.assertEquals("""
                        <sup>啥批</sup>""",
                new SupElement("啥批").toString());
    }
}
