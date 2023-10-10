package com.yurn.sdk;

import com.yurn.sdk.entity.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


/**
 * @author Yurn
 */
public class GlobalEventChannel {
    public static final GlobalEventChannel INSTANCE = new GlobalEventChannel();

    private final List<Consumer<Event>> delegate = new ArrayList<>();

    private GlobalEventChannel() {}

    public void add(Consumer<Event> consumer) {
        delegate.add(consumer);
    }

    public void remove(Consumer<Event> consumer) {
        delegate.remove(consumer);
    }

    public void run(Event event) {
        for (Consumer<Event> consumer : delegate) {
            consumer.accept(event);
        }
    }
}
