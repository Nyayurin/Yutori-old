<div align="center">

# YurnSatoriFramework

基于 [Satori](https://satori.js.org/zh-CN/) 协议的 Java 机器人开发框架

<img src="https://img.shields.io/badge/JDK-17+-brightgreen.svg?style=flat-square" alt="jdk-version">

</div>

# 快速开始

## 基础信息

> 提示: 本文档默认您了解并熟悉 Java 基本语法
> 
> 注意: 仅支持 JDK 17+

- 推荐使用 [YurnQbotFramework](https://github.com/Nyayurn/YurnQbotFramework) 框架进行 QQ 机器人的开发

## 使用流程

1. 首先创建一个空的 Maven 项目(什么?不会?可以右上角关闭本页面了)
2. 依赖引入
3. 基本配置
4. 进阶

## 依赖引入

### Maven

1. 在项目目录下新建 lib 文件夹
2. 下载 jar 包并将其丢进 lib 文件夹内
3. 配置 pom.xml

```xml

<dependencies>
    <!-- 核心框架 -->
    <dependency>
        <groupId>com.yurn</groupId>
        <artifactId>YurnSatoriFramework</artifactId>
        <version>0.0.4-SNAPSHOT</version>
        <scope>system</scope>
        <systemPath>${project.basedir}/lib/YurnSatoriFramework-0.0.4-SNAPSHOT.jar</systemPath>
    </dependency>
    <!-- Http 和 WebSocket 所需依赖 -->
    <dependency>
        <artifactId>okhttp</artifactId>
        <groupId>com.squareup.okhttp3</groupId>
        <version>4.10.0</version>
    </dependency>
    <!-- 日志系统(可替换为其他slf4j实现) -->
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.4.11</version>
    </dependency>
    <!-- JSON 序列化/反序列化 -->
    <dependency>
        <groupId>com.alibaba.fastjson2</groupId>
        <artifactId>fastjson2</artifactId>
        <version>2.0.40</version>
    </dependency>
</dependencies>
```

## 基本配置

### 启动类

```java
public class Main {
    static {
        // new 一个对象以触发监听器的注册
        new TestListener();
    }
    
    public static void main(String[] args) {
        // 初始化核心启动类并传递一个 Properties 参数实体类, 并运行
        new Boot(new PropertiesEntity("127.0.0.1:5500", "token")).run();
    }
}
```

### 第一个监听器

```java
import com.yurn.satori.sdk.BotContainer;

public class TestListener {
    public TestListener() {
        // 通过在构造器内对 GlobalEventChannel 的实例添加一个事件实现注册
        GlobalEventChannel.INSTANCE.addEvent(event -> {
            // 判断事件的类型是否为 message-created(新消息创建, 即接收到消息)
            if (event.getType().equals(MessageEvents.MESSAGE_CREATED)) {
                this.onMessage(event);
            }
        });
    }

    public void onMessage(EventEntity event) {
        if ("test".equals(event.getMessage().getContent())) {
            // 此为单 Bot 写法, 多 Bot 请自行过滤出对应的 LoginEntity 实例
            LoginEntity login = BotContainer.getINSTANCE().getLogins()[0];
            ChannelEntity channel = event.getChannel();
            // 通过对应 API 类的静态方法发送消息
            MessageApi.createMessage((channel.getType().equals(ChannelEntity.DIRECT) ? "private:" : "") + channel.getId(),
                    "test done!", login.getPlatform(), login.getSelfId());
        }
    }
}
```

# 进阶

- 框架整体与 Satori 架构基本一致, 请参考 [Satori 文档](https://satori.js.org/zh-CN/protocol)
- 源码含有大量 javadoc 方便阅读, 请自行阅读源码
