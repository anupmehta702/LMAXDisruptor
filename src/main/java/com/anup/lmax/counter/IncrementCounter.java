package com.anup.lmax.counter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class IncrementCounter {
    int counter = 0;
    AtomicInteger atomicCounter = new AtomicInteger(0);
    public static AtomicInteger fooAtomic = new AtomicInteger(0);
    public volatile static long fooVolatile = 0 ;
    public static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        // simple(main ) incrementing the counter
        IncrementCounter ic = new IncrementCounter();
        ic.incrementCounter(); //~ 137

        //incrementing the counter using volatile
        incrementUsingVolatile(); //~ 12000

        //incrementing the counter using atomic variable
        incrementUsingAtomic(); //11000


        //Incrementing counter using two threads with lock
        Thread.sleep(100);
        IncrementCounter ic3 = new IncrementCounter();
        Thread t3 = new Thread(new CounterThreadUsingLock(ic3), "CounterThreadUsingLock1");
        Thread t4 = new Thread(new CounterThreadUsingLock(ic3), "CounterThreadUsingLock2");
        t3.start();// ~ 150000
        t4.start();// ~ 150000 Total - 3,00,000


        //Incrementing counter using two threads with CAS
        Thread.sleep(100);
        IncrementCounter ic4 = new IncrementCounter();
        Thread t5 = new Thread(new CounterThreadUsingAtomic(ic4), "CounterThreadUsingAtomic1");
        Thread t6 = new Thread(new CounterThreadUsingAtomic(ic4), "CounterThreadUsingAtomic1");
        t5.start(); // ~46000
        t6.start(); // ~46000 total - 92,000


    }

    public void incrementCounter() {
        System.out.println();
        Long startTime = System.nanoTime();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            counter++;
        }
        System.out.println("Total time taken to increment  -" + (startTime - System.nanoTime()) / 1000000 + " by thread - " + Thread.currentThread().getName());
        System.out.println("Counter incremented till - " + counter);//2147483647
    }

    public void incrementCounterUsingLock() {
        System.out.println();
        System.out.println("Starting - " + Thread.currentThread().getName());
        Long startTime = System.nanoTime();
        for (int i = 0; i < Integer.MAX_VALUE / 2; i++) {
            // counter++;
            lock.lock();
            try {
                counter++;
            } finally {
                lock.unlock();
            }
        }
        System.out.println("Total time taken to increment using lock -" + (startTime - System.nanoTime()) / 1000000 + " by thread - " + Thread.currentThread().getName());
        System.out.println("Counter incremented till - " + counter);//2147483647
    }

    public void incrementAtomicCounter() {
        System.out.println();
        System.out.println("Starting - " + Thread.currentThread().getName() + " MAX value " + Integer.MAX_VALUE);
        Long startTime = System.nanoTime();
            for(int l=0;l < Integer.MAX_VALUE/2 ;l++){
                atomicCounter.getAndIncrement();
            }

        System.out.println("Total time taken to increment Atomic counter -" + (startTime - System.nanoTime()) / 1000000 + " by thread - " + Thread.currentThread().getName());
        System.out.println("Counter incremented till - " + atomicCounter.intValue());//2147483647
    }

    public static void incrementUsingVolatile(){
        System.out.println();
        Long startTime =System.nanoTime();
        for(int l=0;l < Integer.MAX_VALUE ;l++){
            fooVolatile++;
        }
        System.out.println("Total time taken to increment using volatile  -"+(startTime-System.nanoTime())/1000000+" by thread - "+ Thread.currentThread().getName());
        System.out.println("Counter incremented till - " + fooVolatile);//2147483647
    }

    public static void incrementUsingAtomic(){
        System.out.println();
        Long startTime =System.nanoTime();
        for(int l=0;l < Integer.MAX_VALUE ;l++){
            fooAtomic.getAndIncrement();
        }
        System.out.println("Total time taken to increment using Atomic -"+(startTime-System.nanoTime())/1000000+" by thread - "+ Thread.currentThread().getName());
        System.out.println("Counter incremented till - " + fooAtomic.get());//2147483647
    }

}

/*
Output


Total time taken to increment  --139 by thread - main
Counter incremented till - 2147483647

Total time taken to increment using volatile  --12587 by thread - main
Counter incremented till - 2147483647

Total time taken to increment using Atomic --11457 by thread - main
Counter incremented till - 2147483647


Starting - CounterThreadUsingAtomic1 MAX value 2147483647
Starting - CounterThreadUsingAtomic1 MAX value 2147483647
Total time taken to increment Atomic counter --46775 by thread - CounterThreadUsingAtomic1
Total time taken to increment Atomic counter --46775 by thread - CounterThreadUsingAtomic1
Counter incremented till - 2147483646
Counter incremented till - 2147483646


Starting - CounterThreadUsingLock1
Starting - CounterThreadUsingLock2
Total time taken to increment using lock --135750 by thread - CounterThreadUsingLock2
Counter incremented till - 2052340464
Total time taken to increment using lock --138069 by thread - CounterThreadUsingLock1
Counter incremented till - 2147483646


 */