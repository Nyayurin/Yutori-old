/*
Copyright (c) 2023 Yurn
yutori is licensed under Mulan PSL v2.
You can use this software according to the terms and conditions of the Mulan PSL v2.
You may obtain a copy of Mulan PSL v2 at:
         http://license.coscl.org.cn/MulanPSL2
THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
See the Mulan PSL v2 for more details.
 */

package io.github.nyayurn.yutori

import com.alibaba.fastjson2.JSONObject
import io.github.nyayurn.yutori.Slf4j.Companion.log
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import org.java_websocket.util.NamedThreadFactory
import java.net.URI
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

class ListenerContainer {
    val onOpenDelegate = mutableListOf<(ServerHandshake) -> Unit>()
    val onMessageDelegate = mutableListOf<(Signaling) -> Unit>()
    val onConnectDelegate = mutableListOf<(Signaling.Ready) -> Unit>()
    val onEventDelegate = mutableListOf<(Event) -> Unit>()
    val onDisconnectDelegate = mutableListOf<(String) -> Unit>()
    val onErrorDelegate = mutableListOf<(Exception) -> Unit>()

    fun runOnOpenListeners(response: ServerHandshake) {
        Executors.defaultThreadFactory().newThread {
            for (listener in onOpenDelegate) {
                listener(response)
            }
        }.start()
    }

    fun runOnMessageListeners(entity: Signaling) {
        Executors.defaultThreadFactory().newThread {
            for (listener in onMessageDelegate) {
                listener(entity)
            }
        }.start()
    }

    fun runOnConnectListeners(ready: Signaling.Ready) {
        Executors.defaultThreadFactory().newThread {
            for (listener in onConnectDelegate) {
                listener(ready)
            }
        }.start()
    }

    fun runOnEventListeners(event: Event) {
        Executors.defaultThreadFactory().newThread {
            for (listener in onEventDelegate) {
                listener(event)
            }
        }.start()
    }

    fun runOnDisconnectListeners(s: String) {
        Executors.defaultThreadFactory().newThread {
            for (listener in onDisconnectDelegate) {
                listener(s)
            }
        }.start()
    }

    fun runOnErrorListeners(e: Exception) {
        Executors.defaultThreadFactory().newThread {
            for (listener in onErrorDelegate) {
                listener(e)
            }
        }.start()
    }
}

@Slf4j
class MyWebSocketClient(address: String, private val token: String? = null, private val listenerContainer: ListenerContainer) :
    WebSocketClient(URI("ws://$address/v1/events"), Draft_6455()) {

    private var heart: ScheduledFuture<*>? = null
    private var reconnect: ScheduledFuture<*>? = null
    private var sequence: Number? = null

    override fun onOpen(serverHandshake: ServerHandshake) {
        log.info("成功建立 WebSocket 连接: $serverHandshake")
        if (reconnect != null && !reconnect!!.isCancelled && !reconnect!!.isDone) {
            reconnect!!.cancel(true)
        }
        listenerContainer.runOnOpenListeners(serverHandshake)
        sendIdentify()
    }

    override fun onMessage(message: String) {
        val connection = Signaling.parse(message)
        listenerContainer.runOnMessageListeners(connection)
        when (connection.op) {
            Signaling.EVENT -> {
                val body = connection.body as Event
                sequence = body.id
                listenerContainer.runOnEventListeners(body)
            }

            Signaling.PONG -> {}
            Signaling.READY -> {
                val ready = connection.body as Signaling.Ready
                log.info("成功建立事件推送: ${ready.logins}")
                // 心跳
                val sendConnection = Signaling(Signaling.PING)
                heart = ScheduledThreadPoolExecutor(1, NamedThreadFactory("Heart")).scheduleAtFixedRate(
                    { send(JSONObject.toJSONString(sendConnection)) }, 10, 10, TimeUnit.SECONDS
                )
                listenerContainer.runOnConnectListeners(ready)
            }

            else -> {
                log.error("Unsupported $connection")
            }
        }
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        log.info("断开连接, code: $code, reason: $reason, remote: $remote")
        if (heart != null && !heart!!.isCancelled && !heart!!.isDone) {
            heart!!.cancel(true)
        }

        listenerContainer.runOnDisconnectListeners(reason)

        // 断线重练
        reconnect = ScheduledThreadPoolExecutor(1, NamedThreadFactory("Reconnect"))
            .scheduleAtFixedRate({
                log.info("尝试重新连接")
                connect()
            }, 3, 3, TimeUnit.SECONDS)
    }

    override fun onError(e: Exception) {
        log.error("出现错误: $e")
        listenerContainer.runOnErrorListeners(e)
    }

    private fun sendIdentify() {
        val connection = Signaling(Signaling.IDENTIFY)
        if (token != null || sequence != null) {
            val body = Identify()
            if (token != null) {
                body.token = token
            }
            if (sequence != null) {
                body.sequence = sequence
            }
            connection.body = body
        }
        this.send(JSONObject.toJSONString(connection))
    }

    companion object {
        infix fun Properties.makeClient(listenerContainer: ListenerContainer) = of(this, listenerContainer)
        fun of(properties: Properties, listenerContainer: ListenerContainer) =
            MyWebSocketClient(properties.address, properties.token, listenerContainer)
    }
}