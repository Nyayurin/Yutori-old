<div align="center">

# Yutori

基于 [Satori](https://satori.js.org/zh-CN/) 协议的 Java 机器人开发工具包

<img src="https://img.shields.io/badge/JDK-17+-brightgreen.svg?style=flat-square" alt="jdk-version">

</div>

# 快速开始

1. 创建一个 Maven 项目
2. 依赖引入
3. 基本配置
4. 进阶

## 依赖引入

### Maven

```xml

<dependencies>
    <dependency>
        <groupId>io.github.nyayurn</groupId>
        <artifactId>yutori</artifactId>
        <version>0.0.9-Fix</version>
    </dependency>
</dependencies>
```

## 基本配置

### 启动类

```java
public class Main {
    private final ListenerContainer listenerContainer = new ListenerContainer();
    private final PropertiesEntity properties = new PropertiesEntity("127.0.0.1:5500", "token");

    public Main() {
        // new 一个对象以触发监听器的注册
        new TestListener(listenerContainer, properties);
    }

    private void run() {
        try {
            // 新建一个 WebSocket 连接
            new MyWebSocketClient(properties.getAddress(), properties.getToken(), listenerContainer).connect();
        } catch (URISyntaxException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
```

### 第一个监听器

```java
public class TestListener {
    private final PropertiesEntity properties;

    public TestListener(ListenerContainer listenerContainer, PropertiesEntity properties) {
        this.properties = properties;
        // 通过在构造器内对 listenerContainer 添加一个事件实现注册
        listenerContainer.addOnEventListener(event -> {
            // 判断事件的类型是否为 message-created(新消息创建, 即接收到消息)
            if (event.getType().equals(MessageEvents.MESSAGE_CREATED)) {
                this.onMessage(event);
            }
        });
    }

    public void onMessage(EventEntity event) {
        if ("test".equals(event.getMessage().getContent())) {
            ChannelEntity channel = event.getChannel();
            // 通过对应 API 类的方法发送消息
            MessageApi messageApi = new MessageApi(event.getPlatform(), event.getSelfId(), properties);
            messageApi.createMessage((channel.getType().equals(ChannelEntity.DIRECT) ? "private:" : "") + channel.getId(),
                    "test done!");
        }
    }
}
```

# 进阶

- 框架整体与 Satori 架构基本一致, 请参考 [Satori 文档](https://satori.js.org/zh-CN/protocol)
- 源码内有 javadoc 方便阅读, 请自行阅读源码

|      API       |     描述      |
|:--------------:|:-----------:|
|   ChannelApi   |  频道相关的 API  |
|    GuildApi    |  群组相关的 API  |
| GuildMemberApi | 群组成员相关的 API |
|  GuildRoleApi  | 群组角色相关的 API |
|    LoginApi    | 登录信息相关的 API |
|   MessageApi   |  消息相关的 API  |
|  ReactionApi   |  表态相关的 API  |
|    UserApi     |  用户相关的 API  |

|            MessageElement             |  描述  |
|:-------------------------------------:|:----:|
|               AtElement               |  AT  |
|              HrefElement              | 超链接  |
|             SharpElement              | 提及频道 |
|              TextElement              | 纯文本  |
|     BoldElement<br>StrongElement      |  粗体  |
| DeleteElement<br>StrikethroughElement | 删除线  |
|      EmElement<br>ItalicElement       |  斜线  |
|    InsElement<br>UnderlineElement     | 下划线  |
|              CodeElement              | 代码片段 |
|              SplElement               |  剧透  |
|              SubElement               |  下标  |
|              SupElement               |  上标  |
|             AuthorElement             |  作者  |
|             QuoteElement              |  引用  |
|             AudioElement              |  语音  |
|              FileElement              |  文件  |
|              ImgElement               |  图片  |
|             VideoElement              |  视频  |
|               BrElement               |  换行  |
|            MessageElement             |  消息  |
|           ParagraphElement            |  段落  |