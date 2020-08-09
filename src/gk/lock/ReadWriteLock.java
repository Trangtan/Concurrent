package gk.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyLock1{
    private volatile Map<String, Object> map = new HashMap<>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String key, Object object)  {
        lock.writeLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + "\t Writing");
            TimeUnit.SECONDS.sleep(1);
            map.put(key, object);
            System.out.println(Thread.currentThread().getName() + "\t Writing Finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }

    }

    public void get(String key)  {
        lock.readLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + "\t Reading");
            TimeUnit.SECONDS.sleep(1);
            map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t Reading Finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.readLock().lock();
        }
    }
}

public class ReadWriteLock {
    public static void main(String[] args) {
        MyLock1 myLock = new MyLock1();

        for (int i = 0; i< 5; i++){
            final int ll = i;
            new Thread(() -> {
                myLock.put(ll+"", ll+"");
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i< 5; i++){
            final int ll = i;
            new Thread(() -> {
                myLock.get(ll+"");
            }, String.valueOf(i)).start();
        }
    }
}
