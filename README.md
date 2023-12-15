<div align="center">

# Yutori

基于 [Satori](https://satori.chat) 协议的 JVM 平台机器人开发工具包

<img src="https://img.shields.io/badge/JDK-8+-brightgreen.svg?style=flat-square" alt="jdk-version">

</div>

# 快速开始

## 依赖引入

### Maven

```xml

<dependencies>
    <dependency>
        <groupId>io.github.nyayurn</groupId>
        <artifactId>yutori</artifactId>
        <version>0.1.8</version>
    </dependency>
</dependencies>
```

### Gradle

```kotlin
implementation("io.github.nyayurn:yutori:0.1.8")
```

## 示例

[Java](src/test/java/example/Main.java)

[Kotlin](src/test/kotlin/example/Example.kt)

# 进阶

## 回话恢复

- 回话恢复的 Sequence 持久化保存可采取自定义 SatoriProperties 实现类实现

## 其他
- 参考 [Satori 文档](https://satori.chat/zh-CN/protocol)
- 请自行阅读源码

# 其他

Yutori: 作者名称 Yurn 与 Satori 协议名称结合而来

# 获取帮助

请添加QQ: 799712878