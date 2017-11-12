package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 16:15 2017/11/12
 * @Modified By:
 */
public class ServerSocket_Thread {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(6666);
        while (true) {
            Socket socket = null;

            socket = serverSocket.accept();
            Thread workThread = new Thread(new Handler(socket));
            workThread.start();

        }
    }
}

class Handler implements Runnable {
    private Socket socket;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String info = null;
            while((info=reader.readLine())!=null){
                System.out.println(info);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}