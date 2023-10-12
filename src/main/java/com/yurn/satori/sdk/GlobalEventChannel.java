package com.yurn.satori.sdk;

import com.yurn.satori.sdk.entity.ConnectionEntity;
import com.yurn.satori.sdk.entity.EventEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


/**
 * @author Yurn
 */
@SuppressWarnings("unused")
public class GlobalEventChannel {
    public static final GlobalEventChannel INSTANCE = new GlobalEventChannel();

    private final List<Consumer<EventEntity>> onEventDelegate = new ArrayList<>();
    private final List<Consumer<ConnectionEntity.Ready>> onConnectDelegate = new ArrayList<>();
    private final List<Consumer<String>> onDisconnectDelegate = new ArrayList<>();
    private final List<Consumer<Exception>> onErrorDelegate = new ArrayList<>();

    private GlobalEventChannel() {}

    public void addEvent(Consumer<EventEntity> consumer) {
        onEventDelegate.add(consumer);
    }

    public void removeEvent(Consumer<EventEntity> consumer) {
        onEventDelegate.remove(consumer);
    }

    public void addConnect(Consumer<ConnectionEntity.Ready> consumer) {
        onConnectDelegate.add(consumer);
    }

    public void removeConnect(Consumer<ConnectionEntity.Ready> consumer) {
        onConnectDelegate.remove(consumer);
    }

    public void addDisconnect(Consumer<String> consumer) {
        onDisconnectDelegate.add(consumer);
    }

    public void removeDisconnect(Consumer<String> consumer) {
        onDisconnectDelegate.remove(consumer);
    }

    public void addError(Consumer<Exception> consumer) {
        onErrorDelegate.add(consumer);
    }

    public void removeError(Consumer<Exception> consumer) {
        onErrorDelegate.remove(consumer);
    }

    public void runEvent(EventEntity event) {
        for (var consumer : onEventDelegate) {
            consumer.accept(event);
        }
    }

    public void runConnect(ConnectionEntity.Ready ready) {
        for (var consumer : onConnectDelegate) {
            consumer.accept(ready);
        }
    }

    public void runDisconnect(String s) {
        for (var consumer : onDisconnectDelegate) {
            consumer.accept(s);
        }
    }

    public void runError(Exception e) {
        for (var consumer : onErrorDelegate) {
            consumer.accept(e);
        }
    }
}
