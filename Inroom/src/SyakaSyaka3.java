import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.Expression;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import jp.fit.jc.it.BorderView;
import jp.fit.jc.it.CombinedView;
import jp.fit.jc.it.DrawItem;
import jp.fit.jc.it.PPanel2;

// シャカシャカ3
// 問題をXMLファイルから読み出す機能(升目の数が同じものに限る)を追加したもの

public class SyakaSyaka3 extends JFrame implements ActionListener{
	static String XMLFileName = "SyakaSyaka.xml";

	String mondai[];
	String kotae[];

	// 操作ボタン
	JButton reset,check,next,end; //
	SyakaSyakaPanel panel;
	int number; // 問題番号
	Element doc; // XML ファイルの内容が入る。
	Element xMondai; // XML形式の問題(1問のみ)

	public SyakaSyaka3(String xml) {

		super(); // いつもと違いタイトルなしで設定
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setLayout(new BorderLayout());
		setSize(600,400);

		doc = ReadDocument.read(xml); // XMLを全て読み出しておく
		number = 0;                   // 問題番号は最初にする。

		// タイトルを取り出してセットする。
		NodeList nl = doc.getElementsByTagName("title");     // タイトルタグ部分を得る
		setTitle(nl.item(0).getFirstChild().getNodeValue()); // 中身を取り出しタイトルバーにセット

		// 最初の問題を取り出してセットする。
		xMondai = (Element) doc.getElementsByTagName("Mondai").item(number);
		mondai = getStrings(xMondai,"question"); // 問題を取り出す。
		kotae  = getStrings(xMondai,"answer");    // 答えを取り出す

		int w = mondai[0].length(); // 横幅
		int h = mondai.length;      // 行数
		panel = new SyakaSyakaPanel(w,h,this); // パネルを作る
		add(panel,BorderLayout.CENTER);
		panel.setMondai(mondai); // 問題をセットする。

		// 操作ボタンを左パネルに貼り付ける。
		JPanel lp = new JPanel();
		lp.setLayout(new GridLayout(6,1)); // グリッドレイアウトで6個のボタンまで

		reset = new JButton("リセット"); // ボタンを作る
		reset.addActionListener(this);
		check = new JButton("チェック");
		check.addActionListener(this);
		next = new JButton("次の問題");
		next.addActionListener(this);
		end = new JButton("終了");
		end.addActionListener(this);

		lp.add(reset);		// ボタンを配置する。
		lp.add(check);
		lp.add(next);
		lp.add(end);

		add(lp,BorderLayout.WEST); // 左パネルを左に配置
	}

	// メインルーチン
	public static void main(String args[]) {
		SyakaSyaka3 frame = new SyakaSyaka3(XMLFileName); // データをXMLから取る
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource(); // ボタンを得る
		if( btn == reset ) { // リセットボタンかチェックする。
			panel.setMondai(mondai);
		} else if( btn == check) { // チェックボタンか
			String[] chare = panel.getCharaenge();
//			for(int i = 0; i<chare.length;i++) { // 答えを印刷する
//				System.out.println(chare[i]);
//			}
			if(checkAnswer(kotae,chare)) { // 正解
				System.out.println("正解"); // 正解と表示：簡易バージョンなのでこのままは不可
			} else {
				System.out.println("不正解"); // 不正解と表示：簡易バージョンなのでこのままは不可
			}
		} else if(btn == next) { // 次の問題：確認を入れるべき
			NodeList mn = doc.getElementsByTagName("Mondai"); // 全ての問題を取り出しておく

			number++;                       // 問題番号を増やす

			if(number >= mn.getLength()) {  // 最後の問題を通り過ぎたら最初に戻す。
				number = 0;
			}
			// 番号の問題を取り出してセットする。
			xMondai = (Element) mn.item(number);
			mondai = getStrings(xMondai,"question"); // 問題を取り出す。
			kotae  = getStrings(xMondai,"answer");    // 答えを取り出す

			panel.setMondai(mondai); // 問題をセットする。
			repaint();

		} else if(btn == end) { // 終了ボタン：確認を入れるべき
			System.exit(0);
		}
	}

	// 答えを正解と比較する。
	boolean checkAnswer(String[] a, String[] b) {
		if(a.length != b.length) { // そもそも大きさが違う
			return false;
		}
		for(int i = 0; i < a.length; i++) { // １行ごとに比較する
			if(! a[i].equals(b[i])) {
				return false;               // 違ったら、偽を返す
			}
		}
		return true;    // 全ての行が一致した。
	}

	// 問題や答えの文字列の並びを得る（答え合わせページに詳細）
	String[] getStrings(Element mondai,String tag) {
		String s[];
		NodeList nl = mondai.getElementsByTagName(tag);
		s = new String[nl.getLength()];
		for(int i = 0; i < nl.getLength() ;i++) {
			try {
				s[i] = nl.item(i).getFirstChild().getNodeValue();
			} catch(Exception e) {
				s[i] = "";
			}
		}
		return s;
	}
}

