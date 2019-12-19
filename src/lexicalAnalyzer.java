import java.util.ArrayList;
import java.util.List;

public class lexicalAnalyzer {//词法分析器
   public String[] k = { "program", "void", "if", "else", "while",
           "for", "int", "char", "string","bool","float", "break", "continue",
            "return","end","true","false" };// 关键字表
   public  String[] p = {"<=",">=","==","=",">","<","&&","||",
        "+","-","*","/","{","}",
        ";","(",")",",","[","]"};//界符表
   public List<String> i = new ArrayList<String>();// 变量名
   public List<String> C = new ArrayList<String>();// 字符
   public List<String> S = new ArrayList<String>();// 字符串
   public List<String> c = new ArrayList<String>();// 数字
   public static List<String> Tokens = new ArrayList<>();//Token序列
   public static List<String> TokenType = new ArrayList<>();//Token序列类型
   public static List<Integer> TokenNum = new ArrayList<>();//Token序列标号

    /*
    *词法分析器
    * 方法说明：调用该函数直接进行词法分析
    */
    public void CharToToken(){//将字符数组扫描成Token
        fileParseUtils.txtParse();//文件读取
        List<String> text = new ArrayList<>();
        text = fileParseUtils.charArr;
        fileParseUtils.clearInfoForFile(fileParseUtils.filePath);//一进来清空之前的内容
        char ch;//每次读取的字符
        for (int i = 0; i < text.size();) {
            //遍历字符数组
            StringBuilder strToken = new StringBuilder();//Token序列
            ch = text.get(i).charAt(0);//扫描一个字符

            int tag = 0;//标志位 用来过滤空格 回车和换行

            if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {//关键词和标识符
                while ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {//加入token序列
                    strToken.append(ch);
                    i++;
                    if(i==text.size())
                    {
                        break;
                    }
                    ch = text.get(i).charAt(0);
                }
                for (int j = 0; j < k.length; j++) {//查阅关键字表

                    if (k[j].equals(strToken.toString()) && tag == 0) {//如果token与关键字表对应 就是关键字 打印 tag置1
                        Tokens.add(strToken.toString());//压入关键词表
                        TokenType.add("k");
                        TokenNum.add((j+1));
                        System.out.println("{k," + (j+1) + "}");
                        fileParseUtils.saveAsFileWriter("{k," + (j+1) + "}\n",fileParseUtils.filePath);
                        tag = 1;
                    }
                }

                //如果不是关键字
                if (this.i.size() == 0 && tag == 0) {//如果标识符表为空且tag为0
                    this.i.add(strToken.toString());
                    Tokens.add(strToken.toString());//标识符
                    TokenType.add("标识符");
                    TokenNum.add((1));
                    System.out.println("{i," + 1 + '}');
                    fileParseUtils.saveAsFileWriter("{i," + 1 + "}\n",fileParseUtils.filePath);
                    tag = 1;
                }

                if (this.i.size() != 0 && tag == 0) {
                    for (int t = 0; t < this.i.size(); t++) {

                        if (this.i.get(t).equals(strToken.toString())) {
                            tag = 1;
                            Tokens.add(strToken.toString());
                            TokenType.add("标识符");
                            TokenNum.add((t+1));
                            System.out.println("{i," + (t+1) + '}');
                            fileParseUtils.saveAsFileWriter("{i," + (t+1) + "}\n",fileParseUtils.filePath);
                            break;
                        }
                        else {
                            Tokens.add(strToken.toString());
                            TokenType.add("标识符");

                            this.i.add(strToken.toString());
                            tag = 1;
                            int put = this.i.size() - 1;
                            TokenNum.add((put+1));
                            System.out.println("{i," + (put+1) + '}' );
                            fileParseUtils.saveAsFileWriter("{i," + (put+1) +"}\n",fileParseUtils.filePath);
                            break;
                        }
                    }
                }
            }
            else if (ch >= '0' && ch <= '9') {//数字字面量
                while ((ch >= '0' && ch <= '9')|| ch == '.') {
                    strToken.append(ch);
                    i += 1;
                    if(i==text.size())
                    {
                        break;
                    }
                    ch = text.get(i).charAt(0);
                }
                if(this.c.size() == 0){
                    Tokens.add(strToken.toString());
                    TokenType.add("常数");
                    TokenNum.add((1));
                    this.c.add(strToken.toString());
                    System.out.println("{c," + 1 + '}');
                    fileParseUtils.saveAsFileWriter("{c," + 1 + "}\n",fileParseUtils.filePath);
                    tag = 1;
                }

                if(this.c.size() != 0 && tag == 0){
                    for (int t = 0; t < this.c.size(); t++) {

                        if (this.c.get(t).equals(strToken.toString()) && tag == 0) {
                            tag = 1;
                            Tokens.add(strToken.toString());
                            TokenType.add("常数");
                            TokenNum.add((t));
                            System.out.println( "{c, " + (t+1) + '}');
                            fileParseUtils.saveAsFileWriter("{i," + (t+1) + "}\n",fileParseUtils.filePath);

                            break;
                        }
                        else {
                           Tokens.add(strToken.toString());

                            this.c.add(strToken.toString());
                            tag = 1;
                            int put = this.c.size() - 1;
                            TokenType.add("常数");
                            TokenNum.add((put+1));
                            System.out.println("{c," + (put+1) + '}' );
                            fileParseUtils.saveAsFileWriter("{i," + (put+1) + "}\n",fileParseUtils.filePath);

                            break;
                        }
                    }
                }
            }
            else if(ch ==  '\''){
                //字符
            while ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch == '\'')) {//加入token序列
            strToken.append(ch);
            i += 1;
                if(i==text.size())
                {
                    break;
                }
            ch = text.get(i).charAt(0);
        }
        if (this.C.size() == 0) {//如果标识符表为空且tag为0
            Tokens.add(CommonUtils.filterChar(strToken.toString()));
            TokenType.add("字符");
            TokenNum.add((1));
            this.C.add(strToken.toString());
            System.out.println( "{C," + 1 + '}');
            fileParseUtils.saveAsFileWriter("{C," + 1 +"}\n",fileParseUtils.filePath);
            tag = 1;
        }
        if (this.C.size() != 0 && tag == 0) {
            for (int t = 0; t < this.C.size(); t++) {

                if (this.C.get(t).equals(strToken.toString()) && tag == 0) {
                    Tokens.add(CommonUtils.filterChar(strToken.toString()));
                    TokenType.add("字符");
                    TokenNum.add((t+1));
                    tag = 1;
                    System.out.println("{C," + (t+1) + '}');
                    fileParseUtils.saveAsFileWriter("{C," + (t+1) +"}\n",fileParseUtils.filePath);
                    break;
                }
                else {
                    Tokens.add(CommonUtils.filterChar(strToken.toString()));
                   this.C.add(strToken.toString());
                    tag = 1;
                    int put = this.C.size() - 1;
                    TokenType.add("字符");
                    TokenNum.add((put+1));
                    System.out.println("{C," + (put+1) + '}' );
                    fileParseUtils.saveAsFileWriter("{C," + (put+1) +"}\n",fileParseUtils.filePath);
                    break;
                }
            }
        }
    }
            else if(ch == '\"'){
            while ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch == '\"')) {//加入token序列
                strToken.append(ch);
                i += 1;
                if(i==text.size())
                {
                    break;
                }
    ch = text.get(i).charAt(0);
   // System.out.println("打印字符串");
}
                if (this.S.size() == 0) {//如果标识符表为空且tag为0
                    Tokens.add(CommonUtils.filterChar(strToken.toString()));
                    TokenType.add("c");
                    TokenNum.add((1));
                    this.S.add(strToken.toString());
                    System.out.println("{S," + 1 + '}');
                    fileParseUtils.saveAsFileWriter("{C," + 1 + "}\n",fileParseUtils.filePath);

                        tag = 1;
                        }
                        if (this.S.size() != 0 && tag == 0) {
                        for (int t = 0; t < this.S.size(); t++) {

        if (this.S.get(t).equals(strToken.toString()) && tag == 0) {
           Tokens.add(CommonUtils.filterChar(strToken.toString()));
            TokenType.add("S");
            TokenNum.add((1));
          tag = 1;
            System.out.println("{S," + (t+1) + '}');
            fileParseUtils.saveAsFileWriter("{S," + (t+1) + '}',fileParseUtils.filePath);
        break;
        }
        else {
            Tokens.add(CommonUtils.filterChar(strToken.toString()));
            this.S.add(strToken.toString());
        tag = 1;
        int put = this.S.size() - 1;
            TokenType.add("S");
            TokenNum.add((put+1));
            System.out.println("{S," + (put+1) + '}' );
            fileParseUtils.saveAsFileWriter("{S," + (put+1) + "}\n",fileParseUtils.filePath);
        break;
          }
        }
        }
           }
            else if((ch >= '!' && ch <= '/')||(ch >= ':' && ch <= '@')|| (ch >= '['&&ch<='`')|| (ch>='{')&&(ch<='}')){
             //界符
                while(String.valueOf(ch).equals(">")||String.valueOf(ch).equals("=")||String.valueOf(ch).equals("<")){
                    //System.out.println(1);
                    strToken.append(ch);
                    i++;
                    if(i==text.size())
                    {
                        break;
                    }
                    ch = text.get(i).charAt(0);
                }

                for (int d = 0; d < this.p.length; d++) {//遍历界符表
                    if (p[d].equals(String.valueOf(ch))) {
                        Tokens.add(String.valueOf(ch));
                        TokenType.add("p");
                        TokenNum.add((d+1));
                        System.out.println("{p," + (d+1) + '}');
                        fileParseUtils.saveAsFileWriter("{p," + (d+1) + "}\n",fileParseUtils.filePath);
                        i++;
                        tag = 1;
                        break;
                    }
                    if (p[d].equals(strToken.toString())) {
                        Tokens.add(strToken.toString());
                        TokenType.add("p");
                        TokenNum.add((d+1));
                        System.out.println("{p," + (d+1) + '}');
                        fileParseUtils.saveAsFileWriter("{p," + (d+1) + "}\n",fileParseUtils.filePath);
                        tag = 1;
                        break;
                    }
                }
            }


        if (tag == 0) {//如果tag为0是空格 回车 换行 需要过滤
                  i++;
              }
        }
    }

    public static void showTokens()
    {
        for (int i = 0; i < lexicalAnalyzer.Tokens.size();i++){
            System.out.println(lexicalAnalyzer.Tokens.get(i));
            System.out.println(lexicalAnalyzer.TokenNum.get(i));
            System.out.println(lexicalAnalyzer.TokenType.get(i));
            System.out.println("-----------------");
        }
    }


    public static void main(String[] args) {//主函数 测试
        /*lexicalAnalyzer l = new lexicalAnalyzer();
        fileParseUtils.txtParse();
        l.CharToToken(fileParseUtils.charArr);
       // System.out.println(lexicalAnalyzer.TokenType.get(1));
        showTokens();*/
    }
}
