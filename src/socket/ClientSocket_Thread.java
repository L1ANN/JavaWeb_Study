package socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 16:42 2017/11/12
 * @Modified By:
 */
public class ClientSocket_Thread {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 6666);
            System.out.println("连接主机成功!");

            Thread thread = new Thread(new SendClientThread(socket));
            thread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class SendClientThread implements Runnable{
    Socket socket;
    PrintWriter writer;
    Scanner scanner;

    public SendClientThread(Socket socket){
        super();
        this.scanner = new Scanner(System.in);
        this.socket=socket;
        try{
            writer = new PrintWriter(socket.getOutputStream());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void run(){
        while(true){
            String message = scanner.nextLine();
            writer.println(message);
            writer.flush();
        }
    }
}

