package com.anup.lmax.example;

import com.lmax.disruptor.EventHandler;

public class SingleEventConsumer implements EventConsumer {
    @Override
    public EventHandler<ValueEvent>[] getEventHandler() {
        final EventHandler<ValueEvent> eventHandler = (event, sequence, endOfBatch) -> print(event, sequence);
        return new EventHandler[]{eventHandler};
    }

    private void print(ValueEvent event, long sequence) throws InterruptedException {
        if(sequence%2 == 0 ) Thread.sleep(1000);
        System.out.println("Printing the event --> " + event + " placed at sequence number --> " + sequence);

    }
}
