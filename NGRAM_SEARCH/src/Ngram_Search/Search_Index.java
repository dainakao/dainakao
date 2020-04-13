package Ngram_Search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Search_Index {

	public void search(String file_name, String pass_name) {
		String search_word = null;//検索する語句
		String[] index = null;//転地インデックスをアレイリストで

		//indexに転置インデックスを代入
		int counter = 0;
		String a = this.Read_String(file_name, pass_name);//一時的な変数
		String b = null;//一時的な変数
		System.out.println(a.substring(0,1));
		for(int i=0; !(a.substring(i,i+1).equals(null)); i++) {
			if(a.substring(i,i+1).equals(",")) {
				index[counter] = b;
				counter++;
				b = null;
			}else{
				b += a.substring(i,i);
			}
		}

		System.out.println("検索する単語を入力してください");
		search_word = this.keyboard();

		boolean ans = false;
		System.out.println("検索結果");
		for(int i=0; 3*i<index.length; i++) {
			if(index[3*i].equals(search_word)) {
				System.out.println(index[(3*i)+1]+","+index[(3*i)+2]);
				ans = true;
			}
		}
		if(ans == false)System.out.println("該当する語句は検出されませんでした");
		System.out.println();
	}

	//キーボードの入力をStringに
	public String keyboard() {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String str = null;
		try {
			str  = br.readLine();
		}catch(IOException e) {
			System.out.println("*入出力エラー*");
		}
		return str;
	}

	//テキストを配列に代入、Stringで返却
	public String Read_String(String file_name, String pass_name){
		String b ="";//一時的な変数
		FileReader fr = null;{
			//finallyで使うのでここで宣言
			try {
				//読み込み先を指定
				File file = new File(pass_name + file_name);
				BufferedReader br = new BufferedReader(new FileReader(file));

				//文字列型に代入できる
				String s;

				//何も無かったらnullが返る
				while((s = br.readLine()) != null){
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