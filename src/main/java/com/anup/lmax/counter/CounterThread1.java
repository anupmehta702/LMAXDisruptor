package com.anup.lmax.counter;

class CounterThread1 implements Runnable{
    IncrementCounter ic;

    public CounterThread1(IncrementCounter ic) {
        this.ic = ic;
    }

    public void run() {
        ic.incrementCounter();
    }
}
class CounterThreadUsingLock implements Runnable{
    IncrementCounter ic ;

    public CounterThreadUsingLock(IncrementCounter ic) {
        this.ic = ic;
    }

    public void run() {
        ic.incrementCounterUsingLock();
    }
}
class CounterThreadUsingAtomic implements Runnable{
    IncrementCounter ic ;

    public CounterThreadUsingAtomic(IncrementCounter ic) {
        this.ic = ic;
    }

    public void run() {
        ic.incrementAtomicCounter();
    }
}
