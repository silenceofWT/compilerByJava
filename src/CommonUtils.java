import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
    public static String filterChar(String str){
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\"]";
        Pattern p   =   Pattern.compile(regEx);
        Matcher m   =   p.matcher(str);
        return   m.replaceAll("").trim();
    }

    public static void main(String[] args) {
    //System.out.println(CommonUtils.filterChar("hello"));



    }
}