//package socket.example;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.Scanner;
//
///**
// * @Author:L1ANN
// * @Description:
// * @Date:Created in 17:38 2017/11/12
// * @Modified By:
// */
//public class ChatServer {
//
//    public static ArrayList<Socket> socketList = new ArrayList() {
//    };
//
//    public static void main(String[] args) {
//        ServerSocket serverSocket = null;
//        try {
//            serverSocket = new ServerSocket(30000);
//            System.out.println("等待客户端连接...");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        while (true) {
//            Socket socket = null;
//            while (true) {
//                try {
//                    socket = serverSocket.accept();
//                    socketList.add(socket);
//                    System.out.println("客户端" + socket.getInetAddress().getHostAddress() + "连接成功");
//
//                    new Thread(new SendThread(socket, socketList)).start();
//                    new Thread(new ReceiveThread(socket, socketList)).start();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    class SendThread implements Runnable {
//        private Socket socket;
//        //在这里使用PrintWriter流来向客户端发送信息，也可以用其他流
//        private PrintWriter writer;
//        //接收来自主线程的客户端集合
//        private ArrayList<Socket> socketList;
//
//        //从键盘获取信息
//        Scanner scanner = new Scanner(System.in);
//
//        public SendThread(Socket socket, ArrayList<Socket> socketList) {
//            super();
//            this.socket = socket;
//            this.socketList = socketList;
//            try {
//                writer = new PrintWriter(socket.getOutputStream());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        public void run() {
//            while(true){
//
//                //获取从键盘输入的信息
//                String message = socket.getInetAddress().getHostAddress()+":"+scanner.nextLine();
//                if(message == "b"){
//                    break;
//                }
//
//                //把服务器收到的信息转发给各个客户端
//            }
//        }
//    }
//
//    class ReceiveThread implements Runnable {
//        public void run() {
//
//        }
//    }
//}
