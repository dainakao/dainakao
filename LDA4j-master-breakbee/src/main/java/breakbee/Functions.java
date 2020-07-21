package breakbee;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;
import org.atilika.kuromoji.Tokenizer.Builder;
import org.atilika.kuromoji.Tokenizer.Mode;

public class Functions {
	//morphorogicalを作る(助詞と助動詞などを除く)
	public void make_morphorogical_2(String pass_name) {
		//ファイル名の一覧を取得する
		File file = new File(pass_name + "\\data");
		File files[] = file.listFiles();

		String FN;//ファイル名
		for(int i=0; i<files.length; i++) {
			FN = files[i].getName();
			System.out.println(FN);
			try {
				//ファイルを読み込む
				System.out.println(pass_name + "\\data\\" + FN);
				FileReader fr = new FileReader(pass_name + "\\data\\" + FN);
				BufferedReader br = new BufferedReader(fr);

				//morphorogicalを作る
				this.make_2(FN, this.Read_String(FN,pass_name), pass_name);

				//終了処理
				br.close();
				fr.close();

			} catch (IOException ex) {
				//例外発生時処理
				ex.printStackTrace();
			}
		}
	}
	public void make_2(String file_name, String parseWord, String pass_name){
		Builder builder = Tokenizer.builder();
		// Searchモード
		builder.mode(Mode.SEARCH);
		Tokenizer search = builder.build();
		List<Token> tokens = search.tokenize(parseWord);

		ArrayList<String> morphorogical = new ArrayList<String>();
		for (Token token : tokens) {
			String[] features = token.getAllFeaturesArray();
			if(features[0].equals("動詞") || features[0].equals("形容詞")|| features[0].equals("名詞")) {
				if(!features[6].equals("*"))morphorogical.add(features[6]);
			}
		}

	    	try {
	    	    File file = new File(pass_name + "\\morphorogical_index.txt");
	    	 
	    	    // 書き込み権限の確認
	    	    if (!file.canWrite()) {
	    	        // 書き込み可能に変更
	    	        file.setWritable(true);
	    	    }
	    	 
	    	    // 2つ目の引数にtrueを追加
	    	    FileWriter filewriter = new FileWriter(file, true);
				for(int i=0; i<=morphorogical.size()-2; i++) {
					filewriter.write(morphorogical.get(i));
					if(i<=morphorogical.size()-3)filewriter.write(" ");
				}
				filewriter.write("\n");
	    	    filewriter.close();
	    	 
	    	    System.out.println("テキストファイルの作成に成功しました");
	    	} catch (IOException e) {
	    	    e.printStackTrace();
	    	}
			System.out.println("morphorogicalの作成に成功しました。");
	}






	//数値のクイックソート
	public static void quick(double[] input1, double[] input2, double[] input3, int left, int right) {
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


	//テキストを配列に代入
	public String Read_String(String file_name, String pass_name){
		String b ="";//一時的な変数
		FileReader fr = null;{
			//finallyで使うのでここで宣言
			try {
				//読み込み先を指定
				File file = new File(pass_name + "\\data\\" + file_name);
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

	public ArrayList<ArrayList<String>> R_S(String file_name, String pass_name){
		ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();
		try {
			File f = new File(file_name);
			BufferedReader br = new BufferedReader(new FileReader(f));

			String line = br.readLine();
			for (; line != null;) {
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

	//テキストファイルごとのmorphorogicalの数をint配列で返却
	public int[] Number_of_words(String pass_name) {
		int [] num = null;
		//ファイル名の一覧を取得する
		File file = new File(pass_name + "\\morphorogical");
		File files[] = file.listFiles();
		num = new int[files.length];//返却するテキストファイルごとの単語数

		String FN;//ファイル名
		for(int i=0; i<files.length; i++) {
			FN = files[i].getName();
			try {
				//ファイルを読み込む
				FileReader fr = new FileReader(pass_name + "\\morphorogical\\" + FN);
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
			FileReader fr = new FileReader(pass_name+"\\morphorogical\\" + file_name);
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
		File file = new File(pass_name + "\\morphorogical");
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

