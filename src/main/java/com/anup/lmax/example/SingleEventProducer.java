package com.anup.lmax.example;

import com.lmax.disruptor.RingBuffer;

import java.util.stream.IntStream;

public class SingleEventProducer implements EventProducer {
    @Override
    public void startProducing(RingBuffer<ValueEvent> ringBuffer, int count) {
        final Runnable producer = () -> produce(ringBuffer,count);
        new Thread(producer).start();
    }

    private void produce(RingBuffer<ValueEvent> ringBuffer,final int count) {
        IntStream.range(0, count)
                .forEach((index) -> {
                    final long seq = ringBuffer.next();
                    final ValueEvent valueEvent = ringBuffer.get(seq);
                    valueEvent.setValue(index);
                    System.out.println("Adding event -->" + valueEvent);
                    ringBuffer.publish(seq);
                });
    }
}
