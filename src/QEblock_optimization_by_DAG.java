import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class QEblock_optimization_by_DAG {//基于DAG的四元式基本块优化算法
    public static List<List<String>> newQt =new ArrayList<List<String>>();//优化后的四元式区
    public static List<String> newQt_0=new ArrayList<String>();//优化后的四元式第一元
    public static List<String> newQt_1=new ArrayList<String>();//优化后的四元式第二元
    public static List<String> newQt_2=new ArrayList<String>();//优化后的四元式第三元
    public static List<String> newQt_3=new ArrayList<String>();//优化后的四元式第四元
    //public static List<List<Integer>> newblockNum=new ArrayList<List<Integer>>();//优化后的基本块划分编号记录.eg:newQtnum.get(i).get(0)为第i个基本块的入口四元式编号
    //newQtnum.get(i).get(1)为第i个基本块的出口四元式编号

    private List<graphNode> graph=new ArrayList<graphNode>();//伪DAG结点列表,每处理一个基本块就清空一次

    class graphNode
    {
        //int nodeNum;//结点编号,相当于在List中的下标
        String op;//结点操作符
        List<String> mark=new ArrayList<String>();//结点标记列表
        String mainMark;//主标记
        int nextB;//结点左后继结点编号,-1表示无
        int nextC;//结点右后继结点编号,-1表示无
        int level;//层次信息，定义：无任何后继结点为0层，否则为后继结点中最高层次+1
        List<Integer> pre=new ArrayList<Integer>();//结点前驱结点编号列表

        graphNode(String opp,int B,int C,String mk,int l)
        {
            op=opp;
            nextB=B;
            nextC=C;
            mark.add(mk);
            mainMark=mk;
            level=l;
        }

        public void addPre(int p)//增加前驱结点
        {
            pre.add(p);
        }

        public void setMainMark(String mmk)
        {
            mainMark=mmk;
            for(int i=0;i<this.mark.size();i++)
            {
                if(this.mark.get(i)==mmk)
                    return;
            }
            mark.add(mmk);
        }

        public void addMark(String mk)
        {
            mark.add(mk);
        }
    }

    public void init()
    {
        newQt.add(newQt_0);
        newQt.add(newQt_1);
        newQt.add(newQt_2);
        newQt.add(newQt_3);
    }

    public void optimize()
    {
        List<List<Integer>> blockNum=block.blockList;//block类传来的输入
        for(int i=0;i<blockNum.size();i++)//一次循环优化一个基本块
        {
            int a=blockNum.get(i).get(0);//取第i个基本块的入口四元式编号
            int b=blockNum.get(i).get(1);//取第i个基本块的出口四元式编号
            System.out.println(a+" "+b);

            //建立伪DAG
            int j;
            for(j=a;j<=b;j++)//先处理基本块开头的纯标号四元式
            {
               // System.out.println(" "+j);
                if(quaternaryExpression.Qt_0.get(j)=="wh"||quaternaryExpression.Qt_0.get(j)=="el"||
                   quaternaryExpression.Qt_0.get(j)=="ie"||quaternaryExpression.Qt_0.get(j)=="we")
                {
                    newQt_0.add(quaternaryExpression.Qt_0.get(j));
                    newQt_1.add(quaternaryExpression.Qt_1.get(j));
                    newQt_2.add(quaternaryExpression.Qt_2.get(j));
                    newQt_3.add(quaternaryExpression.Qt_3.get(j));
                }
                else
                    break;
            }
            for(;j<=b;j++)//处理基本块剩下的四元式
            {
               // System.out.println(" "+j);
                if(quaternaryExpression.Qt_0.get(j).equals("wh")||quaternaryExpression.Qt_0.get(j).equals("el")||
                        quaternaryExpression.Qt_0.get(j).equals("ie")||quaternaryExpression.Qt_0.get(j).equals("we"))
                {
                    break;//除了基本块开头，纯标号四元式只可能在结尾出现一次
                }
                else
                {
                    produce_graphNode(j);
                }
            }

            showgraph();
            System.out.println("---showgraph---");

            //根据伪DAG产生优化后的四元式，添加入newQt
            produce_newQt();


            //添加基本块尾部纯标号四元式
            if(j<=b&&(quaternaryExpression.Qt_0.get(j).equals("wh")||quaternaryExpression.Qt_0.get(j).equals("el")||
                    quaternaryExpression.Qt_0.get(j).equals("ie")||quaternaryExpression.Qt_0.get(j).equals("we")))
            {
                newQt_0.add(quaternaryExpression.Qt_0.get(j));
                newQt_1.add(quaternaryExpression.Qt_1.get(j));
                newQt_2.add(quaternaryExpression.Qt_2.get(j));
                newQt_3.add(quaternaryExpression.Qt_3.get(j));
            }

            graph.clear();

            /*for(int k=0;k<newQt.get(0).size();k++) {

                System.out.print(k + "(" + newQt.get(0).get(k) + " , ");
                System.out.print(newQt.get(1).get(k) + " , ");
                System.out.print(newQt.get(2).get(k) + " , ");
                System.out.println(newQt.get(3).get(k) + ") ");
            }
            System.out.println(" ");
            System.out.println(" ");*/
        }
        for(int k=0;k<newQt.get(0).size();k++) {

            System.out.print(k + "(" + newQt.get(0).get(k) + " , ");
            System.out.print(newQt.get(1).get(k) + " , ");
            System.out.print(newQt.get(2).get(k) + " , ");
            System.out.println(newQt.get(3).get(k) + ") ");
        }
    }

    public void produce_graphNode(int i)//传入四元式标号，根据该标号的四元式产生一个DAG结点
    {
        if(quaternaryExpression.Qt_0.get(i).equals("+")||quaternaryExpression.Qt_0.get(i).equals("-")||
           quaternaryExpression.Qt_0.get(i).equals("*")|| quaternaryExpression.Qt_0.get(i).equals("/"))
        {
            if(lexicalAnalyzer.isWhat_QE(quaternaryExpression.Qt_1.get(i))==0&&lexicalAnalyzer.isWhat_QE(quaternaryExpression.Qt_2.get(i))==0)//判断均为整型常数
            {
                int temp0=0;
                switch (quaternaryExpression.Qt_0.get(i))
                {
                    case "+":temp0=Integer.parseInt(quaternaryExpression.Qt_1.get(i))+Integer.parseInt(quaternaryExpression.Qt_2.get(i));break;
                    case "-":temp0=Integer.parseInt(quaternaryExpression.Qt_1.get(i))-Integer.parseInt(quaternaryExpression.Qt_2.get(i));break;
                    case "*":temp0=Integer.parseInt(quaternaryExpression.Qt_1.get(i))*Integer.parseInt(quaternaryExpression.Qt_2.get(i));break;
                    case "/":temp0=Integer.parseInt(quaternaryExpression.Qt_1.get(i))/Integer.parseInt(quaternaryExpression.Qt_2.get(i));break;
                }
                for(int i1=graph.size()-1;i1>=0;i1--)//确认该值是否在图中已存在,逆序是为了确保找到的结点是最新生成的
                {
                    for(int j1=0;j1<graph.get(i1).mark.size();j1++)
                    {
                        if(String.valueOf(temp0).equals(graph.get(i1).mark.get(j1)))
                            graph.get(i1).addMark(quaternaryExpression.Qt_3.get(i));
                    }
                }
                graphNode gn=new graphNode("_",-1,-1,String.valueOf(temp0),0);
                gn.addMark(quaternaryExpression.Qt_3.get(i));
                graph.add(gn);
                return;
            }
            else if(lexicalAnalyzer.isWhat_QE(quaternaryExpression.Qt_1.get(i))==1&&lexicalAnalyzer.isWhat_QE(quaternaryExpression.Qt_2.get(i))==1)//判断均为浮点型常数
            {
                float temp1=0;
                switch (quaternaryExpression.Qt_0.get(i))
                {
                    case "+":temp1=Integer.parseInt(quaternaryExpression.Qt_1.get(i))+Integer.parseInt(quaternaryExpression.Qt_2.get(i));break;
                    case "-":temp1=Integer.parseInt(quaternaryExpression.Qt_1.get(i))-Integer.parseInt(quaternaryExpression.Qt_2.get(i));break;
                    case "*":temp1=Integer.parseInt(quaternaryExpression.Qt_1.get(i))*Integer.parseInt(quaternaryExpression.Qt_2.get(i));break;
                    case "/":temp1=Integer.parseInt(quaternaryExpression.Qt_1.get(i))/Integer.parseInt(quaternaryExpression.Qt_2.get(i));break;
                }
                for(int i2=graph.size()-1;i2>=0;i2--)//确认该值是否在图中已存在
                {
                    for(int j2=0;j2<graph.get(i2).mark.size();j2++)
                    {
                        if(String.valueOf(temp1).equals(graph.get(i2).mark.get(j2)))
                            graph.get(i2).addMark(quaternaryExpression.Qt_3.get(i));
                    }
                }
                graphNode gn=new graphNode("_",-1,-1,String.valueOf(temp1),0);
                gn.addMark(quaternaryExpression.Qt_3.get(i));
                graph.add(gn);
                return;
            }
            else//两运算对象至少有一个是标识符
            {
                int Bnum=-1;
                int Cnum=-1;
                int maxl=0;
                for(int i2=graph.size()-1;i2>=0;i2--)//确认四元式第二项是否在图中已存在
                {
                    for(int j2=0;j2<graph.get(i2).mark.size();j2++)
                    {
                        if(quaternaryExpression.Qt_1.get(i).equals(graph.get(i2).mark.get(j2))) {
                            Bnum = i2;
                            if(maxl<graph.get(i2).level)
                                maxl=graph.get(i2).level;
                        }
                    }
                }
                if(Bnum==-1)//四元式第二项在图中不存在,则建立新结点
                {
                    graphNode gn=new graphNode("_",-1,-1,quaternaryExpression.Qt_1.get(i),0);
                    graph.add(gn);
                    Bnum=graph.size()-1;
                }

                for(int i2=graph.size()-1;i2>=0;i2--)//确认四元式第三项是否在图中已存在
                {
                    for(int j2=0;j2<graph.get(i2).mark.size();j2++)
                    {
                        if(quaternaryExpression.Qt_2.get(i).equals(graph.get(i2).mark.get(j2)))
                            Cnum=i2;
                        if(maxl<graph.get(i2).level)
                            maxl=graph.get(i2).level;
                    }
                }
                if(Cnum==-1)//四元式第三项在图中不存在,则建立新结点
                {
                    graphNode gn=new graphNode("_",-1,-1,quaternaryExpression.Qt_2.get(i),0);
                    graph.add(gn);
                    Cnum=graph.size()-1;
                }

                graphNode gn=new graphNode(quaternaryExpression.Qt_0.get(i),Bnum,Cnum,quaternaryExpression.Qt_3.get(i),maxl+1);
                graph.add(gn);
                graph.get(Bnum).addPre(graph.size()-1);
                graph.get(Cnum).addPre(graph.size()-1);
            }
        }
        else if(quaternaryExpression.Qt_0.get(i).equals("==")||quaternaryExpression.Qt_0.get(i).equals(">")||
           quaternaryExpression.Qt_0.get(i).equals("<")||quaternaryExpression.Qt_0.get(i).equals(">=")||
           quaternaryExpression.Qt_0.get(i).equals("<=")|| quaternaryExpression.Qt_0.get(i).equals("!="))
        {
            int Bnum=-1;
            int Cnum=-1;
            int maxl=0;
            for(int i2=graph.size()-1;i2>=0;i2--)//确认四元式第二项是否在图中已存在
            {
                for(int j2=0;j2<graph.get(i2).mark.size();j2++)
                {
                    if(quaternaryExpression.Qt_1.get(i).equals(graph.get(i2).mark.get(j2))) {
                        Bnum = i2;
                        if(maxl<graph.get(i2).level)
                            maxl=graph.get(i2).level;
                    }
                }
            }
            if(Bnum==-1)//四元式第二项在图中不存在,则建立新结点
            {
                graphNode gn=new graphNode("_",-1,-1,quaternaryExpression.Qt_1.get(i),0);
                graph.add(gn);
                Bnum=graph.size()-1;
            }

            for(int i2=graph.size()-1;i2>=0;i2--)//确认四元式第三项是否在图中已存在
            {
                for(int j2=0;j2<graph.get(i2).mark.size();j2++)
                {
                    if(quaternaryExpression.Qt_2.get(i).equals(graph.get(i2).mark.get(j2)))
                        Cnum=i2;
                    if(maxl<graph.get(i2).level)
                        maxl=graph.get(i2).level;
                }
            }
            if(Cnum==-1)//四元式第三项在图中不存在,则建立新结点
            {
                graphNode gn=new graphNode("_",-1,-1,quaternaryExpression.Qt_2.get(i),0);
                graph.add(gn);
                Cnum=graph.size()-1;
            }

            graphNode gn=new graphNode(quaternaryExpression.Qt_0.get(i),Bnum,Cnum,quaternaryExpression.Qt_3.get(i),maxl+1);
            graph.add(gn);
            graph.get(Bnum).addPre(graph.size()-1);
            graph.get(Cnum).addPre(graph.size()-1);
        }
        else if(quaternaryExpression.Qt_0.get(i).equals("do")||quaternaryExpression.Qt_0.get(i).equals("if"))//由于while(x)和if(x)只支持x为比较表达式,故生成结点方式只有一种
        {
            int maxl=0;
            int Bnum=-1;
            for(int i2=0;i2<graph.size();i2++)//找四元式第二项标记在哪个结点上
            {
                for(int j2=0;j2<graph.get(i2).mark.size();j2++)
                {
                    if(quaternaryExpression.Qt_1.get(i).equals(graph.get(i2).mark.get(j2)))
                        Bnum = i2;
                    if(maxl<graph.get(i2).level)
                        maxl=graph.get(i2).level;
                }
            }
            graphNode gn=new graphNode(quaternaryExpression.Qt_0.get(i),Bnum,-1,"_",maxl+1);
            graph.add(gn);
            graph.get(Bnum).addPre(graph.size()-1);
        }
        else if(quaternaryExpression.Qt_0.get(i).equals("="))
        {
            int maxl=0;
            int Bnum=-1;
            for(int i2=graph.size()-1;i2>=0;i2--)//找四元式第二项是否存在于图中
            {
                for(int j2=0;j2<graph.get(i2).mark.size();j2++)
                {
                    if(quaternaryExpression.Qt_1.get(i).equals(graph.get(i2).mark.get(j2)))
                        Bnum = i2;
                    if(maxl<graph.get(i2).level)
                        maxl=graph.get(i2).level;
                }
            }
            if(Bnum==-1)//四元式第二项在图中不存在,则建立新结点
            {
                graphNode gn=new graphNode("_",-1,-1,quaternaryExpression.Qt_1.get(i),0);
                graph.add(gn);
                Bnum=graph.size()-1;
            }

            for(int i2=graph.size()-1;i2>=0;i2--)//划掉图中已存在的四元式第四项（划掉后为空则不划）
            {
                for(int j2=0;j2<graph.get(i2).mark.size();j2++)
                {
                    if(quaternaryExpression.Qt_3.get(i).equals(graph.get(i2).mark.get(j2)))
                    {
                        if(graph.get(i2).mark.size()==1)
                            continue;
                        else
                        {
                            graph.get(i2).mark.remove(j2);
                            if(graph.get(i2).mainMark.equals(quaternaryExpression.Qt_3.get(i)))//替换主标记
                            {
                                graph.get(i2).setMainMark(graph.get(i2).mark.get(0));
                            }
                        }
                    }
                }
            }


            graph.get(Bnum).setMainMark(quaternaryExpression.Qt_3.get(i));
        }
    }

    public void produce_newQt()//遍历伪DAG结点列表n次（n为最高层次数），产生优化后的四元式并添加入newQt
    {
        int maxl=0;

        for(int i2=graph.size()-1;i2>=0;i2--)//主标记优先次序：常数、标识符、临时变量
        {
            if(graph.get(i2).level>maxl)//找出最大层次
                maxl=graph.get(i2).level;
            for(int j2=0;j2<graph.get(i2).mark.size();j2++)
            {
                if(lexicalAnalyzer.isWhat_QE(graph.get(i2).mark.get(j2))==0||lexicalAnalyzer.isWhat_QE(graph.get(i2).mark.get(j2))==1)
                {
                    graph.get(i2).setMainMark(graph.get(i2).mark.get(j2));
                    break;
                }
                else if(lexicalAnalyzer.isWhat_QE(graph.get(i2).mark.get(j2))==4)
                {
                    graph.get(i2).setMainMark(graph.get(i2).mark.get(j2));
                }
            }
        }


        List<Integer> s0=new Stack<Integer>();
        List<Integer> s1=new Stack<Integer>();

        for(int i=0;i<=maxl;i++)
        {
            for(int j=0;j<graph.size();j++)
            {
                for(int k=0;k<graph.get(j).mark.size();k++)
                {
                    if(i==0&&lexicalAnalyzer.isWhat_QE(graph.get(j).mark.get(k))==4)//若该标号为标识符，则需要生成赋值表达式
                    {
                        if(!graph.get(j).mark.get(k).equals(graph.get(j).mainMark))
                        {
                            newQt_0.add("=");
                            newQt_1.add(graph.get(j).mainMark);
                            newQt_2.add("_");
                            newQt_3.add(graph.get(j).mark.get(k));
                        }
                    }
                }
                if(graph.get(j).nextB!=-1||graph.get(j).nextC!=-1)//查该结点后继
                {
                    System.out.print("生成优化四元式出错");
                    return;
                }
                for(int i0=0;i0<graph.get(j).pre.size();i0++)
                {
                    int thepre=graph.get(j).pre.get(i0);
                    if(graph.get(thepre).nextB!=-1&&graph.get(thepre).nextC!=-1)
                    {
                        if (graph.get(graph.get(thepre).nextB).nextB == -1 && graph.get(graph.get(thepre).nextB).nextC == -1
                                && graph.get(graph.get(thepre).nextC).nextB == -1 && graph.get(graph.get(thepre).nextC).nextC == -1) {
                            newQt_0.add(graph.get(thepre).op);
                            newQt_1.add(graph.get(graph.get(thepre).nextB).mainMark);
                            newQt_2.add(graph.get(graph.get(thepre).nextC).mainMark);
                            newQt_3.add(graph.get(thepre).mainMark);
                            //graph.get(graph.get(thepre).nextB).pre.remove(thepre);
                            list_remove(graph.get(graph.get(thepre).nextB).pre,thepre);//剪枝
                            list_remove(graph.get(graph.get(thepre).nextC).pre,thepre);
                            graph.get(thepre).nextB=-1;
                            graph.get(thepre).nextC=-1;
                            produce_newQt_stack(s0,s1);
                        }
                        else if(graph.get(thepre).nextB==j
                                && (graph.get(graph.get(thepre).nextC).nextB != -1 || graph.get(graph.get(thepre).nextC).nextC != -1))
                        {
                            s0.add(j);
                            s1.add(thepre);
                        }
                        else if(graph.get(thepre).nextC==j
                                && (graph.get(graph.get(thepre).nextB).nextB != -1 || graph.get(graph.get(thepre).nextB).nextC != -1))
                        {
                            s0.add(j);
                            s1.add(thepre);
                        }
                    }
                    else if(graph.get(thepre).nextB==-1&&graph.get(thepre).nextC!=-1)
                    {
                        newQt_0.add(graph.get(thepre).op);
                        newQt_1.add("_");
                        newQt_2.add(graph.get(graph.get(thepre).nextC).mainMark);
                        newQt_3.add(graph.get(thepre).mainMark);
                        list_remove(graph.get(graph.get(thepre).nextC).pre,thepre);
                        graph.get(thepre).nextC=-1;
                        produce_newQt_stack(s0,s1);
                    }
                    else if(graph.get(thepre).nextB!=-1&&graph.get(thepre).nextC==-1)
                    {
                        newQt_0.add(graph.get(thepre).op);
                        newQt_1.add(graph.get(graph.get(thepre).nextB).mainMark);
                        newQt_2.add("_");
                        newQt_3.add(graph.get(thepre).mainMark);
                        list_remove(graph.get(graph.get(thepre).nextB).pre,thepre);//剪枝
                        graph.get(thepre).nextB=-1;
                        produce_newQt_stack(s0,s1);
                    }
                }

            }
        }
    }

    public void produce_newQt_stack(List<Integer> s0,List<Integer> s1)
    {
        for(int i=0;i<s0.size();i++)
        {
            if(graph.get(graph.get(s1.get(i)).nextB).nextB == -1 && graph.get(graph.get(s1.get(i)).nextB).nextC == -1
                    && graph.get(graph.get(s1.get(i)).nextC).nextB == -1 && graph.get(graph.get(s1.get(i)).nextC).nextC == -1)
            {
                newQt_0.add(graph.get(s1.get(i)).op);
                newQt_1.add(graph.get(graph.get(s1.get(i)).nextB).mainMark);
                newQt_2.add(graph.get(graph.get(s1.get(i)).nextC).mainMark);
                newQt_3.add(graph.get(s1.get(i)).mainMark);
                //graph.get(graph.get(thepre).nextB).pre.remove(thepre);
                list_remove(graph.get(graph.get(s1.get(i)).nextB).pre,s1.get(i));//剪枝
                list_remove(graph.get(graph.get(s1.get(i)).nextC).pre,s1.get(i));
                graph.get(s1.get(i)).nextB=-1;
                graph.get(s1.get(i)).nextC=-1;
            }
        }
    }

    public void list_remove(List<Integer> list,int a)
    {
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i)==a)
                list.remove(i);
        }
    }

    public void showgraph()
    {
        for(int i=0;i<graph.size();i++)
        {
            System.out.print(graph.get(i).op+" is op;  ");
            System.out.print(graph.get(i).mark+" is mark;  ");
            System.out.print(graph.get(i).mainMark+" is mainMark;  ");
            System.out.print(graph.get(i).nextB+" is B;  ");
            System.out.print(graph.get(i).nextC+" is C;  ");
            System.out.print(graph.get(i).pre+" is pre;  ");
            System.out.println(graph.get(i).level+" is level.  ");
        }
    }
}
