package jp.fit.jc.it;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Hashtable;

public class ColorView extends PView {
	protected Color view[][];
	protected Hashtable<Character,Color> colorMap; // 文字対応のカラーマップ

	// ColorMap のサンプル
	public  static String standardColorNames = "白黒灰赤燈黄緑青藍紫";
	public static int standardColors[] = {0xffffff,0x000000,0x7e7e7e,0xff0000,0xff8000,0xffff00,0x00ff00,0x0000ff,0x00ffff,0xff00ff};
	public static Hashtable<Character,Color> getStandardColorMap() {
		return makeColorMap(standardColorNames,standardColors);
	}
	public static Hashtable<Character,Color> makeColorMap(String name,int[] colors) {
		Hashtable<Character,Color> map = new Hashtable<Character,Color>();
		for(int i = 0; i < colors.length; i++) {
			map.put(name.charAt(i), new Color(colors[i]));
		}
		return map;
	}

	public ColorView(PPanel2 p) {
		super(p);
		view = new Color[p.num.y][p.num.x];// 領域を割り当てる
	}
	// x,y 位置の色をcに変更
	public void setColor(int x, int y, Color c) {
		view[y][x] = c;
	}
	public Color getColor(int x,int y) { return view[y][x]; }

	public void setColorArea(Point start,Point end, Color c) {
		int sx = start.x < end.x ? start.x : end.x;
		int sy = start.y < end.y ? start.y : end.y;
		int ex = start.x > end.x ? start.x : end.x;
		int ey = start.y > end.y ? start.y : end.y;
		for(int y = sy; y <= ey; y++) {
			for(int x = sx; x <= ex; x++) {
				view[y][x] = c;
			}
		}
	}
	public void clear() { fill(null); } // 色の消去
	public void fill(Color c) { // 塗りつぶす
		for(int y = 0; y <= panel.num.y; y++) {
			for(int x = 0; x <= panel.num.x; x++) {
				view[y][x] = c;
			}
		}
	}
	// view の中身を全部変更：短い場合は足りない分は変更されない。長い場合は無視
	public void setColorByString(String s[],Hashtable<Character,Color> colorMap) {
		for(int y = 0; y < panel.num.y; y++) {
			for(int x = 0; x < panel.num.x; x++) {
				try {
					view[y][x] = colorMap.get(s[y].charAt(x));
				} catch (Exception e) {}; // 例外は無視する
			}
		}
	}

	// カラーマップの操作
	public Hashtable<Character,Color> getColorMap() { return colorMap; }
	public void setColorMap(Hashtable<Character,Color> map) { colorMap=map; }
	public Color getColorByChar(char c) { if(colorMap !=null) return colorMap.get(c); return null; }
	public void setColorByChar(char c,Color col) {
		if(colorMap==null) {
			colorMap = new Hashtable<Character,Color>();
		}
		colorMap.put(c, col);
	}

	@Override
	protected void drawView(Graphics g) {
		for(int y = 0; y < panel.num.y; y++) {
			for(int x = 0; x < panel.num.x; x++) {
				Color c = view[y][x];
				if(c != null) {
					paintUnit(g,x,y,c); // その色に塗る
				}
			}
		}
	}

	protected void paintUnit(Graphics g,int px,int py, Color c) {
		int x = panel.getOriginX(px); // 座標値を計算する
		int y = panel.getOriginY(py);
		g.setColor(c); // 色をセットし
		g.fillRect(x, y, panel.size.x, panel.size.y); //　矩形に塗る
	}

	// view の中身を全部変更：短い場合は足りない分は変更されない。長い場合は無視
	public void setColorByString(String s[]) {
		for(int y = 0; y < panel.num.y; y++) {
			for(int x = 0; x < panel.num.x; x++) {
				try {
					view[y][x] = colorMap.get(s[y].charAt(x));
				} catch (Exception e) {}; // 例外は無視する
			}
		}
	}
}
