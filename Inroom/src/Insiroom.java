import java.awt.BorderLayout;
import java.awt.Color;
//import java.awt.Graphics;
import java.awt.GridLayout;
//import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//import java.beans.Expression;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import jp.fit.jc.it.BorderView;
import jp.fit.jc.it.CharacterView;
import jp.fit.jc.it.ColorView;
//import jp.fit.jc.it.CombinedView;
//import jp.fit.jc.it.DrawItem;
import jp.fit.jc.it.GroupView;
import jp.fit.jc.it.PPanel2;
import jp.fit.jc.it.StringView;

// 因子の部屋

public class Insiroom extends JFrame implements ActionListener{
	static String XMLFileName = "Insiroom.xml";

	static String mondai[];
	static String kotae[];


	// 操作ボタン
	JButton reset,check,next,end; 
	InsiroomPanel panel;
	JButton[] buttons; //削除と番号ボタンの配列
	int number;//問題番号
	static Element doc; // XML ファイルの内容が入る。
	Element xMondai; // XML形式の問題



	public  Insiroom(int n) {

		super(); // いつもの設定
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setLayout(new BorderLayout());
		setSize(600,400);

		number = n;                   // 問題番号は最初にする。

		// タイトルを取り出してセットする。
		NodeList nl = doc.getElementsByTagName("title");     // タイトルタグ部分を得る
		setTitle(nl.item(0).getFirstChild().getNodeValue()); // 中身を取り出しタイトルバーにセット

		// 最初の問題を取り出してセットする。
		xMondai = (Element) doc.getElementsByTagName("Mondai").item(number);
		mondai = getStrings(xMondai,"question"); // 問題を取り出す
		kotae  = getStrings(xMondai,"answer");    // 答えを取り出す
		Element item = (Element) doc.getElementsByTagName("item").item(n);
		String[] map0 = getStrings(item,"group"); // グループマップの取り出し
		String[] map10 = getStrings(item,"nw");
		
		int[][] gmap = convData(map0);
		panel = new InsiroomPanel(gmap[0].length,gmap.length,this); // パネルを作って配置
		add(panel,BorderLayout.CENTER);
		int[][] smozi = convData1(map10);
		for(int i=0;i<map10.length;i++) {
			panel.setS1(smozi[i][0] ,smozi[i][1], String.valueOf(smozi[i][2]));
		}
		int w = mondai[0].length(); // 横幅
		int h = mondai.length;      // 行数
		panel.setMondai(mondai); // 問題をセットする。
		panel.setGroupMap(gmap); // データをセット


		// 操作ボタンを左パネルに貼り付ける。
		JPanel lp = new JPanel();
		lp.setLayout(new GridLayout(4,2)); // グリッドレイアウトで6個のボタンまで
		JPanel bpanel = new JPanel();
		bpanel.setLayout(new GridLayout(2,5));


		buttons = new JButton[10];
		buttons[0] = new JButton("del");
		buttons[0].addActionListener(this);
		
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
		bpanel.add(buttons[0]);
		for(int i = 1;i<=9; i++) {
			buttons[i] = new JButton(""+i);
			buttons[i].addActionListener(this);//アクションリスナーを装着し
			bpanel.add(buttons[i]);                //パネルに張り付ける
		}
		add(lp,BorderLayout.WEST); // 左パネルを左に配置
		add(bpanel,BorderLayout.SOUTH);


	}
	int [][] convData1(String[] map10) {
		String[][] map11 = new String[map10.length][]; // 2次元配列を準備、行数はmap10から得る。
		for(int i = 0; i < map10.length; i ++ ) { // 各行のデータそれぞれを操作
		    map11[i] = map10[i].split(",");  // コンマで分割し、各列データの配列を格納する。
		}
		int[][] map12 = new int[map11.length][map11[0].length]; // 2次元配列を準備
		for(int i = 0; i < map11.length; i ++ ) { // 各行のデータそれぞれを操作
		    for(int j = 0; j < map11[i].length; j ++ ) { // 各列のデータそれぞれを操作
		        try {
		        	map12[i][j] = Integer.parseInt(map11[i][j]);// 変換して代入
		        } catch(ArrayIndexOutOfBoundsException e) { // 配列データ無し
		        	map12[i][j] = 0; // 0はデータ無し
		        } catch(NumberFormatException e) { // 書式の誤り
		        	map12[i][j] = 0;
		        }
		    }
		}
		return map12;
	}
	int [][] convData(String[] map0) {
		String[][] map1 = new String[map0.length][]; // 2次元配列を準備、行数はmap0から得る。
		for(int i = 0; i < map0.length; i ++ ) { // 各行のデータそれぞれを操作
		    map1[i] = map0[i].split(",");  // コンマで分割し、各列データの配列を格納する。
		}
		int[][] map2 = new int[map1.length][map1[0].length]; // 2次元配列を準備
		for(int i = 0; i < map1.length; i ++ ) { // 各行のデータそれぞれを操作
		    for(int j = 0; j < map1[i].length; j ++ ) { // 各列のデータそれぞれを操作
		        try {
		        	map2[i][j] = Integer.parseInt(map1[i][j]);// 変換して代入
		        } catch(ArrayIndexOutOfBoundsException e) { // 配列データ無し
		        	map2[i][j] = 0; // 0はデータ無し
		        } catch(NumberFormatException e) { // 書式の誤り
		        	map2[i][j] = 0;
		        }
		    }
		}
		return map2;
	}
	
	
	// 問題や答えの文字列の並びを得る（答え合わせページに詳細）
		String[] getStrings(Element mondai,String tag) {
			String st[];
			NodeList nl = mondai.getElementsByTagName(tag);
			st = new String[nl.getLength()];
			for(int i = 0; i < nl.getLength() ;i++) {
				try {
					st[i] = nl.item(i).getFirstChild().getNodeValue();
				} catch(Exception e) {
					st[i] = "";
				}
			}
			return st;
		}

