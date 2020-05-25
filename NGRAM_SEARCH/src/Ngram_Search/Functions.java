package Ngram_Search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Functions {
	//TF_IDF[Nグラム][文書ID]
	public void TF_IDF(String file_name, String pass_name){
		int d;//文書dj内の単語tiの出現回数
		int[] now = Number_of_words(pass_name);//文書djのすべての単語の出現回数の和
		int N = count_file(pass_name);//総文書数
		int df;//単語tiが出現する文書数+1
		ArrayList<ArrayList<String>> ind = R_S(file_name, pass_name);
		double [][] TF_IDF = new double [count_word(file_name, pass_name)][now.length];//TF＿IDF値を入れる型
		//ID=Nグラムの番号
		for(int ID=0; ID<TF_IDF.length; ID++) {
			for(int i=0; i<TF_IDF[0].length;i++) {
				TF_IDF[ID][i] = 0.0;

				//dfを求める・dを求める
				d=0;
				df=1;
				ArrayList<Integer> DF = new ArrayList<Integer>();
				for(int j=1; j<ind.get(ID).size();j=j+2) {
					if(i==Integer.parseInt(ind.get(ID).get(j))) d++;
					DF.add(Integer.parseInt(ind.get(ID).get(j)));
				}
				Collections.sort(DF);
				int a=DF.get(0);//一時的な変数
				for(int j=0; j<DF.size();j++) {
					if(a!=DF.get(j)) {
						df++;
						a=DF.get(j);
					}
				}

				double TF = (double)d/(double)now[i];
				double IDF = Math.log((double)N/(double)df);
				TF_IDF[ID][i] = TF*IDF;
			}
		}
		try {
			FileWriter filewriter = new FileWriter(pass_name+"\\tf_idf.csv");
			for(int j=0; j<TF_IDF.length; j++) {
				for(int h=0; h<TF_IDF[j].length; h++) {
					filewriter.write(String.valueOf(TF_IDF[j][h]));
					if(h!=TF_IDF[0].length-1)filewriter.write(",");
				}
				filewriter.write("\n");
				filewriter.flush();
			}
		}catch(IOException e) {
			System.out.println(e);
		}
	}

	//検索→TF_IDFを用いて順位付け
	//2字語句を新インデックスから検索
	public void search_TF_IDF(String file_name, String pass_name, String s_w) {
		String search_word = s_w;//検索する語句
		System.out.println(search_word);
		ArrayList<ArrayList<String>> indexs = new ArrayList<ArrayList<String>>();//転地インデックスをアレイリストで
		double[][] data = null;//0;TF_IDF値 1;ファイルの番号 2;出現位置

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

		//ファイルからtf_idfに代入
		ArrayList<ArrayList<String>> tf_idf = this.R_S("tf_idf.csv", pass_name);//一時的な変数
		//double[][]  = new double[count_file(pass_name)][indexs.size()];
		int h=0;
		for(int i=0; i<tf_idf.size(); i++) {
			for(int j=0; j<tf_idf.get(i).size(); j++){
				//System.out.println(i+", "+j+", "+tf_idf.get(i).get(j));
			}
		}

		File file = new File(pass_name + "\\data");
		File files[] = file.listFiles();

		boolean ans = false;
		System.out.println("検索結果");
		for(int i=0; i<indexs.size(); i++) {
			if(indexs.get(i).get(0).equals(search_word)) {
				data = new double[(indexs.get(i).size()-1)/2][3];
				//0;TF_IDF値 1;ファイルの番号 2;出現位置
				for(int j=1; j<indexs.get(i).size(); j++) {
					if(j%2 == 1) {
						//System.out.print(tf_idf[Integer.parseInt(indexs.get(i).get(j))][i] +",");//tf_idfの値を出力
						//System.out.print(files[Integer.parseInt(indexs.get(i).get(j))].getName() +",");//ファイル番号を名前に変えて出力
						data[(j-1)/2][0] = Double.parseDouble(tf_idf.get(i).get(Integer.parseInt(indexs.get(i).get(j))));
						data[(j-1)/2][1] = Integer.parseInt(indexs.get(i).get(j));
					}else {
						//System.out.println(indexs.get(i).get(j));//出現位置を出力
						data[(j-1)/2][2] = Integer.parseInt(indexs.get(i).get(j));
					}
				}
				ans = true;
				break;
			}
		}
		//検出された際の重みづけ
		if(ans == true) {
			//quick(data[0], data[1], data[2], 0, data[0].length);
			for(int i=0; i<data.length; i++) {
				for(int j=0; j<data[0].length; j++) {
					System.out.print(data[i][j]+", ");
				}
				System.out.println();
			}
		}else{
			System.out.println("該当する語句は検出されませんでした");
		}
		System.out.println();
	}

	//クイックソート
	private static void quick(double[] input1, double[] input2, double[] input3, int left, int right) {
		double[] array1 = input1;
		double[] array2 = input2;
		double[] array3 = input3;
		int currentLeft = left;
		int currentRight = right;

		// 要素数が1以下のときは、何もせず返却する
		if (array1.length < 2);

		// 軸はcurrentLeftとcurrentRightの真ん中
		double pivot = array1[(currentLeft + currentRight) / 2];

		do {
			while (array1[currentLeft] < pivot) {
				currentLeft++;
			}
			while (array1[currentRight] > pivot) {
				currentRight--;
			}
			if (currentLeft <= currentRight) {
				int index1 = currentLeft++;
				int index2 = currentRight--;
				double temp = array1[index1];
				array1[index1] = array1[index2];
				array1[index2] = temp;
				double TEMP = array2[index1];
				array2[index1] = array2[index2];
				array2[index2] = TEMP;
				TEMP = array3[index1];
				array3[index1] = array3[index2];
				array3[index2] = TEMP;
			}
		} while (currentLeft <= currentRight);

		if (left < currentRight)
			quick(array1, array2, array3, left, currentRight);

		if (currentLeft < right)
			quick(array1, array2, array3, currentLeft, right);
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

	public ArrayList R_S(String file_name, String pass_name){
		ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();
		try {
			File f = new File(file_name);
			BufferedReader br = new BufferedReader(new FileReader(f));

			String line = br.readLine();
			for (int row = 0; line != null; row++) {
				ArrayList<String> data = new ArrayList<String>();
				String[] a = line.split(",", 0);
				for(int i=0; i<a.length; i++)data.add(a[i]);
				datas.add(data);
				line = br.readLine();
			}
			br.close();
			// CSVから読み込んだ配列の中身を表示
			for(int row = 0; row < datas.size(); row++) {
				for(int col = 0; col < datas.get(row).size(); col++) {
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		return datas;
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
}