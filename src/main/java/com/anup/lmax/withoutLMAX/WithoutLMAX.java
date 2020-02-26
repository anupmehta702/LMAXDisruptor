package com.anup.lmax.withoutLMAX;

import com.anup.lmax.example.ValueEvent;

import java.util.concurrent.ArrayBlockingQueue;

public class WithoutLMAX {
    ArrayBlockingQueue<ValueEvent> bq = new ArrayBlockingQueue<ValueEvent>(4);
    public static void main(String[] args) throws InterruptedException {

        WithoutLMAX wl = new WithoutLMAX();
        Thread producerThread = new Thread(new SingleProducer(wl));
        producerThread.start();
        Thread.sleep(1000);

        System.out.println("Start time -- "+System.currentTimeMillis());

        Thread firstConsumerThread = new Thread(new FirstConsumer(wl));
        firstConsumerThread.start();

        Thread.sleep(1000);
        Thread secondConsumerThread = new Thread(new SecondConsumer(wl));
        secondConsumerThread.start();
 //     Thread.sleep(2000);
 //     wl.printEvent();
    }

    public void printEvent() throws InterruptedException {
        for(ValueEvent ve : bq){
            System.out.println("Printing --> "+ve);
        }
    }


}
