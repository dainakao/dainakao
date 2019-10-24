package Ngram_Search;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) {

		InputStreamReader isr = new InputStreamReader(System.in);
		try {
			System.out.println("何をしますか？");
			System.out.println("1;dataの一覧を表示");
			System.out.println("2;Ngramの一覧を表示");
			System.out.println("3;Ngramを生成");
			System.out.println("4;インデックスを生成");
			BufferedReader br = new BufferedReader(isr);
			String str  = br.readLine();


			//ファイルの一覧を表示
			if(str.equals("1")){
				//ファイル名の一覧を取得する
				File file = new File("C:\\\\Eclipse\\\\pleiades-2019-09-java-win-64bit-jre_20191007\\\\pleiades\\\\workspace\\\\NGRAM_SEARCH\\\\data");
				File files[] = file.listFiles();
				String FN;//ファイル名
				for(int i=0; i<files.length; i++) {
					FN = files[i].getName();
					System.out.println(FN);
				}

				//Nグラムの一覧を表示
			}else if(str.equals("2")) {
				//ファイル名の一覧を取得する
				File file = new File("C:\\\\Eclipse\\\\pleiades-2019-09-java-win-64bit-jre_20191007\\\\pleiades\\\\workspace\\\\NGRAM_SEARCH\\\\Ngram");
				File files[] = file.listFiles();
				String FN;//ファイル名
				for(int i=0; i<files.length; i++) {
					FN = files[i].getName();
					System.out.println(FN);
				}

				//Ngramを作成
			}else if(str.equals("3")){
				Make_Ngram mn = new Make_Ngram();
				mn.make_ngram();

				//転置インデックスを作成
			}else if(str.equals("4")) {
				Make_Index mi = new Make_Index();
				mi.make_index();


			}else{
				System.out.println(str + "は無効な入力です");
			}

		}catch(IOException e) {
			System.out.println("*入出力エラー*");
		}
	}
}
