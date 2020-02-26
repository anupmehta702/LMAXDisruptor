package com.anup.lmax.example;

import com.lmax.disruptor.EventHandler;

public class MultiEventConsumer2 implements EventConsumer {
    @Override
    public EventHandler<ValueEvent>[] getEventHandler() {
        final EventHandler<ValueEvent> eventHandler = (event, sequence, endOfBatch) -> print(event, sequence,"secondConsumer");
        return new EventHandler[]{eventHandler};
    }

    private void print(ValueEvent event, long sequence,String updatedBy) throws InterruptedException {
        if(sequence%2 == 0 ) Thread.sleep(2000);
        System.out.println("Printing the event --> " + event + " placed at sequence number --> " + sequence+" by --> "+updatedBy);

    }}
