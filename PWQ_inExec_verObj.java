import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class PWQ_inExec_verObj{
    public static void main(String args[]){
        final int MAX_THREADS = 10;
        ExecutorService exec = Executors.newFixedThreadPool(MAX_THREADS);

        Scanner sc = new Scanner(System.in);

        System.out.print("分割する数を入力してください：");
        int divNum = sc.nextInt();
        System.out.println(divNum+"で分割します");
        Runner.divNum = divNum;

        long startTime = System.currentTimeMillis();

        //スレッドを10本立てる
        for(int i = 0; i < MAX_THREADS; i++){
            //スレッドを起動する
            exec.submit(new Runner());
        }
        
        exec.shutdown();

        try {
            // すべてのスレッドの終了を待機
            exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            // do no
        }

        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;

        System.out.println(Runner.sum);
        System.out.println("処理にかかった時間は：" + timeElapsed + "ミリ秒");
        sc.close();
    }
}

class Runner implements Runnable{
    public static int count;  //インクリメント値を持つグローバル変数
    public static double sum;
    public double localSum;
    public static int divNum;
    public int i;
    public static final int MAX_THREADS = 10;

    public static Object lock = new Object();

    @Override
    public void run(){
        for(this.i = 1; this.i < divNum/MAX_THREADS; this.i++){
            synchronized(Runner.lock){
                count += 1;
            }
            this.localSum += Math.sin(count * Math.PI / divNum) * (Math.PI /divNum);
        }
        synchronized(Runner.lock){
            sum += this.localSum;
            // sum += 1;
        }

    }
}