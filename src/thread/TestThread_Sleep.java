package thread;

import java.util.Date;

/**
 * @Author:L1ANN
 * @Description: 测试线程休眠sleep（）
 * @Date:Created in 12:28 2017/11/12
 * @Modified By:
 */
public class TestThread_Sleep {
    public static void main(String[] args) {
        MyThread thread = new MyThread();

        thread.start();
        try{
            MyThread.sleep(10000);
            System.out.println("主线程睡眠了10S再次启动了！");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        thread.flag=false;
    }

}

class MyThread extends Thread {
    boolean flag = true;

    public void run() {
        while (flag) {
            System.out.println("============" + new Date().toLocaleString() + "=============");
            try {
                MyThread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

