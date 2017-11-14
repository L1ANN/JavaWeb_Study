package socket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author:L1ANN
 * @Description: 同步画板的服务端
 * @Date:Created in 15:12 2017/11/13
 * @Modified By:
 */
public class Server_Draw {

    private ServerSocket serverSocket;
    private ServerThread serverThread;
    public static List<Socket> clientList =new ArrayList<>();

    public static void main(String[] args) {
        new Server_Draw();
    }

    public Server_Draw() {
        JFrame frame = new JFrame();

        //实体化JPanel面板容器类的对象，作为北边面板
        JPanel northPanel = new JPanel();
        //设置面板的背景颜色
        northPanel.setBackground(Color.LIGHT_GRAY);

        //实例化按钮对象
        JButton button_Conn = new JButton("Start");
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
        JPanel centerPanel = new JPanel();
        //设置中间面板的背景颜色
        centerPanel.setBackground(Color.WHITE);
        //将中间面板添加到窗体上的中间部分
        frame.add(centerPanel, BorderLayout.CENTER);

        //设置窗口标题
        frame.setTitle("简易画板服务器端");
        //设置点击关闭方式
        frame.setDefaultCloseOperation(3);
        //设置窗体位置
        frame.setLocation(550, 250);
        //设置窗体大小
        frame.setSize(800, 500);
        //设置窗体可见
        frame.setVisible(true);

        //点击开启按钮，开启服务器，同时启动服务器线程，等待客户端连接
        button_Conn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    serverSocket = new ServerSocket(6666);
                    System.out.println("等待客户端连接！");
                    serverThread = new ServerThread(serverSocket);
                    serverThread.start();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        DrawListener dl = new DrawListener();
        button_Line.addActionListener(dl);
        button_Pencil.addActionListener(dl);
        button_Color.addActionListener(dl);

        dl.setG(centerPanel.getGraphics());
        //为画板添加监听事件，分别是MouseListener和MouseMotionListener
        centerPanel.addMouseListener(dl);
        centerPanel.addMouseMotionListener(dl);

    }

    //服务器线程
    class ServerThread extends Thread {
        private ServerSocket serverSocket;

        public ServerThread(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        public void run() {
            while (true) {
                try {
                    //当有客户端线程连接时，将客户端socket加入到List<Socket>中
                    Socket socket = serverSocket.accept();
                    System.out.println("客户端连接上！");
                    clientList.add(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //定义的监听器，用于监听按钮和画布
    class DrawListener implements ActionListener, MouseListener, MouseMotionListener {

        String type = null;
        Color color = null;
        Graphics2D g = null;

        String x1, y1;
        String x2, y2;

        private BufferedReader reader;
        private PrintWriter writer;

        //按钮的监听器，用于保存画笔颜色和画笔种类
        public void actionPerformed(ActionEvent e) {
            if (!e.getActionCommand().equals("")) {
                type = e.getActionCommand();
            } else {
                //获得事件源对象
                JButton button = (JButton) e.getSource();
                //获得按钮上的颜色信息，存入到自己定义的color属性中
                color = button.getBackground();
            }
        }

        public void setG(Graphics g) {
            this.g = (Graphics2D) g;
        }

        public void mouseClicked(MouseEvent e) {

        }

        //画布的监听器，监听鼠标按下时的执行动作
        public void mousePressed(MouseEvent e) {
            //获取按下的坐标值
            x1 = String.valueOf(e.getX());
            y1 = String.valueOf(e.getY());

            //按下事件处理方法中，设置画笔画布对象的颜色
            g.setColor(color);

        }

        //当你在事件源对象上发生鼠标松开动作时执行的方法
        public void mouseReleased(MouseEvent e) {
            List<String> bot = new ArrayList<>();
            //获取释放的坐标值
            x2 = String.valueOf(e.getX());
            y2 = String.valueOf(e.getY());

            //判断当前选择的图形按钮是否是直线
            if (type.equals("Line")) {
                //绘制直线
                g.setStroke(new BasicStroke(1));
                g.drawLine(Integer.parseInt(x1), Integer.parseInt(y1),Integer.parseInt(x2), Integer.parseInt(y2));
                //将起始坐标发给客户端

                bot.add(x1);
                bot.add(y1);
                bot.add(x2);
                bot.add(y2);
                sendMessage(bot);
            }

        }

        //当你在事件源对象上发生鼠标拖动动作时执行的方法
        public void mouseDragged(MouseEvent e) {
            List<String> bot = new ArrayList<>();
            if (type.equals("Pencil")) {
                x2 = String.valueOf(e.getX());
                y2 = String.valueOf(e.getY());
                g.setStroke(new BasicStroke(1));
                g.drawLine(Integer.parseInt(x1), Integer.parseInt(y1),Integer.parseInt(x2), Integer.parseInt(y2));
                //将起始坐标发给客户端

                bot.add(x1);
                bot.add(y1);
                bot.add(x2);
                bot.add(y2);
                sendMessage(bot);

                x1 = x2;
                y1 = y2;
            }
        }

        public void mouseMoved(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }

        //将坐标值转发给所有连接的客户端
        public void sendMessage(List<String> bots){
            try {
                for (int i = 0; i < clientList.size(); i++) {
                    //使用,将坐标分隔，最后形成,x1,y1,x2,y2,
                    String message = ",";
                    Iterator<String> it = bots.iterator();
                    while(it.hasNext()){
                        String m = it.next();
                        message = message +m+",";
                    }
                    writer = new PrintWriter(new OutputStreamWriter(clientList.get(i).getOutputStream()));
                    writer.println(message);
                    writer.flush();
                }
            }catch(IOException e1){
                e1.printStackTrace();
            }
        }
    }
}

