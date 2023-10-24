package io.github.nyayurn.yutori.message.element;

import io.github.nyayurn.yutori.message.element.resource.AudioElement;
import io.github.nyayurn.yutori.message.element.resource.FileElement;
import io.github.nyayurn.yutori.message.element.resource.ImgElement;
import io.github.nyayurn.yutori.message.element.resource.VideoElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResourceTest {
    @Test
    public void audio() {
        Assertions.assertEquals("""
                        <audio src="audio" cache timeout="60000"/>""",
                new AudioElement("audio", true, "60000").toString());
        Assertions.assertEquals("""
                        <audio src="&quot;&amp;&lt;&gt;" cache timeout="60000"/>""",
                new AudioElement("\"&<>", true, "60000").toString());
    }

    @Test
    public void file() {
        Assertions.assertEquals("""
                        <file src="file" cache timeout="60000"/>""",
                new FileElement("file", true, "60000").toString());
        Assertions.assertEquals("""
                        <file src="&quot;&amp;&lt;&gt;" cache timeout="60000"/>""",
                new FileElement("\"&<>", true, "60000").toString());
    }

    @Test
    public void image() {
        Assertions.assertEquals("""
                        <img src="image" cache timeout="60000" width=1920 height=1080/>""",
                new ImgElement("image", true, "60000", 1920L, 1080L).toString());
        Assertions.assertEquals("""
                        <img src="&quot;&amp;&lt;&gt;" cache timeout="60000" width=1920 height=1080/>""",
                new ImgElement("\"&<>", true, "60000", 1920L, 1080L).toString());
    }

    @Test
    public void video() {
        Assertions.assertEquals("""
                        <video src="video" cache timeout="60000"/>""",
                new VideoElement("video", true, "60000").toString());
        Assertions.assertEquals("""
                        <video src="&quot;&amp;&lt;&gt;" cache timeout="60000"/>""",
                new VideoElement("\"&<>", true, "60000").toString());
    }
}
