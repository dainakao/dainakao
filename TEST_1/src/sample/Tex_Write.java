package sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Tex_Write {

	public void Write() {

		InputStreamReader isr = new InputStreamReader(System.in);

		try {
			//書き込み先を指定
			System.out.println("書き込み先を入力してください");
			BufferedReader br = new BufferedReader(isr);
			String str  = br.readLine();
			System.out.println(str);
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(str)));


			//書き込む内容を入力
			System.out.println("書き込み内容を入力してください");
			String TEX  = br.readLine();
			pw.println(TEX);
			System.out.println("ファイルに書き込みました");

			isr.close();
			pw.close();
			br.close();
		}
		catch(IOException e) {
			System.out.println("*入出力エラー*");
		}
	}
}
