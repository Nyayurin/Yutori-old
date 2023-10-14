package com.yurn.satori.sdk.message.element;

import com.yurn.satori.sdk.message.element.resource.AudioElement;
import com.yurn.satori.sdk.message.element.resource.FileElement;
import com.yurn.satori.sdk.message.element.resource.ImgElement;
import com.yurn.satori.sdk.message.element.resource.VideoElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResourceTest {
    @Test
    public void audio() {
        Assertions.assertEquals("""
                        <audio src="audio" cache="true" timeout="60000"/>""",
                new AudioElement("audio", true, "60000").toString());
    }

    @Test
    public void file() {
        Assertions.assertEquals("""
                        <file src="file" cache="true" timeout="60000"/>""",
                new FileElement("file", true, "60000").toString());
    }

    @Test
    public void image() {
        Assertions.assertEquals("""
                        <img src="image" cache="true" timeout="60000" width="1920" height="1080"/>""",
                new ImgElement("image", true, "60000", 1920L, 1080L).toString());
    }

    @Test
    public void video() {
        Assertions.assertEquals("""
                        <video src="video" cache="true" timeout="60000"/>""",
                new VideoElement("video", true, "60000").toString());
    }
}
