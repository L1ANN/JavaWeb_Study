package socket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:L1ANN
 * @Description: 同步画板的客户端
 * @Date:Created in 0:31 2017/11/14
 * @Modified By:
 */
public class Client_Draw {

    private JFrame frame;
    private JPanel centerPanel;

    public static void main(String[] args) {
        new Client_Draw();
    }

    public Client_Draw() {
        frame = new JFrame();

        //实体化JPanel面板容器类的对象，作为北边面板
        JPanel northPanel = new JPanel();
        //设置面板的背景颜色
        northPanel.setBackground(Color.LIGHT_GRAY);

        //实例化按钮对象
        JButton button_Conn = new JButton("Connect");
        JButton button_Line = new JButton("Line");
        JButton button_Pencil = new JButton("Pencil");
        //将按钮添加到northPanel上
        northPanel.add(button_Conn);
        northPanel.add(button_Line);
        northPanel.add(button_Pencil);


        //实例化按钮对象
        JButton button_Color = new JButton();
        //设置按钮的背景色
        button_Color.setBackground(Color.RED);
        //将按钮添加到northPanel上
        northPanel.add(button_Color);


        //将northPanel添加到窗口的北边
        frame.add(northPanel, BorderLayout.NORTH);
        //实现中间面板（画图面板）
        centerPanel = new JPanel();
        //设置中间面板的背景颜色
        centerPanel.setBackground(Color.WHITE);
        //将中间面板添加到窗体上的中间部分
        frame.add(centerPanel, BorderLayout.CENTER);

        //设置窗口标题
        frame.setTitle("简易画板客户端");
        //设置点击关闭方式
        frame.setDefaultCloseOperation(3);
        //设置窗体位置
        frame.setLocation(550, 250);
        //设置窗体大小
        frame.setSize(800, 500);
        //设置窗体可见
        frame.setVisible(true);

        //点击connect按钮后，连接服务器，同时打开ReceiveThread线程，不停地接收服务器发来的坐标信息并绘制
        button_Conn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Socket socket = new Socket("127.0.0.1", 6666);
                    System.out.println("连接服务器！");

                    new ReceiveThread(socket, centerPanel.getGraphics()).start();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    //接收数据的线程
    class ReceiveThread extends Thread {
        private Socket socket;
        private Graphics2D g;
        private BufferedReader reader;

        //构造函数，接收socket和画布对象g
        public ReceiveThread(Socket socket, Graphics g) {
            this.socket = socket;
            this.g = (Graphics2D) g;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                while (true) {
                    //接收坐标值数据
                    String info = reader.readLine();
                    System.out.println(info);
                    if (info != null) {
                        //将,x1,y1,x2,y2,分隔成字符串数组
                        //这里需要注意，分隔完后，形成一个包含5个字符串的字符串数组，其中第一个字符串是空串
                        String[] bots = info.split(",");
                        //将得到的字符串赋给bot集合，从索引为1开始，因为索引0是空串
                        List<Integer> bot = new ArrayList<>();
                        for (int i=1;i<bots.length;i++) {
                            bot.add(Integer.parseInt(bots[i]));
                        }
                        //画出线段
                        g.setColor(Color.red);
                        g.drawLine(bot.get(0), bot.get(1), bot.get(2), bot.get(3));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
