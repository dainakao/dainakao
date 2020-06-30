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
				System.out.println("1;データの設定");
				System.out.println("2;データの検索");
				System.out.println();
				BufferedReader br = new BufferedReader(isr);
				str  = br.readLine();

				if(str.equals("1")) {
					System.out.println("何をしますか？");
					System.out.println("0;終了");
					System.out.println("1;dataの一覧を表示");
					System.out.println("2;morphorogicalの一覧を表示");
					System.out.println("3;文書から空白を取り除く");
					System.out.println("4;morphorogicalを生成");
					System.out.println("5;新インデックスを生成");
					System.out.println("6;TF_IDFを生成");
					System.out.println("10;上記すべてを実行");

					br = new BufferedReader(isr);
					str  = br.readLine();

					TF_IDF tf = new TF_IDF();
					Make_Index mi = new Make_Index();
					Make_morphorogical mn = new Make_morphorogical();

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

						//文書を調整
					}else if(str.equals("3")) {
						mi.prepare_sentences(pass_name);

						//morphorogicalを作成
					}else if(str.equals("4")){
						mn.make_morphorogical(pass_name);

						//新・転置インデックスを作成
					}else if(str.equals("5")) {
						mi.aaa(pass_name);

						//TF_IDFを生成
					}else if(str.equals("6")) {
						tf.make_TF_IDF("Inverted_Index.csv", pass_name);

						//すべてを実行
					}else if(str.equals("10")) {
						mi.prepare_sentences(pass_name);
						mn.make_morphorogical(pass_name);
						mi.aaa(pass_name);
						tf.make_TF_IDF("Inverted_Index.csv", pass_name);

					}else{
						System.out.println(str + "は無効な入力です");
					}
					System.out.println();
					System.out.println();
				}else if(str.equals("2")) {
					System.out.println("どう検索しますか？");
					System.out.println("0;終了");
					System.out.println("1;dataの一覧を表示");
					System.out.println("2;morphorogicalの一覧を表示");
					System.out.println("3;インデックス内を検索");
					System.out.println("4;TF_IDFを用いて重みづけ");
					System.out.println("5;TF_IDFを用いて複数語句を重みづけ(足し算)");
					System.out.println("6;TF_IDFを用いて複数語句を重みづけ(掛け算)");
					br = new BufferedReader(isr);
					str  = br.readLine();

					Search_Index si = new Search_Index();
					TF_IDF tf = new TF_IDF();

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

						//転置インデックス内を検索(2文字以上)
					}else if(str.equals("3")) {
						System.out.println("検索する単語を入力してください");
						si.long_search_aaa("Inverted_Index.csv",pass_name,f.keyboard());

						//TF_IDFを用いて重みづけ
					}else if(str.equals("4")) {
						System.out.println("検索する単語を入力してください");
						tf.search_TF_IDF("Inverted_Index.csv", pass_name, f.keyboard());
						
						//TF_IDFを用いて複数語句を重みづけ(足し算)
					}else if(str.equals("5")) {
						System.out.println("検索する単語を入力してください");
						tf.add_long_search_TF_IDF("Inverted_Index.csv", pass_name, f.keyboard());

						//TF_IDFを用いて複数語句を重みづけ(掛け算)
					}else if(str.equals("6")) {
						System.out.println("検索する単語を入力してください");
						tf.mul_long_search_TF_IDF("Inverted_Index.csv", pass_name, f.keyboard());

					}else{
						System.out.println(str + "は無効な入力です");
					}
					System.out.println();
					System.out.println();
				}
			}
		}catch(IOException e) {
			System.out.println("*入出力エラー*");
		}
	}
}
