import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class TabbedPane extends JPanel  {
    public TabbedPane()
    {
        super(new GridLayout(1,1));
        JTabbedPane tabbedPane=new JTabbedPane();
        ImageIcon icon=createImageIcon("tab.jp1g");
        JComponent panel1=makeTextPanel("源码编辑");
        tabbedPane.addTab("源码编辑",icon, panel1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        JComponent panel2=makeTextPanel("Token序列");
        tabbedPane.addTab("Token序列",icon,panel2);
        tabbedPane.setMnemonicAt(1,KeyEvent.VK_2);
        JComponent panel3=makeTextPanel("高级");
        tabbedPane.addTab("高级",icon,panel3,"Still does nothing");
        tabbedPane.setMnemonicAt(2,KeyEvent.VK_3);
        JComponent panel4=makeTextPanel("系统保护");
        panel4.setPreferredSize(new Dimension(410,50));
        tabbedPane.addTab("系统保护",icon,panel4,"Does nothing at all");
        tabbedPane.setMnemonicAt(3,KeyEvent.VK_4);
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
        JFrame frame=new JFrame("我的电脑 - 属性");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TabbedPane(),BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

}
