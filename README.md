<div align="center">

# Yutori

基于 [Satori](https://satori.chat) 协议的 Java 机器人开发工具包

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
        <version>0.1.1</version>
    </dependency>
</dependencies>
```

## 示例

[Java](https://github.com/Nyayurn/Yutori/blob/master/src/test/java/example/Main.java)

[Kotlin](https://github.com/Nyayurn/Yutori/blob/master/src/test/kotlin/example/Example.kt)

# 进阶

- 参考 [Satori 文档](https://satori.chat/zh-CN/protocol)
- 请自行阅读源码

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

|     MessageElement      |  描述  |
|:-----------------------:|:----:|
|          Text           | 纯文本  |
|           At            |  AT  |
|          Sharp          | 提及频道 |
|          Href           | 超链接  |
|           Img           |  图片  |
|          Audio          |  语音  |
|          Video          |  视频  |
|          File           |  文件  |
|     Bold<br>Strong      |  粗体  |
|     Idiomatic<br>Em     |  斜线  |
|    Underline<br>Ins     | 下划线  |
| Strikethrough<br>Delete | 删除线  |
|           Spl           |  剧透  |
|          Code           | 代码片段 |
|           Sup           |  上标  |
|           Sub           |  下标  |
|           Br            |  换行  |
|        Paragraph        |  段落  |
|         Message         |  消息  |
|          Quote          |  引用  |
|         Author          |  作者  |
|         Button          |  按钮  |

# 迁移

如果你想从 0.0.9 之前的版本迁移到 0.0.9, 请删除所有的 import, 并重新导入

或从 0.1.1 之前的版本迁移到0.1.1, 请按新格式重写

# 其他

Yutori: 作者名称 Yurn 与 Satori 协议名称结合而来