package gk.lock;

import com.sun.jmx.snmp.ThreadContext;

import java.util.concurrent.CountDownLatch;

public class CountDown {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i =0; i< 6; i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "\t left");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t locked the door");
    }
}
