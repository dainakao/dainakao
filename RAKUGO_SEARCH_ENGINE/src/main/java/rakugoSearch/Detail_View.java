package rakugoSearch;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Detail_View extends JFrame  {
	Functions f = new Functions();
	
	//演目の詳細を表示
	public void detailView(String file_name, String pass_name){
		// ウィンドウの閉じ方
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 位置とサイズ
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screenSize.width/4, screenSize.height/2);

		//表示内容
		JPanel detail = new JPanel();
		detail.setLayout(new BoxLayout(detail, BoxLayout.PAGE_AXIS));

		JTextArea textarea_detailView = new JTextArea(f.Read_String(file_name,pass_name));
		textarea_detailView.setPreferredSize(new Dimension(screenSize.width/4, screenSize.height/2));
		textarea_detailView.setEditable(false);
		textarea_detailView.setLineWrap(true);

		JScrollPane  scrollpane_detailView = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollpane_detailView.setBounds(0, 0, 510, 200);
		this.getContentPane().add(scrollpane_detailView);
		scrollpane_detailView.setViewportView(textarea_detailView);
		scrollpane_detailView.getVerticalScrollBar().setUnitIncrement(25);

		//追加
		detail.add(scrollpane_detailView);

		//上記のパネルをひとまとめに
		Container contentPane_detailView = getContentPane();
		setLayout(new BoxLayout(contentPane_detailView, BoxLayout.X_AXIS));
		getContentPane().add(detail);
	}
}
