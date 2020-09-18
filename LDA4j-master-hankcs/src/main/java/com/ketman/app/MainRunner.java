package com.ketman.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import com.hankcs.lda.Corpus;
import com.hankcs.lda.LdaGibbsSampler;
import com.hankcs.lda.LdaUtil;

public class MainRunner {

	static int topic = 20;
	static int word = 15;

	public static void main(String[] args)	{
		InputStreamReader isr = new InputStreamReader(System.in);
		try {
			String str = "1";
			String pass_name = new File("..").getCanonicalPath() + "\\LDA4j-master-hankcs\\";
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
					// 1. Load corpus from disk
					Corpus corpus;
					try {
						corpus = Corpus.load("morphorogical");
						// 2. Create a LDA sampler
						LdaGibbsSampler ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getVocabularySize());
						// 3. Train it
						ldaGibbsSampler.gibbs(topic);
						// 4. The phi matrix is a LDA model, you can use LdaUtil to explain it.
						double[][] phi = ldaGibbsSampler.getPhi();
						Map<String, Double>[] topicMap = LdaUtil.translate(phi, corpus.getVocabulary(), word);
						LdaUtil.explain(topicMap);
					} catch (IOException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
				}else{
					System.out.println(str + "は無効な入力です");
				}
			}
		}catch(IOException e) {
			System.out.println("*入出力エラー*");
		}
	}
}