	// メインルーチン
	public static void main(String args[]) {
		doc = ReadDocument.read(XMLFileName); // XMLを全て読み出しておく※島添メソッド適応のためここで読み出す。
		Insiroom frame = new Insiroom(0);
		frame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource(); // ボタンを得る
		if( btn == reset ) { // リセットボタンかチェックする。
			int ret = JOptionPane.showConfirmDialog(null,
					"リセットしますか？", "リセット", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE);
			if(ret==JOptionPane.OK_OPTION) {
				panel.setMondai(mondai);
				repaint();
			}
		} else if( btn == check) { // チェックボタンか
			String[] chare = panel.getCharaenge();
			for(int i = 0; i<chare.length;i++) { // 答えを印刷する
				System.out.println(chare[i]);
			}
			if(checkAnswer(kotae,chare)) { // 正解
				int ret = JOptionPane.showConfirmDialog(null,
						"おめでとう！正解です。次の問題へ移ります。", "チェック", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				if(ret==JOptionPane.OK_OPTION) {
					goNext(); // 了解を選択した時の処理
				} else {
					return;
				}
			} else {
				JOptionPane.showMessageDialog(null, "残念！どこか間違っています。",
					        "不正解！", JOptionPane.ERROR_MESSAGE);
			}
		}else if(btn == next) {
			int ret = JOptionPane.showConfirmDialog(null,
					"次の問題に移ります。", "次の問題", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE);
			if(ret==JOptionPane.OK_OPTION) {
				goNext(); // 次の問題に移る。
			}
		} else if(btn == end) { // 終了ボタン：確認を入れるべき
			int ret = JOptionPane.showConfirmDialog(null,
					"因子の部屋を終了しますか？", "ゲーム終了", JOptionPane.YES_NO_OPTION);
			if(ret==JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		} else if(btn == buttons[0]) { // 削除ボタン
			panel.setLetter('\0'); // ナル文字をセットする。
		} else { //数字(文字)ボタン、他にもボタンがある場合は最後に持ってくる
			String text = btn.getText(); // ボタンの文字を得る
			panel.setLetter(text.charAt(0));// ボタンの文字の最初の1つをセット
		}
	}

	void goNext() {
		NodeList mn = doc.getElementsByTagName("Mondai"); // 全ての問題を取り出しておく

		number++;                       // 問題番号を増やす
		if(number >= mn.getLength()) {  // 最後の問題を通り過ぎたら最初に戻す。
			number = 0;
		}
		// ※島添メソッド
		Insiroom next = new Insiroom(number); // 新しいフレームを作る
		next.setVisible(true); // 新フレームを表示
		dispose(); // 現フレームを抹消
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
}


class InsiroomPanel extends PPanel2 implements MouseListener{         // マウスリスナを装着し、マウスクリックに備える。
    CharacterView chview; // 文字を描画するビュー
    ColorView     colview;//文字を入力する色付きユニットを出すためのビュー
    Point cur;			   // 文字を入力する位置
    StringView    s1view;
    GroupView gview; // グループで分割するビュー

    InsiroomPanel(int x, int y, JFrame f) {
		super(x,y,f);
		colview = new ColorView(this);
		addPView(colview);
		cur = null;
		addMouseListener(this); // マウスリスナ装着
		chview = new CharacterView(this); // 大きな文字のビュー（１文字）
		addPView(chview);
		s1view = new StringView(this,StringView.SMALL_NW); // 左上に小さな文字
		addPView(s1view);
		addPView(new BorderView(this, BorderView.FULL_BORDER,Color.black));
		gview = new GroupView(this);
		addPView(gview);


	}
    void setGroupMap(int[][] gmap) {
		gview.setGroupMap(gmap);//枠のグループ分けを行う
	}
    void setS1(int x, int y,String s) { // 小さな文字(上)の1項目をセットする
		s1view.setString(x,y,s);
    }
    // 問題をセットする。
    void setMondai(String[] m) {
    	chview.setCharByString(m);
    }
    // チャレンジ文字列を得る。
    String[] getCharaenge() {
    	return chview.getCharenge();
    }
 // 指定されたユニットに数字(文字)をセットする。
  	void setLetter(Character c) {
  		if(cur != null) { // ユニット指定があるかどうか調べる
  			chview.setCharacter(cur.x, cur.y, c); // そこにセット
  			frame.repaint();//再描画
  		}
  	}
    @Override
	public void mouseClicked(MouseEvent e) {
		Point p = getUnit(e);
		if(p!=null) {
		chview.rotateCharacter(p,false); // 文字を変更する
		frame.repaint();
		}
		if(cur != null) { // 既に設定されているユニットがある
			colview.setColor(cur.x, cur.y, null); // ユニットの色をなくす(透明)
			cur = null;
		}
		cur = getUnit(e); // マウス位置のユニットを得る。ナル値の時は枠外をクリックしている。
		if(cur != null) colview.setColor(cur.x, cur.y, Color.pink); // マウス位置のユニットに色を塗る
		frame.repaint(); // 再描画

	}
	public void mousePressed(MouseEvent e) {	}
	public void mouseReleased(MouseEvent e) {	}
	public void mouseEntered(MouseEvent e) {	}
	public void mouseExited(MouseEvent e) { 	}

}

