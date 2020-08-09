package gk.vola;

class MyData2{
    volatile int a = 0;
    boolean flag = false;
    public void method01(){
        a = 1;
        flag = true;
    }
    public void method02(){
        if (flag)
            a += 5;
    }
}

public class InstructionReordering {
    public static void main(String[] args) {
        MyData2 myData = new MyData2();

        for (int i=0; i< 10000; i++){
            int initCount = Thread.activeCount();

            new Thread(() -> {
                myData.method01();
            }, "AAA").start();


            new Thread(() -> {
                myData.method02();
            }, "BBB").start();

            while(Thread.activeCount() > initCount){
                Thread.yield();
            }

            System.out.println(Thread.currentThread().getName() + "\t" + "after :" + myData.a);
        }

    }
}
