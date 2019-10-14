package com.anup.lmax.counter;

public class Threads {
}
class ThreadUsingLock implements Runnable{
    public void run() {
        IncrementCounterByJaxTV.incrementUsingTwoThreads();
    }
}
class ThreadUsingAtomic implements Runnable{
    public void run() {
        IncrementCounterByJaxTV.incrementUsingAtomicUsingTwoThreads();
    }
}


