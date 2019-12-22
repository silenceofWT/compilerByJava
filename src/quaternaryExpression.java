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
    public static int Tn;
    public static String QTFilePath = "C:\\Users\\Saber\\Desktop\\新建文件夹\\compilerByJava\\src\\outputFile\\quaternaryExp.txt";

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
        Tn=0;
    }

    public static void produceQE(String command)
    {
        String[] splitcommand=command.split("\\(|\\)");
        if(splitcommand[0].equals("PUSH"))
        {
            sem.push(splitcommand[1]);
            System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"+sem.peek());
        }
        else if(command.equals("ASSI(=)"))//d=c
        {
            String c,d;
            Qt_0.add("=");
            Qt_1.add(sem.peek());
            System.out.println("ccccccccccccccccccccccccccccccccccc"+sem.peek());
            c=sem.peek();
            sem.pop();
            Qt_2.add("_");
            Qt_3.add(sem.peek());
            d=sem.peek();
            sem.pop();

            int ctp=lexicalAnalyzer.isWhat_QE(c);
            if(ctp==4)//c为标识符
            {
                if(parser.tb.checkType(d)!=parser.tb.checkType(c))//类型不同则报错
                {
                    System.out.println("等号两端类型不匹配！请检查输入文件。");
                    return;
                }
            }
            else
            {
                if(parser.tb.checkType(d)!=ctp)//类型不同则报错
                {
                    System.out.println("等号两端类型不匹配！请检查输入文件。");
                    return;
                }
            }
        }
        else if(splitcommand[0].equals("GEQ")||splitcommand[0].equals("COMP"))//加减乘除和比较，bOPa
        {
            String a,b;
            Qt_0.add(splitcommand[1]);
            Qt_2.add(sem.peek());
            a=sem.peek();
            sem.pop();
            Qt_1.add(sem.peek());
            b=sem.peek();
            sem.pop();
            Tn++;
            String temp="t"+Tn;

            int atp=lexicalAnalyzer.isWhat_QE(a);
            int btp=lexicalAnalyzer.isWhat_QE(b);
            System.out.println(a+b+atp+"  feds "+btp+"  dsfsfds");
            if((atp==4&&btp==4)||(atp==5&&btp==4)||(atp==4&&btp==5)||(atp==5&&btp==5))//同为标识符，查符号表
            {
                if(parser.tb.checkType(a)==parser.tb.checkType(b))//类型相同，则临时变量填符号表
                {
                    writeTable(temp,parser.tb.checkType(a));
                }
                else
                {
                    System.out.println("两标识符间类型不匹配！请检查输入文件。");
                    return;
                }
            }
            else if(atp==4||atp==5)//仅a为标识符时
            {
                if(parser.tb.checkType(a)==btp)//类型匹配
                {
                    writeTable(temp,parser.tb.checkType(a));
                }
                else
                {
                    System.out.println("标识符和另一运算对象类型不匹配！请检查输入文件。");
                    return;
                }
            }
            else if(btp==4||btp==5)//仅b为标识符时
            {
                if(parser.tb.checkType(b)==atp)//类型匹配
                {
                    writeTable(temp,parser.tb.checkType(b));
                }
                else
                {
                    System.out.println("标识符和另一非标识符运算对象类型不匹配！请检查输入文件。");
                    return;
                }
            }
            else//a、b均不为标识符
            {
                if(atp==btp)
                {
                    writeTable(temp,atp);
                }
                else
                {
                    System.out.println("两非标识符运算对象类型不匹配！请检查输入文件。");
                    return;
                }
            }

            sem.push(temp);
            Qt_3.add(temp);
        }
        else if(command.equals("IF(if)"))
        {
            Qt_0.add("if");
            Qt_1.add(sem.peek());
            sem.pop();
            Qt_2.add("_");
            Qt_3.add("_");
        }
        else if(command.equals("EL(el)")||command.equals("IE(ie)"))
        {
            Qt_0.add(splitcommand[1]);
            Qt_1.add("_");
            Qt_2.add("_");
            Qt_3.add("_");
        }
        //while循环生成四元式
        else if(command.equals("WH()")){
            Qt_0.add("wh");
            Qt_1.add("_");
            Qt_2.add("_");
            Qt_3.add("_");
        }
        else if(command.equals("DO(do)")){
            Qt_0.add("do");
            Qt_1.add(sem.peek());
            sem.pop();
            Qt_2.add("_");
            Qt_3.add("_");
        }
        else if(command.equals("WE(we)")){
            Qt_0.add("we");
            Qt_1.add("_");
            Qt_2.add("_");
            Qt_3.add("_");
        }
    }

    public static void show()
    {
        for(int i=0;i<Qt.get(0).size();i++) {

            System.out.print(i + "(" + Qt.get(0).get(i) + " , ");
            System.out.print(Qt.get(1).get(i) + " , ");
            System.out.print(Qt.get(2).get(i) + " , ");
            System.out.println(Qt.get(3).get(i) + ") ");
        }
    }

    public static void writeToTxt()
    {
        fileParseUtils.clearInfoForFile(QTFilePath);
        String qtStr;//存储四元式字符串
        for(int i=0;i<Qt.get(0).size();i++)
        {
            qtStr  = "("+Qt.get(0).get(i)+" , " + Qt.get(1).get(i)+" , " + Qt.get(2).get(i)+" , " + Qt.get(3).get(i)+")\n";
            fileParseUtils.saveAsFileWriter(qtStr,QTFilePath);
        }
    }

    public static void writeTable(String name,int type)//填符号表
    {
        switch(type) {
            case 0:parser.tb.lenl.add(parser.sizeofint);break;
            case 1:parser.tb.lenl.add(parser.sizeoffloat);break;
            case 2:parser.tb.lenl.add(parser.sizeofchar);break;
            case 3:parser.tb.lenl.add(parser.sizeofbool);break;
        }
        table.iinfor iinfor_new=parser.tb.new iinfor(name,type,"v",parser.no_of_i);
        parser.tb.synbl.add(iinfor_new);
        parser.no_of_i++;
    }

    public static void main(String[] args)
    {
        /*quaternaryExpression qE=new quaternaryExpression();
        qE.init();
        qE.produceQE("PUSH(a)");
        qE.produceQE("PUSH(b)");
        qE.produceQE("ASSI(=)");
        qE.show();*/
    }

}
