package morphologicalSearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static Functions f = new Functions();
	public static void main(String[] args) {
		InputStreamReader isr = new InputStreamReader(System.in);
		try {
			String str = "1";
			String pass_name = new File("..").getCanonicalPath() + "\\MORPHOLOGICAL_SEARCH\\";
			System.out.println(pass_name);
			while(!(str.equals("0"))) {
				System.out.println("何をしますか？");
				System.out.println("0;終了");
				System.out.println("1;dataの一覧を表示");
				System.out.println("2;morphorogicalの一覧を表示");
				System.out.println("3;morphorogicalを生成");
				System.out.println("4;新インデックスを生成");
				System.out.println("5;インデックス内を検索");
				System.out.println("6;TF_IDFを生成");
				System.out.println("7;TF_IDFを用いて重みづけ");
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

					//morphorogicalの一覧を表示
				}else if(str.equals("2")) {
					//ファイル名の一覧を取得する
					File file = new File(pass_name + "\\morphorogical");
					File files[] = file.listFiles();
					String FN;//ファイル名
					for(int i=0; i<files.length; i++) {
						FN = files[i].getName();
						System.out.println(FN);
					}

					//morphorogicalを作成
				}else if(str.equals("3")){
					Make_morphorogical mn = new Make_morphorogical();
					mn.make_morphorogical(pass_name);

					//新・転置インデックスを作成
				}else if(str.equals("4")) {
					Make_Index mi = new Make_Index();
					mi.aaa(pass_name);

					//転置インデックス内を検索(2文字以上)
				}else if(str.equals("5")) {
					System.out.println("検索する単語を入力してください");
					Search_Index si = new Search_Index();
					si.long_search_aaa("Inverted_Index.csv",pass_name,f.keyboard());



					//TF_IDFを生成
				}else if(str.equals("6")) {
					TF_IDF tf = new TF_IDF();
					tf.make_TF_IDF("Inverted_Index.csv", pass_name);

					//TF_IDFを用いて重みづけ
				}else if(str.equals("7")) {
					TF_IDF tf = new TF_IDF();
					System.out.println("検索する単語を入力してください");
					tf.search_TF_IDF("Inverted_Index.csv", pass_name, f.keyboard());



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
