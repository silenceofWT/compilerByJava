import java.util.ArrayList;
import java.util.List;

/*
* 基本块类
* 功能：根据Token序列划分基本块
* 入口语句：
* 1.四元式的第一个语句
* 2.由条件语句或无条件语句能转到的语句
* 3.紧跟在条件转移语句后面的语句
*出口语句：
* 1.下一个入口语句的前导语句
* 2.转移语句（包括本身）
* 3.停语句（包括本身）
* */
public class block {
    /*
    * 基本块 入口和出口序列
    * 比如：B1：1  2 1为入口 2为出口
    *
    * */
    //所有块的属性List
    public static List<List<Integer>> blockList =new ArrayList<List<Integer>>();
    //每一块的属性标号 包括入口和出口
    public static List<Integer> num = new ArrayList<>();
    /*
    * 基本块划分函数
    * */
    public static void divideBlock(List<List<String>> Qt){
        //qtNum 为四元式的数量
        int inNum;//当前基本块的入口号
        int outNum;//当前基本块的出口号


        for(int i = 0;i < Qt.get(0).size();i++){//遍历四元式序列 Qt.get(0).size()为四元式的数量
            //i+1相当于四元式的标号
            if (i == 0){//如果为第一个四元式 那么添加进blockList的入口标号中
                inNum  = i + 1;
                num.add(inNum);
                i++;//转向下一个四元式
            }else if(Qt.get(0).get(i).equals("if")){//入口条件二 由条件语句或无条件语句能转到的语句 Qt.get(0).get(i)条件转移符号


            }else if(){//入口条件三 紧跟在条件转移语句后面的语句

            }else if(){//出口条件一 入口函数的上一句是出口函数

            }else if(){//出口条件二 由条件语句或无条件语句能转到的语句

            }else if(){//出口条件三 由条件语句或无条件语句能转到的语句

            }



        }






    }

    public static void main(String[] args) {

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

       // block.divideBlock(quaternaryExpression.Qt.get(0).size());
    }


}
