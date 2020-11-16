package rakugoSearch;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Ynw_Search extends JFrame {
	private static final long serialVersionUID = 1L;
	// ウィンドウ本体

	Functions f = new Functions();
	public int Selected = 0;
	JRadioButton[] radio;
	ButtonGroup bgroup_pp;
	public int final_matched_length = 0;

	//GUIを設定・表示
	public void conditionSelect(int number,String pass_name, String[] files, String[][] dataName, boolean[][][] data){
		int fontsize = 12;//フォントサイズ

		//データ配列を宣言、初期化（すべてfalse）
		boolean[][] conditions = new boolean[dataName.length+1][dataName[0].length];
		for(int i=0; i<dataName.length; i++) {
			for(int j=0; j<dataName[0].length; j++) {
				conditions[i][j] = false;
			}
		}

		// ウィンドウの閉じ方
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 位置とサイズ
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screenSize.width, screenSize.height);

		int counter = 0;
		int C;
		int distance = 6;

		//種類ラジオボタン作成
		ButtonGroup bgroup1 = new ButtonGroup();
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
		JLabel label = new JLabel("分類");//
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		p1.add(label);
		p1.add(Box.createRigidArea(new Dimension(0,distance)));
		for(int i=0; i<dataName[0].length; i++) {
			if(dataName[0][counter].contentEquals("")) break;
			counter++;
		}
		C = counter;
		JRadioButton radio1[] = new JRadioButton[C];
		for(int i=0; i<C; i++) {
			radio1[i] = new JRadioButton(dataName[0][i]);
			radio1[i].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, fontsize));
			bgroup1.add(radio1[i]);
			p1.add(radio1[i]);
			p1.add(Box.createRigidArea(new Dimension(0,distance)));
		}
		p1.add(Box.createRigidArea(new Dimension(0,2*(fontsize+distance))));


		counter=0;
		//古典・新作ラジオボタン作成
		ButtonGroup bgroup2 = new ButtonGroup();
		label = new JLabel("古典・新作");//
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		p1.add(label);
		p1.add(Box.createRigidArea(new Dimension(0,distance)));
		for(int i=0; i<dataName[0].length; i++) {
			if(dataName[1][counter].contentEquals("")) break;
			counter++;
		}
		C = counter;
		JRadioButton radio2[] = new JRadioButton[C];
		for(int i=0; i<C; i++) {
			radio2[i] = new JRadioButton(dataName[1][i]);
			radio2[i].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, fontsize));
			bgroup2.add(radio2[i]);
			p1.add(radio2[i]);
			p1.add(Box.createRigidArea(new Dimension(0,distance)));
		}
		p1.add(Box.createRigidArea(new Dimension(0,2*(fontsize+distance))));


		counter=0;
		//演目の大きさラジオボタン作成
		ButtonGroup bgroup3 = new ButtonGroup();
		label = new JLabel("演目の大きさ");//
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		p1.add(label);
		p1.add(Box.createRigidArea(new Dimension(0,distance)));
		for(int i=0; i<dataName[0].length; i++) {
			if(dataName[2][counter].contentEquals("")) break;
			counter++;
		}
		C = counter;
		JRadioButton radio3[] = new JRadioButton[C];
		for(int i=0; i<C; i++) {
			radio3[i] = new JRadioButton(dataName[2][i]);
			radio3[i].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, fontsize));
			bgroup3.add(radio3[i]);
			p1.add(radio3[i]);
			p1.add(Box.createRigidArea(new Dimension(0,distance)));
		}
		p1.add(Box.createVerticalGlue());


		counter=0;
		//人物ラジオボタン作成
		for(int i=0; i<dataName[0].length; i++) {
			if(dataName[3][counter].contentEquals("")) break;
			counter++;
		}
		C = counter;
		ButtonGroup[] bgroup4 = new ButtonGroup[C];
		JRadioButton radio4_Y[] = new JRadioButton[C];
		JRadioButton radio4_U[] = new JRadioButton[C];
		JRadioButton radio4_N[] = new JRadioButton[C];

		//「出ている」ラジオボタン
		JPanel p4_Y = new JPanel();
		JPanel p5_Y = new JPanel();
		p4_Y.setLayout(new BoxLayout(p4_Y, BoxLayout.PAGE_AXIS));
		p5_Y.setLayout(new BoxLayout(p5_Y, BoxLayout.PAGE_AXIS));
		//「未選択」ラジオボタン
		JPanel p4_U = new JPanel();
		JPanel p5_U = new JPanel();
		p4_U.setLayout(new BoxLayout(p4_U, BoxLayout.PAGE_AXIS));
		p5_U.setLayout(new BoxLayout(p5_U, BoxLayout.PAGE_AXIS));
		//「出ていない」ラジオボタン
		JPanel p4_N = new JPanel();
		JPanel p5_N = new JPanel();
		p4_N.setLayout(new BoxLayout(p4_N, BoxLayout.PAGE_AXIS));
		p5_N.setLayout(new BoxLayout(p5_N, BoxLayout.PAGE_AXIS));

		for(int i=0; i<C; i++) {
			bgroup4[i] = new ButtonGroup();//ボタングループ

			radio4_Y[i] = new JRadioButton("　");
			radio4_Y[i].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, fontsize));
			bgroup4[i].add(radio4_Y[i]);//ボタンをグループに追加

			radio4_U[i] = new JRadioButton("　");
			radio4_U[i].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, fontsize));
			bgroup4[i].add(radio4_U[i]);//ボタンをグループに追加
			radio4_U[i].setSelected(true);

			radio4_N[i] = new JRadioButton(dataName[3][i]);
			radio4_N[i].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, fontsize));
			bgroup4[i].add(radio4_N[i]);//ボタンをグループに追加
			if(i<C/2) {
				if(i==0) {
					JLabel Yes_No = new JLabel("<html>出<br>る<br>　");//
					Yes_No.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, fontsize));
					Yes_No.setMaximumSize(new Dimension(fontsize, fontsize));
					p4_Y.add(Yes_No);
					p4_Y.add(Box.createRigidArea(new Dimension(0,distance)));
					Yes_No = new JLabel("<html>未<br>選<br>択");//
					Yes_No.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, fontsize));
					Yes_No.setMaximumSize(new Dimension(fontsize, fontsize));
					p4_U.add(Yes_No);
					p4_U.add(Box.createRigidArea(new Dimension(0,distance)));
					Yes_No = new JLabel("<html>出<br>な<br>い");//
					Yes_No.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, fontsize));
					Yes_No.setMaximumSize(new Dimension(fontsize, fontsize));
					p4_N.add(Yes_No);
					p4_N.add(Box.createRigidArea(new Dimension(0,distance)));
				}
				p4_Y.add(radio4_Y[i]);
				p4_Y.add(Box.createRigidArea(new Dimension(0,distance)));
				p4_U.add(radio4_U[i]);
				p4_U.add(Box.createRigidArea(new Dimension(0,distance)));
				p4_N.add(radio4_N[i]);
				p4_N.add(Box.createRigidArea(new Dimension(0,distance)));
			}else {
				if(i==C/2) {
					JLabel Yes_No = new JLabel("<html>出<br>る<br>　");//
					Yes_No.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, fontsize));
					Yes_No.setMaximumSize(new Dimension(fontsize, fontsize));
					p5_Y.add(Yes_No);
					p5_Y.add(Box.createRigidArea(new Dimension(0,distance)));
					Yes_No = new JLabel("<html>未<br>選<br>択");//
					Yes_No.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, fontsize));
					Yes_No.setMaximumSize(new Dimension(fontsize, fontsize));
					p5_U.add(Yes_No);
					p5_U.add(Box.createRigidArea(new Dimension(0,distance)));
					Yes_No = new JLabel("<html>出<br>な<br>い");//
					Yes_No.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, fontsize));
					Yes_No.setMaximumSize(new Dimension(fontsize, fontsize));
					p5_N.add(Yes_No);
					p5_N.add(Box.createRigidArea(new Dimension(0,distance)));
				}
				p5_Y.add(radio4_Y[i]);
				p5_Y.add(Box.createRigidArea(new Dimension(0,distance)));
				p5_U.add(radio4_U[i]);
				p5_U.add(Box.createRigidArea(new Dimension(0,distance)));
				p5_N.add(radio4_N[i]);
				p5_N.add(Box.createRigidArea(new Dimension(0,distance)));
			}
		}
		p4_Y.add(Box.createVerticalGlue());
		p4_U.add(Box.createVerticalGlue());
		p4_N.add(Box.createVerticalGlue());
		p5_Y.add(Box.createVerticalGlue());
		p5_U.add(Box.createVerticalGlue());
		p5_N.add(Box.createVerticalGlue());


		counter=0;
		//チェックボックス作成
		JPanel p6 = new JPanel();
		p6.setLayout(new BoxLayout(p6, BoxLayout.PAGE_AXIS));
		label = new JLabel("舞台");//
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		p6.add(label);
		for(int i=0; i<dataName[0].length; i++) {
			if(dataName[4][counter].contentEquals("")) break;
			counter++;
		}
		C = counter;
		JCheckBox check5[] = new JCheckBox[C];
		for(int i=0; i<C; i++) {
			check5[i] = new JCheckBox(dataName[4][i]);
			check5[i].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, fontsize));
			p6.add(check5[i]);
			p6.add(Box.createRigidArea(new Dimension(0,distance)));
		}


		//検索ボックス作成
		p6.add(Box.createRigidArea(new Dimension(0,3*distance)));
		label = new JLabel("キーワード");//演目表示ラベル追加
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		p6.add(label);
		p6.add(Box.createRigidArea(new Dimension(0,distance)));
		JTextField search_box = new JTextField();
		search_box.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, fontsize));
		search_box.setMaximumSize(new Dimension(30*fontsize, 2*fontsize));
		p6.add(search_box);

		//不適切除外ボタン作成
		p6.add(Box.createRigidArea(new Dimension(0,3*distance)));
		label = new JLabel("不適切な表現を");//演目表示ラベル追加
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		p6.add(label);
		label = new JLabel("含む演目を除外する");//演目表示ラベル追加
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		p6.add(label);

		JCheckBox check6 = new JCheckBox(dataName[5][0]);
		p6.add(check6);

		// ボタン作成
		JButton btn1 = new JButton("　検索　");
		btn1.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		btn1.setPreferredSize(new Dimension(4*btn1.getUIClassID().length()*fontsize, 4*fontsize));// ボタン追加
		JButton btn2 = new JButton("リセット");
		btn2.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		btn2.setPreferredSize(new Dimension(4*btn2.getUIClassID().length()*fontsize, 4*fontsize));// ボタン追加

		p6.add(Box.createRigidArea(new Dimension(0,distance)));
		p6.add(btn1);
		p6.add(Box.createRigidArea(new Dimension(0,distance)));
		p6.add(btn2);
		p6.add(Box.createVerticalGlue());

		//結果出力エリア
		JPanel pp = new JPanel();
		pp.setLayout(new BoxLayout(pp, BoxLayout.Y_AXIS));

		String[] matched = dataSearch(files, dataName, data, conditions);
		TF_IDF tf = new TF_IDF();
		String[][] final_matched = tf.add_long_search_TF_IDF("Inverted_Index.csv", pass_name, search_box.getText(), matched);
		final_matched_length = final_matched[0].length;

		radio = new JRadioButton[final_matched_length];
		bgroup_pp = new ButtonGroup();

		for(int i=0; i<final_matched_length; i++) {
			radio[i] = new JRadioButton(final_matched[0][i] + ", " + final_matched[1][i]);
			radio[i].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, fontsize));
			radio[i].setPreferredSize(new Dimension(4*radio[i].getUIClassID().length()*fontsize, 4*fontsize));// ボタン追加
			pp.add(radio[i]);
			bgroup_pp.add(radio[i]);
		}

		JScrollPane  scrollpane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollpane.setBounds(0, 0, 510, 200);
		this.getContentPane().add(scrollpane);
		scrollpane.setViewportView(pp);
		scrollpane.getVerticalScrollBar().setUnitIncrement(25);
		//詳細ボタン
		JPanel p7 = new JPanel();
		p7.setLayout(new BoxLayout(p7, BoxLayout.PAGE_AXIS));
		JButton btn3 = new JButton("あらすじを見る");
		btn3.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		btn3.setPreferredSize(new Dimension(7*btn3.getUIClassID().length()*fontsize, 4*fontsize));
		p7.add(btn3);
		p7.add(scrollpane);

		//上記のパネルをひとまとめに
		Container contentPane = getContentPane();
		setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		getContentPane().add(Box.createRigidArea(new Dimension(fontsize,0)));
		getContentPane().add(p1);
		getContentPane().add(Box.createRigidArea(new Dimension(3*fontsize,0)));
		getContentPane().add(p4_Y);
		getContentPane().add(p4_U);
		getContentPane().add(p4_N);
		getContentPane().add(p5_Y);
		getContentPane().add(p5_U);
		getContentPane().add(p5_N);
		getContentPane().add(p6);
		getContentPane().add(p7);

		for (int i = 0 ; i < final_matched_length; i++){
			if (radio[i].isSelected()){
				Selected = i;
			}
		}

		//検索クリック時の処理
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//種類の選択内容確認
				for (int i = 0 ; i < radio1.length; i++){
					if (radio1[i].isSelected()){
						conditions[0][i] = true;
					}else{
						conditions[0][i] = false;
					}
				}

				//古典・新作の選択内容確認
				for (int i = 0 ; i < radio2.length; i++){
					if (radio2[i].isSelected()){
						conditions[1][i] = true;
					}else{
						conditions[1][i] = false;
					}
				}

				//江戸・上方の選択内容確認
				for (int i = 0 ; i < radio3.length; i++){
					if (radio3[i].isSelected()){
						conditions[2][i] = true;
					}else{
						conditions[2][i] = false;
					}
				}

				//登場人物の選択内容確認
				for (int i = 0 ; i < radio4_Y.length; i++){
					//登場人物の「出る」の選択内容をチェック
					if (radio4_Y[i].isSelected()){
						conditions[3][i] = true;
					}else{
						conditions[3][i] = false;
					}
					//登場人物の「出ない」の選択内容をチェック
					if (radio4_N[i].isSelected()){
						conditions[conditions.length-1][i] = true;
					}else{
						conditions[conditions.length-1][i] = false;
					}
				}

				//舞台の選択内容確認
				for (int i = 0 ; i < check5.length; i++){
					if (check5[i].isSelected()){
						conditions[4][i] = true;
					}else{
						conditions[4][i] = false;
					}
				}

				//不適切な表現を含む演目の除外確認
				if (check6.isSelected()){
					conditions[5][0] = true;
				}else{
					conditions[5][0] = false;
				}

				//検索
				pp.removeAll();
				scrollpane.removeAll();
				p7.removeAll();
				contentPane.remove(contentPane.getComponentCount()-1);

				String[] matched = dataSearch(files, dataName, data, conditions);
				TF_IDF tf = new TF_IDF();
				String[][] final_matched = tf.add_long_search_TF_IDF("Inverted_Index.csv", pass_name, search_box.getText(), matched);
				final_matched_length = final_matched[0].length;

				radio = new JRadioButton[final_matched_length];
				bgroup_pp = new ButtonGroup();

				for(int i=0; i<final_matched_length; i++) {
					radio[i] = new JRadioButton(final_matched[0][i] + ", " + final_matched[1][i]);
					radio[i].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, fontsize));
					radio[i].setPreferredSize(new Dimension(4*radio[i].getUIClassID().length()*fontsize, 4*fontsize));// ボタン追加
					pp.add(radio[i]);
					bgroup_pp.add(radio[i]);
				}
				JScrollPane scrollpane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollpane.setBounds(0, 0, 510, 200);
				getContentPane().add(scrollpane);
				scrollpane.setViewportView(pp);
				scrollpane.getVerticalScrollBar().setUnitIncrement(25);
				contentPane.revalidate();
				//詳細ボタン
				JPanel p7 = new JPanel();
				p7.setLayout(new BoxLayout(p7, BoxLayout.PAGE_AXIS));
				btn3.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
				btn3.setPreferredSize(new Dimension(4*btn3.getUIClassID().length()*fontsize, 4*fontsize));
				p7.add(btn3);
				p7.add(scrollpane);
				getContentPane().add(p7);
			}
		});

		//リセットクリック時の処理
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("リセット");
				//種類のリセット
				bgroup1.clearSelection();

				//古典・新作のリセット
				bgroup2.clearSelection();

				//江戸・上方のリセット
				bgroup3.clearSelection();

				//登場人物のリセット
				for (int i = 0 ; i < bgroup4.length; i++){
					bgroup4[i].clearSelection();
					radio4_U[i].setSelected(true);
				}

				//チェックボックスのリセット
				for (int i = 0 ; i < check5.length; i++){
					check5[i].setSelected(false);
				}
			}
		});

		//詳細クリック時の処理
		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0 ; i < final_matched_length; i++){
					if (radio[i].isSelected()){
						Selected = i;
					}
				}

				//検索
				pp.removeAll();
				scrollpane.removeAll();
				p7.removeAll();
				contentPane.remove(contentPane.getComponentCount()-1);

				String[] matched = dataSearch(files, dataName, data, conditions);
				TF_IDF tf = new TF_IDF();
				String[][] final_matched = tf.add_long_search_TF_IDF("Inverted_Index.csv", pass_name, search_box.getText(), matched);
				final_matched_length = final_matched[0].length;

				radio = new JRadioButton[final_matched_length];
				bgroup_pp = new ButtonGroup();

				for(int i=0; i<final_matched_length; i++) {
					radio[i] = new JRadioButton(final_matched[0][i] + ", " + final_matched[1][i]);
					radio[i].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, fontsize));
					radio[i].setPreferredSize(new Dimension(4*radio[i].getUIClassID().length()*fontsize, 4*fontsize));// ボタン追加
					pp.add(radio[i]);
					bgroup_pp.add(radio[i]);
				}
				JScrollPane  scrollpane = new JScrollPane();
				scrollpane.setBounds(0, 0, 510, 200);
				getContentPane().add(scrollpane);
				scrollpane.setViewportView(pp);
				scrollpane.getVerticalScrollBar().setUnitIncrement(25);
				contentPane.revalidate();
				//詳細ボタン
				JPanel p7 = new JPanel();
				p7.setLayout(new BoxLayout(p7, BoxLayout.PAGE_AXIS));
				btn3.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
				btn3.setPreferredSize(new Dimension(4*btn3.getUIClassID().length()*fontsize, 4*fontsize));
				p7.add(btn3);
				p7.add(scrollpane);
				getContentPane().add(p7);

				//演目の選択内容確認
				System.out.println(final_matched[0][Selected]);
				f.Start_detailView(final_matched[0][Selected], pass_name);
				repaint();

			}
		});
		btn1.doClick();
	}

	//データを探す
	public String[] dataSearch(String[] files, String[][] dataName, boolean[][][] data, boolean[][] conditions) {
		ArrayList<String> matched = new ArrayList<String>();
		for(int i=0; i<data.length; i++) {
			second:for(int j=0; j<data[0].length; j++) {
				for(int k=0; k<data[0][0].length; k++) {
					if(conditions[5][0]==true) {
						if(data[i][5][0]==true) {
							break second;
						}
					}
					if(conditions[conditions.length-1][k]==true) {
						if(data[i][3][k]==true) {
							break second;
						}
					}
					if(j!=5) {
						if(conditions[j][k]==true) {
							if(data[i][j][k]==false) {
								break second;
							}
						}
					}
					if(j==data[0].length-1 && k==data[0][0].length-1) {
						matched.add(files[i]);
					}
				}
			}
		}
		return matched.toArray(new String[matched.size()]);
	}

	//演目の詳細を表示
	public void detailView(String file_name, String pass_name){
		// ウィンドウの閉じ方
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 位置とサイズ
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screenSize.width/4, screenSize.height/2);

		//表示内容
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));

		JTextArea textarea = new JTextArea(f.Read_String(file_name,pass_name));
		textarea.setPreferredSize(new Dimension(screenSize.width/4, screenSize.height/2));
		textarea.setEditable(false);
		textarea.setLineWrap(true);

		JScrollPane  scrollpane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollpane.setBounds(0, 0, 510, 200);
		this.getContentPane().add(scrollpane);
		scrollpane.setViewportView(textarea);
		scrollpane.getVerticalScrollBar().setUnitIncrement(25);

		//追加
		p1.add(scrollpane);

		//上記のパネルをひとまとめに
		Container contentPane = getContentPane();
		setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		getContentPane().add(p1);
	}
}