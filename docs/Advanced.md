# Satori

- 核心交互类, 存储监听器上下文, 用于注册事件监听器
- 组合 SatoriSocketClient 与 Satori Server 建立连接
- 负责转换参数并传递给监听器

# 注册监听器

- 通过 Satori 类的 onXxx 方法注册监听器
- 注册时第一个可变形参可用于为监听器添加触发条件, 只有所有条件都满足时才会触发监听器, 类似于 Filter (注: 将在 0.2.5
  修改为更合适的方式, 并更名为 Filter)
- 注册事件相关监听器时, 第三个参数为只保留纯文本后的字符串, 去掉 \<img\> 等非纯文本元素
- Java 注册监听器时, 请在最后返回 null 或 Unit.INSTANCE

# Bot

- 封装了所有 [Action](Action.md)
- 负责与 Satori Server 交互

# 多帐号

Yutori 支持多帐号, 只需多实例几个 Satori 并分别连接即可

# 消息构建方式

## 链式构建

```java
public class Main {
    public static void main(String[] args) {
        MessageChainBuilder.of()
            .at(event.getUser().getId())
            .text(" 菜单:\n")
            .text("红烧肉 红烧排骨 可乐鸡翅 糖醋排骨 水煮鱼 红烧鱼\n")
            .text("凉拌黑木耳 鱼香肉丝 水煮肉片 意大利面 麻辣小龙虾 凉拌木耳\n")
            .text("茶叶蛋 龙井虾仁 口水鸡 回锅肉 红烧猪蹄 皮蛋瘦肉\n")
            .text("粥酸菜鱼 咖喱牛肉 西红柿炒鸡蛋 辣椒酱 麻辣烫 辣白菜\n")
            .text("牛肉酱 红烧茄子 蛋炒饭 佛跳墙 四物汤 固元膏\n")
            .text("龟苓膏 银耳莲子 羹酸梅 汤腊肉")
            .build();
    }
}
```

## DSL (仅 Kotlin)

DSL 提供多种语法, 根据自己喜好选择即可

```kotlin
fun main() {
    message {
        at { id = event.user.id }
        at { id { event.user.id } }
        at(event.user.id)
        at(id = event.user.id)
        text(" 菜单:\n")
        text { "红烧肉 红烧排骨 可乐鸡翅 糖醋排骨 水煮鱼 红烧鱼\n" }
    }
}
```

Bot.createMessage 等方法提供更方便的 DSL 使用

```kotlin
fun listen(bot: Bot, event: MessageEvent, msg: String) {
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
```

# 主动发送消息

## Kotlin

```kotlin
fun main() {
    val bot = Bot.of("platform", "selfId", properties)
    bot.createMessage("channelId", "Hello, world!")
}
```

## Java

```java
public class Main {
    public static void main(String[] args) {
        Bot bot = Bot.of("platform", "selfId", properties);
        bot.createMessage("channelId", "Hello, world!");
    }
}
```

# 消息链

```kotlin
fun listen(bot: Bot, event: MessageEvent, msg: String) {
    val msgChain = event.toMsgChain()
    msgChain.forEach(::println)
}
```

# 会话恢复

- 会话恢复的 Sequence 持久化保存可采取自定义 SatoriProperties

# 管理接口

- 不准备实现
- 若你有想法, 欢迎提 PR

# 内部接口

- 请自行使用 HTTP 库发送 HTTP 请求