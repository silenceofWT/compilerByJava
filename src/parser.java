import java.util.*;

public class parser {//进行语法和语义的分析

    public static Map<String,Map<String,String>>table= new HashMap<>();
    public static List<String>Vn=new ArrayList<>();
    public static List<String>Vt=new ArrayList<>();
    public static table tb=new table();//建立全局符号表

    public static final int sizeofint = 1;
    public static final int sizeoffloat = 2;
    public static final int sizeofchar = 4;
    public static final int sizeofbool = 1;

    public static void iniTable()
    {

        Map<String,String> program=new HashMap<>();
        program.put("program","#program#{#函数主体#}#end#");
        Map<String,String> function_body=new HashMap<>();
        function_body.put("标识符","#变量声明列表##语句列表#");function_body.put("char","#变量声明列表##语句列表#");function_body.put("int","#变量声明列表##语句列表#");
        function_body.put("float","#变量声明列表##语句列表#");function_body.put("bool","#变量声明列表##语句列表#");function_body.put("}","#变量声明列表##语句列表#");
        function_body.put("if","#变量声明列表##语句列表#");function_body.put("while","#变量声明列表##语句列表#");
        Map<String,String> variable_declaration_list=new HashMap<>();
        variable_declaration_list.put("标识符","");variable_declaration_list.put("}","");
        variable_declaration_list.put("char","#变量声明##变量声明列表#");variable_declaration_list.put("int","#变量声明##变量声明列表#");variable_declaration_list.put("float","#变量声明##变量声明列表#");
        variable_declaration_list.put("bool","#变量声明##变量声明列表#");variable_declaration_list.put("if","");variable_declaration_list.put("while","");
        Map<String,String> variable_declaration=new HashMap<>();
        variable_declaration.put("char","#数据类型##标识符#;");variable_declaration.put("int","#数据类型##标识符#;");variable_declaration.put("float","#数据类型##标识符#;");
        variable_declaration.put("bool","#数据类型##标识符#;");
        Map<String,String> data_type=new HashMap<>();
        data_type.put("char","#char#");data_type.put("int","#int#");
        data_type.put("float","#float#");data_type.put("bool","#bool#");
        Map<String,String> statement_list=new HashMap<>();
        statement_list.put("}","");statement_list.put("标识符","#语句##语句列表#");statement_list.put("if","#语句##语句列表#");statement_list.put("while","#语句##语句列表#");
        Map<String,String> statement=new HashMap<>();
        statement.put("标识符","#赋值语句#;");statement.put("if","#if_else语句#");statement.put("while","#while语句#");
        Map<String,String> assignment_statement=new HashMap<>();
        assignment_statement.put("标识符","#标识符#[PUSH(标识符)]=#基本表达式#[ASSI(=)]");
        Map<String,String> basic_expression=new HashMap<>();
        basic_expression.put("标识符","#算术表达式#");basic_expression.put("常数","#算术表达式#");basic_expression.put("字符","#字符#[PUSH(字符)]");
        basic_expression.put("true","#true#[PUSH(true)]");basic_expression.put("false","#false#[PUSH(false)]");

        //while循环
        Map<String,String> while_statement = new HashMap<>();
        while_statement.put("while","#while#[WH()]#(##比较表达式##)#[DO(do)]#{##语句列表##}#[WE(we)]");
        //if_else
        Map<String,String> if_else_statement=new HashMap<>();
        if_else_statement.put("if","#if##(##比较表达式##)#[IF(if)]#{##语句列表##}##else#[EL(el)]#{##语句列表##}#[IE(ie)]");
        Map<String,String> compare_expression=new HashMap<>();
        compare_expression.put("标识符","#比较项##比较符号##比较项#[COMP(OP)]");compare_expression.put("常数","#比较项##比较符号##比较项#[COMP(OP)]");
        compare_expression.put("字符","#比较项##比较符号##比较项#[COMP(OP)]");compare_expression.put("(","#比较项##比较符号##比较项#[COMP(OP)]");
        Map<String,String> compare_item=new HashMap<>();
        compare_item.put("标识符","#算术表达式#");compare_item.put("常数","#算术表达式#");compare_item.put("(","#算术表达式#");
        compare_item.put("字符","#字符#[PUSH(字符)]");
        Map<String,String> compare_character=new HashMap<>();
        compare_character.put(">","#>#");compare_character.put("<","#<#");compare_character.put("==","#==#");
        compare_character.put(">=","#>=#");compare_character.put("!=","#!=#");compare_character.put("<=","#<=#");

        Map<String,String> arithmetic_expression=new HashMap<>();
        arithmetic_expression.put("标识符","#算术表达式T##算术表达式X#");arithmetic_expression.put("常数","#算术表达式T##算术表达式X#");arithmetic_expression.put("(","#算术表达式T##算术表达式X#");
        Map<String,String> arithmetic_expressionX=new HashMap<>();
        arithmetic_expressionX.put("+","#+##算术表达式T#[GEQ(+)]#算术表达式X#");arithmetic_expressionX.put("-","#-##算术表达式T#[GEQ(-)]#算术表达式X#");
        arithmetic_expressionX.put(")","");arithmetic_expressionX.put(";","");arithmetic_expressionX.put(">","");arithmetic_expressionX.put("<","");
        arithmetic_expressionX.put("==","");arithmetic_expressionX.put(">=","");arithmetic_expressionX.put("<=","");arithmetic_expressionX.put("!=","");
        Map<String,String> arithmetic_expressionT=new HashMap<>();
        arithmetic_expressionT.put("标识符","#算术表达式F##算术表达式Y#");arithmetic_expressionT.put("常数","#算术表达式F##算术表达式Y#");arithmetic_expressionT.put("(","#算术表达式F##算术表达式Y#");
        Map<String,String> arithmetic_expressionY=new HashMap<>();
        arithmetic_expressionY.put("*","#*##算术表达式F#[GEQ(*)]#算术表达式Y#");arithmetic_expressionY.put("/","#/##算术表达式F#[GEQ(/)]#算术表达式Y#");
        arithmetic_expressionY.put(")","");arithmetic_expressionY.put("+","");arithmetic_expressionY.put("-","");arithmetic_expressionY.put(";","");
        arithmetic_expressionY.put(">","");arithmetic_expressionY.put("<","");arithmetic_expressionY.put("==","");
        arithmetic_expressionY.put(">=","");arithmetic_expressionY.put("<=","");arithmetic_expressionY.put("!=","");
        Map<String,String> arithmetic_expressionF=new HashMap<>();
        arithmetic_expressionF.put("标识符","#终结符I#");arithmetic_expressionF.put("常数","#终结符I#");arithmetic_expressionF.put("(","(#算术表达式#)");
        Map<String,String> terminalI=new HashMap<>();
        terminalI.put("标识符","#标识符#[PUSH(标识符)]");terminalI.put("常数","#常数#[PUSH(常数)]");

        table.put("程序",program);
        table.put("函数主体",function_body);
        table.put("变量声明列表",variable_declaration_list);
        table.put("变量声明",variable_declaration);
        table.put("数据类型",data_type);
        table.put("语句列表",statement_list);
        table.put("语句",statement);
        table.put("赋值语句",assignment_statement);
        table.put("基本表达式",basic_expression);
        table.put("if_else语句",if_else_statement);
        table.put("while语句",while_statement);//while语句
        table.put("比较表达式",compare_expression);
        table.put("比较项",compare_item);
        table.put("比较符号",compare_character);
        table.put("算术表达式",arithmetic_expression);
        table.put("算术表达式X",arithmetic_expressionX);
        table.put("算术表达式T",arithmetic_expressionT);
        table.put("算术表达式Y",arithmetic_expressionY);
        table.put("算术表达式F",arithmetic_expressionF);
        table.put("终结符I",terminalI);

        System.out.println();
        System.out.println(" Map Elements");
        System.out.println("\t" + table);

        Vt.add("标识符");Vt.add("常数");Vt.add("字符");Vt.add("program");Vt.add("int");Vt.add("float");
        Vt.add("char");Vt.add("bool");Vt.add("(");Vt.add(")");Vt.add("{");Vt.add("}");Vt.add("=");Vt.add(";");
        Vt.add("+");Vt.add("-");Vt.add("*");Vt.add("/");Vt.add("if");Vt.add("else");Vt.add("while");Vt.add(">");Vt.add("==");
        Vt.add("end");Vt.add("<");Vt.add(">=");Vt.add("!=");Vt.add("<=");Vt.add("true");Vt.add("false");

        Vn.add("程序");
        Vn.add("函数主体");Vn.add("变量声明列表");Vn.add("变量声明");
        Vn.add("数据类型");Vn.add("语句列表");Vn.add("语句");Vn.add("赋值语句");Vn.add("基本表达式");
        Vn.add("if_else语句");Vn.add("while语句");Vn.add("比较表达式");Vn.add("比较项");Vn.add("比较符号");
        Vn.add("算术表达式");Vn.add("算术表达式X");Vn.add("算术表达式T");Vn.add("算术表达式Y");Vn.add("算术表达式F");
        Vn.add("终结符I");

    }

