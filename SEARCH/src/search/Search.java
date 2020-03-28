package search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Search {

	//文中に単語が何回出てきたか？
	String X;
	private int counter = 0;
	public int getcounter() {
		return this.counter;
	}
	public void setcounter(int i) {
		this.counter = i;
	}

	Search(String A){
		InputStreamReader isr = new InputStreamReader(System.in);

		try {
			//書き込み先を指定
			System.out.println("検索語を入力してください");
			BufferedReader br = new BufferedReader(isr);
			String str  = br.readLine();

			for(int i=0; i<A.length()-str.length(); i++ ) {
				if(Objects.equals(A.substring(i,i+str.length()), str)) {
					this.counter++;
				}
			}

			System.out.println(str + "は文中から" + this.counter + "個検出されました。");

		}
		catch(IOException e) {
			System.out.println("*入出力エラー*");
		}
	}
}
