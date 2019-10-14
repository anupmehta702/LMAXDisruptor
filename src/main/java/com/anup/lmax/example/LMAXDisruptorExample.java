package com.anup.lmax.example;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.util.concurrent.ThreadFactory;

public class LMAXDisruptorExample {
    public Disruptor<ValueEvent> disruptor;
    public WaitStrategy waitStrategy = new BusySpinWaitStrategy();

    public void singleProducerSingleConsumer() throws InterruptedException {
            final EventConsumer eventConsumer = new SingleEventConsumer();
            final EventProducer eventProducer = new SingleEventProducer();
            createDisruptor(ProducerType.SINGLE, eventConsumer);
            final RingBuffer<ValueEvent> ringBuffer = disruptor.start();
            startProducing(ringBuffer, 8, eventProducer);
            Thread.sleep(10000);

    }

    public void singleProducerMultipleConsumer() throws InterruptedException {
        final EventConsumer multiEventConsumer = new MultiEventConsumer();
        final EventProducer eventProducer = new SingleEventProducer();

        createDisruptorForMultiConsumer(ProducerType.SINGLE, multiEventConsumer);
        final RingBuffer<ValueEvent> ringBuffer = disruptor.start();

        startProducing(ringBuffer, 8, eventProducer);
        while(true){

        }
        /*lmax.disruptor.halt();
        lmax.disruptor.shutdown();*/
    }
    public static void main(String[] args) throws InterruptedException {
        LMAXDisruptorExample lmax = new LMAXDisruptorExample();
        //lmax.singleProducerSingleConsumer();
        lmax.singleProducerMultipleConsumer();
    }

    private void createDisruptor(final ProducerType producerType, final EventConsumer eventConsumer) {
        final ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;
        disruptor = new Disruptor<>(ValueEvent.EVENT_FACTORY, 4, threadFactory, producerType, waitStrategy);
        disruptor.handleEventsWith(eventConsumer.getEventHandler());
    }
    private void createDisruptorForMultiConsumer(final ProducerType producerType, final EventConsumer eventConsumer) {
        final ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;
        disruptor = new Disruptor<>(ValueEvent.EVENT_FACTORY, 4, threadFactory, producerType, waitStrategy);
        EventHandlerGroup<ValueEvent> eventHandlerGroup=disruptor.handleEventsWith(eventConsumer.getEventHandler());
        eventHandlerGroup.then(new MultiEventConsumer2().getEventHandler());

    }

    private void startProducing(final RingBuffer<ValueEvent> ringBuffer, final int count, final EventProducer eventProducer) {
        eventProducer.startProducing(ringBuffer, count);
    }
}
