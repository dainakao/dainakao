package morphologicalSearch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;
import org.atilika.kuromoji.Tokenizer.Builder;

public class TF_IDF {
	Functions f = new Functions();

	//TF_IDF[morphorogical][文書ID]を作成
	public void make_TF_IDF(String file_name, String pass_name){
		int d;//文書dj内の単語tiの出現回数
		int[] now = f.Number_of_words(pass_name);//文書djのすべての単語の出現回数の和
		int N = f.count_file(pass_name);//総文書数
		int df;//単語tiが出現する文書数+1
		ArrayList<ArrayList<String>> ind = f.R_S(file_name, pass_name);
		double [][] TF_IDF = new double [f.count_word(file_name, pass_name)][now.length];//TF＿IDF値を入れる型
		//ID=morphorogicalの番号
		for(int ID=0; ID<TF_IDF.length; ID++) {
			for(int i=0; i<TF_IDF[0].length;i++) {
				TF_IDF[ID][i] = 0.0;

				//dfを求める・dを求める
				d=0;
				df=1;
				ArrayList<Integer> DF = new ArrayList<Integer>();
				for(int j=1; j<ind.get(ID).size();j=j+2) {
					//System.out.println(i+", "+ind.get(ID).get(j));
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
	//2字語句を新インデックスから検索
	public void search_TF_IDF(String file_name, String pass_name, String search_word) {
		if(search_word.equals("")) {
			System.out.println("検索語が入力されていません");
		}else {
			double[][] data = null;//0;TF_IDF値 1;ファイルの番号 2;出現位置

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

			boolean ans = false;
			System.out.println("検索結果");
			ArrayList<ArrayList<String>> tf_idf = f.R_S("tf_idf.csv", pass_name);//一時的な変数7
			for(int i=0; i<indexs.size(); i++) {
				if(indexs.get(i).get(0).equals(search_word)) {
					data = new double[(indexs.get(i).size()-1)/2][3];
					//0;TF_IDF値 1;ファイルの番号 2;出現位置
					for(int j=1; j<indexs.get(i).size(); j++) {
						if(j%2 == 1) {
							//System.out.print(tf_idf[Integer.parseInt(indexs.get(i).get(j))][i] +",");//tf_idfの値を出力
							//System.out.print(files[Integer.parseInt(indexs.get(i).get(j))].getName() +",");//ファイル番号を名前に変えて出力
							data[(j-1)/2][0] = Double.parseDouble(tf_idf.get(i).get(Integer.parseInt(indexs.get(i).get(j))));
							data[(j-1)/2][1] = Integer.parseInt(indexs.get(i).get(j));
						}else {
							//System.out.println(indexs.get(i).get(j));//出現位置を出力
							data[(j-1)/2][2] = Integer.parseInt(indexs.get(i).get(j));
						}
					}
					ans = true;
					break;
				}
			}
			//検出された際の重みづけ
			if(ans == true) {
				System.out.println("TF_IDF値, 演目名, 単語の出現場所");
				//quick(data[0], data[1], data[2], 0, data[0].length);
				for(int i=0; i<data.length; i++) {
					for(int j=0; j<data[0].length; j++) {
						if(j==1) {
							System.out.print(files[(int)data[i][j]].getName()+", ");
						}else if(j==2) {
							System.out.print((int)data[i][j]+", ");
						}else {
							System.out.print(data[i][j]+", ");
						}
					}
					System.out.println();
				}
			}else{
				System.out.println("該当する語句は検出されませんでした");
			}
			System.out.println();
		}
	}



	//1単語に対するTF_IDF値を返却(, , , ,転置インデックス)
	public double [] tf_idf(String pass_name, String search_word, ArrayList<ArrayList<String>> indexs, ArrayList<ArrayList<String>> tf_idf) {
		File file = new File(pass_name + "\\data");
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
	public void add_long_search_TF_IDF(String file_name, String pass_name, String search_word) {
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

			//ファイルごとのTF_IDF値のリストを制定[検索語の形態素解析数][ファイル数]
			double[][] datas_1 = new double[search_words.length][files.length];

			ArrayList<ArrayList<String>> tf_idf = f.R_S("tf_idf.csv", pass_name);//一時的な変数7

			for(int i=0; i<search_words.length; i++) {
				datas_1[i] = this.tf_idf(pass_name, search_words[i], indexs,  tf_idf);
			}
			System.out.println();

			//本格的に部分検索
			/*for(int h=0; h<search_words.length; h++) {
				for(int i=0; i<indexs.size(); i++) {
					if(indexs.get(i).get(0).equals(search_words[h])) {
						for(int j=1; j<indexs.get(i).size(); j=j+2) {
							datas_1[Integer.parseInt(indexs.get(i).get(j))][h] = tf_idf();
						}
					}
				}
			}*/

			double [][] answer = new double[2][files.length];//２{ファイル名・TF_IDF値}×ファイル数

			for(int i=0; i<answer[0].length; i++) {
				answer[0][i] = i;
				for(int j=0; j<search_words.length; j++) {
					answer[1][i] += datas_1[j][i]; 
				}
			}

			//数合わせの無駄な配列
			double [] fake = new double[files.length];
			for(int i=0; i<fake.length; i++)fake[i]=0;

			System.out.println(answer.length);
			System.out.println(answer[0].length);
			System.out.println(fake.length);
			//answer配列をクイックソート
			Functions.quick(answer[1], answer[0], fake, 0, answer[1].length-1);

			//結果を出力
			for(int i=0; i<answer[0].length; i++) {
				System.out.print(files[(int)answer[0][i]].getName() +", ");
				System.out.println(answer[1][i]);

			}
		}
	}



	//長い語句を新インデックスから検索(掛け算)
	public void mul_long_search_TF_IDF(String file_name, String pass_name, String search_word) {
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

			//ファイルごとのTF_IDF値のリストを制定[検索語の形態素解析数][ファイル数]
			double[][] datas_1 = new double[search_words.length][files.length];

			ArrayList<ArrayList<String>> tf_idf = f.R_S("tf_idf.csv", pass_name);//一時的な変数7

			for(int i=0; i<search_words.length; i++) {
				datas_1[i] = this.tf_idf(pass_name, search_words[i], indexs,  tf_idf);
			}
			System.out.println();

			//本格的に部分検索
			/*for(int h=0; h<search_words.length; h++) {
				for(int i=0; i<indexs.size(); i++) {
					if(indexs.get(i).get(0).equals(search_words[h])) {
						for(int j=1; j<indexs.get(i).size(); j=j+2) {
							datas_1[Integer.parseInt(indexs.get(i).get(j))][h] = tf_idf();
						}
					}
				}
			}*/

			double [][] answer = new double[2][files.length];//２{ファイル名・TF_IDF値}×ファイル数

			for(int i=0; i<answer[0].length; i++) {
				answer[0][i] = i;
				answer[1][i] = 1;
				for(int j=0; j<search_words.length; j++) {
					if(datas_1[j][i] == 0) {
						
					}else {
					answer[1][i] *= (1+datas_1[j][i]); 
					}
				}
			}

			//数合わせの無駄な配列
			double [] fake = new double[files.length];
			for(int i=0; i<fake.length; i++)fake[i]=0;

			System.out.println(answer.length);
			System.out.println(answer[0].length);
			System.out.println(fake.length);
			//answer配列をクイックソート
			Functions.quick(answer[1], answer[0], fake, 0, answer[1].length-1);

			//結果を出力
			for(int i=0; i<answer[0].length; i++) {
				System.out.print(files[(int)answer[0][i]].getName() +", ");
				System.out.println(answer[1][i]);

			}
		}
	}
}
