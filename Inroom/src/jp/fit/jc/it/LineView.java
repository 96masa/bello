package jp.fit.jc.it;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class LineView extends PView {
	// 4方向の線を管理するクラス。
	protected class Rules {
		boolean up = false;
		boolean down = false;
		boolean left = false;
		boolean right = false;
		// 線が存在するかどうかだけ判断するメソッド
		boolean hasLine() { return (up || down || left || right); }
		void clear() { up = down = left = right = false; } // 全消し
		void set(Rules r) { // r のtrue の所をtrueにする
			if(r.up) up = true; if(r.down) down = true;
			if(r.left) left = true;if(r.right) right = true;
		}
		void reset(Rules r) { // r のtrue の所をfalseにする
			if(r.up) up = false; if(r.down) down = false;
			if(r.left) left = false;if(r.right) right = false;
		}
		void flip(Rules r) { // r のtrue の所を反転する
			if(r.up) up = !up; if(r.down) down = !down;
			if(r.left) left = !left;if(r.right) right = !right;
		}
	}

	protected Rules view[][];
	public LineView(PPanel2 p) {//コンストラクタ
		super(p);
		view = new Rules[p.num.y][p.num.x];
		for(int y = 0; y < p.num.y; y++) {
			for(int x = 0; x < p.num.x; x++) {
				view[y][x] = new Rules();
			}
		}
	}
	public void clear() {// 全消し
		for(int y = 0; y < panel.num.y; y++) {
			for(int x = 0; x < panel.num.x; x++) {
				view[y][x].clear();
			}
		}
	}

	protected int lineWidth = 8;				// 線の太さ
	protected Color lineColor = Color.gray;	// 線の色
	public int getLineWidth() { return lineWidth; }
	public void setLineWidth(int w) { lineWidth = w; }
	public Color getLineColor() { return lineColor;}
	public void setLineColor(Color c) { lineColor = c; }
	// view上の特定の線を参照/操作する
	public boolean getUp(int x,int y) { return view[y][x].up; }
	public boolean getDown(int x,int y) { return view[y][x].down; }
	public boolean getLeft(int x,int y) { return view[y][x].left; }
	public boolean getRight(int x,int y) { return view[y][x].right; }
	public void setUp(int x,int y, boolean b) { view[y][x].up = b; }
	public void setDown(int x,int y, boolean b) { view[y][x].down = b; }
	public void setLeft(int x,int y, boolean b) { view[y][x].left = b; }
	public void setRight(int x,int y, boolean b) { view[y][x].right = b; }
	public boolean getUp(Point p) { return view[p.y][p.x].up; }
	public boolean getDown(Point p) { return view[p.y][p.x].down; }
	public boolean getLeft(Point p) { return view[p.y][p.x].left; }
	public boolean getRight(Point p) { return view[p.y][p.x].right; }
	public void setUp(Point p, boolean b) { view[p.y][p.x].up = b; }
	public void setDown(Point p, boolean b) { view[p.y][p.x].down = b; }
	public void setLeft(Point p, boolean b) { view[p.y][p.x].left = b; }
	public void setRight(Point p, boolean b) { view[p.y][p.x].right = b; }



	// 他のLineViewを統合するメソッド(Viewの大きさは同じであること)
	public void set(LineView r) { //r のtrue の所をtrueにする
		for(int y = 0; y < panel.num.y; y++) {
			for(int x = 0; x < panel.num.x; x++) {
				view[y][x].set(r.view[y][x]);
			}
		}
	}
	public void reset(LineView r) { //r のtrue の所をfalseにする
		for(int y = 0; y < panel.num.y; y++) {
			for(int x = 0; x < panel.num.x; x++) {
				view[y][x].reset(r.view[y][x]);
			}
		}
	}
	public void flip(LineView r) { //r のtrue の所を反転させる
		for(int y = 0; y < panel.num.y; y++) {
			for(int x = 0; x < panel.num.x; x++) {
				view[y][x].flip(r.view[y][x]);
			}
		}
	}

	@Override
	protected void drawView(Graphics g) {
		g.setColor(lineColor);
		for(int y = 0; y < panel.num.y; y++) {
			int py = panel.getOriginY(y);// ユニット左上の座標値を計算する
			for(int x = 0; x < panel.num.x; x++) { // 線の描画を行う
				if(view[y][x].hasLine()) { // 線があれば描画（なければ必要なし)
					int px = panel.getOriginX(x);// ユニット左上の座標値を計算する
					if(view[y][x].up) {// 上線があるなら上線を引く
						g.fillRect(px + panel.size.x/2 - lineWidth/2, py, lineWidth, panel.size.y/2);
					}
					if(view[y][x].down) {// 下線があるなら下線を引く
						g.fillRect(px + panel.size.x/2 - lineWidth/2, py + panel.size.y - panel.size.y / 2, lineWidth, panel.size.y/2);
					}
					if(view[y][x].left) {// 左線があるなら左線を引く
						g.fillRect(px , py + panel.size.y / 2 - lineWidth/2, panel.size.x / 2, lineWidth);
					}
					if(view[y][x].right) {// 右線があるなら右線を引く
						g.fillRect(px + panel.size.x - panel.size.x/2, py + panel.size.y / 2 - lineWidth/2, panel.size.x / 2, lineWidth);
					}
					// 線に丸みを持たせる
					g.fillOval(px+panel.size.x/2-lineWidth/2, py+panel.size.y/2-lineWidth/2, lineWidth, lineWidth);
				}
			}
		}
	}

	// 答えあわせのために文字列化するメソッド
	public String[] getCharenge() {
		String[] ans = new String[panel.num.y];
		for(int y = 0; y < panel.num.y; y++) {
			ans[y] = "";
			for(int x = 0; x < panel.num.x; x++) {
				ans[y] += lineToChar(view[y][x]);
			}

		}
		return ans;
	}

	// 線を表すRulesクラスを罫線素に置き換える。ただし端点のみの罫線素はないので数字で置き換えている
	public static char lineToChar(Rules r) {
		if(r.up) {
			if(r.down) {
				if(r.left) {
					if(r.right) { return '┼'; }
					else        { return '┤';	}
				} else {
					if(r.right) { return '├'; }
					else        { return '│';	}
				}
			} else {
				if(r.left) {
					if(r.right) { return '┴';	}
					else        { return '┘'; }
				} else {
					if(r.right) { return '└'; }
					else  		{ return '１'; }
				}
			}
		} else {
			if(r.down) {
				if(r.left) {
					if(r.right) { return '┬'; }
					else 		{ return '┐'; }
				} else {
					if(r.right) { return '┌'; }
					else		{ return '２'; }
				}
			} else {
				if(r.left) {
					if(r.right) { return '─'; }
					else 		{ return '３';	}
				} else {
					if(r.right) { return '４'; }
					else		{ return '　';	}
				}
			}
		}
	}
}
