# 快速开始

## 基础信息

- 本项目仅支持 WebSocket 连接而不支持 WebHook 连接
- 本项目建议使用 Kotlin 语言开发
- 本项目不依赖也不建议搭配 Spring 进行开发

## 项目创建

1. 首先创建一个空的项目
2. [引入依赖](#依赖引入)
3. [基础使用](#基础使用)

## 依赖引入

### Maven

```xml
<dependency>
    <groupId>io.github.nyayurn</groupId>
    <artifactId>yutori</artifactId>
    <version>0.2.4-fix</version>
</dependency>
```

### Gradle Kotlin DSL

```kotlin
implementation("io.github.nyayurn:yutori:0.2.4-fix")
```

### Gradle Groovy DSL

```groovy
implementation 'io.github.nyayurn:yutori:0.2.4-fix'
```

## 基础使用

### Java

```java
public class Main {
    public static SimpleSatoriProperties properties = new SimpleSatoriProperties("127.0.0.1:5500", "token");
    public static void main(String[] args) {
        Satori client = Satori.client(properties);
        client.onMessageCreated((bot, event, msg) -> {
            if ("在吗".equals(msg)) {
                bot.createMessage(event.getChannel().getId(), new At(event.getUser().getId()) + "我在!");
            }
            return null;
        });
        client.connect();
    }
}
```

### Kotlin

```kotlin
val properties = SimpleSatoriProperties("127.0.0.1:5500", "token")
fun main() {
    val client = Satori.client(properties)
    client.onMessageCreated { bot, event, msg ->
        if (msg == "在吗") {
            // 通过 Bot 类发送消息
            bot.createMessage(event.channel.id, "" + At(event.user.id) + " 我在!")
        }
    }
    client.connect()
}
```
## 其他

Yutori: 作者名称 Yurn 与 Satori 协议名称结合而来

## 下一步
[进阶](Advanced.md)