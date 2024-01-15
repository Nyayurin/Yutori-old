package io.github.nyayurn.yutori

import io.github.nyayurn.yutori.Level.*
import org.slf4j.LoggerFactory

enum class Level {
    ERROR, WARN, INFO, DEBUG, TRACE
}

interface Logger {
    fun log(level: Level, msg: String, clazz: Class<*>) {
        when (level) {
            ERROR -> error(msg, clazz)
            WARN -> warn(msg, clazz)
            INFO -> info(msg, clazz)
            DEBUG -> debug(msg, clazz)
            TRACE -> trace(msg, clazz)
        }
    }

    fun log(level: Level, msg: String, clazzName: String) {
        when (level) {
            ERROR -> error(msg, clazzName)
            WARN -> warn(msg, clazzName)
            INFO -> info(msg, clazzName)
            DEBUG -> debug(msg, clazzName)
            TRACE -> trace(msg, clazzName)
        }
    }

    fun error(msg: String, clazz: Class<*>)
    fun error(msg: String, clazzName: String)
    fun warn(msg: String, clazz: Class<*>)
    fun warn(msg: String, clazzName: String)
    fun info(msg: String, clazz: Class<*>)
    fun info(msg: String, clazzName: String)
    fun debug(msg: String, clazz: Class<*>)
    fun debug(msg: String, clazzName: String)
    fun trace(msg: String, clazz: Class<*>)
    fun trace(msg: String, clazzName: String)
}

class Slf4jLogger : Logger {
    override fun error(msg: String, clazz: Class<*>) = LoggerFactory.getLogger(clazz).error(msg)
    override fun error(msg: String, clazzName: String) = LoggerFactory.getLogger(clazzName).error(msg)
    override fun warn(msg: String, clazz: Class<*>) = LoggerFactory.getLogger(clazz).warn(msg)
    override fun warn(msg: String, clazzName: String) = LoggerFactory.getLogger(clazzName).warn(msg)
    override fun info(msg: String, clazz: Class<*>) = LoggerFactory.getLogger(clazz).info(msg)
    override fun info(msg: String, clazzName: String) = LoggerFactory.getLogger(clazzName).info(msg)
    override fun debug(msg: String, clazz: Class<*>) = LoggerFactory.getLogger(clazz).debug(msg)
    override fun debug(msg: String, clazzName: String) = LoggerFactory.getLogger(clazzName).debug(msg)
    override fun trace(msg: String, clazz: Class<*>) = LoggerFactory.getLogger(clazz).trace(msg)
    override fun trace(msg: String, clazzName: String) = LoggerFactory.getLogger(clazzName).trace(msg)
}