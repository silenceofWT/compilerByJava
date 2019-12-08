import java.util.ArrayList;
import java.util.List;

public class lexicalAnalyzer {//词法分析器
   public String[] k = { "main", "void", "if", "else", "while",
           "for", "int", "char", "string", "break", "continue",
            "return","switch","case","default" };// 关键字表
   public  String[] p = {"<=","==","=",">","<",
        "+","-","*","/","{","}",
        ";","(",")",",","[","]"};//界符表
   public List<String> i = new ArrayList<String>();// 变量名
   public List<String> C = new ArrayList<String>();// 字符
   public List<String> S = new ArrayList<String>();// 字符串
   public List<Double> c = new ArrayList<Double>();// 数字

    /*
    *词法分析器
    *
    *
    */


    public static void main(String[] args) {//主函数 测试
        System.out.println("lexicalAnalyzer");
        lexicalAnalyzer l = new lexicalAnalyzer();
        //System.out.println(l.k[0]);
        //System.out.println(l.p[0]);
        //l.i.add("myName");
        //System.out.println(l.i.get(0));

    }
}
