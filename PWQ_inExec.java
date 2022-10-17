import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class PWQ_inExec implements Runnable{
    public int count;
    public int threadNum;
    public String tempName;
    public String tempEncName;
    public double sumPerThread;
    public static double sum;
    public static int divNum;
    public static final int MAX_THREADS = 30;
    public static int THREADS;

    public static Object lock = new Object();

    @Override
    public void run(){
        this.tempName = Thread.currentThread().getName();
        for(int i = 15; i <= this.tempName.length(); i++){
            this.tempEncName = this.tempName.substring(14,i);
        }
        
        this.threadNum = Integer.parseInt(this.tempEncName)-1;
        this.sumPerThread = 0;

        // System.out.println("threadNum "+this.threadNum+" is working");
        // System.out.println("uwa");
        this.count = divNum/THREADS*this.threadNum;
        for(int i = 0; i < divNum/THREADS; i++){
            this.count += 1;
            this.sumPerThread += Math.sin(this.count * Math.PI / divNum) * (Math.PI /divNum);
        }

        synchronized(PWQ_inExec.lock){
            sum += this.sumPerThread;
        }
    }

    public static void main(String args[]){
        ExecutorService exec = Executors.newFixedThreadPool(MAX_THREADS);

        Scanner sc = new Scanner(System.in);

        System.out.print("enter the number to divide by :");
        divNum = sc.nextInt();
        System.out.println("divide by "+divNum);

        System.out.print("enter the number of threads ");
        THREADS = sc.nextInt();
        if(THREADS > MAX_THREADS)THREADS = MAX_THREADS;
        System.out.println("number of threads is "+THREADS);

        long startTime = System.currentTimeMillis();

        for(int i = 0; i < THREADS; i++){
            // System.out.println("uwa");
            exec.submit(new PWQ_inExec());
        }
        
        exec.shutdown();

        try {
            exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            // do no
        }

        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;

        System.out.println(PWQ_inExec.sum);
        System.out.println("processing time is " + timeElapsed + "[msec]");
        sc.close();
    }
}