package jp.fit.jc.it;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

public class StringView extends PView {
	String view[][];
	Font font;
	FontMetrics fm;
	Point textMargin = new Point(5,5);
	Color fontColor;
	// 表示モード：0x100 小さな文字 0x000 大きな(通常の)文字
	//           : 0x00 下、0x10 上下の中央、0x20 上にそろえる
	//           : 0x00 左、0x01 中央        0x02 右
	int mode = NORMAL;
	public static final int NORMAL = 0;
	public static final int SIZE_NORMAL = 0x000;
	public static final int SIZE_SMALL = 0x100;
	public static final int SIZE_MASK = 0xf00;
	public static final int BASEHIGHT_MASK = 0xf0;
	public static final int BASEHIGHT_BOTTOM = 0x00;
	public static final int BASEHIGHT_CENTER = 0x10;
	public static final int BASEHIGHT_TOP = 0x20;
	public static final int ALIGNMENT_MASK = 0x0f;
	public static final int ALIGNMENT_LEFT = 0x00;
	public static final int ALIGNMENT_CENTER = 0x01;
	public static final int ALIGNMENT_RIGHT = 0x02;
	public static final int SMALL_NW = (SIZE_SMALL | BASEHIGHT_TOP | ALIGNMENT_LEFT);
	public static final int SMALL_SW = ( SIZE_SMALL | BASEHIGHT_BOTTOM | ALIGNMENT_LEFT);
	public static final int CENTER = 3;
	public void setFontColor(Color c) { fontColor = c; }
	public Color getFontColor() { return fontColor; }
	public void setTextMargin(int x, int y) { textMargin = new Point(x,y); }
	public Point getTextMargin() { return textMargin;}
	public void setMode(int m) {mode = m; fixFont();}
	public int getMode() { return mode; }
	@Override
	public void changeUnitSize() { fixFont(mode); }

	public StringView(PPanel2 p) {
		this(p,NORMAL);
	}
	public StringView(PPanel2 p, int m) {
		super(p);
		view = new String[panel.num.y][panel.num.x];
		fixFont(m);
	}

	// フォントの設定
	public void fixFont(int m) {
		mode = m;
		fixFont();
	}

	public void fixFont() {
		int fSize;
		int minSize = panel.size.y<panel.size.x ? panel.size.y : panel.size.x; // 枠の小さいほう基準
		if(font == null) font = panel.getFont();
		switch(mode & SIZE_MASK) {
		case SIZE_SMALL: // 左上
			fSize = minSize / 3; // 枠の1/3の大きさ
			break;
		case SIZE_NORMAL:
		default:
			fSize = minSize * 3 / 4; // 枠の3/4の大きさ
			break;
		}
		// 上下マージン
		int vmargin;
		switch(mode & BASEHIGHT_MASK) {
		case BASEHIGHT_CENTER: // 中央
			vmargin = (panel.size.y + fSize)/2;
			break;
		case BASEHIGHT_TOP: // 上
			vmargin = fSize + 1;//(int)(fSize*1.3)+1;
			break;

		case BASEHIGHT_BOTTOM: // 下
		default:
			vmargin = panel.size.y - ((int)(fSize*0.3)+1);
			break;
		}
		int hmargin = panel.size.x / 10 + 1;
		setTextMargin(hmargin,vmargin);
		font = new Font(font.getName(),font.getStyle(),fSize);
		fm = panel.getFontMetrics(font);
		fontColor = panel.getForeground();
	}

	// view の中身を全部変更：短い場合は足りない分はナルとなる。長い場合は無視
	public void setStringByArray(String s[][]) {
		for(int y = 0; y < panel.num.y; y++) {
			for(int x = 0; x < panel.num.x; x++) {
				try {
					view[y][x] = s[y][x];
				} catch (Exception e) {view[y][x] = null;}; // 例外はナル
			}
		}
	}

	// 指定されたviewの値変更
	public void setString(int x,int y, String s) { view[y][x] = s; }
	// 指定されたviewの値を得る
	public String getString(int x,int y) { return view[y][x]; }

	// 挑戦文字列を得る
	String[][] getCharenge() {
		String[][] ans = new String[panel.num.y][panel.num.x];
		for(int y = 0; y < panel.num.y; y++) {
			for(int x = 0; x < panel.num.x; x++) {
				ans[y][x] = view[y][x];
			}
		}
		return ans;
	}

	@Override
	protected void drawView(Graphics g) {
		g.setFont(font);
		g.setColor(fontColor);
		for(int y = 0; y < panel.num.y; y++) {
			for(int x = 0; x < panel.num.x; x++) {
				if(view[y][x] != null && ! view[y][x].equals("")) {
					int px= panel.getOriginX(x) + textMargin.x;
					switch(mode & ALIGNMENT_MASK) {
					case ALIGNMENT_CENTER:
						px = panel.getOriginX(x) + (panel.size.x - fm.stringWidth(view[y][x])) / 2;
						break;
					case ALIGNMENT_RIGHT:
						px = panel.getOriginX(x) + (panel.size.x - textMargin.x - fm.stringWidth(view[y][x]));
						break;
					}
					int py = panel.getOriginY(y) + textMargin.y;
					g.drawString(view[y][x], px, py);
				}
			}
		}
	}
}
