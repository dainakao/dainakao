package Ngram_Search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Search_Index {
	
	//2字語句を新インデックスから検索
	public void search_aaa(String file_name, String pass_name, String s_w) {
		String search_word = s_w;//検索する語句
		ArrayList<ArrayList<String>> indexs = new ArrayList<ArrayList<String>>();//転地インデックスをアレイリストで

		//indexに転置インデックスを代入
		String a[] = this.Read_Strings(file_name, pass_name);//一時的な変数

		ArrayList<String> index = new ArrayList<String>();//転地インデックス
		for(int i=0; i<a.length; i++) {
			if(a[i].equals(",")) {
			}else if(a[i].equals("\n")){
				indexs.add(index);
				index = new ArrayList<String>();//転地インデックス
			}else {
				index.add(a[i]);
			}
		}
		System.out.println(search_word);

		File file = new File(pass_name + "\\data");
		File files[] = file.listFiles();

		boolean ans = false;
		System.out.println("検索結果");
		for(int i=0; i<indexs.size(); i++) {
			if(indexs.get(i).get(0).equals(search_word)) {
				for(int j=0; j<indexs.get(i).size(); j++) {
					if(j%2 == 1) {
						System.out.print(files[Integer.parseInt(indexs.get(i).get(j))].getName());
					}else {
						System.out.print(indexs.get(i).get(j));
					}
					if(j!=indexs.get(i).size()-1)System.out.print(",");
				}
				ans = true;
				break;
			}
		}
		if(ans == false)System.out.println("該当する語句は検出されませんでした");
		System.out.println();
	}

	//長い語句を新インデックスから検索
	public void long_search_aaa(String file_name, String pass_name, String s_w) {
		String search_word = s_w;//検索する語句
		String search_words[] = new String[s_w.length()-1];//検索語句
		ArrayList<ArrayList<String>> indexs = new ArrayList<ArrayList<String>>();//転地インデックスをアレイリストで

		//indexに転置インデックスを代入
		String a[] = this.Read_Strings(file_name, pass_name);//一時的な変数

		ArrayList<String> index = new ArrayList<String>();//転地インデックス
		for(int i=0; i<a.length; i++) {
			if(a[i].equals(",")) {
			}else if(a[i].equals("\n")){
				indexs.add(index);
				index = new ArrayList<String>();//転地インデックス
			}else {
				index.add(a[i]);
			}
		}

		//検索語をNgram状にカット
		int N = 2;//Ngramの区切り数
		String n;//一時的にコピーする変数
		for(int i=0; i<search_word.length()-1; i++) {
			n = search_word.substring(i,i+N);
			search_words[i] = n;
		}

		File file = new File(pass_name + "\\data");
		File[] files = file.listFiles();//ファイル名リスト
		boolean[][] answers = new boolean[files.length][search_words.length];//部分検索の合致リスト
		//初期化
		for(int i=0; i<answers.length; i++) {
			for(int j=0; j<answers[0].length; j++) {
				answers[i][j] = false;
			}
		}

		//検索部位
		System.out.println("～検索結果～");
		//まずは完全一致検索から
		System.out.println("・完全一致");
		boolean ans = false;//一時的な変数
		for(int i=0; i<indexs.size(); i++) {
			if(indexs.get(i).get(0).equals(search_words[0])) {
				for(int j=1; j<indexs.get(i).size(); j=j+2) {
					String str = this.Read_String(files[Integer.parseInt(indexs.get(i).get(j))].getName(), pass_name+"data\\");//引っかかったファイルをStringに
					if(Objects.equals(str.substring(Integer.parseInt(indexs.get(i).get(j+1)),Integer.parseInt(indexs.get(i).get(j+1))+search_word.length()), search_word)) {
						ans = true;
						System.out.println(files[Integer.parseInt(indexs.get(i).get(j))].getName()+","+indexs.get(i).get(j+1));
						//System.out.println("→" + str.substring(Integer.parseInt(index.get((i)+2))-10,Integer.parseInt(index.get((i)+2))+search_word.length()+10));
					}
				}
				break;
			}
		}
		if(ans == false)System.out.println("該当無し");

		//本格的に部分検索
		for(int h=0; h<search_words.length; h++) {
			for(int i=0; i<indexs.size(); i++) {
				if(indexs.get(i).get(0).equals(search_words[h])) {
					for(int j=1; j<indexs.get(i).size(); j=j+2) {
							answers[Integer.parseInt(indexs.get(i).get(j))][h] = true;
					}
				}
			}
		}
		//部分検索の結果を表示
		System.out.println();
		System.out.println("・部分一致");
		for(int h=0; h<answers.length; h++) {
			for(int i=0; i<answers[h].length; i++) {
				if(answers[h][i]==true) {
					System.out.print("〇,");
				}else {
					System.out.print("×,");
				}
			}
			System.out.println(";"+files[h]);
		}
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

	//テキストを配列に代入、String[]で返却(改行は\nで格納)
	public String[] Read_Strings(String file_name, String pass_name){
		String str[] = null;
		List<String> ST = new ArrayList<String>();//一時的な変数
		FileReader fr = null;
		//finallyで使うのでここで宣言
		try {
			//読み込み先を指定
			File file = new File(pass_name + file_name);
			BufferedReader br = new BufferedReader(new FileReader(file));

			//文字列型に代入できる
			String s="";
			String t="";
			//何も無かったらnullが返る
			while((s = br.readLine()) != null){
				for(int i=0; i<s.length();i++) {
					if(s.substring(i,i+1).equals(",")) {
						ST.add(t);
						t = "";
					}else{
						t += s.charAt(i);
					}
				}
				ST.add(t);
				t = "";
				ST.add("\n");
			}
			str = new String[ST.size()];
			for(int i=0; i<ST.size();i++) {
				str[i] = ST.get(i);
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
		return str;
	}

	//インデックスの単語の数を数える
	public int count_word(String file_name, String pass_name){
		int counter=0;//カウンター変数
		FileReader fr = null;
		//finallyで使うのでここで宣言
		try {
			//読み込み先を指定
			File file = new File(pass_name + file_name);
			BufferedReader br = new BufferedReader(new FileReader(file));

			//何も無かったらnullが返る
			while(br.readLine() != null){
				counter++;
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
		return counter;
	}

}




/*	
//2字語句を旧インデックスから検索
public void search(String file_name, String pass_name, String s_w) {
	String search_word = s_w;//検索する語句
	ArrayList<String> index = new ArrayList<String>();//転地インデックスをアレイリストで

	//indexに転置インデックスを代入
	String a = this.Read_String(file_name, pass_name);//一時的な変数
	String b = "";//一時的な変数

	for(int i=0; i<a.length(); i++) {
		if(a.substring(i,i+1).equals(",")||a.substring(i,i+1).equals("\n")) {
			index.add(new String(b));
			b = "";
		}else{
			b += a.substring(i,i+1);
		}
	}
	System.out.println(search_word);
	boolean ans = false;
	System.out.println("検索結果");
	for(int i=0; i<index.size(); i=i+3) {
		if(index.get(i).equals(search_word)) {
			System.out.println(index.get((i)+1)+","+index.get((i)+2));
			ans = true;
		}
	}
	if(ans == false)System.out.println("該当する語句は検出されませんでした");
	System.out.println();
}

//長い語句を旧インデックスから検索
public void long_search(String file_name, String pass_name, String s_w) {
	String search_word = s_w;//検索する語句
	String search_words[] = new String[s_w.length()-1];//検索語句
	ArrayList<String> index = new ArrayList<String>();//転地インデックスをアレイリストで

	//indexに転置インデックスを代入
	String a = this.Read_String(file_name, pass_name);//一時的な変数
	String b = "";//一時的な変数
	for(int i=0; i<a.length(); i++) {
		if(a.substring(i,i+1).equals(",")||a.substring(i,i+1).equals("\n")) {
			index.add(new String(b));
			b = "";
		}else{
			b += a.substring(i,i+1);
		}
	}


	//検索語をNgram状にカット
	int N = 2;//Ngramの区切り数
	String n;//一時的にコピーする変数
	for(int i=0; i<search_word.length()-1; i++) {
		n = search_word.substring(i,i+N);
		search_words[i] = n;
	}

	File file = new File(pass_name + "\\Ngram");
	File[] files = file.listFiles();//ファイル名リスト
	boolean[][] answers = new boolean[files.length][search_words.length];//部分検索の合致リスト
	for(int i=0; i<answers.length; i++) {
		for(int j=0; j<answers[0].length; j++) {
			answers[i][j] = false;
		}
	}

	//検索部位
	System.out.println("～検索結果～");
	//まずは完全一致検索から
	System.out.println("・完全一致");
	boolean ans = false;//一時的な変数
	for(int i=0; i<index.size(); i=i+3) {
		if(index.get(i).equals(search_words[0])) {
			ans=true;
			String str = this.Read_String(index.get((i)+1).substring(6,index.get((i)+1).length()), pass_name+"data\\");//引っかかったファイルをStringに
			if(Objects.equals(str.substring(Integer.parseInt(index.get((i)+2)),Integer.parseInt(index.get((i)+2))+search_word.length()), search_word)) {
				System.out.println(index.get((i)+1)+","+index.get((i)+2));
				//System.out.println("→" + str.substring(Integer.parseInt(index.get((i)+2))-10,Integer.parseInt(index.get((i)+2))+search_word.length()+10));
			}
		}
	}
	if(ans == false)System.out.println("該当無し");

	//本格的に部分検索
	for(int h=0; h<search_words.length; h++) {
		for(int i=0; i<index.size(); i=i+3) {
			if(index.get(i).equals(search_words[h])) {
				for(int j=0; j<files.length; j++) {
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append(pass_name);
					stringBuilder.append("Ngram\\");
					stringBuilder.append(index.get((i)+1));
					String c = files[j].toString();
					if(c.equals(stringBuilder.toString())) {
						answers[j][h] = true;
					}
				}
			}
		}
	}
	//部分検索の結果を表示
	for(int h=0; h<answers.length; h++) {
		for(int i=0; i<answers[h].length; i++) {
			if(answers[h][i]==true) {
				System.out.print("〇,");
			}else {
				System.out.print("×,");
			}
		}
		System.out.println(";"+files[h]);
	}
}
*/