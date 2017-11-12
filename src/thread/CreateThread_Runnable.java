package thread;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 12:15 2017/11/12
 * @Modified By:
 */
public class CreateThread_Runnable implements Runnable {
    public void run(){
        for(int i=0;i<10;i++){
            System.out.println("Runner1:"+i);
        }
    }
}
