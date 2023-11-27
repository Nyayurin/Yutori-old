package io.github.nyayurn.yutori

import mu.KotlinLogging
import org.slf4j.Logger

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Slf4j {
    companion object {
        val <reified T> T.log: Logger
            inline get() = logger
        val <reified T> T.logger: Logger
            inline get() = KotlinLogging.logger { T::class.java.name }
    }
}
