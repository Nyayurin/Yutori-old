package example

import io.github.nyayurn.yutori.*
import io.github.nyayurn.yutori.message.message

// Satori 相关设置
val properties = Properties("127.0.0.1:5500", "token")

fun main() {
    // new 一下触发自动注册
    ExampleKotlinListener
    // 调用工厂方法得到 WebSocketClient 并调用连接方法
    (MyWebSocketClient of properties).connect()
}

object ExampleKotlinListener {
    init {
        // 在 init 内对 ListenerContainer 注册事件
        ListenerDispatcher onMessageCreated { idkWhatIShouldNameIt(it) }
        ListenerDispatcher onMessageCreated { recipeMenu(it) }
    }

    private fun idkWhatIShouldNameIt(event: MessageEvent) {
        // 判断消息内容是否符合触发条件
        if ("在吗" == event.message.content) {
            // 通过对应 API 类的方法发送消息
            val messageApi = MessageApi.of(event, properties)
            messageApi.createMessage(event.channel.id,
                message {
                    at(event.user.id)
                    +"我在!"
                })
        }
    }

    private fun recipeMenu(event: MessageEvent) {
        if ("菜单" == event.message.content) {
            val messageApi = MessageApi.of(event, properties)
            // 资源来自: https://home.meishichina.com/recipe-menu.html
            messageApi.createMessage(event.channel.id,
                message {
                    at(event.user.id)
                    +"菜单:<br/>"
                    +"红烧肉 红烧排骨 可乐鸡翅 糖醋排骨 水煮鱼 红烧鱼<br/>"
                    +"凉拌黑木耳 鱼香肉丝 水煮肉片 意大利面 麻辣小龙虾 凉拌木耳<br/>"
                    +"茶叶蛋 龙井虾仁 口水鸡 回锅肉 红烧猪蹄 皮蛋瘦肉<br/>"
                    +"粥酸菜鱼 咖喱牛肉 西红柿炒鸡蛋 辣椒酱 麻辣烫 辣白菜<br/>"
                    +"牛肉酱 红烧茄子 蛋炒饭 佛跳墙 四物汤 固元膏<br/>"
                    +"龟苓膏 银耳莲子 羹酸梅 汤腊肉"
                })
        }
    }
}