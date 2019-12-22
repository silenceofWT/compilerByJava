import java.util.ArrayList;
import java.util.List;
/*
  定义符号表
 */
public class table {
    public class iinfor {// 符号表总表成员
        String name;// 变量名
        int type;//指向类型表的一项
        String cate;//种类，种类为：v(变量)/...
        int addr;//根据cate不同，指向长度表(LENL)/...某一项

        iinfor(String nam,int typ,String cat,int adr)//构造函数
        {
            name=nam;
            type=typ;
            cate=cat;
            addr=adr;
        }

        public void show()
        {
            System.out.println(name +"  "+type+"  "+cate+"  "+addr);
        }

    }


    public class type{//类型表成员
        char tval;//类型代码:i(int)、f(float)、c(char)、b(bool)
        int tpoint;//根据tval，指向null/数组表（暂无）/结构表（暂无）的某一项,-1代表空指针

        type(char tva,int tpt)//构造函数
        {
            tval=tva;
            tpoint=tpt;
        }

        public void show()
        {
            System.out.println(tval +"  "+tpoint);
        }
    }


    List<iinfor> synbl = new ArrayList<iinfor>();// 符号表总表
    List<type> typel = new ArrayList<type>();//类型表
    List<Integer> lenl = new ArrayList<Integer>();//长度表

    table()//符号表构造函数，定义一些初始项
    {
        typel.add(new type('i',-1));//类型表新增项：int,空指针
        typel.add(new type('f',-1));//类型表新增项：float,空指针
        typel.add(new type('c',-1));//类型表新增项：char,空指针
        typel.add(new type('b',-1));//类型表新增项：bool,空指针
    }

    public void show()
    {
        System.out.println("SYNBL(符号表总表):");
        for(int i=0;i<synbl.size();i++)
        {
            synbl.get(i).show();
        }

        System.out.println("TYPEL(类型表):");
        for(int i=0;i<typel.size();i++)
        {
            typel.get(i).show();
        }

        System.out.println("LENL(长度表):");
        for(int i=0;i<lenl.size();i++)
        {
            System.out.println(lenl.get(i));
        }
    }

    public int checkType(String input)//查类型并返回
    {
        for(int i=0;i<synbl.size();i++)
        {
            if(input.equals(synbl.get(i).name))
                return synbl.get(i).type;
        }
        return -1;
    }
}
