package yn_search;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
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


public class YN_Search extends JFrame {
	private static final long serialVersionUID = 1L;
	// ウィンドウ本体

	Functions f = new Functions();

	//GUIを設定・表示
	public void conditionSelect(int number,String pass_name, String[] files, String[][] dataName, boolean[][][] data){
		int fontsize = 24;//フォントサイズ

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
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rect = env.getMaximumWindowBounds();
		setBounds(rect);

		int counter = 0;
		int C;

		counter=0;
		//江戸・上方ラジオボタン作成
		ButtonGroup bgroup1 = new ButtonGroup();
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
			radio1[i].setFont(new Font(radio1[i].getFont().getName(), Font.BOLD, fontsize));
			bgroup1.add(radio1[i]);
			p1.add(radio1[i]);
			p1.add(Box.createRigidArea(new Dimension(0,10)));
		}



		counter=0;
		//古典・新作ラジオボタン作成
		ButtonGroup bgroup2 = new ButtonGroup();
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
			radio2[i].setFont(new Font(radio2[i].getFont().getName(), Font.BOLD, fontsize));
			bgroup2.add(radio2[i]);
			p2.add(radio2[i]);
			p2.add(Box.createRigidArea(new Dimension(0,10)));
		}


		counter=0;
		//江戸・上方ラジオボタン作成
		ButtonGroup bgroup3 = new ButtonGroup();
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
			radio3[i].setFont(new Font(radio3[i].getFont().getName(), Font.BOLD, fontsize));
			bgroup3.add(radio3[i]);
			p3.add(radio3[i]);
			p3.add(Box.createRigidArea(new Dimension(0,10)));
		}


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
			radio4_Y[i].setFont(new Font(radio4_Y[i].getFont().getName(), Font.BOLD, fontsize));
			bgroup4[i].add(radio4_Y[i]);//ボタンをグループに追加

			radio4_U[i] = new JRadioButton("　");
			radio4_U[i].setFont(new Font(radio4_U[i].getFont().getName(), Font.BOLD, fontsize));
			bgroup4[i].add(radio4_U[i]);//ボタンをグループに追加
			radio4_U[i].setSelected(true);

			radio4_N[i] = new JRadioButton(dataName[3][i]);
			radio4_N[i].setFont(new Font(radio4_N[i].getFont().getName(), Font.BOLD, fontsize));
			bgroup4[i].add(radio4_N[i]);//ボタンをグループに追加
			if(i<C/2) {
				if(i==0) {
					JLabel Yes_No = new JLabel("　出る");//
					p4_Y.add(Yes_No);
					p4_Y.add(Box.createRigidArea(new Dimension(0,5)));
					Yes_No = new JLabel("未選択");//
					p4_U.add(Yes_No);
					p4_U.add(Box.createRigidArea(new Dimension(0,5)));
					Yes_No = new JLabel("出ない");//
					p4_N.add(Yes_No);
					p4_N.add(Box.createRigidArea(new Dimension(0,5)));
				}
				p4_Y.add(radio4_Y[i]);
				p4_Y.add(Box.createRigidArea(new Dimension(0,5)));
				p4_U.add(radio4_U[i]);
				p4_U.add(Box.createRigidArea(new Dimension(0,5)));
				p4_N.add(radio4_N[i]);
				p4_N.add(Box.createRigidArea(new Dimension(0,5)));
			}else {
				if(i==C/2) {
					JLabel Yes_No = new JLabel("　出る");//
					p5_Y.add(Yes_No);
					p5_Y.add(Box.createRigidArea(new Dimension(0,5)));
					Yes_No = new JLabel("未選択");//
					p5_U.add(Yes_No);
					p5_U.add(Box.createRigidArea(new Dimension(0,5)));
					Yes_No = new JLabel("出ない");//
					p5_N.add(Yes_No);
					p5_N.add(Box.createRigidArea(new Dimension(0,5)));
				}
				p5_Y.add(radio4_Y[i]);
				p5_Y.add(Box.createRigidArea(new Dimension(0,5)));
				p5_U.add(radio4_U[i]);
				p5_U.add(Box.createRigidArea(new Dimension(0,5)));
				p5_N.add(radio4_N[i]);
				p5_N.add(Box.createRigidArea(new Dimension(0,5)));
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
			check5[i].setFont(new Font(check5[i].getFont().getName(), Font.BOLD, fontsize));
			p6.add(check5[i]);
			p6.add(Box.createRigidArea(new Dimension(0,5)));
		}


		// ボタン作成
		JLabel label = new JLabel("条件を指定して検索してください");//演目表示ラベル追加
		label.setFont(new Font(label.getFont().getName(), Font.BOLD, fontsize));

		JButton btn1 = new JButton("　検索　");
		btn1.setFont(new Font(btn1.getFont().getName(), Font.BOLD, fontsize));
		btn1.setPreferredSize(new Dimension(100, 100));// ボタン追加
		JButton btn2 = new JButton("リセット");
		btn2.setFont(new Font(btn2.getFont().getName(), Font.BOLD, fontsize));
		btn2.setPreferredSize(new Dimension(100, 100));// ボタン追加

		JPanel pp = new JPanel();
		pp.setLayout(new BoxLayout(pp, BoxLayout.Y_AXIS));
		pp.add(label);
		pp.add(Box.createRigidArea(new Dimension(0,30)));
		pp.add(btn1);
		pp.add(Box.createRigidArea(new Dimension(0,30)));
		pp.add(btn2);

		Container contentPane = getContentPane();
		setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		getContentPane().add(p1);
		p1.add(Box.createRigidArea(new Dimension(30,0)));
		getContentPane().add(p2);
		p2.add(Box.createRigidArea(new Dimension(30,0)));
		getContentPane().add(p3);
		p3.add(Box.createRigidArea(new Dimension(30,0)));
		getContentPane().add(p4_Y);
		p4_Y.add(Box.createRigidArea(new Dimension(30,0)));
		getContentPane().add(p4_U);
		getContentPane().add(p4_N);
		getContentPane().add(p5_Y);
		getContentPane().add(p5_U);
		p5_Y.add(Box.createRigidArea(new Dimension(30,0)));
		getContentPane().add(p5_N);
		getContentPane().add(p6);
		p6.add(Box.createRigidArea(new Dimension(30,0)));
		getContentPane().add(pp);
		pp.add(Box.createRigidArea(new Dimension(150,0)));


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
						conditions[5][i] = true;
					}else{
						conditions[5][i] = false;
					}
				}

				//チェックボックスの選択内容確認
				for (int i = 0 ; i < check5.length; i++){
					if (check5[i].isSelected()){
						conditions[4][i] = true;
					}else{
						conditions[4][i] = false;
					}
				}
				System.out.println("～検索結果～");
				dataSearch(files, dataName, data, conditions);
				System.out.println();
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
				}

				//チェックボックスのリセット
				for (int i = 0 ; i < check5.length; i++){
					check5[i].setSelected(false);
				}
			}
		});

	}


	//データを探す
	public void dataSearch(String[] files, String[][] dataName, boolean[][][] data, boolean[][] conditions) {
		ArrayList<String> matched = new ArrayList<String>();
		for(int i=0; i<data.length; i++) {
			second:for(int j=0; j<data[0].length; j++) {
				for(int k=0; k<data[0][0].length; k++) {
					if(conditions[5][k]==true) {
						if(data[i][3][k]==true) {
							break second;
						}
					}
					if(conditions[j][k]==true) {
						if(data[i][j][k]==false) {
							break second;
						}
					}
					if(j==data[0].length-1 && k==data[0][0].length-1) {
						matched.add(files[i]);
					}
				}
			}
		}


		for(int i=0; i<matched.size(); i++) {
			System.out.println(matched.get(i));
		}
	}

}
