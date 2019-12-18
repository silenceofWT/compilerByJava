
public class Main {//主函数
    public static void main(String[] args)
    {
        lexicalAnalyzer l = new lexicalAnalyzer();
        l.CharToToken();//词法分析
        lexicalAnalyzer.showTokens();
        quaternaryExpression.init();

        parser.analyzer();//语法分析
        parser.tb.show();
        quaternaryExpression.writeToTxt();//将四元式打印到txt
        quaternaryExpression.show();//打印四元式
    }
}
