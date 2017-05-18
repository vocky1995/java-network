// Readnet.java
// ネットワーク上のサーバからデータを受け取りそのんまま画面に出力
// $ java Readnet DNS_name port_number

import java.io.*;
import java.net.*;

//Readnet class
public class Readnet{
    public static void main(String[] args){
        byte[] buff = new byte[1024];
        Socket readsocket = null;//サーバ接続用ソケット
        InputStream instr = null;//データ読み取り用オブジェクト
        boolean cont = true;
        //指定のポートに対してソケットを作成
        //オブジェクトinstrを作りデータ読み出しを準備
        try{
            readsocket = new Socket(args[0],Integer.parseInt(args[1]));
            instr = readsocket.getInputStream();
        }
        catch(Exception e){
            System.err.println("ネットワークエラー");
            System.exit(1);
        }
        while(cont){
            try{
                //読み込み
                int n = instr.read(buff);
                //System.outへ書き出し
                System.out.write(buff,0,n);
            }
            catch(Exception e){
                cont = false;
            }
        }
        //コネクションを閉じる
        try{
            instr.close();
        }
        catch(Exception e){
            System.err.println("ネットワークのエラーです");
            System.exit(1);
        }
    }
}