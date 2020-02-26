package com.anup.lmax.withoutLMAX;

import com.anup.lmax.example.ValueEvent;

public class SingleProducer implements Runnable {
    WithoutLMAX wl;

    public SingleProducer(WithoutLMAX wl) {
        this.wl = wl;
    }

    @Override
    public void run() {
        int count = 1;
        while (count <= 8) {
            synchronized (wl) {
                if(wl.bq.remainingCapacity()>=1) {
                    try {
                        wl.bq.put(new ValueEvent(count, "producer"));
                        System.out.println("Added value event with count - " + count);
                        count++;
                        wl.notifyAll();
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
