package Ngram_Search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Functions {
	public void TF_IDF(String file_name, String pass_name){	
		int d;//文書dj内の単語tiの出現回数
		int[] now = Number_of_words(pass_name);//文書djのすべての単語の出現回数の和
		int N = count_file(pass_name);//総文書数
		int df;//単語tiが出現する文書数+1
		String[] str = Read_Strings(file_name, pass_name);
		double [][] TF_IDF = new double [now.length][count_word(file_name, pass_name)];//TF＿IDF値を入れる型

		int i = 1;//インデックス番号
		int I = i;
		int ID = 0;
		while(i<str.length) {


			int s=i;//検索する単語ID
			while(!str[s].equals("\n")) {
				i=I;
				d=0;
				while(!str[i].equals("\n")) {

					if(Integer.parseInt(str[i]) == Integer.parseInt(str[s])) {
						d++;
					}
					i=i+2;
				}
				//dfを求める
				df=0;
				for(int j=0; j<TF_IDF.length; j++) {
					if(TF_IDF[j][ID] != 0)df++;
				}
				df++;//dfは単語tiが出現する文書数に+1しておく
				double TF = (double)d/(double)now[Integer.parseInt(str[s])];
				double IDF = Math.log((double)N/(double)df);
				TF_IDF[Integer.parseInt(str[s])][ID] = TF*IDF;
				s= s+2;	
			}
			ID++;
			i=i+2;
			I = i;//先頭のインデックスを一時記憶
		}
		try {
			FileWriter filewriter = new FileWriter(pass_name+"\\tf_idf.csv");
		for(int j=0; j<TF_IDF.length; j++) {
			for(int h=0; h<TF_IDF[0].length; h++) {
				System.out.print(TF_IDF[j][h]+",");
				filewriter.write(TF_IDF[j][h]+",");
			}
			System.out.println();
			filewriter.write("\n");
		}
		}catch(IOException e) {
			System.out.println(e);
		}
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

	//テキストファイルごとのNグラムの数をint配列で返却
	public int[] Number_of_words(String pass_name) {
		int [] num = null;
		//ファイル名の一覧を取得する
		File file = new File(pass_name + "\\Ngram");
		File files[] = file.listFiles();
		num = new int[files.length];//返却するテキストファイルごとの単語数

		String FN;//ファイル名
		for(int i=0; i<files.length; i++) {
			FN = files[i].getName();
			try {
				//ファイルを読み込む
				FileReader fr = new FileReader(pass_name + "\\Ngram\\" + FN);
				BufferedReader br = new BufferedReader(fr);

				//単語数を調べる
				num[i] = m_o_w(FN,pass_name);

				//終了処理
				br.close();
				fr.close();

			} catch (IOException ex) {
				//例外発生時処理
				ex.printStackTrace();
			}
		}
		return num;
	}
	public int m_o_w(String file_name, String pass_name) {
		int num = 0;
		try {
			//ファイルを読み込む
			FileReader fr = new FileReader(pass_name+"\\Ngram\\" + file_name);
			BufferedReader br = new BufferedReader(fr);
			//読み込んだファイルの単語数を調べる
			while (br.readLine() != null) {
				num++;
			}
			//終了処理
			br.close();
			fr.close();
		} catch (IOException ex) {
			//例外発生時処理
			ex.printStackTrace();
		}
		return num;
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

	//ファイルの数を数える
	public int count_file(String pass_name) {
		//ファイル名の一覧を取得する
		File file = new File(pass_name + "\\Ngram");
		File files[] = file.listFiles();
		//返却するテキストファイルごとの単語数
		return files.length;
	}
}
