package example

import io.github.nyayurn.yutori.*
import io.github.nyayurn.yutori.MyWebSocketClient.Companion.makeClient

// 监听器容器
val listenerContainer = ListenerContainer()

// Satori 相关设置
val properties = Properties("127.0.0.1:5500", "token")

fun main() {
    // new 一下触发自动注册
    ExampleKotlinListener
    // 调用工厂方法得到 WebSocketClient 并调用连接方法
    (properties makeClient listenerContainer).connect()
}

object ExampleKotlinListener {
    init {
        // 通过在 init 内对 listenerContainer 添加一个事件实现注册
        listenerContainer.onEventDelegate.add { event: Event ->
            // 判断事件的类型是否为 message-created(新消息创建, 即接收到消息)
            if (event.type == MessageEvents.MESSAGE_CREATED) {
                onMessage(event)
            }
        }
    }

    private fun onMessage(event: Event) {
        // 判断消息内容是否符合触发条件
        if ("test" == event.message?.content) {
            // 通过对应 API 类的方法发送消息
            val messageApi = MessageApi.of(event, properties)
            messageApi.createMessage(event.channel!!.id,
                message {
                    at(event.user!!.id)
                    +"Test done!"
                })
        }
    }
}