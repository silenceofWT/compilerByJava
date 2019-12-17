import java.util.*;

public class parser {//进行语法和语义的分析

    public static Map<String,Map<String,String>>table= new HashMap<String,Map<String,String>>();
    public static List<String>Vn=new ArrayList<>();
    public static List<String>Vt=new ArrayList<>();

    public static void iniTable()
    {

        Map program=new HashMap();
        program.put("program","<program>{<函数主体>}<end>");
        /*Map external_declaration=new HashMap();
        external_declaration.put("void","<函数定义>");external_declaration.put("char","<函数定义>");external_declaration.put("int","<函数定义>");external_declaration.put("float","<函数定义>");
        Map function_definition=new HashMap();
        function_definition.put("void","<函数返回值类型><函数声明>{<函数主体>}"); function_definition.put("char","<函数返回值类型><函数声明>{<函数主体>}");
        function_definition.put("int","<函数返回值类型><函数声明>{<函数主体>}"); function_definition.put("float","<函数返回值类型><函数声明>{<函数主体>}");
        Map type_specifier	=new HashMap();
        type_specifier.put("void","<void>");type_specifier.put("char","<char>");type_specifier.put("int","<int>");type_specifier.put("float","<float>");
        Map declaratory	=new HashMap();
        declaratory.put("标识符","<标识符>(<参数列表>)");
        Map parameter_list	=new HashMap();
        parameter_list.put("char","<参数声明>，<参数列表>");parameter_list.put("int","<参数声明>，<参数列表>");parameter_list.put("float","<参数声明>，<参数列表>");
        parameter_list.put(")","");
        Map parameter_declaration	=new HashMap();
        parameter_declaration.put("char","<数据类型><标识符>");parameter_declaration.put("int","<数据类型><标识符>");parameter_declaration.put("float","<数据类型><标识符>");
        */
        Map function_body=new HashMap();
        function_body.put("标识符","<变量声明列表><语句列表>");function_body.put("char","<变量声明列表><语句列表>");function_body.put("int","<变量声明列表><语句列表>");
        function_body.put("float","<变量声明列表><语句列表>");function_body.put("}","<变量声明列表><语句列表>");
        Map variable_declaration_list=new HashMap();
        variable_declaration_list.put("标识符","");variable_declaration_list.put("}","");
        variable_declaration_list.put("char","<变量声明><变量声明列表>");variable_declaration_list.put("int","<变量声明><变量声明列表>");variable_declaration_list.put("float","<变量声明><变量声明列表>");
        Map variable_declaration=new HashMap();
        variable_declaration.put("char","<数据类型><标识符>;");variable_declaration.put("int","<数据类型><标识符>;");variable_declaration.put("float","<数据类型><标识符>;");
        Map data_type=new HashMap();
        data_type.put("char","<char>");data_type.put("int","<int>");data_type.put("float","<float>");
        Map statement_list=new HashMap();
        statement_list.put("}","");statement_list.put("标识符","<语句><语句列表>");
        Map statement=new HashMap();
        statement.put("标识符","<赋值表达式>;");
        Map assignment_expression=new HashMap();
        assignment_expression.put("标识符","<标识符>[PUSH(标识符)]=<基本表达式>[ASSI(=)]");
        Map basic_expression=new HashMap();
        basic_expression.put("标识符","<标识符>[PUSH(标识符)]");basic_expression.put("常数","<常数>[PUSH(常数)]");basic_expression.put("字符","<字符>[PUSH(字符)]");

        table.put("程序",program);
        /*table.put("外部声明",external_declaration);
        table.put("函数定义",function_definition);
        table.put("函数返回值类型",type_specifier);
        table.put("函数声明",declaratory);
        table.put("参数列表",parameter_list);
        table.put("参数声明",parameter_declaration);*/
        table.put("函数主体",function_body);
        table.put("变量声明列表",variable_declaration_list);
        table.put("变量声明",variable_declaration);
        table.put("数据类型",data_type);
        table.put("语句列表",statement_list);
        table.put("语句",statement);
        table.put("赋值表达式",assignment_expression);
        table.put("基本表达式",basic_expression);

        System.out.println();
        System.out.println(" Map Elements");
        System.out.println("\t" + table);

        Vt.add("标识符");Vt.add("常数");Vt.add("字符");Vt.add("program");Vt.add("int");Vt.add("float");
        Vt.add("char");Vt.add("(");Vt.add(")");Vt.add("{");Vt.add("}");Vt.add("=");Vt.add(";");Vt.add("end");

        Vn.add("程序");//Vn.add("外部声明");Vn.add("函数定义");Vn.add("函数返回值类型");Vn.add("函数声明");Vn.add("参数列表");Vn.add("参数声明");
        Vn.add("函数主体");Vn.add("变量声明列表");Vn.add("变量声明");
        Vn.add("数据类型");Vn.add("语句列表");Vn.add("语句");Vn.add("赋值表达式");Vn.add("基本表达式");

    }

