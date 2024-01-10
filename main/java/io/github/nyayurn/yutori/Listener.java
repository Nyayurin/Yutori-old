package io.github.nyayurn.yutori;

@FunctionalInterface
public interface Listener<T extends Event> {
    void invoke(Bot bot, T event);
}