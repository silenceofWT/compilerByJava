import java.util.*;
import java.io.*;


public class quaternaryExpression {//生成四元式
    /*public String[] k = { "main", "void", "if", "else", "while",
            "for", "int", "char", "string", "break", "continue",
            "return","switch","case","default" };// 关键字表
    public  String[] p = {"<=",">=","==","=",">","<","&&","||",
            "+","-","*","/","{","}",
            ";","(",")",",","[","]"};//界符表
    public List<String> i = new ArrayList<String>();// 标识符
    public List<String> C = new ArrayList<String>();// 字符
    public List<String> S = new ArrayList<String>();// 字符串
    public List<String> c = new ArrayList<String>();// 数字*/
    public static Stack<String> sem = new Stack<>();//语义栈
    public static List<List<String>> Qt =new ArrayList<List<String>>();//四元式区
    public static List<String> Qt_0=new ArrayList<String>();//四元式第一元
    public static List<String> Qt_1=new ArrayList<String>();//四元式第二元
    public static List<String> Qt_2=new ArrayList<String>();//四元式第三元
    public static List<String> Qt_3=new ArrayList<String>();//四元式第四元


    /*
    * 初始化
    * */
    public static void init()//public void init(List<String> ii,List<String> CC,List<String> SS,List<String> cc)
    {
        /*i=ii;
        C=CC;
        S=SS;
        c=cc;*/
        Qt.add(Qt_0);
        Qt.add(Qt_1);
        Qt.add(Qt_2);
        Qt.add(Qt_3);
    }
  /*
  * 生成四元式
  * */
    public static void produceQE(String command)
    {
        String[] splitcommand=command.split("\\(|\\)");
        if(splitcommand[0].equals("PUSH"))
        {
            sem.push(splitcommand[1]);
        }
        if(command.equals("ASSI(=)"))
        {
            Qt_0.add("=");
            Qt_1.add(sem.peek());
            sem.pop();
            Qt_2.add("_");
            Qt_3.add(sem.peek());
            sem.pop();
        }
    }
   /*
   * 打印四元式到控制台
   * */
    public static void show()
    {
        for(int i=0;i<Qt.get(0).size();i++)
        {
            System.out.print("("+Qt.get(0).get(i)+" , ");
            System.out.print(Qt.get(1).get(i)+" , ");
            System.out.print(Qt.get(2).get(i)+" , ");
            System.out.print(Qt.get(3).get(i)+")");
        }
    }
    /*
    * 将四元式写入txt
    * */
    public static void QtWriteFile(){
        String path = "F:\\javaProject\\compilerByJava\\src\\outputFile\\quaternaryExp.txt";
        fileParseUtils.clearInfoForFile(path);//一进来清空txt文件
        for(int i=0;i<Qt.get(0).size();i++)
        {
            String QtString = "("+Qt.get(0).get(i)+" , " + Qt.get(1).get(i)+" , " + Qt.get(2).get(i)+" , " + Qt.get(3).get(i)+")\n";
            fileParseUtils.saveAsFileWriter(QtString,path);
        }
    }
    public static void main(String[] args)
    {
       /*// quaternaryExpression qE=new quaternaryExpression();
        quaternaryExpression.init();
        quaternaryExpression.produceQE("PUSH(a)");
        quaternaryExpression.produceQE("PUSH(b)");
        quaternaryExpression.produceQE("ASSI(=)");
        quaternaryExpression.show();
        quaternaryExpression.QtWriteFile();*/

       //词法分析
        lexicalAnalyzer l = new lexicalAnalyzer();
        fileParseUtils.txtParse();
        l.CharToToken(fileParseUtils.charArr);
        lexicalAnalyzer.showTokens();
       /* quaternaryExpression qE=new quaternaryExpression();*/
        quaternaryExpression.init();

        parser.analyzer();

        quaternaryExpression.show();

        quaternaryExpression.QtWriteFile();
    }

}
