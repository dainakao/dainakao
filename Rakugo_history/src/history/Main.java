package history;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		InputStreamReader isr = new InputStreamReader(System.in);
		try {
			Functions functions = new Functions();
			String str = "1";
			
			//データファイルを定義
			String file_name = "\\Rakugo_history\\data\\history.csv";
			//データファイルのカレントディレクトリを取得
			String pass_name = new File("..").getCanonicalPath();
			System.out.println(pass_name);
			while(!(str.equals("0"))) {
				System.out.println("演目管理システムです。何をしますか？\n 1:見た演目を追加する\n 2:今まで見た演目を確認する");
				BufferedReader br = new BufferedReader(isr);
				str  = br.readLine();

				//見た演目を追加する
				if(str.equals("1")){
					functions.Make_String(file_name, pass_name);

				//今まで見た演目を確認する
				}else if(str.equals("2")) {
					System.out.println(functions.Read_String(file_name, pass_name));

				}else{
					System.out.println(str + "は無効な入力です");
				}
				System.out.println();
				System.out.println();
			}
		}catch(IOException e) {
			System.out.println("*入出力エラー*");
		}
	}
}
