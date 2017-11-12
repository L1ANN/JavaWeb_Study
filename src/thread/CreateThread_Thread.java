package thread;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 12:13 2017/11/12
 * @Modified By:
 */
public class CreateThread_Thread extends Thread{
    public void run(){
        for(int i=0;i<=10;i++){
            System.out.println("Runner2:"+i);
        }
    }
}
