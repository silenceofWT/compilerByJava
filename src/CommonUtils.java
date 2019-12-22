import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
* 公共组件类
* 功能：放置一些所有类中可以使用到的公共工具
* */
public class CommonUtils {
    /*字符串解析
    * 功能：去掉字符和字符串的引号
    * 举例："hello"经过解析可以输出hello
    * */
    public static String filterChar(String str){
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\"]";
        Pattern p   =   Pattern.compile(regEx);
        Matcher m   =   p.matcher(str);
        return   m.replaceAll("").trim();
    }

    public static void main(String[] args) {//测试
    //System.out.println(CommonUtils.filterChar("hello"));



    }
}