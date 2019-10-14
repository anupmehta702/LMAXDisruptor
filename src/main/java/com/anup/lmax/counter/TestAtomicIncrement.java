package com.anup.lmax.counter;

import java.util.concurrent.atomic.AtomicLong;

public final class TestAtomicIncrement
        implements Runnable
{
    public static final long COUNT = 500L * 1000L * 1000L;
    public static final AtomicLong counter = new AtomicLong(0L);

    public static void main(final String[] args) throws Exception
    {
        final int numThreads = 2;
        final long start = System.nanoTime();
        runTest(numThreads);
        System.out.println("duration = " + (System.nanoTime() - start)/1000000);
        System.out.println("counter = " + counter);
    }

    private static void runTest(final int numThreads)
            throws InterruptedException
    {
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < threads.length; i++)
        {
            threads[i] = new Thread(new TestAtomicIncrement());
        }

        for (Thread t : threads)
        {
            t.start();
        }

        for (Thread t : threads)
        {
            t.join();
        }
    }

    public void run()
    {
        long i = 0L;
        while (i < COUNT)
        {
            i = counter.incrementAndGet();
        }
    }
}