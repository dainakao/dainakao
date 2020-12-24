package ynwSearch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;
import org.atilika.kuromoji.Tokenizer.Builder;

public class TF_IDF extends JFrame{
	Functions f = new Functions();

	//TF_IDF[morphorogical][文書ID]を作成
	public void make_TF_IDF(String index_file_name, String pass_name){
		int d;//文書dj内の単語tiの出現回数
		int[] now = f.Number_of_words(pass_name);//文書djのすべての単語の出現回数の和
		int N = f.count_file(pass_name);//総文書数
		int df;//単語tiが出現する文書数+1
		ArrayList<ArrayList<String>> ind = f.R_S(index_file_name, pass_name);
		double [][] TF_IDF = new double [f.count_word(index_file_name, pass_name)][now.length];//TF＿IDF値を入れる型
		//ID=morphorogicalの番号
		for(int ID=0; ID<TF_IDF.length; ID++) {
			for(int i=0; i<TF_IDF[0].length;i++) {
				TF_IDF[ID][i] = 0.0;

				//dfを求める・dを求める
				d=0;
				df=0;
				ArrayList<Integer> DF = new ArrayList<Integer>();
				for(int j=1; j<ind.get(ID).size();j=j+2) {
					if(i==Integer.parseInt(ind.get(ID).get(j))) d++;
					DF.add(Integer.parseInt(ind.get(ID).get(j)));
				}
				Collections.sort(DF);
				int a=DF.get(0);//一時的な変数
				for(int j=0; j<DF.size();j++) {
					if(a!=DF.get(j)) {
						df++;
						a=DF.get(j);
					}
				}

				double TF = (double)d/(double)now[i];
				double IDF = Math.log((double)N/(double)df);
				TF_IDF[ID][i] = TF*IDF;
			}
		}
		try {
			File newfile = new File(pass_name+"\\tf_idf.csv");
			FileWriter filewriter = new FileWriter(newfile);
			for(int j=0; j<TF_IDF.length; j++) {
				for(int h=0; h<TF_IDF[j].length; h++) {
					filewriter.write(String.valueOf(TF_IDF[j][h]));
					if(h!=TF_IDF[0].length-1)filewriter.write(",");
				}
				filewriter.write("\n");
				filewriter.flush();
			}
			filewriter.close();
			System.out.println("TF_IDFを作成しました");
		}catch(IOException e) {
			System.out.println(e);
		}
	}

	//検索→TF_IDFを用いて順位付け
	//1単語に対するTF_IDF値を返却(, , , ,転置インデックス)
	public double [] tf_idf(String pass_name, String search_word, ArrayList<ArrayList<String>> indexs, ArrayList<ArrayList<String>> tf_idf) {
		File file = new File(pass_name + "\\file");
		File[] files = file.listFiles();//ファイル名リスト
		double answer[] = new double[files.length];//単語の全TF_IDF値を格納する配列[ファイル数]

		//TF_IDF値[morphorogical][文書ID]

		for(int i=0; i<indexs.size(); i++) {
			if(indexs.get(i).get(0).equals(search_word)) {
				for(int j=0; j<tf_idf.get(i).size(); j++) {
					answer[j] = Double.parseDouble(tf_idf.get(i).get(j));
				}
				break;
			}
		}
		return answer;
	}


	//長い語句を新インデックスから検索(足し算)
	public String[][] add_long_search_TF_IDF(String file_name, String pass_name, String search_word, String[] matched_r) {
		String [][] final_matched;
		if(search_word.equals("")) {
			final_matched = new String[2][matched_r.length];
			//結果を出力
			final_matched [0] = matched_r;
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
			File file = new File(pass_name + "\\file");
			File[] files = file.listFiles();//ファイル名リスト

			//検索部位
			System.out.println("～検索結果～");

			//検索語をmorphorogical状にカット
			Builder builder = Tokenizer.builder();
			// ノーマルモード
			Tokenizer normal = builder.build();
	        Tokenizer tokenizer = null;
	        try {
	            tokenizer = builder.userDictionary("./user_dic.csv").build();
	        } catch (IOException e1) {
	        }
			List<Token> tokens = tokenizer.tokenize(search_word);
			//空白を取り除く
			for (int i=0; i<tokens.size(); i++) {
				System.out.print(tokens.get(i).getSurfaceForm());
				if(tokens.get(i).getSurfaceForm().equals(" ")||tokens.get(i).getSurfaceForm().equals("　")) {
					tokens.remove(tokens.indexOf(tokens.get(i)));
					i--;
				}
			}
			System.out.println();
			
			search_words = new String[tokens.size()*2];
			int counter=0;//一時的な変数
			//原本を検索配列に代入
			for (Token token : tokens) {
				search_words[counter] = token.getSurfaceForm();
				counter++;
			}
			//原本の読みを代入
			for (Token token : tokens) {
				search_words[counter] = token.getReading();
				counter++;
			}

			//ファイルごとのTF_IDF値のリストを制定[検索語の形態素解析数][ファイル数]
			double[][] datas_1 = new double[search_words.length][files.length];

			ArrayList<ArrayList<String>> tf_idf = f.R_S("tf_idf.csv", pass_name);//一時的な変数7

			for(int i=0; i<search_words.length; i++) {
				System.out.println(search_words[i]);
				datas_1[i] = this.tf_idf(pass_name, search_words[i], indexs,  tf_idf);
			}
			System.out.println();

			double [][] answer = new double[2][files.length];//２{ファイル名・TF_IDF値}×ファイル数

			for(int i=0; i<answer[0].length; i++) {
				answer[0][i] = i;
				for(int j=0; j<search_words.length; j++) {
					answer[1][i] += datas_1[j][i];
				}
			}

			//数合わせの無駄な配列（quickを動かすため）
			double [] fake = new double[files.length];
			for(int i=0; i<fake.length; i++)fake[i]=0;

			//answer配列をクイックソート
			Functions.quick(answer[1], answer[0], fake, 0, answer[1].length-1);

			//条件検索にも引っ掛かっているもののみを絞り込み
			ArrayList<String> matched_w = new ArrayList<String>();
			ArrayList<Double> tf_value = new ArrayList<Double>();
			for(int i=0; i<answer[0].length; i++) {
				matched_w.add(files[(int)answer[0][i]].getName());
				tf_value.add(answer[1][i]);
			}
			for(int i=0; i<matched_w.size(); i++) {
				second: for(int j=0; j<matched_r.length; j++) {
					if(matched_w.get(i).equals(matched_r[j]) && tf_value.get(i)!=0.0) {
						break second;
					}
					if(j==matched_r.length-1) {
						matched_w.remove(i);
						tf_value.remove(i);
						i--;
					}
				}
			}

			//答えを返す
			final_matched = new String[2][matched_w.size()];
			for(int i=0; i<matched_w.size(); i++) {
				final_matched[0][i] = matched_w.get(i);
				final_matched[1][i] = String.valueOf(tf_value.get(i));
			}
		}
		return final_matched;
	}
}