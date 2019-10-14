package com.anup.lmax.counter;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class IncrementCounterByJaxTV {

    public static long foo = 0 ;
    public volatile static long fooVolatile = 0 ;
    public static Lock lock = new ReentrantLock();
    public static AtomicLong fooAtomic=new AtomicLong(0);

    public static void main(String[] args) {
        /*
        increment();//~227
        incrementUsingVolatile();//~4000
        incrementUsingLock();//~9029
        incrementUsingAtomic();//~2800
        */
        /*
        Thread t1 = new Thread(new ThreadUsingLock());
        Thread t2 = new Thread(new ThreadUsingLock());
        t1.start(); //~17471
        t2.start(); //~17518 total = ~35000
       */
        Thread t3 = new Thread(new ThreadUsingAtomic());
        Thread t4 = new Thread(new ThreadUsingAtomic());
        t3.start(); //~7757
        t4.start(); //~7763 total = ~15500

    }

    public static void increment(){
        Long startTime =System.nanoTime();
        for(long l=0;l < 500000000L ;l++){
                foo++;
        }
        System.out.println("Total time taken to increment  -"+(startTime-System.nanoTime())/1000000+" by thread - "+ Thread.currentThread().getName());
    }


    public static void incrementUsingVolatile(){
        Long startTime =System.nanoTime();
        for(long l=0;l < 500000000L ;l++){
            fooVolatile++;
        }
        System.out.println("Total time taken to increment using volatile  -"+(startTime-System.nanoTime())/1000000+" by thread - "+ Thread.currentThread().getName());
    }


    public static void incrementUsingLock(){
        Long startTime =System.nanoTime();
        for(long l=0;l < 500000000L ;l++){
            lock.lock();
            try{
                foo++;
            }finally{
                lock.unlock();
            }
        }
        System.out.println("foo value "+foo);
        System.out.println("Total time taken to increment using lock -"+(startTime-System.nanoTime())/1000000+" by thread - "+ Thread.currentThread().getName());
    }
    public static void incrementUsingAtomic(){
        Long startTime =System.nanoTime();
        for(long l=0;l < 500000000L ;l++){
            fooAtomic.getAndIncrement();
        }
        System.out.println("Total time taken to increment using Atomic -"+(startTime-System.nanoTime())/1000000+" by thread - "+ Thread.currentThread().getName());
    }

    public static void incrementUsingAtomicUsingTwoThreads(){
        Long startTime =System.nanoTime();
        for(long l=0;l < 250000000L ;l++){
            fooAtomic.getAndIncrement();
        }
        System.out.println("counter -> "+fooAtomic);
        System.out.println("Total time taken to increment using Atomic -"+(startTime-System.nanoTime())/1000000+" by thread - "+ Thread.currentThread().getName());
    }


    public static void incrementUsingTwoThreads(){
        Long startTime =System.nanoTime();
        for(long l=0;l < 250000000L ;l++){
            lock.lock();
            try{
                foo++;
            }finally{
                lock.unlock();
            }
        }
        System.out.println("foo value "+foo);
        System.out.println("Total time taken to increment using lock -"+(startTime-System.nanoTime())/1000000+" by thread - "+ Thread.currentThread().getName());
    }
}
