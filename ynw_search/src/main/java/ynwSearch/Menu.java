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
import javax.swing.JLabel;
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


		//説明文
		JLabel label = new JLabel("落語の演目データを入力する");//演目表示ラベル追加
		label.setFont(new Font(label.getFont().getName(), Font.BOLD, fontsize));
		// ボタン作成
		JButton btn1 = new JButton("データの設定");
		btn1.setFont(new Font(btn1.getFont().getName(), Font.BOLD, 2*fontsize));
		btn1.setMaximumSize(new Dimension(4*btn1.getUIClassID().length()*fontsize, 2*fontsize));// ボタン追加

		JPanel p1 = new JPanel();
		p1.add(Box.createRigidArea(new Dimension(0,3*fontsize)));
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		p1.add(btn1);
		p1.add(Box.createRigidArea(new Dimension(0,12*fontsize)));
		p1.add(label);
		p1.add(Box.createVerticalGlue());

		//説明文
		label = new JLabel("落語の演目を検索する");//演目表示ラベル追加
		label.setFont(new Font(label.getFont().getName(), Font.BOLD, fontsize));
		// ボタン作成
		JButton btn2 = new JButton("データの検索");
		btn2.setFont(new Font(btn2.getFont().getName(), Font.BOLD, 2*fontsize));
		btn2.setMaximumSize(new Dimension(4*btn2.getUIClassID().length()*fontsize, 2*fontsize));// ボタン追加

		JPanel p2 = new JPanel();
		p2.add(Box.createRigidArea(new Dimension(0,3*fontsize)));
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		p2.add(btn2);
		p2.add(Box.createRigidArea(new Dimension(0,12*fontsize)));
		p2.add(label);
		p2.add(Box.createVerticalGlue());

		Container contentPane = getContentPane();
		setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		getContentPane().add(Box.createHorizontalGlue());
		getContentPane().add(p1);
		getContentPane().add(Box.createHorizontalGlue());
		getContentPane().add(p2);
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
		//検索クリック時の処理
		btn2.addActionListener(new ActionListener() {
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

	}
}
