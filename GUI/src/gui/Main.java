package gui;

import java.io.File;

public class Main {
	public static void main(String[] args) {


		try {
			Functions f = new Functions();

			String pass_name = new File("..").getCanonicalPath() + "\\GUI\\";

			//まだ処理されていないファイルをリストアップ
			String[] untreated_files = f.listUp(pass_name);

			//ラベルを取得
			String[][] dataName = f.dataName(pass_name);

			f.aaa(0, pass_name, untreated_files, dataName);


		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
