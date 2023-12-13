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
        <version>0.1.7</version>
    </dependency>
</dependencies>
```

### Gradle

```kotlin
implementation("io.github.nyayurn:yutori:0.1.7")
```

## 示例

[Java](src/test/java/example/Main.java)

[Kotlin](src/test/kotlin/example/Example.kt)

# 进阶

## 回话恢复

- 在 0.1.6 版本之前若要进行 Sequence 属性持久化可自行写一个定时器定时读取 Sequence(为private, 需使用反射) 并保存
- 由于 0.1.6 版本的更新提取了抽象类增加了可扩展性, 对于回话恢复的 Sequence 持久化保存可采取自定义 SatoriProperties 实现类或自定义 SatoriSocketClient 实现类通过重写对应方法实现访问数据库等持久化操作

## WebHook

请等待后续版本支持...

## 其他
- 参考 [Satori 文档](https://satori.chat/zh-CN/protocol)
- 请自行阅读源码

# 迁移

如果你想从 0.0.9 之前的版本迁移到 0.0.9, 请删除所有的 import, 并重新导入

或从 0.1.1 之前的版本迁移到0.1.1, 请按新格式重写

# 其他

Yutori: 作者名称 Yurn 与 Satori 协议名称结合而来