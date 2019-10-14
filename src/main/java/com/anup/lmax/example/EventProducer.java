package com.anup.lmax.example;

import com.lmax.disruptor.RingBuffer;

public interface EventProducer {
    public void startProducing(final RingBuffer<ValueEvent> ringBuffer, final int count);
}
