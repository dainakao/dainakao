package search;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tex_Read {
	String a;
	//テキストファイルを読み込んで一回書き出す
	public void Read(){

		FileReader fr = null;{
			//finallyで使うのでここで宣言
			InputStreamReader isr = new InputStreamReader(System.in);

			try {
				//読み込み先を指定
				System.out.println("読み込み先を入力してください");
				BufferedReader br = new BufferedReader(isr);
				String str = br.readLine();
				fr = new FileReader(str);

				//例外処理
				br = new BufferedReader(fr);

				//文字列型に代入できる
				String s;

				//何も無かったらnullが返る
				while((s = br.readLine()) != null){
					System.out.println(s);
				}
				isr.close();
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
	}

	//テキストを配列に代入
	public String Read_String(String a){

		FileReader fr = null;{
			//finallyで使うのでここで宣言
			InputStreamReader isr = new InputStreamReader(System.in);

			try {
				//読み込み先を指定
				System.out.println("読み込み先を入力してください");
				BufferedReader br = new BufferedReader(isr);
				String str = br.readLine();
				fr = new FileReader(str);

				//例外処理
				br = new BufferedReader(fr);

				//文字列型に代入できる
				String s;

				//何も無かったらnullが返る
				while((s = br.readLine()) != null){
					a = a + s;
					a = a + "\n";
				}
				

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
		return a;
	}
}