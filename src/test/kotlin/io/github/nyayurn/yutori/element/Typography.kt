package io.github.nyayurn.yutori.element

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class TypeTest {
    @Test
    fun br() {
        Assertions.assertEquals(
            "<br/>",
            Br().toString()
        )
    }

    @Test
    fun message() {
        Assertions.assertEquals(
            "<message id=\"123456\" forward>message</message>",
            Message("message", "123456", true).toString()
        )
    }

    @Test
    fun paragraph() {
        Assertions.assertEquals(
            "<p/>",
            Paragraph().toString()
        )
    }
}
