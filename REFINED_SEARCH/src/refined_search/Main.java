package refined_search;

import java.io.File;

public class Main {
	public static void main(String[] args) {


		try {
			Functions f = new Functions();

			String pass_name = new File("..").getCanonicalPath() + "\\REFINED_SEARCH\\";

			//まだ処理されていないファイルをリストアップ
			String[] files = f.listUp(pass_name);

			//ラベルを取得
			String[][] dataName = f.dataName(pass_name);

			//データを取得
			boolean[][][] data = f.dataGet(pass_name, files, dataName);

			f.aaa(0, pass_name, files, dataName, data);


		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}

