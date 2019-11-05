package jp.fit.jc.it;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

public class CharacterView extends PView {
	protected char view[][];

	protected Font font;
	protected FontMetrics fm;
	protected Point textMargin = new Point(5,5);
	protected char defaultNullChar = ' '; // ナル文字の表示文字
	protected Color fontColor;

	@Override
	protected void changeUnitSize() { fixFont(); } // 枠のサイズが変わったら、フォントの大きさの調整も必要

	public void setFontColor(Color c) { fontColor = c; }
	public Color getFontColor() { return fontColor; }

	public CharacterView(PPanel2 p) {
		super(p);
		view = new char[p.num.y][p.num.x];// 領域を割り当てる
		fixFont();
		fontColor = p.getForeground();
	}

	 // x,y 位置の文字を得る
    public char getCharacter(int x, int y) {
        return view[y][x];
    }

	// なる文字
	public void setDefaultNullChar(char c) { defaultNullChar = c; }
	public char getDefaultNullChar() { return defaultNullChar; }

	// x,y 位置の文字をcに変更
	public void setCharacter(int x, int y, char c) {
		view[y][x] = c;
	}

	// view の中身を全部変更：短い場合は足りない分は変更されない。長い場合は無視
	public void setCharByString(String s[]) {
		for(int y = 0; y < panel.num.y; y++) {
			for(int x = 0; x < panel.num.x; x++) {
				try {
					view[y][x] = s[y].charAt(x);
				} catch (Exception e) {}; // 例外は無視する
			}
		}
	}

	@Override
	protected void drawView(Graphics g) {
		g.setFont(font);
		g.setColor(fontColor);
		for(int y = 0; y < panel.num.y; y++) {
			for(int x = 0; x < panel.num.x; x++) {
				char c = view[y][x];
				if(c == '\0' || Character.isISOControl(c)) { // ナル文字や制御文字のときは空白(デフォルト時)にする。
					c = defaultNullChar;
				}
				drawChar(g,x,y,c); // x,y 位置の文字を表示
			}
		}
	}

	protected String rotateChars = null; // 変更順序を表す文字列
	public String getRotateChars() { return rotateChars; }
	public void setRotateChars(String s) { rotateChars = s; }

	public void rotateCharacter(int x, int y, String rc, boolean nullFlag) { // x,y 位置の文字を1ステップ変更する。
		if(rc == null || rc.isEmpty()) return;
		int ind = rc.indexOf(view[y][x]);
		if(ind < 0) { // 文字がない
			if(nullFlag) view[y][x] = rc.charAt(0);
		} else {
			ind++;
			if(ind >= rc.length()) ind = 0; // 最後まできたら０に戻す
			view[y][x]=rc.charAt(ind);
		}
	}
	public void rotateCharacter(int x, int y, String rc) {	rotateCharacter(x,y,rc,false); 	}

	public void rotateCharacter(int x, int y, boolean nullFlag) { // x,y 位置の文字を1ステップ変更する。
		if(rotateChars==null || rotateChars.isEmpty()) return; // 文字列が設定されていないときは仕方ない
		int ind = rotateChars.indexOf(view[y][x]);
		if(ind < 0) { // 文字がない
			if(nullFlag) view[y][x] = rotateChars.charAt(0);
		} else {
			ind++;
			if(ind >= rotateChars.length()) ind = 0; // 最後まできたら０に戻す
			view[y][x] = rotateChars.charAt(ind);
		}
	}
	public void rotateCharacter(int x,int y) { rotateCharacter(x,y,false); }

	// 座標値をPointにしたメソッドをオーバーロードしておく
	public void rotateCharacter(Point p) { rotateCharacter(p.x,p.y); }
	public void rotateCharacter(Point p,boolean f) { rotateCharacter(p.x,p.y,f); }
	public void rotateCharacter(Point p,String s) { rotateCharacter(p.x,p.y,s); }
	public void rotateCharacter(Point p,String s, boolean f) { rotateCharacter(p.x,p.y,s, f); }

	// 枠に文字を描くメソッド
	public void drawChar(Graphics g,int px,int py,char c) {
		String s = ""+c;
		int x = panel.getOriginX(px) + (panel.size.x - fm.stringWidth(s)) / 2; // 座標値を計算する(横はセンター)
		int y = panel.getOriginY(py) + (panel.size.y - textMargin.y);
		g.drawString(s, x, y);
	}

	// 枠いっぱいのフォント
	public void fixFont() {
		int fSize = panel.size.y - textMargin.y*2;
		if(font == null) font = panel.getFont();
		font = new Font(font.getName(),font.getStyle(),fSize);
		fm = panel.getFontMetrics(font);
	}

	// 挑戦文字列を得る
	public String[] getCharenge() {
		String[] ans = new String[panel.num.y];
		for(int y = 0; y < panel.num.y; y++) {
			ans[y] = "";
			for(int x = 0; x < panel.num.x; x++) {
				if(view[y][x] == '\0') ans[y] += defaultNullChar;
				else ans[y] += view[y][x];
			}
		}
		return ans;
	}
}
