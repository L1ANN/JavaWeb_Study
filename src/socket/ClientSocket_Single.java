package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Author:L1ANN
 * @Description: 客户端
 * @Date:Created in 15:28 2017/11/12
 * @Modified By:
 */
public class ClientSocket_Single {
    public static void main(String[] args) throws Exception{
        /**
         * Client申请连接到Server端上
         */
        Socket socket = new Socket("127.0.0.1",6666);
        System.out.println("Connect Server!");
        /**
         * 连接上服务器端以后，就可以向服务器端输出信息和接收从服务器端返回的信息
         * 输出信息和接收返回信息都要使用流式的输入输出原理进行信息的处理
         */

        /**
         * 获取输出流，向服务器端发送信息
         */
        Thread.sleep(5000);//连接服务器5S后，向服务器发送信息
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.write("用户名:admin;密码：123");
        writer.flush();
        writer.close();
        System.out.println("向服务器发送信息成功！");

        /**
         * 获取输入流，并读取服务器端的响应信息
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String info = null;
        while((info=reader.readLine())!=null){
            System.out.println("我是客户端，服务器说："+info);
        }
        System.out.println("接收服务器信息！");
    }
}
