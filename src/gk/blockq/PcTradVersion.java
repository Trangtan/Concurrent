package gk.blockq;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PcTradVersion {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws Exception{
        lock.lock();
        try {
            while(number != 0){
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void decrement() throws Exception{
        lock.lock();
        try {
            while(number == 0){
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

class DemoT{
    public static void main(String[] args) {
        PcTradVersion pcTradVersion = new PcTradVersion();

        new Thread(()->{
                for (int i = 0; i< 5; i++){
                     try {
                        pcTradVersion.increment();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }, "AA").start();

        new Thread(()->{
            for (int i = 0; i< 5; i++){
                try {
                    pcTradVersion.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();
    }
}
