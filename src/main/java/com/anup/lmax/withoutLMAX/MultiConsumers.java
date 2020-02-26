package com.anup.lmax.withoutLMAX;

import com.anup.lmax.example.ValueEvent;

public class MultiConsumers {
}

class FirstConsumer implements Runnable {
    WithoutLMAX wl;

    public FirstConsumer(WithoutLMAX wl) {
        this.wl = wl;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (wl) {
                /*though all operations of bq are synchronized using locks we still have to synchronize this bcoz
                we want below statements to work as atomic .If not synchronized ,after getting the peek and polling it ,some other thread might
                put value in bq making it full and hence we would not be able to put(valueEvent)*/
                if (!wl.bq.isEmpty() && wl.bq.peek() != null && wl.bq.peek().getUpdatedBy().equalsIgnoreCase("producer")) {
                    ValueEvent valueEvent = wl.bq.poll();
                    valueEvent.setUpdatedBy("FirstConsumerProcessed");
                    try {
                        wl.bq.put(valueEvent); //bq.add() throws exception if queue is full ,
                        // bq.offer() adds if queue has space but doesn't throw exception when empty
                        //bq.put() waits till queue has space available
                        System.out.println("Value event added successfully by First Consumer for count -- " + valueEvent.getValue());
                        wl.notifyAll();
                        if(valueEvent.getValue() == 8) return;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        wl.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}

class SecondConsumer implements Runnable {
    WithoutLMAX wl;

    public SecondConsumer(WithoutLMAX wl) {
        this.wl = wl;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (wl) {
                if (!wl.bq.isEmpty() && wl.bq.peek() != null && wl.bq.peek().getUpdatedBy().equalsIgnoreCase("FirstConsumerProcessed")) {
                    ValueEvent valueEvent = wl.bq.poll();
                    System.out.println("Second Consumer consumed msg with value Event - " + valueEvent);
                    valueEvent.setUpdatedBy("SecondConsumerProcessed");
                    wl.notifyAll();
                    if(valueEvent.getValue() == 8) return;
                }else{
                    try {
                        wl.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //System.out.println("End time -- " + System.currentTimeMillis());
    }
}
