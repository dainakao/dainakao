package breakbee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Main {

	static int topic = 30;
	static int word = 10;
	static int trial = 20;

	public static void main(String[] args) throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
		try {
			String str = "1";
			String pass_name = new File("..").getCanonicalPath() + "\\LDA4j-master-breakbee\\";
			System.out.println(pass_name);
			while(!(str.equals("0"))) {
				System.out.println("何をしますか？");
				System.out.println("0;終了");
				System.out.println("1;形態素解析（分かち書き）");
				System.out.println("2;LDA実行");
				System.out.println();
				BufferedReader br = new BufferedReader(isr);
				str  = br.readLine();
				Functions f = new Functions();


				if(str.equals("1")) {

					f.make_morphorogical_2(pass_name);
				}else if(str.equals("2")) {
					ArrayList<String[]> corpus = new ArrayList<String[]>();

					String filename = "morphorogical_index.txt";
					int k = topic;
					int iter = trial;

					String summaryPath = "summary.txt";

					String pathTheta = "theta.csv";
					String pathPhi = "phi.csv";

					if(args.length == 0){
						System.out.println("Please input file name!");
						//return;
					}

					if(args.length > 0){
						filename = args[0];
					}

					if(args.length > 1){
						k = Integer.parseInt(args[1]);
					}

					if(args.length > 2){
						iter = Integer.parseInt(args[2]);
					}

					if(args.length > 3){
						summaryPath = args[3];
					}

					if(args.length > 4){
						pathTheta = args[4];
					}

					if(args.length > 5){
						pathPhi = args[5];
					}

					BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
					String line = null;
					while((line=reader.readLine())!=null){
						String[] words = line.split(" ");
						corpus.add(words);
					}
					reader.close();

					LDA lda = new LDA(corpus, k);
					lda.fit(iter, true);
					lda.summarize(0, word, summaryPath);
					lda.saveParameters(pathTheta, pathPhi);
					System.out.println("LDA completed.");
				}else{
					System.out.println(str + "は無効な入力です");
				}
			}
		}catch(IOException e) {
			System.out.println("*入出力エラー*");
		}
	}
}
