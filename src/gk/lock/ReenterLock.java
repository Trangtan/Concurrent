package gk.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone{
    public synchronized void sendSMS() throws Exception{
        System.out.println(Thread.currentThread().getName() + "\t invoked sendSMS()");
        sendEmail();
    }

    public synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getName() + "\t invoked sendEmail()");
    }

    ////////////////////////////////////////////

    Lock lock = new ReentrantLock();

    public void run(){
        get();
    }

    private void get() {
        try{
            lock.lock();
        System.out.println(Thread.currentThread().getName() + "\t get() invoked");
        set();
        }finally {
            lock.unlock();
        }

    }

    private void set() {
        try{
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "\t set() invoked");
        }finally {
            lock.unlock();
        }
    }
}

public class ReenterLock {
    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();

        ////////////////////////////////

        new Thread(() -> {
            try {
                phone.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t3").start();

        new Thread(() -> {
            try {
                phone.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t4").start();
    }
}
