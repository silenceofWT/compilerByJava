
public class Main {//主函数
    public static void main(String[] args)
    {
        lexicalAnalyzer l = new lexicalAnalyzer();
        fileParseUtils.txtParse();//文件读取
        l.CharToToken(fileParseUtils.charArr);//词法分析
        lexicalAnalyzer.showTokens();
        quaternaryExpression.init();

        parser.analyzer();//语法分析
        parser.tb.show();

        quaternaryExpression.show();//打印四元式
    }
}
