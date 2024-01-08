package example

import io.github.nyayurn.yutori.Bot
import io.github.nyayurn.yutori.MessageEvent
import io.github.nyayurn.yutori.Satori
import io.github.nyayurn.yutori.SimpleSatoriProperties
import io.github.nyayurn.yutori.message.element.At

// Satori 相关设置
val properties = SimpleSatoriProperties("127.0.0.1:5500", "token")

fun main() {
    // 也可以直接 Satori.client("127.0.0.1:5500", "token");
    Satori.client(properties) {
        onMessageCreated { bot, event, msg ->
            // msg: 只保留纯文本后的字符串, 去掉 <img> 等非纯文本元素
            // 判断消息内容是否符合触发条件
            if ("在吗" == msg) {
                // 通过 Bot 类发送消息
                bot.createMessage(event.channel.id, "" + At(event.user.id) + " 我在!")
            }
        }
        onMessageCreated(handle = ::recipeMenu)
    }.connect()
}

fun recipeMenu(bot: Bot, event: MessageEvent, msg: String) {
    if ("菜单" == msg) {
        // 资源来自: https://home.meishichina.com/recipe-menu.html
        // 使用 DSL 构建消息
        bot.createMessage(event.channel.id) {
            at { id = event.user.id }
            text { " 菜单:\n" }
            text { "红烧肉 红烧排骨 可乐鸡翅 糖醋排骨 水煮鱼 红烧鱼\n" }
            text { "凉拌黑木耳 鱼香肉丝 水煮肉片 意大利面 麻辣小龙虾 凉拌木耳\n" }
            text { "茶叶蛋 龙井虾仁 口水鸡 回锅肉 红烧猪蹄 皮蛋瘦肉\n" }
            text { "粥酸菜鱼 咖喱牛肉 西红柿炒鸡蛋 辣椒酱 麻辣烫 辣白菜\n" }
            text { "牛肉酱 红烧茄子 蛋炒饭 佛跳墙 四物汤 固元膏\n" }
            text { "龟苓膏 银耳莲子 羹酸梅 汤腊肉" }
        }
    }
}