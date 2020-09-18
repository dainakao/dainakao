package scraping;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Functions {
	//全文を抜き出す
	public static String body_scr1(String str) {
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

	//本文の指定箇所を抜き出す
	public static String body_scr2(String str) {
		String ans = "";
		String body=null;
		//try-catch文が必要
		try {
			//Document A = Jsoup.connect("url").get(); urlにスクレイピング対象
			Document doc = Jsoup.connect(str).get();

			//Elements B = A.select("タグ"); この形でソースに含まれるタグで指定された範囲を書き出す
			//DIVタグを書き出す
			//Elements elm_p = doc.select("DIV");
			//クラス名を指定して書き出す
			Elements elm = doc.getElementsByClass("entry-content");
			Elements elm_p = elm.select("p");

			//拡張for文
			int status = 0;
			//status 0=書き出さない・1=書き出し・2=停止
			for(Element elms : elm_p) {
				if(status==1 && elms.className().equals("has-text-align-right has-small-font-size"))break;
				if(status==1 && elms.text().contentEquals("【しりたい】"))break;
				if(status == 1) {
					body = elms.text();
					ans += body;
				}
				if(elms.text().contentEquals("【あらすじ】")) status = 1;
			}

			//例外処理
		}catch(IOException e) {
			e.printStackTrace();
		}
		return ans;
	}

	//タイトルとリンクを２次配列で返却
	public static String[][] list_scr(String str) {
		ArrayList<String> title = new ArrayList<String>();
		ArrayList<String> url = new ArrayList<String>();
		//status 0=書き出さない・1=1Pか2Pの時の書き出し・2=2P3Pの判断・3=3Pの時の書き出し・4=書き出す
		int status = 0;
		Elements links = url_get(str);
		for (Element link : links) {
			if(link.text().contentEquals("青菜")) status = 1;

			if(status == 1) {
				if(!link.text().substring(0,1).contentEquals("【")) {
					title.add(link.text());
					url.add(link.attr("href"));
				}else{

				}

			}if(status == 0){

			}

			if(link.text().contentEquals("ん廻し"))break;
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
