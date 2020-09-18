package refined_search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Functions {
	//まず動く
	public void aaa(int number, String pass_name, String[] files, String[][] dataName, boolean[][][] data) {
		Refined_Search frm = new Refined_Search();   // ウィンドウ作成
		frm.conditionSelect(number, pass_name, files, dataName, data);
		frm.setVisible(true);  // 表示
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


	//ファイル名の一覧を取得する
	public String[] listUp(String pass_name) {
		ArrayList<String> answer = new ArrayList<String>();
		File  file= new File(pass_name + "\\file");
		File files[] = file.listFiles();
		for(int j=0; j<files.length; j++) {
			answer.add(files[j].getName());
		}
		System.out.println(answer);
		return answer.toArray(new String[answer.size()]);
	}

	//データを取得
	public boolean[][][] dataGet(String pass_name, String[] files, String[][] dataName){
		boolean[][][] data = new boolean[files.length][dataName.length][dataName[0].length];
		for(int i=0; i<files.length; i++) {
			//データのラベルを入手
			BufferedReader br = null;
			try {
				File file = new File(pass_name + "\\file\\"+ files[i]);
				br = new BufferedReader(new FileReader(file));

				int counter = 0;
				// readLineで一行ずつ読み込む
				String line; // 読み込み行
				String str[][] = new String[dataName.length][dataName[0].length];
				while ((line = br.readLine()) != null) {
					// lineをカンマで分割し、配列dataに設定
					str[counter] = line.split(",", 0);
					counter++;
					for(int j=0; j<str[counter].length; j++) {
						data[i][counter][j] = Boolean.valueOf(str[counter][j]); // 分割後のデータを保持する配列
					}
				}
				System.out.println(files[i] + "のデータを取得");
				br.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return data;
	}



	public String[][] dataName(String pass_name) {
		String[][] dataName = new String[5][10];
		for(int i=0; i<dataName.length; i++) {
			for(int j=0; j<dataName[0].length; j++) {
				dataName[i][j] = "";
			}
		}
		//データのラベルを入手
		BufferedReader br = null;
		try {
			File file = new File(pass_name + "\\dataName.csv");
			br = new BufferedReader(new FileReader(file));

			int counter = 0;
			// readLineで一行ずつ読み込む
			String line; // 読み込み行
			String[][] data = new String[dataName.length][dataName[0].length]; // 分割後のデータを保持する配列
			while ((line = br.readLine()) != null) {
				// lineをカンマで分割し、配列dataに設定
				data[counter] = line.split(",", 0);
				for(int j=0; j<data[counter].length; j++) {
					dataName[counter][j] = data[counter][j];
				}
				counter++;
			}
			br.close();
			for(int i=0; i<dataName.length; i++) {
				for(int j=0; j<dataName[0].length; j++) {
					if(dataName[i][j]!=null)System.out.print(dataName[i][j] + " ");
				}
				System.out.println();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return dataName;
	}


}