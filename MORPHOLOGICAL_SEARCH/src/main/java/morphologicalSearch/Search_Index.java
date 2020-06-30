package morphologicalSearch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;
import org.atilika.kuromoji.Tokenizer.Builder;

public class Search_Index {
	Functions f = new Functions();

	//2字語句を新インデックスから検索
	public void search_aaa(String file_name, String pass_name, String s_w) {
		String search_word = s_w;//検索する語句
		ArrayList<ArrayList<String>> indexs = new ArrayList<ArrayList<String>>();//転地インデックスをアレイリストで

		//indexに転置インデックスを代入
		String a[] = f.Read_Strings(file_name, pass_name);//一時的な変数

		ArrayList<String> index = new ArrayList<String>();//転地インデックス
		for(int i=0; i<a.length; i++) {
			if(a[i].equals(",")) {
			}else if(a[i].equals("\n")){
				indexs.add(index);
				index = new ArrayList<String>();//転地インデックス
			}else {
				index.add(a[i]);
			}
		}
		System.out.println(search_word);

		File file = new File(pass_name + "\\data");
		File files[] = file.listFiles();

		boolean ans = false;
		System.out.println("検索結果");
		for(int i=0; i<indexs.size(); i++) {
			if(indexs.get(i).get(0).equals(search_word)) {
				for(int j=1; j<indexs.get(i).size(); j++) {
					if(j%2 == 1) {
						System.out.print(files[Integer.parseInt(indexs.get(i).get(j))].getName() + ",");
					}else {
						System.out.println(indexs.get(i).get(j));
					}
				}
				ans = true;
				break;
			}
		}
		if(ans == false)System.out.println("該当する語句は検出されませんでした");
		System.out.println();
	}

	//長い語句を新インデックスから検索
	public void long_search_aaa(String file_name, String pass_name, String search_word) {
		if(search_word.equals("")) {
			System.out.println("検索語が入力されていません");
		}else {
		String search_words[];//検索語句
		ArrayList<ArrayList<String>> indexs = new ArrayList<ArrayList<String>>();//転地インデックスをアレイリストで

		//indexに転置インデックスを代入
		String a[] = f.Read_Strings(file_name, pass_name);//一時的な変数

		ArrayList<String> index = new ArrayList<String>();//転地インデックス
		for(int i=0; i<a.length; i++) {
			if(a[i].equals(",")) {
			}else if(a[i].equals("\n")){
				indexs.add(index);
				index = new ArrayList<String>();//転地インデックス
			}else {
				index.add(a[i]);
			}
		}
		File file = new File(pass_name + "\\data");
		File[] files = file.listFiles();//ファイル名リスト


		//検索部位
		System.out.println("～検索結果～");
		//まずは完全一致検索から
		System.out.println("・完全一致");

		//検索語をmorphorogical状にカット
		Builder builder = Tokenizer.builder();
		// ノーマルモード
		Tokenizer normal = builder.build();
		List<Token> tokens = normal.tokenize(search_word);

		search_words = new String[tokens.size()];
		int counter=0;//一時的な変数
		for (Token token : tokens) {
			search_words[counter] = token.getSurfaceForm();
			counter++;
		}

		//部分一致検索の合致リストを制定

		boolean[][] answers = new boolean[files.length][search_words.length];//部分検索の合致リスト
		//初期化
		for(int i=0; i<answers.length; i++) {
			for(int j=0; j<answers[0].length; j++) {
				answers[i][j] = false;
			}
		}

		boolean ans = false;//一時的な変数
		for(int i=0; i<indexs.size(); i++) {
			if(indexs.get(i).get(0).equals(search_word)) {
				for(int j=1; j<indexs.get(i).size(); j++) {
					if(j%2 == 1) {
						System.out.print(files[Integer.parseInt(indexs.get(i).get(j))].getName() + ",");
					}else {
						System.out.println(indexs.get(i).get(j));
					}
				}
				ans = true;
				break;
			}
		}
		if(ans == false)System.out.println("該当無し");
		System.out.println();

		//本格的に部分検索
		for(int h=0; h<search_words.length; h++) {
			for(int i=0; i<indexs.size(); i++) {
				if(indexs.get(i).get(0).equals(search_words[h])) {
					for(int j=1; j<indexs.get(i).size(); j=j+2) {
						answers[Integer.parseInt(indexs.get(i).get(j))][h] = true;
					}
				}
			}
		}
		//部分検索の結果を表示
		System.out.println();
		System.out.println("・部分一致");
		for(int h=0; h<answers[0].length; h++) {
			System.out.print(search_words[h]+",");
		}
		System.out.println();
		for(int h=0; h<answers.length; h++) {
			for(int i=0; i<answers[h].length; i++) {
				for(int j=search_words[i].length(); j>1; j--)System.out.print("　");
				if(answers[h][i]==true) {
					System.out.print("〇,");
				}else {
					System.out.print("×,");
				}
			}
			System.out.println(";"+files[h]);
		}
		}
	}

}