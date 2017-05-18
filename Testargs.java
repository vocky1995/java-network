// Testargs.java
// コマンドへの引数を画面に表示する
// $java Testarg 123 abc what the fu*k

import java.io.*;
public class Testargs{
    public static void main(String[] args){
        //メッセージの出力
        int number = 0;
        while(number < args.length){
            System.out.println(args[number++]);
        }
    }
}