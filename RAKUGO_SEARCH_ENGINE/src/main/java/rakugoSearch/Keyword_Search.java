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
import javax.swing.JTextField;


public class Keyword_Search extends JFrame {
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

		int distance = 6;

		//検索ボックス作成
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));

		p1.add(Box.createRigidArea(new Dimension(0,3*distance)));
		JLabel label = new JLabel("キーワード");//演目表示ラベル追加
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		p1.add(label);
		p1.add(Box.createRigidArea(new Dimension(0,distance)));
		JTextField search_box = new JTextField();
		search_box.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		search_box.setMaximumSize(new Dimension(30*fontsize, 2*fontsize));
		p1.add(search_box);

		//不適切除外ボタン作成
		p1.add(Box.createRigidArea(new Dimension(0,3*distance)));
		label = new JLabel("不適切な表現を");//演目表示ラベル追加
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		p1.add(label);
		label = new JLabel("含む演目を除外する");//演目表示ラベル追加
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		p1.add(label);

		JCheckBox check = new JCheckBox(dataName[5][0]);
		p1.add(check);
		check.setSelected(true);

		// ボタン作成
		JButton btn1 = new JButton("　検索　");
		btn1.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		btn1.setPreferredSize(new Dimension(4*btn1.getUIClassID().length()*fontsize, 4*fontsize));// ボタン追加
		JButton btn2 = new JButton("リセット");
		btn2.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 2*fontsize));
		btn2.setPreferredSize(new Dimension(4*btn2.getUIClassID().length()*fontsize, 4*fontsize));// ボタン追加

		p1.add(Box.createRigidArea(new Dimension(0,distance)));
		p1.add(btn1);
		p1.add(Box.createRigidArea(new Dimension(0,distance)));
		p1.add(btn2);
		p1.add(Box.createVerticalGlue());

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

		getContentPane().add(Box.createHorizontalGlue());
		getContentPane().add(p1);
		getContentPane().add(Box.createHorizontalGlue());
		getContentPane().add(p7);
		getContentPane().add(Box.createHorizontalGlue());

		for (int i = 0 ; i < final_matched_length; i++){
			if (radio[i].isSelected()){
				Selected = i;
			}
		}

		//検索クリック時の処理
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				//不適切な表現を含む演目の除外確認
				if (check.isSelected()){
					conditions[5][0] = true;
				}else{
					conditions[5][0] = false;
				}

				//検索
				pp.removeAll();
				scrollpane.removeAll();
				p7.removeAll();
				contentPane.remove(contentPane.getComponentCount()-1);
				contentPane.remove(contentPane.getComponentCount()-1);

				String[] matched = dataSearch(files, dataName, data, conditions);
				TF_IDF tf = new TF_IDF();
				String[][] final_matched = tf.add_long_search_TF_IDF("Inverted_Index.csv", pass_name, search_box.getText(), matched);
				final_matched_length = final_matched[0].length;

				radio = new JRadioButton[final_matched_length];
				bgroup_pp = new ButtonGroup();

				for(int i=final_matched_length-1; i>=0; i--) {
					if(final_matched[0][i]!=null || final_matched[0][i]!="") {
						radio[i] = new JRadioButton(final_matched[0][i] + ", " + final_matched[1][i]);
						radio[i].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, fontsize));
						radio[i].setPreferredSize(new Dimension(4*radio[i].getUIClassID().length()*fontsize, 4*fontsize));// ボタン追加
						pp.add(radio[i]);
						bgroup_pp.add(radio[i]);
					}
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
				getContentPane().add(Box.createHorizontalGlue());
			}
		});

		//リセットクリック時の処理
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("リセット");
				//キーワードを削除
				search_box.setText("");
			}
		});

		//詳細クリック時の処理
		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0 ; i < radio.length; i++){
					if (radio[i].isSelected()){
						//演目の選択内容確認
						String[] keyword = radio[i].getText().split(",");
						f.Start_detailView(keyword[0], pass_name);
						repaint();

					}
				}
			}		});
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
}
