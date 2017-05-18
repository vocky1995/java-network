// Readfile.java
// ファイルの内容を読み取りそのまま画面に出力する
// $ java Readfile filename

import java.io.*;

//Readfileクラス
public class Readfile{
    public static void main(String[] args){
        byte[] buff = new byte[1024];
        boolean cont = true;
        FileInputStream infile = null;//ファイル読み取り用
        //オブジェクトinfileを作成しファイル読み出しを準備する
        try{
            infile = new FileInputStream(args[0]);
        }catch(FileNotFoundException e){
            System.err.println("ファイルがありません");
            System.exit(1);
        }
        //ファイルの終了まで以下のループ
        while(cont){
            try{
                //ファイルからの読み込み
                int n = infile.read(buff);
                //System.out.write(buff,0,n)へ書き出し
                System.out.write(buff,0,n);
            }
            catch(Exception e){
                cont = false;
            }
        }
        //ファイルを閉じる
        try{
            infile.close();
        }
        catch(IOException e){
            System.err.println("ファイルのエラーです");
            System.exit(1);
        }
    }
}