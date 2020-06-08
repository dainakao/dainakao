package scraping;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Functions {
	//本文を抜き出す
	public static String body_scr(String str) {
		String body=null;
		//try-catch文が必要
		try {
			//Document A = Jsoup.connect("url").get(); urlにスクレイピング対象
			Document doc = Jsoup.connect(str).get();

			//Elements B = A.select("タグ"); この形でソースに含まれるタグで指定された範囲を書き出す。
			Elements elm = doc.select("body");

			//拡張for文
			for(Element elms : elm) {
				body = elms.text();
			}
			//例外処理
		}catch(IOException e) {
			e.printStackTrace();
		}
		return body;
	}

	//タイトルとリンクを２次配列で返却
	public static String[][] list_scr(String str) {
		ArrayList<String> title = new ArrayList<String>();
		ArrayList<String> url = new ArrayList<String>();
		//status 0=書き出さない・1=書き出す
		int status = 0;
		Elements links = url_get(str);
		for (Element link : links) {
			if(status == 1) {
				if(link.text().contentEquals("前のページ"))break;
				title.add(link.text());
				url.add(link.attr("href"));
			}else {
				if(link.text().contentEquals("次のページ")) status = 1;
			}
		}
		String[][] Strings = new String[title.size()][2];
		for(int i=0; i<title.size(); i++) {
			//titleを代入
			Strings[i][0] = title.get(i);
			//urlを代入
			Strings[i][1] = url.get(i);
		}
		return Strings;
	}

	//HTMLのリンクを書き出し
	public static void url_scr(String str) {
		//status 0=書き出さない・1=書き出す
		int status = 0;
		Elements links = url_get(str);
		for (Element link : links) {
			if(status == 1) {
				if(link.text().contentEquals("前のページ"))break;
				System.out.println(link.text());
				System.out.println(link.attr("href"));
			}else {
				if(link.text().contentEquals("次のページ")) status = 1;
			}

		}
	}

	//HTMLのa_hrefリンクをエレメントに代入
	public static Elements url_get(String str) {
		try {
			Document document = Jsoup.connect(str).get();

			Elements links = document.getElementsByTag("a").select("[href]");

			return links;
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}
}
