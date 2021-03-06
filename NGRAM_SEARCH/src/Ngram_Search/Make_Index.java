package Ngram_Search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Make_Index {

	//旧・インデックスを作成
	public void make_index(String pass_name) {
		//ファイル名の一覧を取得する
		File file = new File(pass_name+"\\Ngram");
		File files[] = file.listFiles();

		//[単語] [文章名] [出現位置]で入力
		List<String> WL = new ArrayList<String>();

		String FN;//ファイル名
		for(int i=0; i<files.length; i++) {
			FN = files[i].getName();
			try {
				//ファイルを読み込む
				FileReader fr = new FileReader(pass_name+"\\Ngram\\" + FN);
				BufferedReader br = new BufferedReader(fr);

				//読み込んだファイルをFSに入力
				String line;

				int number=0;//出現位置をカウント
				//Ngram_から始まる文章のみをリストに加える
				if(FN.substring(0,6).equals("Ngram_")) {;
				while ((line = br.readLine()) != null) {
					//読み込んだbufをFNに代入
					WL.add(line + "");
					WL.add(FN + "");
					WL.add(number + "");
					number++;
				}
				}

				//終了処理
				br.close();
				fr.close();

			} catch (IOException ex) {
				//例外発生時処理
				ex.printStackTrace();
			}

			//クイックソートを実行
			WL = quickSort(WL);

		}
		//エラー処理
		try {
			FileWriter filewriter = new FileWriter(pass_name+"\\Inverted_Index.txt");
			//結果を出力
			for(int i=0; i<WL.size(); i++) {
				System.out.print(WL.get(i));
				if(i%3 == 2) {
					System.out.println();
				}else {
					System.out.print(",");
				}
				filewriter.write(WL.get(i));
				if(i%3 == 2) {
					filewriter.write("\n");
				}else {
					filewriter.write(",");
				}
			}
			System.out.println("転置インデックスの作成に成功しました。");
			filewriter.close();
		}catch(IOException e) {
			System.out.println(e);
		}

	}

	//新・インデックスを作成
	public void aaa(String pass_name) {
		//ファイル名の一覧を取得する
		File file = new File(pass_name+"\\Ngram");
		File files[] = file.listFiles();

		//[単語] [文章名] [出現位置]で入力
		List<String> WL = new ArrayList<String>();

		String FN;//ファイル名
		for(int i=0; i<files.length; i++) {
			FN = files[i].getName();
			try {
				//ファイルを読み込む
				FileReader fr = new FileReader(pass_name+"\\Ngram\\" + FN);
				BufferedReader br = new BufferedReader(fr);

				//読み込んだファイルをFSに入力
				String line;

				int number=0;//出現位置をカウント
				//Ngram_から始まる文章のみをリストに加える
				if(FN.substring(0,6).equals("Ngram_")) {;
				while ((line = br.readLine()) != null) {
					//読み込んだbufをFNに代入
					WL.add(line + "");
					WL.add(i + "");
					WL.add(number + "");
					number++;
				}
				}

				//終了処理
				br.close();
				fr.close();

			} catch (IOException ex) {
				//例外発生時処理
				ex.printStackTrace();
			}

			//クイックソートを実行
			WL = quickSort(WL);

		}
		//エラー処理
		try {
			FileWriter filewriter = new FileWriter(pass_name+"\\Inverted_Index.csv");
			//結果をまとめて、出力
			String str;
			int i=0;
			while(i<WL.size()) {
				str = WL.get(i);
				if(i==0) {
					System.out.print(WL.get(i));//文書を出力
					filewriter.write(WL.get(i));//文書を出力
					System.out.print(",");
					filewriter.write(",");
					i++;
					System.out.print(files[Integer.parseInt(WL.get(i))].getName());//ファイル名を出力
					filewriter.write(WL.get(i));//ファイル名を出力
					System.out.print(",");
					filewriter.write(",");
					i++;
					System.out.print(WL.get(i));//番号を出力
					filewriter.write(WL.get(i));//番号を出力
					i++;
				}else {
					if(!str.equals(WL.get(i-3))) {//違う単語なら改行
						System.out.println();
						filewriter.write("\n");
						System.out.print(WL.get(i));//文書を出力
						filewriter.write(WL.get(i));//文書を出力
						System.out.print(",");
						filewriter.write(",");
					}else {//同じ単語なら後ろに追加
						System.out.print(",");
						filewriter.write(",");
					}
					i++;
					System.out.print(files[Integer.parseInt(WL.get(i))].getName());//ファイル名を出力
					filewriter.write(WL.get(i));//ファイル名を出力
					System.out.print(",");
					filewriter.write(",");
					i++;
					System.out.print(WL.get(i));//番号を出力
					filewriter.write(WL.get(i));//番号を出力
					i++;
				}
			}
			System.out.println();
			System.out.println("転置インデックスの作成に成功しました。");
			filewriter.close();
		}catch(IOException e) {
			System.out.println(e);
		}

	}

	// クイックソートのメソッド（）
	public static  List<String> quickSort(List<String> WL) {
		int count = WL.size()/3;//配列の長さは(リストの長さ/要素（今回は３）)
		//リストを配列にコピー
		long [] wl_word_num = new long [count];//単語を数値に変換した配列
		String[] wl_word = new String [count];//単語の配列
		String[] wl_file = new String [count];//ファイル名
		String[] wl_number = new String [count];//出現位置

		String a;
		for(int i=0; i<count; i++) {
			a = WL.get(3*i+1);
			wl_file[i] = a;//ファイル名

			a = WL.get(3*i+2);
			wl_number[i] = a;//出現位置

			a = WL.get(3*i);
			wl_word[i] = a;//単語の配列

			try {
				byte[] bytes;
				//Nグラムの2文字の文字コードをbyte型に変換
				bytes = a.substring(0,2).getBytes("UTF-8");
				//long型の変数に収納・半角のバイト長が足りないものは0を代入
				byte[] b = new byte[6];
				for(int j=0; j<b.length; j++) {
					if(j<bytes.length) {
						b[j] = bytes[j];
					}else {
						b[j] = 0;
					}
				}
				wl_word_num[i] = (long) (Math.pow(256, 5)*(((long)b[0])+128) + Math.pow(256, 4)*(((long)b[1])+128) + Math.pow(256, 3)*(((long)b[2])+128) + Math.pow(256, 2)*(((long)b[3])+128) + 256*(((long)b[4])+128) + (((long)b[5])+128));
				//System.out.println("ソート対象；"+wl_word_num[i] + "\n");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		//ソートアルゴリズムを呼び出す
		quick( wl_word_num, wl_word, wl_file, wl_number,  0, wl_word_num.length - 1);

		//ソートした結果をリストに戻す
		for(int i1=0; i1<count; i1++) {
			for(int j=0; j<3; j++) {
				if(j == 0)WL.set(3*i1, wl_word[i1]);
				if(j == 1)WL.set(3*i1+1, wl_file[i1]);
				if(j == 2)WL.set(3*i1+2, wl_number[i1]);
			}
		}

		//ソートしたリストを返す
		return WL;
	}

	private static void quick(long[] input1, String[] input2, String[] input3, String[] input4, int left, int right) {
		long[] array1 = input1;
		String[] array2 = input2;
		String[] array3 = input3;
		String[] array4 = input4;
		int currentLeft = left;
		int currentRight = right;

		// 要素数が1以下のときは、何もせず返却する
		if (array1.length < 2);

		// 軸はcurrentLeftとcurrentRightの真ん中
		long pivot = array1[(currentLeft + currentRight) / 2];

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
				long temp = array1[index1];
				array1[index1] = array1[index2];
				array1[index2] = temp;
				String TEMP = array2[index1];
				array2[index1] = array2[index2];
				array2[index2] = TEMP;
				TEMP = array3[index1];
				array3[index1] = array3[index2];
				array3[index2] = TEMP;
				TEMP = array4[index1];
				array4[index1] = array4[index2];
				array4[index2] = TEMP;
			}
		} while (currentLeft <= currentRight);

		if (left < currentRight)
			quick(array1, array2, array3, array4, left, currentRight);

		if (currentLeft < right)
			quick(array1, array2, array3, array4, currentLeft, right);
	}

}