    public static void analyzer()
    {
        Stack<String>sq=new Stack<>();//分析栈
        //stack<String>SEM;//语义栈
        //sq.push("#");
        sq.push("程序");
        iniTable();
        int tag = 0;
        while (!sq.empty())
        {
            String b=lexicalAnalyzer.TokenType.get(tag);
            if(b.equals("k")||b.equals("p"))
            {
                b=lexicalAnalyzer.Tokens.get(tag);
            }
            if (Vn.contains(sq.peek()) ) //非终结符号
            {
                String a=sq.peek();
                if (Vt.contains(b))//判断是否在分析表内，a已在上一个if判断过
                {
                    if(table.get(a).get(b)==null)
                    {
                        System.out.println("没有可以匹配的规则，不能识别！出错！");
                        return ;
                    }
                    System.out.println(sq.peek()+"出栈");
                    System.out.println(b+" input");
                    sq.pop();
                    System.out.println(table.get(a).get(b));
                    String[] temp=table.get(a).get(b).split("<|>|\\[|]" );
                    for (int t = temp.length - 1; t > 0; t--)
                    {
                        if(temp[t].length()!=0)
                        {
                            System.out.println(temp[t]+" 入栈");
                            sq.push(temp[t]);
                        }
                    }
                }
                else
                {
                    System.out.println("读取的单词不合法！");
                    return ;
                }
            }
            else if(Vt.contains(sq.peek()))//终结符号
            {
                if (sq.peek().equals(b))
                {
                    System.out.println("识别 "+sq.peek()+" ，退栈");
                    tag++;

                    sq.pop();
                }
                else
                {
                    System.out.println("不能识别"+sq.peek()+"！出错！");
                    return ;
                }
            }
            else//动作符号
            {
                System.out.println("动作符号aa|"+sq.peek()+"|aa");
                String[] temp=sq.peek().split("\\(|\\)");
                if(temp[0].equals("ASSI"))
                {
                    quaternaryExpression.produceQE(sq.peek());
                    System.out.println(sq.peek());
                    sq.pop();
                }
                else if(temp[0].equals("PUSH"))
                {
                    quaternaryExpression.produceQE("PUSH("+lexicalAnalyzer.Tokens.get(tag-1)+")");
                    System.out.println(tag+"|PUSH("+lexicalAnalyzer.Tokens.get(tag-1)+")|"+tag);
                    sq.pop();
                }
                else
                {
                    System.out.println("cut");
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
        lexicalAnalyzer l = new lexicalAnalyzer();
        fileParseUtils.txtParse();
        l.CharToToken(fileParseUtils.charArr);
        lexicalAnalyzer.showTokens();
        quaternaryExpression qE=new quaternaryExpression();
        qE.init();

        parser.analyzer();

        qE.show();

    }

}



