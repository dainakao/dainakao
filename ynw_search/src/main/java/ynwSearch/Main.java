package ynwSearch;

import java.io.File;
import java.io.IOException;

public class Main {
	static Functions f = new Functions();
	public static void main(String[] args) {
			try {
				String pass_name;
				pass_name = new File("./").getCanonicalPath() + "\\";
				System.out.println(pass_name);
				f.menuStart(pass_name);
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
	}
}
