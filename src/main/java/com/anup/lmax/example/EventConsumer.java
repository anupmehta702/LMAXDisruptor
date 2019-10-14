package com.anup.lmax.example;

import com.lmax.disruptor.EventHandler;

public interface EventConsumer {
    public EventHandler<ValueEvent>[] getEventHandler();
}
