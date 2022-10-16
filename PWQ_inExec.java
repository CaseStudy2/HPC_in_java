import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class PWQ_inExec implements Runnable{
    public static int count = 1;  //インクリメント値を持つグローバル変数
    public static double sum = 0;
    public static int divNum = 0;
    public static final int MAX_THREADS = 10;

    public static Object lock = new Object();

    @Override
    public void run(){
        synchronized(PWQ_inExec.lock) {
            System.out.println("uwa");
            for(int i = 0; i < divNum/MAX_THREADS; i++){
                count += 1;
                sum += Math.sin(count * Math.PI / divNum) * (Math.PI /divNum);
            }
        }
    }

    public static void main(String args[]){
        ExecutorService exec = Executors.newFixedThreadPool(MAX_THREADS);

        Scanner sc = new Scanner(System.in);

        System.out.print("分割する数を入力してください：");
        int divNum = sc.nextInt();
        System.out.println(divNum+"で分割します");
        PWQ_inExec.divNum = divNum;

        long startTime = System.currentTimeMillis();

        //スレッドを10本立てる
        for(int i = 0; i < MAX_THREADS; i++){
            //スレッドを起動する
            // System.out.println("uwa");
            exec.submit(new PWQ_inExec());
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

        System.out.println(PWQ_inExec.sum);
        System.out.println("処理にかかった時間は：" + timeElapsed + "ミリ秒");
        sc.close();
    }
}