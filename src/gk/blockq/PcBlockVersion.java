package gk.blockq;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Blc{
    private volatile boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null;

    public Blc(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void myProc() throws Exception{
        String data = null;
        boolean ret;
        while(FLAG){
            data = atomicInteger.incrementAndGet() + "";
            ret = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (ret){
                System.out.println(Thread.currentThread().getName() + "\t succeed");
            }else{
                System.out.println(Thread.currentThread().getName() + "\t failed");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println("Main thread call off Producing");
    }

    public void myCons() throws Exception{
        String ret = null;
        while(FLAG){
            ret = blockingQueue.poll( 2L, TimeUnit.SECONDS);
            if (ret == null || ret.equalsIgnoreCase("")){
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t trying for 2s without a success, exit");
                return;
            }
            System.out.println("End of Consuming");
        }

    }

    public void stop() {
        this.FLAG = false;
    }
}

public class PcBlockVersion {
    public static void main(String[] args) throws InterruptedException {
        Blc blc = new Blc(new ArrayBlockingQueue<>(10));
        new Thread(()->{
                try {
                    blc.myProc();
                } catch (Exception e) {
                    e.printStackTrace();
            }
        }, "Prod").start();

        new Thread(()->{
            try {
                blc.myCons();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Cons").start();

        TimeUnit.SECONDS.sleep(5);

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("Main thread call off the operation");
        blc.stop();
    }
}
