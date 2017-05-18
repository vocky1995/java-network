// Writefile.java
// Keyboardからの入力を受け取りそのまま画面に出力
// ファイルに入力を順に格納
// "."入力で終了
// $ java Writefile fileName

import java.io.*;

//Writefile class
public class Writefile{
    public static void main(String[] args){
        byte[] buff = new byte[1024];
        boolean cont = true;
        FileOutputStream outfile = null;//ファイル出力用オブジェクト
        try{
            outfile = new FileOutputStream(args[0]);
        }
        catch(FileNotFoundException e){
            System.err.println("File Not Found");
            System.exit(1);
        }
        while(cont){
            try{
                //System.inからの読み込み
                int n = System.in.read(buff);
                //System.outへの書き出し
                System.out.write(buff,0,n);
                //行頭ピリオドの検出
                if(buff[0] == '.')cont = false;
                else outfile.write(buff,0,n);
            }
            //例外処理
            catch(Exception e){
                System.exit(1);
            }
        }
        //ファイルを閉じる
        try{
            outfile.close();
        }
        catch(IOException e){
            //ファイルクローズ失敗
            System.err.println("ファイルのエラー");
            System.exit(1);
        }
    }
}
