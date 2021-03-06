package scraping;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		dl_tex("https://ja.wikipedia.org/wiki/Category:%E8%90%BD%E8%AA%9E%E3%81%AE%E6%BC%94%E7%9B%AE");
		dl_tex("https://ja.wikipedia.org/w/index.php?title=Category:%E8%90%BD%E8%AA%9E%E3%81%AE%E6%BC%94%E7%9B%AE&pagefrom=%E3%81%97%E3%82%86%E3%81%86%E3%82%8D%E3%82%93%0A%E5%AE%97%E8%AB%96+%28%E8%90%BD%E8%AA%9E%29#mw-pages");
		dl_tex("https://ja.wikipedia.org/w/index.php?title=Category:%E8%90%BD%E8%AA%9E%E3%81%AE%E6%BC%94%E7%9B%AE&pagefrom=%E3%81%BF%E3%81%A4%E3%81%84%E3%81%AE%E3%81%9F%E3%81%84%E3%81%93%E3%81%8F%0A%E4%B8%89%E4%BA%95%E3%81%AE%E5%A4%A7%E9%BB%92#mw-pages");
	}


	public static void dl_tex(String url) {
		try {
			String pass_name = new File("..").getCanonicalPath() + "\\SCRAPING\\";
			String top = "https://ja.wikipedia.org";
			String body=null;

			//titleとURLのリストを取得
			String[][] a = Functions.list_scr(url);
			for(int i=0; i<a.length; i++) {
				System.out.println(a[i][0]);
				body = Functions.body_scr2(top+a[i][1]);
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

