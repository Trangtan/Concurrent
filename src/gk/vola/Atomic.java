package gk.vola;

import java.util.concurrent.atomic.AtomicInteger;

class MyData1{
    volatile int  number = 0;
    public void addPlusPlus(){
        this.number++;
    }


    AtomicInteger integer = new AtomicInteger();
    public void addAtomic(){
        integer.getAndIncrement();
    }
}

public class Atomic {
    public static void main(String[] args) {
        MyData1 myData = new MyData1();
        //后台已经存在有两个线程，一个main线程，一个gc线程
        int initialCount = Thread.activeCount();
        System.out.println(String.valueOf(initialCount));
        for (int i = 0; i< 20; i++){
            new Thread(() -> {
                for (int j = 0; j< 1000; j++){
                    myData.addPlusPlus();
                    myData.addAtomic();
                }
            }
            , String.valueOf(i)).start();
        }

        while(Thread.activeCount() > initialCount){
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + "\t" + "after :" + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t" + "after :" + myData.integer);
    }
}
