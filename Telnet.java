// tenlnet
// このプログラムは指定されたアドレスのポートに標準入出力を接続する
// 接続先がtelnetのポート(23番)の場合, ネゴシエーションを行う
// ネゴシエーションではサーバからの要求を全て断る
// 使い方: java telnet サーバアドレス ポート番号
//        ポート番号はデフォルトで23番
// 終了はCtrl+C

import java.net.*;
import java.io.*;


//telnet クラス
//ネットワーク接続の管理を行う
//StreamConnectorクラスを用いてスレッド処理を行う
public class Telnet{
    Socket serverSocket;//接続用ソケット
    public OutputStream serverOutput;//ネットワーク出力用ストリーム
    public BufferedInputStream serverInput;//ネットワーク入力用ストリーム
    String host;//接続用サーバアドレス
    int port;//接続先サーバポート番号

    static final int DEFAULT_TELNET_PORT = 23;//telnetのポート番号23番

    //コンストラクタアドレスとポートの指定がある場合
    public Telnet(String host,int port){
        this.host = host;
        this.port = port;
    }
    //コンストラクタアドレスのみの指定
    public Telnet(String host){
        this(host,DEFAULT_TELNET_PORT);//telnetポートを仮定
    }

    //OpenConnectionメソッド
    //アドレスとポート番号からソケットを作りストリームを作成
    public void openConnection() throws IOException,UnknownHostException{
        serverSocket = new Socket(host,port);
        serverOutput = serverSocket.getOutputStream();
        serverInput = new BufferedInputStream(serverSocket.getInputStream());
        //接続先がtelnetポートならばネゴシエーションを行う
        if(port == DEFAULT_TELNET_PORT){
            negotiation(serverInput,serverOutput);
        }
    }

    // main_procメソッド
    // ネットワークとのやり取りをするスレッドをスタートさせる
    public void main_proc() throws IOException{
        try{
            //スレッド用クラスStreamConnectorのオブジェクトを生成
            StreamConnector stdin_to_socket = new StreamConnector(System.in,serverOutput);
            StreamConnector socket_to_stdout = new StreamConnector(serverInput,System.out);
            //スレッドを生成する
            Thread input_thread = new Thread(stdin_to_socket);
            Thread output_thread = new Thread(socket_to_stdout);
            //スレッドを起動する
            input_thread.start();
            output_thread.start();
        }catch(Exception e){
            System.err.print(e);
            System.exit(1);
        }
    }
    //ネゴシエーションに用いるコマンドの定義
    static final byte IAC = (byte)255;
    static final byte DONT = (byte)254;
    static final byte DO = (byte)253;
    static final byte WONT = (byte)252;
    static final byte WILL = (byte)251;

    //negotiation メソッド
    //NVTによる通信をネゴシエートする
    static void negotiation(BufferedInputStream in,OutputStream out) throws IOException{
        byte[] buff = new byte[3];//コマンド受信用配列
        while(true){
            in.mark(buff.length);
            if(in.available()>=buff.length){
                in.read(buff);
                if(buff[0]!=IAC){//ネゴシエーションの終了
                    in.reset();
                    return;
                }else if(buff[1]==DO){//DOコマンド
                    buff[1] = WONT;//WONTで返答する
                    out.write(buff);
                }
            }
        }       
    }

    //mainメソッド
    //TCPコネクションを開いて処理を開始する
    public static void main(String[] arg){
        try{
            Telnet t = null;
            //引数の個数によってコンストラクタが異なる
            switch(arg.length){
                case 1://サーバアドレスのみ
                    t = new Telnet(arg[0]);
                    break;
                case 2://アドレスポートの指定
                    t = new Telnet(arg[0],Integer.parseInt(arg[1]));
                    break;
                default:
                    System.out.println("usage: java telnet <host name> {<port number>}");
                    return;
            }
            t.openConnection();
            t.main_proc();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}

//StreamConnectorクラス
//ストリームを受け取り両者を結合してデータを受け流す
//StreamConnectorクラスはスレッドを構成するためのクラス
class StreamConnector implements Runnable{
    InputStream src = null;
    OutputStream dist = null;

    //コンストラクタ入出力ストリームを受け取る
    public StreamConnector(InputStream in,OutputStream out){
        src = in;
        dist = out;
    }
    //処理の本体
    //ストリームの読み書きを無限に繰り返す
    public void run(){
        byte[] buff = new byte[1024];
        while(true){
            try{
                int n = src.read(buff);
                if(n>0){
                    dist.write(buff,0,n);
                }
            }catch(Exception e){
                e.printStackTrace();
                System.err.print(e);
                System.exit(1);
            }
        }
    }
}
































