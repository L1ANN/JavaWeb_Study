package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author:L1ANN
 * @Description: 多线程客户端
 * @Date:Created in 16:42 2017/11/12
 * @Modified By:
 */
public class ClientSocket_Thread {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 6666);
            System.out.println("连接主机成功!");

            new Thread(new SendClientThread(socket)).start();
            new Thread(new ReceiveClientThread(socket)).start();

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

class ReceiveClientThread implements Runnable{
    private Socket socket;
    private BufferedReader reader;

    public ReceiveClientThread(Socket socket){
        super();
        this.socket = socket;
        try{
            reader =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            while(true){
                String message = reader.readLine();
                System.out.println(message);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
