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

/**
 * Satori Websocket 客户端
 * @property client Satori
 * @property name 客户端名称, 用于区分多个客户端
 * @property heartbeat 心跳
 * @property reconnect 重练
 */
@Slf4j
class SatoriSocketClient(private val client: Satori, private val name: String? = null) : WebSocketClient(
    URI("ws://${client.properties.address}/${client.properties.version}/events"), Draft_6455()
) {
    private var heartbeat: ScheduledFuture<*>? = null
    private var reconnect: ScheduledFuture<*>? = null

    override fun onOpen(serverHandshake: ServerHandshake) {
        log.info("[$name]: 成功建立 WebSocket 连接")
        reconnect?.cancel(false)
        sendIdentify()
    }

    override fun onMessage(message: String) {
        val signaling = Signaling.parse(message)
        when (signaling.op) {
            Signaling.READY -> {
                val ready = signaling.body as Ready
                log.info("[$name]: 成功建立事件推送(${ready.logins.size}): \n${
                    ready.logins.joinToString("\n") { "{platform: ${it.platform}, selfId: ${it.selfId}}" }
                }")
                // 心跳
                heartbeat?.cancel(false)
                val sendSignaling = Signaling(Signaling.PING)
                heartbeat = ScheduledThreadPoolExecutor(1, NamedThreadFactory("Heart")).scheduleAtFixedRate({
                    if (this.isOpen) send(JSONObject.toJSONString(sendSignaling))
                }, 10, 10, TimeUnit.SECONDS)
            }

            Signaling.EVENT -> sendEvent(signaling)
            Signaling.PONG -> log.debug("[$name]: 收到 PONG")
            else -> log.error("Unsupported $signaling")
        }
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        log.info("[$name]: 断开连接, code: $code, reason: $reason, remote: $remote")
        heartbeat?.cancel(false)
        reconnect?.cancel(false)
        setReconnect()
    }

    override fun onError(e: Exception) {
        log.error("[$name]: 出现错误: $e")
        heartbeat?.cancel(false)
        reconnect?.cancel(false)
        setReconnect()
    }

    private fun sendIdentify() {
        val connection = Signaling(Signaling.IDENTIFY)
        val token = client.properties.token
        val sequence = client.properties.sequence
        if (token != null || sequence != null) {
            val body = Identify()
            body.token = token
            body.sequence = sequence
            connection.body = body
        }
        this.send(JSONObject.toJSONString(connection))
    }

    private fun sendEvent(signaling: Signaling) {
        val body = signaling.body as Event
        log.info("[$name]: 接收事件: platform: ${body.platform}, selfId: ${body.selfId}, type: ${body.type}")
        log.debug("[$name]: 事件详细信息: $body")
        client.properties.sequence = body.id
        client.runEvent(body)
    }

    private fun setReconnect() {
        reconnect = ScheduledThreadPoolExecutor(1, NamedThreadFactory("Reconnect")).scheduleAtFixedRate({
            log.info("[$name]: 尝试重新连接")
            connect()
        }, 3, 3, TimeUnit.SECONDS)
    }

    companion object {
        @JvmStatic
        @JvmOverloads
        fun of(client: Satori, name: String? = null) = SatoriSocketClient(client, name)
    }
}