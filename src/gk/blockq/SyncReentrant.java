package gk.blockq;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Dm{
    private int number = 1;
    private Lock lock = new ReentrantLock();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();

    public void print5() throws InterruptedException {
        lock.lock();

        try{
            while(number != 1){
                c1.await();
            }

            for (int i = 0; i< 5; i++){
                System.out.println(Thread.currentThread().getName() + "\t " + number);
            }

            number = 2;
            c2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        }

    public void print10() throws InterruptedException {
        lock.lock();

        try{
            while(number != 2){
                c2.await();
            }

            for (int i = 0; i< 10; i++){
                System.out.println(Thread.currentThread().getName() + "\t " + number);
            }

            number = 3;
            c3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void print15() throws InterruptedException {
        lock.lock();

        try{
            while(number != 3){
                c3.await();
            }

            for (int i = 0; i< 15; i++){
                System.out.println(Thread.currentThread().getName() + "\t " + number);
            }

            number = 1;
            c1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

public class SyncReentrant {
    public static void main(String[] args) {
        Dm dm = new Dm();

        new Thread(()->{
            for (int i = 0; i< 10; i++){
                try {
                    dm.print5();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(()->{
            for (int i = 0; i< 10; i++){
                try {
                    dm.print10();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();

        new Thread(()->{
            for (int i = 0; i< 10; i++){
                try {
                    dm.print15();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();
    }
}
