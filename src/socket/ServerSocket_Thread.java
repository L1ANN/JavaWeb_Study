package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author:L1ANN
 * @Description: 多线程的服务端
 * @Date:Created in 16:15 2017/11/12
 * @Modified By:
 */
public class ServerSocket_Thread {

    public static List<Socket> socketList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("等待客户端连接....");

        while (true) {
            Socket socket = null;
            socket = serverSocket.accept();
            socketList.add(socket);
            System.out.println("客户端" + socket.getInetAddress().getHostAddress() + "连接成功！");

            //为该客户端分别开启一个发送信息线程和接收信息线程
            new Thread(new ReceiveThread(socket,socketList)).start();
            new Thread(new SendThread(socket, socketList)).start();


        }
    }
}

/**
 * 服务端接收信息线程
 */
class ReceiveThread implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private List<Socket> socketList;

    public ReceiveThread(Socket socket,List<Socket> socketList) {
        super();
        this.socket = socket;
        this.socketList = socketList;
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收信息的同时将信息转发给当前在线的所有客户端
     */
    public void run() {
        while(true) {
            try {
                String info = reader.readLine();
                System.out.println(info);

                for(Socket clientSock:socketList){
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSock.getOutputStream()));
                    writer.println(info);
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

/**
 * 服务端发送信息线程
 */
class SendThread implements Runnable {
    private PrintWriter writer;
    private Socket socket;
    private List<Socket> socketList;
    private Scanner scanner = new Scanner(System.in);

    public SendThread(Socket socket, List<Socket> socketList) {
        super();
        this.socket = socket;
        this.socketList = socketList;
        try {
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            String message = scanner.nextLine();
            if (message == "b") {
                break;
            }

            //把服务器收到的信息转发到各个客户端
            for (Socket clientSock : socketList) {
                PrintWriter writer;
                try {
                    writer = new PrintWriter(new OutputStreamWriter(clientSock.getOutputStream()));
                    writer.println(message);
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}