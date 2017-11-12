package thread;

/**
 * @Author:L1ANN
 * @Description: 测试线程的优先级
 * @Date:Created in 13:36 2017/11/12
 * @Modified By:
 */
public class TestThread_Priority {
    public static void main(String[] args){
        MyThread4 t4 = new MyThread4();
        MyThread5 t5 = new MyThread5();
        Thread t1 = new Thread(t4);
        Thread t2 = new Thread(t5);

        t1.setPriority(Thread.NORM_PRIORITY+3);

        t1.start();
        t2.start();
        System.out.println("t1线程的优先级是"+t1.getPriority());
    }
}

class MyThread4 implements Runnable{
    public void run(){
        for(int i = 0 ; i <= 10 ; i++){
            System.out.println("T1:"+1);
        }
    }
}

class MyThread5 implements Runnable{
    public void run(){
        for(int i = 0 ; i <=10;i++){
            System.out.println("=================T2:"+i);
        }
    }
}