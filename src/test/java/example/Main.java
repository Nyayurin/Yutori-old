package example;

import io.github.nyayurn.yutori.*;

public class Main {
    // 监听器容器
    public static final ListenerContainer listenerContainer = new ListenerContainer();
    // Satori 相关设置
    public static final Properties properties = new Properties("127.0.0.1:5500", "token");

    public static void main(String[] args) {
        // new 一下触发自动注册
        new ExampleJavaListener();
        // 调用工厂方法得到 WebSocketClient 并调用连接方法
        MyWebSocketClient.Companion.of(properties, listenerContainer).connect();
    }
}

class ExampleJavaListener {
    public ExampleJavaListener() {
        // 通过在构造器内对 listenerContainer 添加一个事件实现注册
        Main.listenerContainer.getOnEventDelegate().add((event) -> {
            // 判断事件的类型是否为 message-created(新消息创建, 即接收到消息)
            if (event.getType().equals(MessageEvents.MESSAGE_CREATED)) {
                this.onMessage(event);
            }
            return null;
        });
    }

    private void onMessage(Event event) {
        // 判断消息内容是否符合触发条件
        if (event.getMessage() != null && "test".equals(event.getMessage().getContent())) {
            // 通过对应 API 类的方法发送消息
            var messageApi = MessageApi.Companion.of(event, Main.properties);
            messageApi.createMessage(event.getChannel().getId(), "test done!");
        }
    }
}