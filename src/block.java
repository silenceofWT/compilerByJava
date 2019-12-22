import java.util.ArrayList;
import java.util.List;

/*
* 基本块类
* 功能：根据四元式序列划分基本块
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
    /*
    * [1,7] 1为入口 7为出口 1~7为一个基本块
    * [8,9]
    *
    * */
    public static List<List<Integer>> blockList =new ArrayList<List<Integer>>();
    public static List<Integer> inNum = new ArrayList<>();//入口函数标号
    /*
    * 基本块划分函数
    * */
    public static void divideBlock(List<List<String>> Qt){

        for(int i = 0;i < Qt.get(0).size();i++){
            //遍历四元式序列 Qt.get(0).size()为四元式的数量  找出所有入口标号
            //i+1相当于四元式的标号
            if (i == 0){//如果为第一个四元式 那么添加进blockList的入口标号中
                inNum.add(i);
            }else if(Qt.get(0).get(i).equals("if")){//入口条件二 由条件语句或无条件语句能转到的语句 Qt.get(0).get(i)条件转移符号
                inNum.add(i + 1);
            }else if(Qt.get(0).get(i).equals("wh")){//入口条件二 由条件语句或无条件语句能转到的语句 Qt.get(0).get(i)条件转移符号
                inNum.add(i);
            }else if(Qt.get(0).get(i).equals("ie")){
                inNum.add(i);
            }else if(Qt.get(0).get(i).equals("we")){
                inNum.add(i);
            }else if(Qt.get(0).get(i).equals("el")){
                inNum.add(i + 1);
            }else if(Qt.get(0).get(i).equals("do")){
                inNum.add(i+1);
            }
        }
        for(int i = 0;i < inNum.size();i++){
            List<Integer> aBlock = new ArrayList<>();//一个基本块属性
            if(i < inNum.size() -1){
                if(inNum.get(i)!=inNum.get(i+1)) {
                    aBlock.add(inNum.get(i));
                    aBlock.add(inNum.get(i+1)-1);
                    blockList.add(aBlock);
                    System.out.println(aBlock);
                }

            }else if(i == inNum.size() - 1){
                aBlock.add(inNum.get(i));
                aBlock.add(inNum.get(i));
                blockList.add(aBlock);
                System.out.println(aBlock);
            }
            aBlock = null;
        }

        if(blockList.size()==1)
        {
            List<Integer> aBlock = new ArrayList<>();//一个基本块属性
            aBlock.add(0);
            aBlock.add(quaternaryExpression.Qt_0.size()-1);
            blockList.clear();
            blockList.add(aBlock);

        }
    }

    public static void main(String[] args) {

        //词法分析
        lexicalAnalyzer l = new lexicalAnalyzer();//新建词法分析对象
        fileParseUtils.txtParse();//解析txt文件
        l.CharToToken();//将txt文件内容解析成Token
        lexicalAnalyzer.showTokens();//显示生成的Token属性
        quaternaryExpression.init();//初始化四元式形式
        parser.analyzer();//语法分析
        quaternaryExpression.show();//显示生成的四元式
        quaternaryExpression.writeToTxt();//将生成的四元式写入txt
        block.divideBlock(quaternaryExpression.Qt);
        System.out.println("-----------------------");
        System.out.println(block.inNum);
        System.out.println(block.blockList);
    }


}
