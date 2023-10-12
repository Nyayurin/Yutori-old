<div align="center">

# YurnSatoriFramework

基于 [Satori](https://satori.js.org/zh-CN/) 协议的 Java 机器人开发框架

<img src="https://img.shields.io/badge/JDK-17+-brightgreen.svg?style=flat-square" alt="jdk-version">

</div>

# 快速开始

## 基础信息

> 提示: 本文档默认您了解并熟悉 Java 基本语法以及 SpringBoot 开发体系

> 注意: 仅支持 JDK 17+ 与 SpringBoot 3.0.0+

- 推荐使用 [YurnQbotFramework](https://github.com/Nyayurn/YurnQbotFramework) 框架进行 QQ 机器人的开发

## 项目创建

1. 首先创建一个空的 SpringBoot 项目(什么?不会?可以右上角关闭本页面了)
2. 依赖引入
3. 第一个监听器
4. 进阶

## 依赖引入

### Maven

- 在项目目录下新建 lib 文件夹
- 下载 jar 包并将其丢进 lib 文件夹内
- 配置 pom.xml

```xml
<dependency>
    <groupId>com.yurn</groupId>
    <artifactId>YurnSatoriFramework</artifactId>
    <version>0.0.1</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/lib/YurnSatoriFramework-0.0.1.jar</systemPath>
</dependency>
```

## 第一个监听器

> 注意: Chronocat 暂不支持接收好友请求, 所以该监听器应当失效

```java
@Component
public class FriendRequestListener {
    public FriendRequestListener() {
        GlobalEventChannel.INSTANCE.add(event -> {
            if (event.getType().equals(UserEvents.FRIEND_REQUEST)) {
                this.onMessage(event);
            }
        });
    }

    public void onMessage(Event event) {
        LoginEntity login = BotContainer.getLogins()[0];
        MessageApi.createMessage("private:114514", "有新的好友请求哦: " + event.toString(), login.getPlatform(), login.getSelfId());
    }
}
```

# 进阶

- 框架整体与 Satori 架构基本一致, 请参考 [Satori 文档](https://satori.js.org/zh-CN/protocol)
- 源码含有大量 javadoc 方便阅读, 请自行阅读源码

## 打包

- 修改 pom.xml

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <includeSystemScope>true</includeSystemScope>
    </configuration>
</plugin>
```
