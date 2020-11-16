package rakugoSearch;

import java.io.File;
import java.io.IOException;

public class Main {
	static Functions f = new Functions();
	public static void main(String[] args) {
			try {
				String pass_name;
				pass_name = new File("..").getCanonicalPath() + "\\落語検索エンジン\\";
				//ファイルをリストアップ
				String[] files = f.listUp(pass_name + "\\condition\\");

				//ラベルを取得
				String[][] dataName = f.dataName(pass_name);

				//データを取得
				boolean[][][] data = f.dataGet(pass_name, files, dataName);

				//GUIを表示
				f.Start_Search(0, pass_name, files, dataName, data);
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
	}
}
