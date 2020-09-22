package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Gui extends JFrame {
	private static final long serialVersionUID = 1L;
	// ウィンドウ本体

	Functions f = new Functions();



	public void guiInput(int number,String pass_name, String[] untreated_files, String[][] dataName){
		//データ配列を宣言、初期化（すべてfalse）
		boolean[][] data = new boolean[dataName.length][dataName[0].length];
		for(int i=0; i<dataName.length; i++) {
			for(int j=0; j<dataName[0].length; j++) {
				data[i][j] = false;
			}
		}

		// ウィンドウの閉じ方
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 位置とサイズ
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rect = env.getMaximumWindowBounds();
		setBounds(rect);

		int counter = 0;
		int C;

		counter=0;
		//江戸・上方ラジオボタン作成
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
		for(int i=0; i<dataName[0].length; i++) {
			if(dataName[0][counter].contentEquals("")) break;
			counter++;
		}
		C = counter;
		JRadioButton radio1[] = new JRadioButton[C];
		for(int i=0; i<C; i++) {
			radio1[i] = new JRadioButton(dataName[0][i]);
			p1.add(radio1[i]);
			p1.add(Box.createRigidArea(new Dimension(0,10)));
		}


		counter=0;
		//古典・新作ラジオボタン作成
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.PAGE_AXIS));
		for(int i=0; i<dataName[0].length; i++) {
			if(dataName[1][counter].contentEquals("")) break;
			counter++;
		}
		C = counter;
		JRadioButton radio2[] = new JRadioButton[C];
		for(int i=0; i<C; i++) {
			radio2[i] = new JRadioButton(dataName[1][i]);
			p2.add(radio2[i]);
			p2.add(Box.createRigidArea(new Dimension(0,10)));
		}


		counter=0;
		//江戸・上方ラジオボタン作成
		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.PAGE_AXIS));
		for(int i=0; i<dataName[0].length; i++) {
			if(dataName[2][counter].contentEquals("")) break;
			counter++;
		}
		C = counter;
		JRadioButton radio3[] = new JRadioButton[C];
		for(int i=0; i<C; i++) {
			radio3[i] = new JRadioButton(dataName[2][i]);
			p3.add(radio3[i]);
			p3.add(Box.createRigidArea(new Dimension(0,10)));
		}


		counter=0;
		//チェックボックス作成
		JPanel p4 = new JPanel();
		JPanel p5 = new JPanel();
		p4.setLayout(new BoxLayout(p4, BoxLayout.PAGE_AXIS));
		p5.setLayout(new BoxLayout(p5, BoxLayout.PAGE_AXIS));
		for(int i=0; i<dataName[0].length; i++) {
			if(dataName[3][counter].contentEquals("")) break;
			counter++;
		}
		C = counter;
		JCheckBox check4[] = new JCheckBox[C];
		for(int i=0; i<C; i++) {
			check4[i] = new JCheckBox(dataName[3][i]);
			if(i<C/2) {
				p4.add(check4[i]);
				p4.add(Box.createRigidArea(new Dimension(0,5)));
			}else {
				p5.add(check4[i]);
				p5.add(Box.createRigidArea(new Dimension(0,5)));
			}
		}

		counter=0;
		//チェックボックス作成
		JPanel p6 = new JPanel();
		p6.setLayout(new BoxLayout(p6, BoxLayout.PAGE_AXIS));
		for(int i=0; i<dataName[0].length; i++) {
			if(dataName[4][counter].contentEquals("")) break;
			counter++;
		}
		C = counter;
		JCheckBox check5[] = new JCheckBox[C];
		for(int i=0; i<C; i++) {
			check5[i] = new JCheckBox(dataName[4][i]);
			p6.add(check5[i]);
			p6.add(Box.createRigidArea(new Dimension(0,5)));
		}


		// ボタン作成
		JLabel label = new JLabel(untreated_files[number]);//演目表示ラベル追加

		JButton btn1 = new JButton("　決定　");
		btn1.setPreferredSize(new Dimension(100, 100));// ボタン追加
		JButton btn2 = new JButton("リセット");
		btn2.setPreferredSize(new Dimension(100, 100));// ボタン追加
		JButton btn3 = new JButton("スキップ");
		btn3.setPreferredSize(new Dimension(100, 100));// ボタン追加

		JPanel pp = new JPanel();
		pp.setLayout(new BoxLayout(pp, BoxLayout.Y_AXIS));
		pp.add(label);
		pp.add(Box.createRigidArea(new Dimension(0,30)));
		pp.add(btn1);
		pp.add(Box.createRigidArea(new Dimension(0,30)));
		pp.add(btn2);
		pp.add(Box.createRigidArea(new Dimension(0,30)));
		pp.add(btn3);

		Container contentPane = getContentPane();
		setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		getContentPane().add(p1);
		p1.add(Box.createRigidArea(new Dimension(30,0)));
		getContentPane().add(p2);
		p2.add(Box.createRigidArea(new Dimension(30,0)));
		getContentPane().add(p3);
		p3.add(Box.createRigidArea(new Dimension(30,0)));
		getContentPane().add(p4);
		p4.add(Box.createRigidArea(new Dimension(30,0)));
		getContentPane().add(p5);
		p5.add(Box.createRigidArea(new Dimension(30,0)));
		getContentPane().add(p6);
		p5.add(Box.createRigidArea(new Dimension(30,0)));
		getContentPane().add(pp);
		pp.add(Box.createRigidArea(new Dimension(150,0)));



		//決定クリック時の処理
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//種類の選択内容確認
				for (int i = 0 ; i < radio1.length; i++){
					if (radio1[i].isSelected()){
						data[0][i] = true;
					}else{
						data[0][i] = false;
					}
				}

				//古典・新作の選択内容確認
				for (int i = 0 ; i < radio2.length; i++){
					if (radio2[i].isSelected()){
						data[1][i] = true;
					}else{
						data[1][i] = false;
					}
				}

				//江戸・上方の選択内容確認
				for (int i = 0 ; i < radio3.length; i++){
					if (radio3[i].isSelected()){
						data[2][i] = true;
					}else{
						data[2][i] = false;
					}
				}

				//チェックボックスの選択内容確認
				for (int i = 0 ; i < check4.length; i++){
					if (check4[i].isSelected()){
						data[3][i] = true;
					}else{
						data[3][i] = false;
					}
				}

				//チェックボックスの選択内容確認
				for (int i = 0 ; i < check5.length; i++){
					if (check5[i].isSelected()){
						data[4][i] = true;
					}else{
						data[4][i] = false;
					}
				}
				System.out.println("");
				safety(number, pass_name, untreated_files[number], data, untreated_files, dataName);
				System.out.println("");
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

				//チェックボックスのリセット
				for (int i = 0 ; i < check4.length; i++){
					check4[i].setSelected(false);
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
				System.out.println(untreated_files[number] + "のデータ入力をスキップしました。");
				dispose();
				f.aaa(number+1, pass_name, untreated_files, dataName);
			}
		});

	}


	public void dataEntry(int number, String pass_name, String file_name, boolean[][] data, String[] untreated_files, String[][] dataName) {
		//ファイルを作成
		File newfile = new File(pass_name + "\\file\\" + file_name);
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
			System.out.println(file_name + "のデータを入力しました。");
			filewriter.close();
			dispose();
			f.aaa(number+1, pass_name, untreated_files, dataName);
			//エラー処理
		}catch(IOException e) {
			System.out.println(e);
		}
	}

	public void safety(int number, String pass_name, String file_name, boolean[][] data, String[] untreated_files, String[][] dataName) {
		boolean status = true;
		fast :for(int i=0; i<data.length; i++) {
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
			System.out.println("～検索結果～");
			dataEntry(number, pass_name, untreated_files[number], data,untreated_files, dataName);
		}
	}
}

