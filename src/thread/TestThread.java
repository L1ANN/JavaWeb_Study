package thread;

import org.junit.Test;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 12:16 2017/11/12
 * @Modified By:
 */
public class TestThread {

    public static void main(String[] args){
        CreateThread_Thread r1 = new CreateThread_Thread();
        CreateThread_Runnable run = new CreateThread_Runnable();
        Thread r2 = new Thread(run);
        r1.start();
        r2.start();
        for(int i =0;i<10;i++){
            System.out.println("maintheod"+i);
        }
    }
}
