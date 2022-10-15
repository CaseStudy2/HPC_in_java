import java.util.*;
import java.util.concurrent.TimeUnit;

class PiecewiseQuadrature{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);

        System.out.print("分割する数を入力してください：");
        int divNum = sc.nextInt();
        double sum = 0;

        /*MultiThread1 mt1 = new MultiThread1();
        mt1.start();*/

        long startTime = System.currentTimeMillis();

        for(int i = 0; i < divNum; i++){
            sum += Math.sin(i * Math.PI / divNum) * (Math.PI /divNum);
        }

        /*sum += mt1.run(divNum);*/

        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;

        System.out.println(sum);
        System.out.println("処理にかかった時間は：" + timeElapsed + "ミリ秒");
        sc.close();
    }
}

/*class MultiThread1 extends Thread{
    public double run(int divNum){
        double sum = 0;
        for(int i = 1; i < divNum; i++){
            sum += Math.sin(i * Math.PI / divNum) * (Math.PI /divNum);
        }
        return sum;
    }
}*/