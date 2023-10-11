package com.yurn.sdk.message;

import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息构造器
 *
 * @author Yurn
 */
@Getter
public class MessageBuilder {
    private final List<Object> list = new LinkedList<>();

    public String build() {
        return list.stream().map(Object::toString).collect(Collectors.joining());
    }

    public String build(String delimiter) {
        return list.stream().map(Object::toString).collect(Collectors.joining(delimiter));
    }

    public MessageBuilder append(@NonNull String string) {
        list.add(string);
        return this;
    }

    public MessageBuilder append(@NonNull Object object) {
        list.add(object);
        return this;
    }
}