// Wrnet.java
// ネットワーク上のサーバーに接続しデータを送る
// その後サーバからデータを受け取りそのまま画面に出力
// $ java Wrnet DNS_name port_num

import java.io.*;
import java.net.*;

public class Wrnet{
    public static void main(String[] args){
        byte[] buff = new byte[1024];
        Socket wrsocket = null;//サーバ接続用のソケット
        InputStream instr = null;//データ読み取り用オブジェクト
        OutputStream outstr = null;//データ出力用オブジェクト
        boolean cont = true;
        //指定のポートに対してソケットを作成
        //入出力のストリームを作りデータを読みだしを準備します
        try{
            wrsocket = new Socket(args[0],Integer.parseInt(args[1]));
            instr = wrsocket.getInputStream();
            outstr = wrsocket.getOutputStream();
        }catch(Exception e){
            System.err.println("ネットワークエラー");
            System.exit(1);
        }
        while(cont){
            try{
                //System.inからの読み出し
                int n = System.in.read(buff);
                //System.outへの書き出し
                System.out.write(buff,0,n);
                //行頭ピリオドの検出
                if(buff[0] == '.')cont = false;
                else outstr.write(buff,0,n);
            }catch(Exception e){
                System.exit(1);
            }
        }
        cont = true;
        while(cont){
            try{
                //読み込み
                int n = instr.read(buff);
                //System.outへの書き出し
                System.out.write(buff,0,n);
            }catch(Exception e){
                cont = false;
            }
        }
        //コネクションを閉じる
        try{
            instr.close();
        }catch(Exception e){
            System.err.println("ネットワークエラー");
            System.exit(1);
        }
    }
}