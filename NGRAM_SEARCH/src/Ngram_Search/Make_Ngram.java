package Ngram_Search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//Nグラムを作る
public class Make_Ngram {
	public void make_ngram(String pass_name) {
		//ファイル名の一覧を取得する
		File file = new File(pass_name + "\\NGRAM_SEARCH\\data");
		File files[] = file.listFiles();

		String FN;//ファイル名
		for(int i=0; i<files.length; i++) {
			FN = files[i].getName();
			System.out.println(FN);
			try {
				//ファイルを読み込む
				FileReader fr = new FileReader(pass_name + "\\NGRAM_SEARCH\\data\\" + FN);
				BufferedReader br = new BufferedReader(fr);

				//Nグラムを作る
				this.make(FN, this.Read_String(FN,pass_name), pass_name);

				//終了処理
				br.close();
				fr.close();

			} catch (IOException ex) {
				//例外発生時処理
				ex.printStackTrace();
			}
		}
	}
	public void make(String a, String b, String pass_name){
		//Ngramの区切り数
		int N = 2;

		//ファイルを作成
		File newfile = new File(pass_name + "\\NGRAM_SEARCH\\Ngram\\\\Ngram_" + a);

		//エラー処理
		try {

			//Ngramを入力
			String n;//一時的にコピーする変数
			FileWriter filewriter = new FileWriter(newfile);
			for(int i=0; i<=b.length()-N-1; i++) {
				n = b.substring(i,i+N);
				filewriter.write(n+"\n");
			}
			System.out.println("Ngramの作成に成功しました。");
			filewriter.close();
		}catch(IOException e) {
			System.out.println(e);
		}
	}

	//テキストを配列に代入
	String a;
	public String Read_String(String a, String pass_name){
		String b ="";//一時的な変数
		FileReader fr = null;{
			//finallyで使うのでここで宣言
			try {
				//読み込み先を指定
				File file = new File(pass_name + "\\NGRAM_SEARCH\\\\data\\\\" + a);
				BufferedReader br = new BufferedReader(new FileReader(file));

				//文字列型に代入できる
				String s;

				//何も無かったらnullが返る
				while((s = br.readLine()) != null){
					System.out.println(s);
					b = b + s;
					b = b + "\n";
				}
				//終了処理
				br.close();

			}catch(FileNotFoundException e1){
				System.out.println("ファイルが見つかりません。");
			}catch(IOException e2){
				System.out.println("ファイルエラーです。");
			}finally{
				try{
					//ここでも例外処理が必要
					if(fr != null) {
						System.out.println("File_Close");
						fr.close();
					}
					//nullでなければここでファイルクローズ
				}catch(IOException e){
					System.out.println("ファイルクローズい失敗しました。");
				}
			}
		}
		return b;
	}
}
