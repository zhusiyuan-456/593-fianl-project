import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MyBlockingQueue {
    private List<Integer> integerList=new ArrayList<>();
    private int maxSize;
    //object lock
    private Object lock =new Object();
    public MyBlockingQueue(int maxSize){
        this.maxSize=maxSize;
        System.out.println("Thread"+Thread.currentThread().getName()+"Thread initialized completed, which size is "+maxSize);
        System.out.println("---------------------------------------");
    }


    public void put(int element){
        synchronized (lock) {
            //Check the queue is full or not
            try {
                if (this.integerList.size() == maxSize) {
                    System.out.println("Thread" + Thread.currentThread().getName() + "The Queue is full, put method now waiting");
                    lock.wait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Queue is not full
            this.integerList.add(element);
            System.out.println("Thread" + Thread.currentThread().getName() + " adds element  " + element);
            lock.notifyAll();
        }
    }

    public void take(){
        synchronized (lock) {
            if (this.integerList.size() == 0) {
                System.out.println("Thread" + Thread.currentThread().getName() + " Thread is now empty,take is  waiting");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //not an empty queue

            int result = integerList.get(0);
            integerList.remove(0);
            System.out.println("Thread" + Thread.currentThread().getName() + "  takes the element " + result);

            lock.notifyAll();
        }
    }

    public static void main(String[] args) {
        MyBlockingQueue myBlockingQueue=new MyBlockingQueue(5);
        new Thread(()->{
                myBlockingQueue.put(1);
                myBlockingQueue.put(2);
                myBlockingQueue.put(3);
                myBlockingQueue.put(4);
                myBlockingQueue.put(5);


        },"T1_put").start();
        new Thread(()->{
            myBlockingQueue.take();
            myBlockingQueue.take();
            myBlockingQueue.take();
            myBlockingQueue.take();
            myBlockingQueue.take();
            myBlockingQueue.take();
            myBlockingQueue.take();

        },"T2_take").start();


        new Thread(()->{
            Queue<Integer> queue=new ArrayList<>();
        })

//        new Thread(()->{
//            myBlockingQueue.put(1);
//            myBlockingQueue.put(2);
//            myBlockingQueue.put(3);
//            myBlockingQueue.put(4);
//            myBlockingQueue.put(5);
//
//
//        },"T3_put").start();
//
//
//        new Thread(()->{
//            myBlockingQueue.take();
//            myBlockingQueue.take();
//            myBlockingQueue.take();
//            myBlockingQueue.take();
//            myBlockingQueue.take();
//            myBlockingQueue.take();
//            myBlockingQueue.take();
//
//        },"T4_take").start();
    }
}
