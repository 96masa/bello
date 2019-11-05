package jp.fit.jc.it;
// PPanel2(最終更新：2017/12/07)
// 新たなPPanel

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PPanel2 extends JPanel {
	private static final long serialVersionUID = 20171225L;// バージョン管理番号（警告がうるさいので）
	protected JFrame frame;  // 親のフレーム
	protected Point offset = new Point(16,16);  // 左オフセット/上オフセット
	protected Point size   = new Point(32,32);  // 1枠の大きさ（幅/高さ)
	protected Point num;                        // 枠の数
	protected int width;  // パネルの高さ
	protected int height; // パネルの幅

	// View とのインターフェイス
	protected class PViewList {
		PView view;
		PViewList next;

		PViewList(PView v) {
			view = v;
			next = null;
		}
	}
	PViewList viewList; // view のリスト：装着されたviewを格納する
	protected void addPView(PView v) {
		if(viewList == null) {
			viewList = new PViewList(v);
		} else {
			PViewList list;
			for(list = viewList; list.next != null; list = list.next) ; // 最後を探す
			list.next = new PViewList(v);
		}
	}

	public PPanel2(int x, int y,JFrame f) { // 横枠の数と縦枠の数指定
		super();
		frame = f;
		num = new Point(x,y);              // 枠の数を保存
		width  = size.x*num.x + 2*offset.x; // パネルの幅を計算
		height = size.y*num.y + 2*offset.y; // パネルの幅を計算
		setMinimumSize(new Dimension(width,height)); // パネルの大きさ設定
		setPreferredSize(new Dimension(width,height));

//		fixFont();
	}
	// 無引数のコンストラクタ：2013/01/15追加
	public PPanel2() { super(); }

	public int getWidth() { return width; }   // パネルの高さ
	public int getHeight() { return height; } // パネルの幅

	// マウス位置から、どの枠でのイベントかを計算する。ポイントクラスを返す。
	// マウスが枠の中に入っていなければ null を返す
	protected Point getUnit(MouseEvent e) {
		int x = (e.getX() - offset.x) / size.x; // マウス位置から枠位置を計算
		int y = (e.getY() - offset.y) / size.y;
		if( 0 <= x && x < num.x && 0 <= y && y < num.y) { // 枠内に入っているか
			return new Point(x,y); // 枠位置を返す
		}
		return null;  // 枠に入っていないのでnull
	}

	// 枠のサイズを変更する。
	public void setUnitSize(int x, int y) {
		size = new Point(x,y);
		if(num != null) { //枠数が設定されているなら、各パラメタを計算しておく
			width  = size.x*num.x + 2*offset.x; // パネルの幅を計算
			height = size.y*num.y + 2*offset.y; // パネルの幅を計算
			setMinimumSize(new Dimension(width,height)); // パネルの大きさ設定
			setPreferredSize(new Dimension(width,height));
		}
		for(PViewList pv = viewList;pv != null; pv = pv.next) { // 各ビューに対して通知する
			pv.view.changeUnitSize();
		}
	}

	public Point getUnitSize() { return size; }

	public Point getUnitNumber() { return num; }
	// オフセットを変更する。
	public void setOffset(int x, int y) {
		offset = new Point(x,y);
		if(num != null) { //枠数が設定されているなら、各パラメタを計算しておく
			width  = size.x*num.x + 2*offset.x; // パネルの幅を計算
			height = size.y*num.y + 2*offset.y; // パネルの幅を計算
			setMinimumSize(new Dimension(width,height)); // パネルの大きさ設定
			setPreferredSize(new Dimension(width,height));
		}
	}

	// 座標値の計算メソッド
	public int getOriginX(Point p) { return getOriginX(p.x); }
	public int getOriginX(int x) { return x * size.x + offset.x; }
	public int getOriginY(Point p) { return getOriginY(p.y); }
	public int getOriginY(int y) { return y * size.y + offset.y; }
	public Point getOrigin(Point p) { return new Point(getOriginX(p.x), getOriginY(p.y)); }
	public Point getOrigin(int x, int y) { return new Point(getOriginX(x), getOriginY(y)); }

	public void paintComponent(Graphics g) { // view を次々に呼び出して描画する。
		for(PViewList list = viewList; list != null; list = list.next) {
			list.view.drawView(g);
		}
	}
}
