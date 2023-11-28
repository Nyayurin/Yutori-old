package example;

import io.github.nyayurn.yutori.*;
import io.github.nyayurn.yutori.message.MessageBuilder;
import kotlin.Unit;

public class Main {
    // Satori 相关设置
    public static final Properties properties = new Properties("127.0.0.1:5500", "token");

    public static void main(String[] args) {
        // new 一下触发自动注册
        new ExampleJavaListener();
        // 调用工厂方法得到 WebSocketClient 并调用连接方法
        MyWebSocketClient.Companion.of(properties).connect();
    }
}

class ExampleJavaListener {
    public ExampleJavaListener() {
        // 在构造器内对 listenerContainer 注册事件
        ListenerDispatcher.INSTANCE.onMessageCreated(this::idkWhatIShouldNameIt);
        ListenerDispatcher.INSTANCE.onMessageCreated(this::recipeMenu);
    }

    private Unit idkWhatIShouldNameIt(MessageEvent event) {
        // 判断消息内容是否符合触发条件
        if ("在吗".equals(event.getMessage().getContent())) {
            // 通过对应 API 类的方法发送消息
            final var messageApi = MessageApi.Companion.of(event, Main.properties);
            messageApi.createMessage(event.getChannel().getId(), MessageBuilder.Companion.of()
                    .at(event.getUser().getId())
                    .text("我在!").build());
        }
        return null;
    }

    private Unit recipeMenu(MessageEvent event) {
        if ("菜单".equals(event.getMessage().getContent())) {
            final var messageApi = MessageApi.Companion.of(event, Main.properties);
            // 资源来自: https://home.meishichina.com/recipe-menu.html
            messageApi.createMessage(event.getChannel().getId(), MessageBuilder.Companion.of()
                    .at(event.getUser().getId())
                    .custom("菜单:<br/>")
                    .custom("红烧肉 红烧排骨 可乐鸡翅 糖醋排骨 水煮鱼 红烧鱼<br/>")
                    .custom("凉拌黑木耳 鱼香肉丝 水煮肉片 意大利面 麻辣小龙虾 凉拌木耳<br/>")
                    .custom("茶叶蛋 龙井虾仁 口水鸡 回锅肉 红烧猪蹄 皮蛋瘦肉<br/>")
                    .custom("粥酸菜鱼 咖喱牛肉 西红柿炒鸡蛋 辣椒酱 麻辣烫 辣白菜<br/>")
                    .custom("牛肉酱 红烧茄子 蛋炒饭 佛跳墙 四物汤 固元膏<br/>")
                    .custom("龟苓膏 银耳莲子 羹酸梅 汤腊肉").build());
        }
        return null;
    }
}