import javax.swing.*;

public class TextFileOpener extends JFrame {
    private static final long serialVersionUID=-9077023825514749548L;
    private JTextArea ta_showText;    //定义显示文件属性的文本域
    private JTextArea ta_showProperty;    //定义显示文件内容的文本域
    //Launch the application.
    public static void main(String[] args)
    {
        TextFileOpener frame=new TextFileOpener();
        frame.setVisible(true);
    }
}