    public static void analyzer()
    {
        Stack<String>sq=new Stack<>();//分析栈
        sq.push("程序");
        iniTable();
        int tag = 0;

        int no_of_i=0;//记录符号表中填入的符号数
        int type=-1;//暂存待填符号type
        boolean flag_addi=false;//标示是否需要填符号表

        while (!sq.empty())
        {
            String b=lexicalAnalyzer.TokenType.get(tag);
            if(b.equals("k")||b.equals("p"))
            {
                b=lexicalAnalyzer.Tokens.get(tag);
            }

            else if(b.equals("标识符")&&flag_addi)//若为标识符，则看是否需要填符号表
            {
                System.out.println("填符号表");
                table.iinfor iinfor_new=tb.new iinfor(lexicalAnalyzer.Tokens.get(tag),type,"v",no_of_i);
                tb.synbl.add(iinfor_new);
                no_of_i++;
                flag_addi=false;
            }

            if (Vn.contains(sq.peek()) ) //非终结符号
            {
                String a=sq.peek();
                if (Vt.contains(b))//判断是否在分析表内，a已在上一个if判断过
                {
                    if(table.get(a).get(b)==null)
                    {
                        System.out.println(a+b);
                        System.out.println("没有可以匹配的规则，不能识别！出错！");
                        return ;
                    }
                    System.out.println(sq.peek()+"出栈");
                    System.out.println(b+" input");
                    sq.pop();
                    System.out.println(table.get(a).get(b));
                    String[] temp=table.get(a).get(b).split("#|\\[|]" );

                    //填符号表相关操作
                    if(table.get(a).get(b).equals("#int#"))
                    {
                        type=0;
                        tb.lenl.add(sizeofint);
                        flag_addi=true;
                    }
                    else if(table.get(a).get(b).equals("#float#"))
                    {
                        type=1;
                        tb.lenl.add(sizeoffloat);
                        flag_addi=true;
                    }
                    else if(table.get(a).get(b).equals("#char#"))
                    {
                        type=2;
                        tb.lenl.add(sizeofchar);
                        flag_addi=true;
                    }
                    else if(table.get(a).get(b).equals("#bool#"))
                    {
                        type=3;
                        tb.lenl.add(sizeofbool);
                        flag_addi=true;
                    }
                    //填符号表相关操作结束

                    for (int t = temp.length - 1; t >= 0; t--)
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
                    System.out.println("不能识别"+sq.peek()+b+"！出错！");
                    return ;
                }
            }
            else//动作符号
            {
                System.out.println("动作符号aa|"+sq.peek()+"|aa");
                String[] temp=sq.peek().split("\\(|\\)");
                if(temp[0].equals("ASSI")||temp[0].equals("IF")||temp[0].equals("EL")||temp[0].equals("IE")||temp[0].equals("WH")||temp[0].equals("DO")||temp[0].equals("WE"))
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
                else if(temp[0].equals("GEQ"))
                {
                    quaternaryExpression.produceQE("GEQ("+temp[1]+")");
                    System.out.println(tag+"|GEQ("+temp[1]+")|"+tag);
                    sq.pop();
                }
                else if(temp[0].equals("COMP"))
                {
                    String OP;
                    for(int j=tag;;j--)//向前查找最近的比较符
                    {
                        if(lexicalAnalyzer.Tokens.get(j-1).equals(">")||lexicalAnalyzer.Tokens.get(j-1).equals("<")||lexicalAnalyzer.Tokens.get(j-1).equals("==")||
                                lexicalAnalyzer.Tokens.get(j-1).equals(">=")||lexicalAnalyzer.Tokens.get(j-1).equals("<=")||lexicalAnalyzer.Tokens.get(j-1).equals("!="))
                        {
                            OP=lexicalAnalyzer.Tokens.get(j-1);
                            break;
                        }
                    }
                    quaternaryExpression.produceQE("COMP("+OP+")");
                    System.out.println(tag+"|COMP("+OP+")|"+tag);
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


    }

}



