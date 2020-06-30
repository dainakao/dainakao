package morphologicalSearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;
import org.atilika.kuromoji.Tokenizer.Builder;

//morphorogicalを作る
public class Make_morphorogical {
	Functions f = new Functions();
	
	//morphorogicalを作る
	public void make_morphorogical(String pass_name) {
		//ファイル名の一覧を取得する
		File file = new File(pass_name + "\\data");
		File files[] = file.listFiles();

		String FN;//ファイル名
		for(int i=0; i<files.length; i++) {
			FN = files[i].getName();
			System.out.println(FN);
			try {
				//ファイルを読み込む
				FileReader fr = new FileReader(pass_name + "\\data\\" + FN);
				BufferedReader br = new BufferedReader(fr);

				//morphorogicalを作る
				this.make(FN, f.Read_String(FN,pass_name), pass_name);

				//終了処理
				br.close();
				fr.close();

			} catch (IOException ex) {
				//例外発生時処理
				ex.printStackTrace();
			}
		}
	}
	public void make(String file_name, String parseWord, String pass_name){
        Builder builder = Tokenizer.builder();
        // ノーマルモード
        Tokenizer normal = builder.build();
        List<Token> tokens = normal.tokenize(parseWord);
		
        ArrayList<String> morphorogical = new ArrayList<String>();
        for (Token token : tokens) {
        	morphorogical.add(token.getSurfaceForm());
        }


		//ファイルを作成
		File newfile = new File(pass_name + "\\morphorogical\\morphorogical_" + file_name);

		//エラー処理
		try {
			//morphorogicalを入力
			FileWriter filewriter = new FileWriter(newfile);
			for(int i=0; i<=morphorogical.size()-2; i++) {
					filewriter.write(morphorogical.get(i));
					if(i<=morphorogical.size()-3)filewriter.write("\n");
			}
			System.out.println("morphorogicalの作成に成功しました。");
			filewriter.close();
		}catch(IOException e) {
			System.out.println(e);
		}
	}

}