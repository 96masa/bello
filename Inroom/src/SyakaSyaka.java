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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jp.fit.jc.it.BorderView;
import jp.fit.jc.it.CombinedView;
import jp.fit.jc.it.DrawItem;
import jp.fit.jc.it.PPanel2;

// シャカシャカ
// Sample2607 を変更して(全面改訂して)作る

public class SyakaSyaka extends JFrame implements ActionListener{

	static String mondai[] = {     // 問題
			"２□□□□３□□２■",
			"□□□□□□□□□２",
			"□□４□□□□□□□",
			"２■□□□４□□□□",
			"□□□□■□□□□２",
			"□□□□□□□■■■",
			"□■□□□■□□□□",
			"□２□□４□□■□□",
			"□□■□□□□□□□",
			"□□２□□３□□■１"};

	static String kotae[] = {     // 正解文字列
	    "２┘└┘└３┘└２■",
	    "┘□┌┐┌┘□□└２",
	    "┐┌４┘└┐□□□└",
	    "２■┘□┌４┐□□┌",
	    "┘└┐┌■┘└┐┌２",
	    "┐┌□┘└┐┌■■■",
	    "□■┘□┌■□□□□",
	    "□２┐┌４┘└■┘└",
	    "┘└■┘└┐□└┐┌",
		"┐┌２┐┌３┐┌■１"};

	// 操作ボタン
	JButton reset,check,next,end; //
	SyakaSyakaPanel panel;

	public SyakaSyaka(String title) {

		super(title); // いつもの設定
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setLayout(new BorderLayout());
		setSize(600,400);

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
		SyakaSyaka frame = new SyakaSyaka("シャカシャカ");
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
		} else if(btn == next) { // 次の問題：未実装

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

}

class SyakaSyakaPanel extends PPanel2
implements MouseListener            // マウスリスナを装着し、マウスクリックに備える。
{
    CombinedView cview; // 複合ビュー、あとでアクセスするのでフィールドにしておく{

    // 背景が黒の文字を描くための反転文字クラス
    class DrawInvert extends DrawItem {
    	Color fgColor,bgColor; // 文字色と背景色
		public DrawInvert(PPanel2 p,Color fg,Color bg) {
			super(p);
			fgColor = fg;
			bgColor = bg;
		}
		@Override
		public void draw(Graphics g, int px, int py, char ch) {
	    	int x = getOriginX(px); // 座標値を計算する
	    	int y = getOriginY(py);
			g.setColor(bgColor); // 色をセットし
			g.fillRect(x, y, getUnitSize().x, getUnitSize().y); //　矩形に塗る
			cview.setFontColor(fgColor); // 色をセットし
			cview.drawChar(g, px, py, ch);
		}
    }

    // 三角を描画するクラス
    class DrawTriangle extends DrawItem {
    	Color fgColor; // 文字色と背景色
		public DrawTriangle(PPanel2 p,Color fg) {
			super(p);
			fgColor = fg;
		}
		@Override
		public void draw(Graphics g, int x0, int y0, char ch) {
	    	int x = getOriginX(x0); // 座標値を計算する
	    	int y = getOriginY(y0);
			g.setColor(fgColor); // 色をセットし
			int[] px = new int[3]; int [] py = new int[3];
			switch(ch) {
			case '┐':
				px[0] = x; px[1] = x + size.x; px[2] = x;
				py[0] = y; py[1] = y + size.y; py[2] = y + size.y; break;
			case '┌':
				px[0] = x+size.x; px[1] = x + size.x; px[2] = x;
				py[0] = y;        py[1] = y + size.y; py[2] = y + size.y; break;

			case '└':
				px[0] = x; px[1] = x + size.x; px[2] = x + size.x;
				py[0] = y; py[1] = y         ; py[2] = y + size.y; break;

			case '┘':
				px[0] = x + size.x; px[1] = x; px[2] = x;
				py[0] = y;          py[1] = y; py[2] = y + size.y; break;
			default: // いらない
				px[0] = x; px[1] = x + size.x; px[2] = x;
				py[0] = y; py[1] = y + size.y; py[2] = y; break;
			}
			g.fillPolygon(px, py, 3);
		}
    }

    SyakaSyakaPanel(int x, int y, JFrame f) {
		super(x,y,f);
		cview = new CombinedView(this);
		addPView(cview);
		addPView(new BorderView(this, BorderView.FULL_BORDER,Color.black));
		addMouseListener(this); // マウスリスナ装着
		cview.setRotateChars("□┌┐┘└"); // 文字の変更順序をセット

		cview.setDrawColor('■',Color.black);   // ■は黒く塗る
		DrawItem bstr = new DrawInvert(this,Color.white,Color.black); // 反転文字クラスのインスタンスを作る
		cview.setDrawItem('０', bstr);
		cview.setDrawItem('１', bstr);
		cview.setDrawItem('２', bstr);
		cview.setDrawItem('３', bstr);
		cview.setDrawItem('４', bstr);
		DrawItem tri = new DrawTriangle(this,new Color(100,100,100)); // 三角描画クラスのインスタンスを作る
		cview.setDrawItem('┐',tri);
		cview.setDrawItem('┘',tri);
		cview.setDrawItem('┌',tri);
		cview.setDrawItem('└',tri);
	}

    // 問題をセットする。
    void setMondai(String[] m) {
    	cview.setCharByString(m);
    }

    // チャレンジ文字列を得る。
    String[] getCharaenge() {
    	return cview.getCharenge();
    }

    @Override
	public void mouseClicked(MouseEvent e) {
		Point p = getUnit(e);
		cview.rotateCharacter(p,false); // 文字を変更する
		frame.repaint();

	}
	public void mousePressed(MouseEvent e) {	}
	public void mouseReleased(MouseEvent e) {	}
	public void mouseEntered(MouseEvent e) {	}
	public void mouseExited(MouseEvent e) { 	}

}
