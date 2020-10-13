package ynwSearch;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Menu extends JFrame{
	private static final long serialVersionUID = 1L;
	static Functions f = new Functions();
	public void menuOpen(String pass_name) {
		int fontsize = 24;//フォントサイズ
		// ウィンドウの閉じ方
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 位置とサイズ
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(screenSize.width/2, 0, screenSize.width/2, screenSize.height);
		  Dimension size = getSize();
		  int width = size.width;


		//説明文
		// ボタン作成
		JButton btn1 = new JButton("データの設定");
		btn1.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, width/22));
		btn1.setMaximumSize(new Dimension(width/2, 2*fontsize));// ボタン追加

		JPanel p1 = new JPanel();
		p1.add(Box.createRigidArea(new Dimension(0,3*fontsize)));
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		p1.add(btn1);
		p1.add(Box.createVerticalGlue());

		//説明文
		// ボタン作成
		JButton btn2 = new JButton("データの処理");
		btn2.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, width/22));
		btn2.setMaximumSize(new Dimension(width/2, 2*fontsize));// ボタン追加

		JPanel p2 = new JPanel();
		p2.add(Box.createRigidArea(new Dimension(0,3*fontsize)));
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		p2.add(btn2);
		p2.add(Box.createVerticalGlue());

		//説明文
		// ボタン作成
		JButton btn3 = new JButton("データの検索");
		btn3.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, width/22));
		btn3.setMaximumSize(new Dimension(width/2, 2*fontsize));// ボタン追加
		
		// ボタン作成
		JButton btn4 = new JButton("データの編集");
		btn3.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, width/22));
		btn3.setMaximumSize(new Dimension(width/2, 2*fontsize));// ボタン追加

		JPanel p3 = new JPanel();
		p3.add(Box.createRigidArea(new Dimension(0,3*fontsize)));
		p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
		p3.add(btn3);
		p3.add(Box.createRigidArea(new Dimension(0,3*fontsize)));
		p3.add(btn4);
		p3.add(Box.createVerticalGlue());

		Container contentPane = getContentPane();
		setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		getContentPane().add(Box.createHorizontalGlue());
		getContentPane().add(p1);
		getContentPane().add(Box.createHorizontalGlue());
		getContentPane().add(p2);
		getContentPane().add(Box.createHorizontalGlue());
		getContentPane().add(p3);
		getContentPane().add(Box.createHorizontalGlue());

		//設定クリック時の処理
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				//まだ処理されていないファイルをリストアップ
				String[] untreated_files = f.listUp(pass_name+"\\untreated_file\\");
				//処理髄のファイルをリストアップ
				String[] files = f.listUp(pass_name + "\\condition\\");

				//ラベルを取得
				String[][] dataName = f.dataName(pass_name);

				f.Start_Setting(0, pass_name, untreated_files, files, dataName);
			}

		});
		//処理クリック時の処理
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				TF_IDF tf = new TF_IDF();
				Make_Index mi = new Make_Index();
				mi.make_index(pass_name);
				tf.make_TF_IDF("Inverted_Index.csv", pass_name);
				f.menuStart(pass_name);
			}
		});
		//検索クリック時の処理
		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				//ファイルをリストアップ
				String[] files = f.listUp(pass_name + "\\condition\\");

				//ラベルを取得
				String[][] dataName = f.dataName(pass_name);

				//データを取得
				boolean[][][] data = f.dataGet(pass_name, files, dataName);

				//GUIを表示
				f.Start_Search(0, pass_name, files, dataName, data);
			}
		});

		//検索クリック時の処理
		btn4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				//ファイルをリストアップ
				String[] files = f.listUp(pass_name + "\\condition\\");
				
				//まだ処理されていないファイルをリストアップ
				String[] untreated_files = f.listUp(pass_name+"\\untreated_file\\");

				//ラベルを取得
				String[][] dataName = f.dataName(pass_name);

				//データを取得
				boolean[][][] data = f.dataGet(pass_name, files, dataName);

				//GUIを表示
				f.Start_Correction_menu(0, pass_name, untreated_files, files, data, dataName);
			}
		});
	}
}
