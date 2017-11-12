package thread;

/**
 * @Author:L1ANN
 * @Description: 测试合并线程Join
 * @Date:Created in 13:04 2017/11/12
 * @Modified By:
 */
public class TestThread_Join {
    public static void main(String[] args){
        MyThread2 thread2 = new MyThread2("mythread");
        thread2.start();
        try{
            System.out.println("合并线程！");
            //调用join（）方法合并线程，将子线程mythread合并到主线程里面
            //合并线程之后，程序的执行过程就相当于方法的调用的执行过程
            thread2.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        for(int i =0;i<=5;i++){
            System.out.println("I am main Thread");
        }
    }
}

class MyThread2 extends Thread{
    /**
     * 使用super关键字调用父类的构造函数
     * 父类Thread的其中一个构造方法：public Thread(String name)
     * 通过这样的构造方法可以给新开辟的线程命名，便于线程管理
     * @param s
     */
    MyThread2(String s){
        super(s);
    }

    public void run(){
        for(int i=0;i<=5;i++){
            System.out.println("I am a\t" + getName());
            try{
                sleep(1000);
            }catch(InterruptedException e){
                return;
            }
        }
    }
}
