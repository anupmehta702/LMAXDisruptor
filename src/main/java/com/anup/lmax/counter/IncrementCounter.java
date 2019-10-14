package com.anup.lmax.counter;

import java.util.concurrent.atomic.AtomicInteger;

public class IncrementCounter {
    int counter=0;
    AtomicInteger atomicCounter =new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        IncrementCounter ic = new IncrementCounter();
        ic.incrementCounter();
        Thread.sleep(2000);

        IncrementCounter ic1 = new IncrementCounter();
        Thread t = new Thread(new CounterThread1(ic1),"SingleThread");
        t.start();

        Thread.sleep(2000);
        IncrementCounter ic2 = new IncrementCounter();
        Thread t1 = new Thread(new CounterThread1(ic2),"CounterThread1");
        Thread t2 = new Thread(new CounterThread1(ic2),"CounterThread2");
        t1.start();
        t2.start();

        Thread.sleep(2000);
        System.out.println("Counter missed by -"+(Integer.MAX_VALUE-ic2.counter));

        Thread.sleep(2000);
        IncrementCounter ic3 = new IncrementCounter();
        Thread t3 = new Thread(new CounterThreadUsingLock(ic3),"CounterThreadUsingLock1");
        Thread t4 = new Thread(new CounterThreadUsingLock(ic3),"CounterThreadUsingLock2");
        t3.start();
        t4.start();

        Thread.sleep(2000);
        IncrementCounter ic4 = new IncrementCounter();
        Thread t5 = new Thread(new CounterThreadUsingAtomic(ic4),"CounterThreadUsingAtomic1");
        Thread t6 = new Thread(new CounterThreadUsingAtomic(ic4),"CounterThreadUsingAtomic1");
        t5.start();
        t6.start();



    }
    public void incrementCounter(){
        System.out.println();
        System.out.println("Starting - "+Thread.currentThread().getName());
        Long startTime =System.nanoTime();
        for(int i=0 ;i< Integer.MAX_VALUE;i++){
            counter++;
        }
        System.out.println("Total time taken to increment  -"+(startTime-System.nanoTime())/1000000+" by thread - "+ Thread.currentThread().getName());
        System.out.println("Counter incremented till - "+counter);//2147483647
    }

    public synchronized void incrementCounterUsingLock(){
        System.out.println();
        System.out.println("Starting - "+Thread.currentThread().getName());
        Long startTime =System.nanoTime();
        for(int i=0 ;i< Integer.MAX_VALUE/2;i++){
            counter++;
        }
        System.out.println("Total time taken to increment using lock -"+(startTime-System.nanoTime())/1000000+" by thread - "+ Thread.currentThread().getName());
        System.out.println("Counter incremented till - "+counter);//2147483647
    }

    public void incrementAtomicCounter(){
        System.out.println();
        System.out.println("Starting - "+Thread.currentThread().getName()+" MAX value "+Integer.MAX_VALUE);
        Long startTime =System.nanoTime();
        for(int i=0 ;i< Integer.MAX_VALUE/2;i++){
           // boolean operation=atomicCounter.compareAndSet(atomicCounter.intValue(),atomicCounter.intValue()+1);
            //counter++;
            //System.out.println("--> "+counter);
            int current = atomicCounter.get();
            int next = atomicCounter.get()+1;
            atomicCounter.compareAndSet(current,next);
        }
        System.out.println("Total time taken to increment Atomic counter -"+(startTime-System.nanoTime())/1000000+" by thread - "+ Thread.currentThread().getName());
        System.out.println("Counter incremented till - "+atomicCounter.intValue());//2147483647
    }

}

