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
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

abstract class SatoriSocketClient(protected val client: Satori) :
    WebSocketClient(URI("ws://${client.properties.address}/v1/events"), Draft_6455()) {
    protected fun sendIdentify() {
        val connection = Signaling(Signaling.IDENTIFY)
        val token = client.properties.token
        val sequence = client.properties.sequence
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

    protected fun sendEvent(signaling: Signaling) {
        val body = signaling.body as Event
        client.properties.sequence = body.id
        client.runEvent(body)
    }
}

@Slf4j
class SimpleSatoriSocketClient(client: Satori) : SatoriSocketClient(client) {
    private var heart: ScheduledFuture<*>? = null

    override fun onOpen(serverHandshake: ServerHandshake) {
        log.info("成功建立 WebSocket 连接: $serverHandshake")
        sendIdentify()
    }

    override fun onMessage(message: String) {
        val signaling = Signaling.parse(message)
        when (signaling.op) {
            Signaling.READY -> {
                val ready = signaling.body as Signaling.Ready
                log.info("成功建立事件推送: ${ready.logins}")
                // 心跳
                val sendConnection = Signaling(Signaling.PING)
                heart = ScheduledThreadPoolExecutor(1, NamedThreadFactory("Heart")).scheduleAtFixedRate(
                    { send(JSONObject.toJSONString(sendConnection)) }, 10, 10, TimeUnit.SECONDS
                )
            }

            Signaling.EVENT -> sendEvent(signaling)
            Signaling.PONG -> {}
            else -> log.error("Unsupported $signaling")
        }
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        log.info("断开连接, code: $code, reason: $reason, remote: $remote")
        if (heart != null && !heart!!.isCancelled && !heart!!.isDone) {
            heart!!.cancel(true)
        }
    }

    override fun onError(e: Exception) {
        log.error("出现错误: $e")
    }

    companion object {
        @JvmStatic
        fun of(client: Satori) = SimpleSatoriSocketClient(client)
    }
}