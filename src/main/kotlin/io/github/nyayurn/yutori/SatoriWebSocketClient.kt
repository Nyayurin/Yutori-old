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

@Slf4j
class SatoriSocketClient(private val client: Satori) : WebSocketClient(
    URI("ws://${client.properties.address}/v1/events"), Draft_6455()
) {
    private var heart: ScheduledFuture<*>? = null
    private var reconnect: ScheduledFuture<*>? = null

    override fun onOpen(serverHandshake: ServerHandshake) {
        log.info("成功建立 WebSocket 连接: ${serverHandshake.httpStatusMessage}")
        if (reconnect != null && !reconnect!!.isCancelled && !reconnect!!.isDone) {
            reconnect!!.cancel(true)
        }
        sendIdentify()
    }

    override fun onMessage(message: String) {
        val signaling = Signaling.parse(message)
        when (signaling.op) {
            Signaling.READY -> {
                val ready = signaling.body as Ready
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
        setReconnect()
    }

    override fun onError(e: Exception) {
        log.error("出现错误: $e")
        if (heart != null && !heart!!.isCancelled && !heart!!.isDone) {
            heart!!.cancel(true)
        }
        setReconnect()
    }

    private fun sendIdentify() {
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

    private fun sendEvent(signaling: Signaling) {
        val body = signaling.body as Event
        client.properties.sequence = body.id
        client.runEvent(body)
    }

    private fun setReconnect() {
        reconnect = ScheduledThreadPoolExecutor(1, NamedThreadFactory("Reconnect"))
            .scheduleAtFixedRate({
                log.info("尝试重新连接")
                connect()
            }, 3, 3, TimeUnit.SECONDS)
    }

    companion object {
        @JvmStatic
        fun of(client: Satori) = SatoriSocketClient(client)
    }
}