# compilerByJava
该项目是使用java实现的简易编译器。

### 文件说明：
- inputFile文件夹 存储源代码的输入文件

 1.inputCode 为检测的源代码。


- outputFile文件夹 存储运行过程中输出的结果

1. Tokens 会输出Token序列
```c
int main(){
    int a=1;
}
``` 
输出来的结果为：["int","main","(",")","{","int","a","=","1",";","}"];是一个字符串数组。


