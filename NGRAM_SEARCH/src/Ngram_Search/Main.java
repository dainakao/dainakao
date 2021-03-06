package Ngram_Search;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) {
		InputStreamReader isr = new InputStreamReader(System.in);
		try {
			String str = "1";
			String pass_name = new File("..").getCanonicalPath() + "\\NGRAM_SEARCH\\";
			System.out.println(pass_name);
			while(!(str.equals("0"))) {
				System.out.println("何をしますか？");
				System.out.println("0;終了");
				System.out.println("1;dataの一覧を表示");
				System.out.println("2;Ngramの一覧を表示");
				System.out.println("3;Ngramを生成");
				System.out.println("4;旧インデックスを生成");
				System.out.println("5;新インデックスを生成");
				System.out.println("6;インデックス内を検索(2文字)");
				System.out.println("7;インデックス内を検索(2文字以上)");

				System.out.println("8;TF_IDFを生成");
				System.out.println("9;TF_IDFを用いて重みづけ");
				BufferedReader br = new BufferedReader(isr);
				str  = br.readLine();


				//ファイルの一覧を表示
				if(str.equals("1")){
					//ファイル名の一覧を取得する
					File file = new File(pass_name + "\\data");
					File files[] = file.listFiles();
					String FN;//ファイル名
					for(int i=0; i<files.length; i++) {
						FN = files[i].getName();
						System.out.println(FN);
					}

					//Nグラムの一覧を表示
				}else if(str.equals("2")) {
					//ファイル名の一覧を取得する
					File file = new File(pass_name + "\\Ngram");
					File files[] = file.listFiles();
					String FN;//ファイル名
					for(int i=0; i<files.length; i++) {
						FN = files[i].getName();
						System.out.println(FN);
					}

					//Ngramを作成
				}else if(str.equals("3")){
					Make_Ngram mn = new Make_Ngram();
					mn.make_ngram(pass_name);

					//旧・転置インデックスを作成
				}else if(str.equals("4")) {
					Make_Index mi = new Make_Index();
					mi.make_index(pass_name);

					//新・転置インデックスを作成
				}else if(str.equals("5")) {
					Make_Index mi = new Make_Index();
					mi.aaa(pass_name);

					//転置インデックス内を検索(2文字のみ)
				}else if(str.equals("6")) {
					System.out.println("検索する単語(2文字)を入力してください");
					Search_Index si = new Search_Index();
					si.search_aaa("Inverted_Index.csv",pass_name,si.keyboard());

					//転置インデックス内を検索(2文字以上)
				}else if(str.equals("7")) {
					System.out.println("検索する単語(2文字以上)を入力してください");
					Search_Index si = new Search_Index();
					si.long_search_aaa("Inverted_Index.csv",pass_name,si.keyboard());



					//TF_IDFを生成
				}else if(str.equals("8")) {
					Functions f = new Functions();
					f.TF_IDF("Inverted_Index.csv", pass_name);

					//TF_IDFを用いて重みづけ
				}else if(str.equals("9")) {
					Functions f = new Functions();
					System.out.println("検索する単語(2文字)を入力してください");
					f.search_TF_IDF("Inverted_Index.csv", pass_name, f.keyboard());






				}else{
					System.out.println(str + "は無効な入力です");
				}
				System.out.println();
				System.out.println();
			}
		}catch(IOException e) {
			System.out.println("*入出力エラー*");
		}
	}
}
