package history;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Functions {
	private String[] data = new String[7];

	//テキストを配列に代入、Stringで返却
	public String Read_String(String file_name, String pass_name){
		String b ="";//一時的な変数
		FileReader fr = null;{
			//finallyで使うのでここで宣言
			try {
				//読み込み先を指定
				File file = new File(pass_name + file_name);
				BufferedReader br = new BufferedReader(new FileReader(file));

				//文字列型に代入できる
				String s;

				//何も無かったらnullが返る
				while((s = br.readLine()) != null){
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


	//テキストにデータを入力
	public void Make_String(String file_name, String pass_name) {
		// 0, 1, 2,  3,   4,   5,   6,
		//年,月,日,場所,芸種,演者,演目
		for(int i=0; i<1;){
			System.out.println("演芸を見た日を入力してください。\n 例:2020年1月1日の場合\n2020\n01\n01");
			data[0] = this.keyboard();//年
			data[1] = this.keyboard();//月
			data[2] = this.keyboard();//日

			System.out.println("\n\n演芸を見た場所を入力してください。\n 例:浅草演芸ホール");
			data[3] = this.keyboard();//場所

			System.out.println("\n\n見た芸種を入力してください。\n 1:落語 2:色物 3:コーナー 4:その他");
			data[4] = this.keyboard();//芸種

			System.out.println("\n\n見た演者を入力してください。\n 例:立川志の輔");
			data[5] = this.keyboard();//演者

			System.out.println("\n\n見た演目を入力してください。\n 例:");
			data[6] = this.keyboard();//演目

			System.out.println("\n\n入力内容はこちらで間違いありませんか？");
			System.out.println("日時:" + data[0]+"/"+data[1]+"/"+data[2]);
			System.out.println("場所:"+data[3]);
			System.out.println("芸種:"+data[4]);
			System.out.println("演者:"+data[5]);
			System.out.println("演目:"+data[6]);
			System.out.println("1:確定 2:訂正");

			String a = this.keyboard();//一時的な変数
			if(a.equals("1")||a.equals("１")) {
				i++;//１が入力されれば次のステップへ進む
				System.out.println("次のステップへ");
			}
		}

		try {
			//入力をファイルに書き込む
			File file = new File(pass_name + file_name);
			FileWriter filewriter = new FileWriter(file, true);
			for(int i=0; i<data.length; i++) {
				filewriter.write(data[i]+",");
				System.out.println(data[i]+"を書き込みました");
			}
			filewriter.write("\n");
			filewriter.close();
			System.out.println("書き込みが完了しました");
		}catch(IOException e){
			System.out.println(e);
		}
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
