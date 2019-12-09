import java.io.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class fileParseUtils {//txt读取插件
    public static final String fileName = "F:\\javaProject\\compilerByJava\\src\\inputFile\\inputCode.txt";
    public static List<String> charArr = new ArrayList<String>();// 字符数组
    public static String filePath = "F:\\javaProject\\compilerByJava\\src\\outputFile\\Tokens.txt";
    /*文件清空*/
    public static void clearInfoForFile(String fileName) {
        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    * 文件写入
    * */
    public static void saveAsFileWriter(String content) {
        FileWriter fWriter = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fWriter = new FileWriter(filePath,true);
            fWriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fWriter.flush();
                fWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    /*
    txt解析
    功能：将txt内容解析成字符数组
    */

    public static void txtParse(){
        //读取txt文件 将txt的内容放入字符数组
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "GBK")); //这里可以控制编码
            sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String s = new String(sb); //StringBuffer ==> String
        charArr = Arrays.asList(s.split(""));

    }

    public static void main(String[] args) {

    }



}
