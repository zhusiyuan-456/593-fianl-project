import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

 

public class BlockingDeque {
    //FIFO
    private static LinkedBlockingQueue<Integer> concurrentLinkedQueue = new LinkedBlockingQueue<Integer>(); 

          
 public static void main(String[] args) {  
     ExecutorService executorService = Executors.newFixedThreadPool(2);  

     executorService.submit(new Producer("writer1"));  
     executorService.submit(new Producer("writer2"));  
     executorService.submit(new Producer("writer3"));  
     executorService.submit(new Consumer("reader1"));  
     executorService.submit(new Consumer("reader2"));  
     executorService.submit(new Consumer("reader3"));  

 }  

 static class Producer implements Runnable {  
     private String name;  

     public Producer(String name) {  
         this.name = name;  
     }  

     public void run() {  
         for (int i = 1; i < 10; ++i) {  
             System.out.println(name+ "  make： " + i);  
             //concurrentLinkedQueue.add(i);  
             try {
                concurrentLinkedQueue.put(i);
                Thread.sleep(200); //Simulate slow production and produce blocking effects
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
             
         }  
     }  
 }  

 static class Consumer implements Runnable {  
     private String name;  

     public Consumer(String name) {  
         this.name = name;  
     }  
     public void run() {  
         for (int i = 1; i < 10; ++i) {  
             try {          
                    //You must use the take() method to block when acquiring
                      System.out.println(name+"read： " +  concurrentLinkedQueue.take());  
                      //Using poll() method will produce non-blocking effect
                      //System.out.println(name+"read： " +  concurrentLinkedQueue.poll());  
                     
                     //There is also a timeout usage. When the queue is empty, return after the blocking time is specified, and it will not be blocked forever
                     
                    //System.out.println(name+" reader " +  concurrentLinkedQueue.poll(300, TimeUnit.MILLISECONDS));                    
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }  

         }  
     }  
 }  
}
