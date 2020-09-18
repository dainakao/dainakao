package scraping;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		dl_tex("https://senjiyose.com/archives/43");
		}


	public static void dl_tex(String url) {
		try {
			String pass_name = new File("..").getCanonicalPath() + "\\SCRAPING_1000\\";
			String top = "https://senjiyose.com";
			String body=null;

			//titleとURLのリストを取得
			String[][] a = Functions.list_scr(url);
			for(int i=0; i<a.length; i++) {
				System.out.println(a[i][0]);
				System.out.println(a[i][1]);
				body = Functions.body_scr2(a[i][1]);
				System.out.println(body);

				//ファイルを作成し、bodyを書き込む
				File newfile = new File(pass_name + "data\\" + a[i][0] + ".txt");
				FileWriter filewriter = new FileWriter(newfile);
				filewriter.write(body);
				filewriter.close();
				Thread.sleep(1000);//負荷を減らすため、アクセスは１秒に１回
			}
		}catch(IOException e) {
			System.out.println(e);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}

