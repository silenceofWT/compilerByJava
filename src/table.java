import java.util.ArrayList;
import java.util.List;
/*
  定义符号表
 */
public class table {
    class iinfor {// 符号表总表成员
        String name;// 变量名
        int type;//指向类型表的一项
        String cate;//种类，种类为：v(变量)/...
        int addr;//根据cate不同，指向长度表(LENL)/...某一项
    }


    class type{//类型表成员
        char tval;//类型代码:i(int)、f(float)、c(char)
        int tpoint;//根据tval，指向null/数组表（暂无）/结构表（暂无）的某一项
    }


    List<iinfor> synbl = new ArrayList<iinfor>();// 符号表总表
    List<Integer> lenl = new ArrayList<Integer>();//长度表

}
