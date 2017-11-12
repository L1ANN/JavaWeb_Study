package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author:L1ANN
 * @Description: 单线程通信服务端
 * @Date:Created in 14:54 2017/11/12
 * @Modified By:
 */
public class ServerSocket_Single {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(6666);
        /**
         * 在服务器端接收客户端连接的请求是不间断地接收的，所以服务器端的编程一般都是死循环，永不休止地运行着
         */
        while(true){

            /**
             * 在服务器端调用accept()方法接收客户端的连接对象，accept()方法是一个阻塞式方法，一直等待着是否有
             * 客户端的连接请求，如果接受了客户端的请求，那么在服务器就安装上一个Socket插座，通过这个插座与连
             * 接上的客户端就可以建立连接，互相通信了。
             */
            Socket socket =serverSocket.accept();
            System.out.println("A Client Connected");


            /**
             * 获取输入流，并读取服务器的响应信息
             */
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String info = null;
            while((info = reader.readLine())!=null){
                System.out.println("我是服务器，客户端说："+info);
            }
            System.out.println("成功接收客户端信息！");

            /**
             * 获取输出流，向服务器发送信息
             */
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.write("欢迎你!");
            writer.flush();
            System.out.println("向客户端发送信息！");
        }
    }
}
