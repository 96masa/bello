package jp.fit.jc.it;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class RulesView extends PView {
	protected boolean[][] vrules,hrules; // 縦線と横線の描画フラグ
	protected Color rulesColor;
	public RulesView(PPanel2 p) {
		super(p);
		if(p != null) {
			vrules = new boolean[p.num.y][p.num.x+1]; // 縦線はユニットの列数より１つ多い。
			hrules = new boolean[p.num.y+1][p.num.y]; //　横線はユニットの行数より１つ多い。
			rulesColor = p.getForeground();
		}
	}

	public void setVirt(int x, int y, boolean f) { vrules[y][x]=f; }
	public void setVirt(int x, int y) { vrules[y][x]=true; }
	public void flipVirt(int x, int y) { vrules[y][x]=!vrules[y][x]; }
	public void clearVirt(int x, int y) { vrules[y][x]=false; }
	public boolean getVirt(int x, int y) { return vrules[y][x]; }
	public void setHori(int x, int y, boolean f) { hrules[y][x]=f; }
	public void setHori(int x, int y) { hrules[y][x]=true; }
	public void flipHori(int x, int y) { hrules[y][x]=!hrules[y][x]; }
	public void clearHori(int x, int y) { hrules[y][x]=false; }
	public boolean getHori(int x, int y) { return hrules[y][x]; }

	public static final int HORIZONTAL = 1;
	public static final int VIRTICAL = 2;
	protected boolean narrowTouch;
	protected String convChars = " -|+";

	public void setConvChars(String s) { convChars = s; }
	public String setConvChars() { return convChars; }

	public class Rule {
		private Point p;
		private int type;
		Rule(Point p0,int ty) {
			p = p0;
			type = ty;
		}
		public Point getPoint() { return p; }
		public int getX() { return p.x; }
		public int getY() { return p.y; }
		public int getType() { return type; }
		public void setPoint(Point p0) { p = p0; }
		public void setX(int x) { p.x = x; }
		public void setY(int y) { p.y = y; }
		public void setType(int ty) { type = ty; }
	}

	public void setNarrow() { narrowTouch = true; }
	public void setWide() { narrowTouch = false; }

	public Rule getRule(MouseEvent e) {
		int x = (e.getX() - panel.offset.x) / panel.size.x; // マウス位置から枠位置を計算
		int y = (e.getY() - panel.offset.y) / panel.size.y;
		if(e.getX() - panel.offset.x < 0) { // 枠より左
			if(e.getY() - panel.offset.y < 0) return null; // 該当線なし
			if(e.getY() - (panel.num.y+1)*panel.size.y - panel.offset.y >= 0) return null; // 該当線なし
			return new Rule(new Point(0,y),VIRTICAL);
		}
		if(e.getX() - (panel.num.x+1)*panel.size.x - panel.offset.x >= 0) {// 枠より右
			if(e.getY() - panel.offset.y < 0) return null; // 該当線なし
			if(e.getY() - (panel.num.y+1)*panel.size.y - panel.offset.y >= 0) return null; // 該当線なし
			if(e.getX() - (panel.num.x+1)*panel.size.x - 2*panel.offset.x >= 0) return null; // 離れすぎ
			return new Rule(new Point(panel.num.x,y),VIRTICAL);
		}
		if(e.getY() - panel.offset.y < 0) { // 枠の上
			if(e.getX() - panel.offset.x < 0) return null; // 該当線なし
			if(e.getX() - (panel.num.x+1)*panel.size.x - panel.offset.x >= 0) return null; // 該当線なし
			return new Rule(new Point(x,0),HORIZONTAL);
		}
		if(e.getY() - (panel.num.y+1)*panel.size.y - panel.offset.y >= 0) {// 枠より下
			if(e.getX() - panel.offset.x < 0) return null; // 該当線なし
			if(e.getX() - (panel.num.x+1)*panel.size.x - panel.offset.x >= 0) return null; // 該当線なし
			if(e.getY() - (panel.num.y+1)*panel.size.y - 2*panel.offset.y >= 0) return null; // 離れすぎ
			return new Rule(new Point(x,panel.num.y),HORIZONTAL);
		}
		int dx = e.getX() - x * panel.size.x - panel.offset.x;
		int dy = e.getY() - y * panel.size.y - panel.offset.y;
		if(narrowTouch) {
			if(panel.size.x/4 < dx && dx < panel.size.x *3/4 &&
					panel.size.y/4 < dy && dy < panel.size.y*3/4) return null; // ユニットの検出も行うため
		}
		if(dx*panel.size.x > dy*panel.size.y) { // 右上
			if(dx*panel.size.x + dy*panel.size.y < panel.size.x * panel.size.y) { // 左上
				return new Rule(new Point(x,y),HORIZONTAL);
			} else {                                                              // 左下
				return new Rule(new Point(x+1,y),VIRTICAL);
			}
		} else { // 右下
			if(dx*panel.size.x + dy*panel.size.y < panel.size.x * panel.size.y) { // 左上
				return new Rule(new Point(x,y),VIRTICAL);
			} else {                                                              // 左下
				return new Rule(new Point(x,y+1),HORIZONTAL);
			}
		}
	}

	public void setRulesByStrings(String[] s) { // 文字列配列sでセットする
		// 罫線配列 の中身を全部変更：短い場合は足りない分は罫線なしとなる。長い場合は無視
		vrules = new boolean[panel.num.y][panel.num.x+1]; // 縦線はユニットの列数より１つ多い。
		hrules = new boolean[panel.num.y+1][panel.num.y]; //　横線はユニットの行数より１つ多い。
		int y; // 後で使う
		for(y = 0; y < panel.num.y; y++) {
			int x; // 後で使う
			for(x = 0; x < panel.num.x; x++) {
				try {
					int t = convChars.indexOf(s[y].charAt(x));
					switch(t) {
					case HORIZONTAL: hrules[y][x] = true; break;
					case VIRTICAL:   vrules[y][x] = true; break;
					case VIRTICAL+HORIZONTAL:
						hrules[y][x] = true;
						vrules[y][x] = true;
						break;
					}
				} catch (Exception e) {}; // 例外は無視
			}
			// 最後の1列
			try {
				int t = convChars.indexOf(s[y].charAt(x));
				switch(t) {
				case VIRTICAL:
				case VIRTICAL+HORIZONTAL:
					vrules[y][x] = true;
					break;
				}
			} catch (Exception e) {}; // 例外は無視
		}
		for(int x = 0; x < panel.num.x; x++) {
			try {
				int t = convChars.indexOf(s[y].charAt(x));
				switch(t) {
				case HORIZONTAL: hrules[y][x] = true; break;
				case VIRTICAL+HORIZONTAL:
					hrules[y][x] = true;
					break;
				}
			} catch (Exception e) {}; // 例外は無視
		}
	}

	public String[] getRulesByStrings() { // 罫線を表す文字列配列を得る
		String[] ans = new String[panel.num.y+1];
		int y; // 後で使う
		for(y = 0; y < panel.num.y; y++) {
			ans[y] = "";
			int x; // 後で使う
			for(x = 0; x < panel.num.x; x++) {

				if(vrules[y][x]) {
					if(hrules[y][x]) ans[y] += convChars.charAt(HORIZONTAL+VIRTICAL);
					else ans[y] += convChars.charAt(VIRTICAL);
				} else {
					if(hrules[y][x]) ans[y] += convChars.charAt(HORIZONTAL);
					else ans[y] += convChars.charAt(0);
				}
			}
			// 最後の1列
			if(vrules[y][x]) ans[y] += convChars.charAt(VIRTICAL);
			else ans[y] += convChars.charAt(0);
		}
		ans[y] = "";
		for(int x = 0; x < panel.num.x; x++) {
			if(hrules[y][x]) ans[y] += convChars.charAt(HORIZONTAL);
			else ans[y] += convChars.charAt(0);
		}
		return ans;
	}

	@Override
	protected void drawView(Graphics g) {
		g.setColor(rulesColor);
		// 縦線の描画
		for(int y = 0; y < panel.num.y; y++) {
			for(int x = 0; x < panel.num.x+1; x++) {
				if(vrules[y][x]) { // 縦線の描画
					g.drawLine(panel.getOriginX(x), panel.getOriginY(y), panel.getOriginX(x), panel.getOriginY(y+1));
				}
			}
		}
		// 横線の描画
		for(int y = 0; y < panel.num.y+1; y++) {
			for(int x = 0; x < panel.num.x; x++) {
				if(hrules[y][x]) { // 横線の描画
					g.drawLine(panel.getOriginX(x), panel.getOriginY(y), panel.getOriginX(x+1), panel.getOriginY(y));
				}
			}
		}
	}
}
