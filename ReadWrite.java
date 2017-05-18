// ReadWrite.java
// Keyboardからの入力を受け取りそのまま画面に出力する
// このプログラムを終了するにはCtrl+C

import java.io.*;

//ReadWrite class
public class ReadWrite{
    public static void main(String[] args){
        byte[] buff = new byte[1024];
        while(true){
            try{
                //System.inから読み込み
                int n = System.in.read(buff);
                //System.outへの書き出し
                System.out.write(buff,0,n);
            }
            //例外処理
            catch(Exception e){
                System.exit(1);
            }
        }
    }
}