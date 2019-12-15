import javax.swing.*;
import java.awt.*;

public class userUI extends JFrame{//用户界面

    public userUI(){
        //构造函数
        //初始主窗体
        setTitle("简易编译器");//设置主窗体标题
        setSize(1000,600);//设置主窗体的尺寸
        setLocationRelativeTo(this);//设置主窗体默认打开的位置
        setResizable(false);//设置主窗体不可改变大小
        setDefaultCloseOperation(EXIT_ON_CLOSE);//设置主窗体的默认关闭状态
       /* TabbedPane tp = new TabbedPane();
        Container c=getContentPane();    //获取当前窗口的内容窗格
        c.add(tp);*/

        lexicalAnalyzer l = new lexicalAnalyzer();
        fileParseUtils.txtParse();
        l.CharToToken(fileParseUtils.charArr);
        JLabel jl=new JLabel((lexicalAnalyzer.Tokens.get(1)));    //创建一个标签

        /*c.add(jl);    //将标签组件添加到内容窗格上*/
        setVisible(true);//设置主窗体可见

    }










    public static void main(String[] args) {
        new userUI();
    }
}
