package morphologicalSearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Make_Index {

	//文書から空白を取り除く
	public void prepare_sentences(String pass_name) {
		//ファイル名の一覧を取得する
		File file = new File(pass_name+"\\unorganized_data");
		File files[] = file.listFiles();

		String FN;//ファイル名
		for(int i=0; i<files.length; i++) {
			FN = files[i].getName();
			try {
				//ファイルを読み込む
				FileReader fr = new FileReader(pass_name+"\\unorganized_data\\" + FN);
				BufferedReader br = new BufferedReader(fr);

				//読み込んだファイルをFSに入力
				String line = br.readLine();

				//終了処理
				br.close();
				fr.close();

				FileWriter filewriter = new FileWriter(pass_name+"\\data\\" + FN);
				//結果を出力
				String str=null;
				int leng = line.length();
				for(int j=0; j<leng; j++) {
					str = line.substring(j,j+1);
					if(str.equals(" ")||str.equals("　")) {
						System.out.print("␣");
						filewriter.write("␣");
					}else if(str.equals("[")) {
						if(line.substring(j+2,j+3).equals("]")||line.substring(j+3,j+4).equals("]")) {
							j++;
							j++;
						}
					}else {
						System.out.print(str);
						filewriter.write(str);
					}

				}
				System.out.println("文書から空白を取り除きました");
				filewriter.close();
			}catch(IOException e) {
				System.out.println(e);
			}
		}

	}

	//新・インデックスを作成
	public void aaa(String pass_name) {
		//ファイル名の一覧を取得する
		File file = new File(pass_name+"\\morphorogical");
		File files[] = file.listFiles();

		//[単語] [文章名] [出現位置]で入力
		List<String> WL = new ArrayList<String>();

		String FN;//ファイル名
		for(int i=0; i<files.length; i++) {
			FN = files[i].getName();
			try {
				//ファイルを読み込む
				FileReader fr = new FileReader(pass_name+"\\morphorogical\\" + FN);
				BufferedReader br = new BufferedReader(fr);

				//読み込んだファイルをFSに入力
				String line;

				int number=0;//出現位置をカウント
				//morphorogical_から始まる文章のみをリストに加える
				if(FN.substring(0,14).equals("morphorogical_")) {;
				while ((line = br.readLine()) != null) {
					//読み込んだbufをFNに代入
					WL.add(line + "");
					WL.add(i + "");
					WL.add(number + "");
					number+=line.length();
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

		}

		//ソートアルゴリズムを呼び出す
		quick(wl_word, wl_file, wl_number,  0, wl_word.length - 1);

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

	private static void quick(String[] input1, String[] input2, String[] input3, int left, int right) {
		String[] array1 = input1;
		String[] array2 = input2;
		String[] array3 = input3;
		int currentLeft = left;
		int currentRight = right;

		// 要素数が1以下のときは、何もせず返却する
		if (array1.length < 2);

		// 軸はcurrentLeftとcurrentRightの真ん中
		String pivot = array1[(currentLeft + currentRight) / 2];

		do {
			while (pivot.compareTo(array1[currentLeft]) > 0) {
				currentLeft++;
			}
			while (pivot.compareTo(array1[currentRight]) < 0) {
				currentRight--;
			}
			if (currentLeft <= currentRight) {
				int index1 = currentLeft++;
				int index2 = currentRight--;
				String TEMP = array1[index1];
				array1[index1] = array1[index2];
				array1[index2] = TEMP;
				TEMP = array2[index1];
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

}