package ynwSearch;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

public class Data_correction extends JFrame {
	private static final long serialVersionUID = 1L;
	// ウィンドウ本体

	Functions f = new Functions();

	//GUIを設定・表示
	public void guiSelect(int number,String pass_name, String[] untreated_files, String[] files, boolean[][][] data, String[][] dataName){
		int fontsize = 12;//フォントサイズ
		// ウィンドウの閉じ方
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 位置とサイズ
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(screenSize.width/2, 0, screenSize.width/2, screenSize.height);

		int distance = 6;

		JPanel p1 = new JPanel();
		//演目表示ラベル追加
		JLabel label = new JLabel("修正したい演目を選択してください");
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));

		p1.add(Box.createRigidArea(new Dimension(0,4*distance)));
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		p1.add(label);

		// ボタン作成
		ButtonGroup bgroup1 = new ButtonGroup();
		JRadioButton radio[] = new JRadioButton[files.length];
		for(int i=0; i<files.length; i++) {
			radio[i] = new JRadioButton(files[i]);
			radio[i].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
			radio[i].setPreferredSize(new Dimension(4*radio[i].getUIClassID().length()*fontsize, 4*fontsize));// ボタン追加
			p1.add(Box.createRigidArea(new Dimension(0,distance)));
			p1.add(radio[i]);
			bgroup1.add(radio[i]);
		}

		p1.add(Box.createVerticalGlue());
		JScrollPane  scrollpane = new JScrollPane(); 
		scrollpane.setBounds(0, 0, 510, 400);
		this.getContentPane().add(scrollpane);
		scrollpane.setViewportView(p1);

		// 決定ボタン作成
		JButton btn1 = new JButton("　変更　");
		btn1.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		btn1.setPreferredSize(new Dimension(4*btn1.getUIClassID().length()*fontsize, 4*fontsize));// ボタン追加


		Container contentPane = getContentPane();
		setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));


		getContentPane().add(Box.createRigidArea(new Dimension(fontsize,0)));
		getContentPane().add(scrollpane);
		getContentPane().add(btn1);
		getContentPane().add(Box.createRigidArea(new Dimension(fontsize,0)));
		getContentPane().add(Box.createHorizontalGlue());

		//決定クリック時の処理
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//種類の選択内容確認
				for(int i=0; i<radio.length; i++) {
					if (radio[i].isSelected()){
						for(int j=0; j<untreated_files.length; j++) {
							if(files[i].equals(untreated_files[j])) {
								dispose();
								f.Start_Correction(i,j, pass_name, untreated_files, files, data, dataName);
							}
						}
					}
				}

			}
		});
	}

	//修正画面を表示
	public void dataCorrection(int file_number, int untreated_files_number, String pass_name, String[] untreated_files, String[] files, boolean[][][] data, String[][] dataName){
		int fontsize = 12;//フォントサイズ

		// ウィンドウの閉じ方
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 位置とサイズ
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screenSize.width, screenSize.height);

		int counter = 0;
		int C;
		int distance = 6;

		//種類ラジオボタン作成
		//ButtonGroup bgroup1 = new ButtonGroup();
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
		p1.add(Box.createRigidArea(new Dimension(0,2*(fontsize+distance))));
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
			p1.add(radio1[i]);
			p1.add(Box.createRigidArea(new Dimension(0,distance)));
		}
		p1.add(Box.createRigidArea(new Dimension(0,2*(fontsize+distance))));



		counter=0;
		//古典・新作ラジオボタン作成
		//ButtonGroup bgroup2 = new ButtonGroup();
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
			p1.add(radio2[i]);
			p1.add(Box.createRigidArea(new Dimension(0,distance)));
		}
		p1.add(Box.createRigidArea(new Dimension(0,2*(fontsize+distance))));


		counter=0;
		//演目の大きさラジオボタン作成
		//ButtonGroup bgroup3 = new ButtonGroup();
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
		p6.add(Box.createRigidArea(new Dimension(0,3*fontsize)));
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

		//不適切除外ボタン作成
		label = new JLabel("不適切な表現を含む");//演目表示ラベル追加
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		JCheckBox check6 = new JCheckBox("不適切な表現を含む");
		p6.add(Box.createRigidArea(new Dimension(0,3*distance)));
		p6.add(label);
		p6.add(check6);
		p6.add(Box.createVerticalGlue());

		//演目表示ラベル追加
		label = new JLabel(untreated_files[untreated_files_number]);
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));

		// ボタン作成
		JButton btn1 = new JButton("　決定　");
		btn1.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		btn1.setPreferredSize(new Dimension(4*btn1.getUIClassID().length()*fontsize, 4*fontsize));// ボタン追加
		JButton btn2 = new JButton("リセット");
		btn2.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		btn2.setPreferredSize(new Dimension(4*btn1.getUIClassID().length()*fontsize, 4*fontsize));// ボタン追加
		JButton btn3 = new JButton("スキップ");
		btn3.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		btn3.setPreferredSize(new Dimension(4*btn1.getUIClassID().length()*fontsize, 4*fontsize));// ボタン追加

		JPanel pp = new JPanel();
		pp.add(Box.createRigidArea(new Dimension(0,4*fontsize)));
		pp.setLayout(new BoxLayout(pp, BoxLayout.Y_AXIS));
		pp.add(label);
		pp.add(Box.createRigidArea(new Dimension(0,fontsize)));
		pp.add(btn1);
		pp.add(Box.createRigidArea(new Dimension(0,fontsize)));
		pp.add(btn2);
		pp.add(Box.createRigidArea(new Dimension(0,fontsize)));
		pp.add(btn3);
		pp.add(Box.createVerticalGlue());

		//ボタンの選択状況を反映
		for (int i = 0 ; i < radio1.length; i++){
			if (data[file_number][0][i] == true){
				radio1[i].setSelected(true);
			}
		}

		//古典・新作の状況を反映
		for (int i = 0 ; i < radio2.length; i++){
			if (data[file_number][1][i] == true){
				radio2[i].setSelected(true);
			}
		}

		//江戸・上方の状況を反映
		for (int i = 0 ; i < radio3.length; i++){
			if (data[file_number][2][i] == true){
				radio3[i].setSelected(true);
			}
		}

		//登場人物の状況を反映
		for (int i = 0 ; i < radio4_Y.length; i++){
			//登場人物の「出る」の内容選択
			if (data[file_number][3][i] == true){
				radio4_Y[i].setSelected(true);
			}else {
				radio4_N[i].setSelected(true);
			}
		}

		//チェックボックスの状況を反映
		for (int i = 0 ; i < check5.length; i++){
			if (data[file_number][4][i] == true){
				check5[i].setSelected(true);
			}
		}
		//不適切な表現を含む演目の除外状況を反映
		if (data[file_number][5][0] == true){
			check6.setSelected(true);
		}

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
		getContentPane().add(Box.createRigidArea(new Dimension(fontsize,0)));
		getContentPane().add(pp);
		getContentPane().add(Box.createRigidArea(new Dimension(fontsize,0)));
		getContentPane().add(Box.createHorizontalGlue());


		//決定クリック時の処理
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//種類の選択内容確認
				for (int i = 0 ; i < radio1.length; i++){
					if (radio1[i].isSelected()){
						data[file_number][0][i] = true;
					}else{
						data[file_number][0][i] = false;
					}
				}

				//古典・新作の選択内容確認
				for (int i = 0 ; i < radio2.length; i++){
					if (radio2[i].isSelected()){
						data[file_number][1][i] = true;
					}else{
						data[file_number][1][i] = false;
					}
				}

				//江戸・上方の選択内容確認
				for (int i = 0 ; i < radio3.length; i++){
					if (radio3[i].isSelected()){
						data[file_number][2][i] = true;
					}else{
						data[file_number][2][i] = false;
					}
				}

				//登場人物の選択内容確認
				for (int i = 0 ; i < radio4_Y.length; i++){
					//登場人物の「出る」の選択内容をチェック
					if (radio4_Y[i].isSelected()){
						data[file_number][3][i] = true;
					}else{
						data[file_number][3][i] = false;
					}
				}

				//チェックボックスの選択内容確認
				for (int i = 0 ; i < check5.length; i++){
					if (check5[i].isSelected()){
						data[file_number][4][i] = true;
					}else{
						data[file_number][4][i] = false;
					}
				}
				//不適切な表現を含む演目の除外確認
				if (check6.isSelected()){
					data[file_number][5][0] = true;
				}else{
					data[file_number][5][0] = false;
				}

				//登場人物の選択内容確認
				for (int i = 0 ; i < radio4_U.length; i++){
					//登場人物の「出る」の選択内容をチェック
					if (radio4_U[i].isSelected()){
						System.out.println();
						System.out.println("選択されていない項目があります！");
						break;
					}
					if (i==radio4_U.length-1) {
						safety(untreated_files_number, pass_name, data[file_number], untreated_files, files, dataName);
						System.out.println();
						dispose();
						f.Start_Correction_menu(0, pass_name, untreated_files, files, data, dataName);
					}
				}
			}
		});

		//リセットクリック時の処理
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("リセット");
				//種類のリセット
				for (int i = 0 ; i < radio1.length; i++){
					radio1[i].setSelected(false);
				}

				//古典・新作のリセット
				for (int i = 0 ; i < radio2.length; i++){
					radio2[i].setSelected(false);
				}

				//江戸・上方のリセット
				for (int i = 0 ; i < radio3.length; i++){
					radio3[i].setSelected(false);
				}

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
		//スキップクリック時の処理
		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("スキップ");
				System.out.println(untreated_files[untreated_files_number] + "のデータ入力をスキップしました。");
				dispose();
				f.Start_Correction_menu(0, pass_name, untreated_files, files, data, dataName);
			}
		});

	}

	public void safety(int number, String pass_name, boolean[][] data, String[] untreated_files, String[] files, String[][] dataName) {
		boolean status = true;
		fast :for(int i=0; i<data.length-1; i++) {
			for(int j=0; j<data[i].length; j++) {
				if(data[i][j]==true) break;
				if(j==data[i].length-1) {
					System.out.println("各項目最低1つは入力してください");
					status = false;
					break fast;
				}
			}
		}

		if(status==true) {
			dataEntry(number, pass_name, untreated_files[number], data,untreated_files, files, dataName);
			Make_Index mi = new Make_Index();
			Make_Morphorogical mm = new Make_Morphorogical();
			mi.prepare_sentences(pass_name, untreated_files[number]);
			mm.start_morphorogical(pass_name, untreated_files[number]);
			System.out.println(untreated_files[number] + "のデータを入力しました。");
		}
	}

	public void dataEntry(int number, String pass_name, String file_name, boolean[][] data, String[] untreated_files, String[] files, String[][] dataName) {
		//ファイルを作成
		File newfile = new File(pass_name + "\\condition\\" + file_name);
		try {
			//fileを入力
			FileWriter filewriter = new FileWriter(newfile);
			for(int i=0; i<data.length; i++) {
				for(int j=0; j<data[i].length; j++) {
					filewriter.write(String.valueOf(data[i][j]));
					System.out.print(String.valueOf(data[i][j]));
					if(j==data[i].length-1) {
						filewriter.write("\n");
						System.out.println();
					}else {
						filewriter.write(",");
						System.out.print(".");
					}
				}
			}
			filewriter.close();
			//エラー処理
		}catch(IOException e) {
			System.out.println(e);
		}
	}
}
