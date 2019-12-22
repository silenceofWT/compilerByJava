package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class TabbedPane extends JPanel{
    public TabbedPane()
    {
        //布局充满主窗口
        super(new GridLayout(1,1));
        //新建选项卡1
        JTabbedPane tabbedPane=new JTabbedPane();
        //选项卡图标
        ImageIcon icon1=createImageIcon("./../icon/tab1.png");
        ImageIcon icon2=createImageIcon("./../icon/tab2.png");
        ImageIcon icon3=createImageIcon("./../icon/tab3.png");
        ImageIcon icon4=createImageIcon("./../icon/tab4.png");
        ImageIcon icon5=createImageIcon("./../icon/tab5.png");
        //选项卡2标题
        JComponent panel1=makeTextPanel("源码");
        //添加选项卡1
        tabbedPane.addTab("源码",icon1, panel1);
        //选项卡1快键键
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        //选项卡2标题
        JComponent panel2=makeTextPanel("Token序列");
        //添加选项卡2
        tabbedPane.addTab("Token序列",icon2,panel2);
        //选项卡2快键键
        tabbedPane.setMnemonicAt(1,KeyEvent.VK_2);


        JComponent panel3=makeTextPanel("四元式序列");
        tabbedPane.addTab("四元式序列",icon3,panel3,"Still does nothing");
        tabbedPane.setMnemonicAt(2,KeyEvent.VK_3);
        JComponent panel4=makeTextPanel("优化的四元式序列");
        panel4.setPreferredSize(new Dimension(410,50));
        tabbedPane.addTab("优化的四元式序列",icon4,panel4,"Does nothing at all");
        tabbedPane.setMnemonicAt(3,KeyEvent.VK_4);
        JComponent panel5=makeTextPanel("四元式序列");
        tabbedPane.addTab("四元式序列",icon5,panel5,"Still does nothing");
        tabbedPane.setMnemonicAt(2,KeyEvent.VK_5);
        add(tabbedPane);
    }
    protected JComponent makeTextPanel(String text)
    {//创建一个内容面板，并在面板上添加指定的文本内容
        JPanel panel=new JPanel(false);
        JLabel filler=new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1,1));
        panel.add(filler);
        return panel;
    }
    protected static ImageIcon createImageIcon(String path)
    {
        java.net.URL imgURL=TabbedPane.class.getResource(path);
        if(imgURL!=null)
        {
            return new ImageIcon(imgURL);
        }
        else
        {
            System.err.println("Couldn't find file: "+path);
            return null;
        }
    }

    public static void main(String[] args) {
       /* JFrame frame=new JFrame("我的电脑 - 属性");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TabbedPane(),BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);*/
    }

}
