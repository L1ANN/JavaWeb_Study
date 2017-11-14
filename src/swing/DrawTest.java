package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 12:25 2017/11/13
 * @Modified By:
 */
public class DrawTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        //实体化JPanel面板容器类的对象，作为北边面板
        JPanel northPanel = new JPanel();
        //设置面板的背景颜色
        northPanel.setBackground(Color.LIGHT_GRAY);

        //实例化按钮对象
        JButton button_Line = new JButton("Line");
        JButton button_Pencil = new JButton("Pencil");
        //将按钮添加到northPanel上
        northPanel.add(button_Line);
        northPanel.add(button_Pencil);


        //实例化按钮对象
        JButton button_color = new JButton();
        //设置按钮的背景色
        button_color.setBackground(Color.RED);
        //将按钮添加到northPanel上
        northPanel.add(button_color);


        //将northPanel添加到窗口的北边
        frame.add(northPanel, BorderLayout.NORTH);
        //实现中间面板（画图面板）
        JPanel centerPanel = new JPanel();
        //设置中间面板的背景颜色
        centerPanel.setBackground(Color.WHITE);
        //将中间面板添加到窗体上的中间部分
        frame.add(centerPanel, BorderLayout.CENTER);

        //设置窗口标题
        frame.setTitle("简易画板");
        //设置点击关闭方式
        frame.setDefaultCloseOperation(3);
        //设置窗体位置
        frame.setLocation(550, 250);
        //设置窗体大小
        frame.setSize(800, 500);
        //设置窗体可见
        frame.setVisible(true);

        //为按钮添加监听事件
        DrawListener d1 = new DrawListener();
        button_Line.addActionListener(d1);
        button_Pencil.addActionListener(d1);
        button_color.addActionListener(d1);
        //将centerPanel画板上的画布对象传递到DrawListener中，这行代码是获取组件上的画笔画布对象，然后传递到DrawListener累中
        d1.setG(centerPanel.getGraphics());
        //为画板添加监听事件，分别是MouseListener和MouseMotionListener
        centerPanel.addMouseListener(d1);
        centerPanel.addMouseMotionListener(d1);
    }
}

class DrawListener implements ActionListener, MouseListener, MouseMotionListener {

    String type = null;
    Color color = null;
    Graphics2D g = null;

    int x1, y1;
    int x2, y2;

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

    //当你在事件源对象上发生鼠标按下动作时执行的方法
    public void mousePressed(MouseEvent e) {
        //获取按下的坐标值
        x1 = e.getX();
        y1 = e.getY();
        //按下事件处理方法中，设置画笔画布对象的颜色
        g.setColor(color);
    }
    //当你在事件源对象上发生鼠标松开动作时执行的方法
    public void mouseReleased(MouseEvent e) {
        //获取释放的坐标值
        x2 = e.getX();
        y2 = e.getY();
        //判断当前选择的图形按钮是否是直线
        if (type.equals("Line")) {
            //绘制直线
            g.setStroke(new BasicStroke(1));
            g.drawLine(x1, y1, x2, y2);
        }
    }
    //当你在事件源对象上发生鼠标拖动动作时执行的方法
    public void mouseDragged(MouseEvent e) {
        if (type.equals("Pencil")) {
            x2 = e.getX();
            y2 = e.getY();
            g.setStroke(new BasicStroke(1));

            g.drawLine(x1, y1, x2, y2);
            x1 = x2;
            y1 = y2;
        }
    }

    public void mouseMoved(MouseEvent e){
    }
    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }


}