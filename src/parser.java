import java.util.*;

public class parser {//进行语法和语义的分析

    public static Map<String, Map<String,String>>table= new HashMap<String,Map<String,String>>();
    public static List<String> Vn=new ArrayList<>();
    public static List<String>Vt=new ArrayList<>();
    public static String[] input={"i","a","i","#"};//输入

    public static void iniTable()
    {
        Map Em=new HashMap();
        Em.put("i","<T><X>");Em.put("(","<T><X>");
        Map Xm=new HashMap();
        Xm.put("a","<a><T><X>");Xm.put(")","");Xm.put("#","");
        Map Tm=new HashMap();
        Tm.put("i","<F><Y>");Tm.put("(","<F><Y>");
        Map Ym=new HashMap();
        Ym.put("a","");Ym.put("b","<b><F><Y>");Ym.put(")","");Ym.put("#","");
        Map Fm=new HashMap();
        Fm.put("i","<i>");Fm.put("(","<(><E><)>");

        table.put("E", Em);
        table.put("X", Xm);
        table.put("T", Tm);
        table.put("Y", Ym);
        table.put("F", Fm);
        System.out.println();
        System.out.println(" Map Elements");
        System.out.println("\t" + table);

        Vn.add("E");Vn.add("X");Vn.add("T");Vn.add("Y");Vn.add("F");
        Vt.add("i");Vt.add("a");Vt.add("b");Vt.add("(");Vt.add(")");Vt.add("#");
        System.out.println(input[0]);
    }

    public static void analyzer() {
        Stack<String> sq = new Stack<>();//分析栈
        //stack<String>SEM;//语义栈
        sq.push("#");
        sq.push("E");
        iniTable();
        int tag = 0;
        while (!sq.empty()) {
            if (Vn.contains(sq.peek()) ) //非终结符号
            {
                String a = sq.peek();
                System.out.println(sq.peek());
                String b = input[tag];
                boolean x = Vn.contains(a);
                boolean y = Vt.contains(b);
                if (x && y) {
                    System.out.println(sq.peek() + "出栈");
                    sq.pop();
                    System.out.println(table.get(a).get(b));
                    String[] temp = table.get(a).get(b).split("<|>");//按照<>拆开
                    for (int t = temp.length - 1; t > 0; t--)
                    {
                        if(temp[t].length()!=0)//去除空字符串
                        {
                            System.out.println(temp[t]+" 入栈");
                            sq.push(temp[t]);
                        }
                    }
                } else {
                    System.out.println("没有可以匹配的规则，不能识别！出错！");
                    return;
                }
            } else //终结符号
            {
                if (sq.peek().equals(input[tag])) {
                    System.out.println("识别 " + sq.peek() + " ，退栈");
                    tag++;
                    sq.pop();
                } else {
                    System.out.println("不能识别" + sq.peek() + "！出错！");
                    return;
                }
            }
        }
        System.out.println("匹配成功");
    }

    public static List<String> change(String token_in)//拆开token
    {
        List<String> rep=new ArrayList<>();
        String t[]=token_in.split("\\{|}|,");
        for(int m=0;m<t.length;m++)
        {
            if(t[m].length()!=0) {
                rep.add(t[m]);
            }
        }
        return rep;
    }

    public static void main(String[] args) {
        System.out.println("parser");
    }
}
