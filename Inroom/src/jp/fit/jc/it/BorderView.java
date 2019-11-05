package jp.fit.jc.it;
import java.awt.Color;
import java.awt.Graphics;

public class BorderView extends PView {
	// 枠線に関する記述
	public static final int NO_BORDER   = 0;
	public static final int OUTER_BORDER = 1;
	public static final int VIRTICAL_BORDER = 2;
	public static final int HORIZONTAL_BORDER = 4;
	public static final int FULL_BORDER = 7;
	public static final int CENTER = 8; // BORDER(端)ではなく中央に引く(碁盤など)
	public static final int CENTER_OUTER_BORDER = CENTER | OUTER_BORDER;
	public static final int CENTER_VIRTICAL_BORDER = CENTER | VIRTICAL_BORDER;
	public static final int CENTER_HORIZONTAL_BORDER = CENTER | HORIZONTAL_BORDER;
	public static final int CENTER_FULL_BORDER = CENTER | FULL_BORDER;
	protected int hasBorder = NO_BORDER; // 枠線を書くかどうかのフラグ
	protected Color borderColor;         // 枠線の色、指定されていないときはforeground
	protected int borderWidth = 1;       // 枠線の幅（外枠のみ）

	public BorderView(PPanel2 p,int type,Color c) {
		super(p);
		hasBorder = type;
		borderColor = c;
	}

	public void setBorder(int type)    { hasBorder = type; }
	public void setBorderWidth(int w)  { borderWidth = w; }
	public void setBorderColor(Color c) { borderColor = c; }

	@Override
	protected void drawView(Graphics g) {
		if(hasBorder!=NO_BORDER) { // 枠線を描くかどうか
			// 枠線の色を設定
			if(borderColor == null) g.setColor(panel.getForeground());
			else                    g.setColor(borderColor);

			// 端に引く
			if((hasBorder & CENTER) != CENTER) {
				//縦線を引く
				if((hasBorder & VIRTICAL_BORDER) != NO_BORDER) {
					for(int x = 0; x <= panel.num.x; x++) {
						int xs = panel.getOriginX(x); // Ｘ座標(共通)
						int ye = panel.getOriginY(panel.num.y); // Ｙ座標（終点）
						g.drawLine(xs,panel.offset.y,xs,ye); //線を引く
					}
				}
				//横線を引く
				if((hasBorder & HORIZONTAL_BORDER) != NO_BORDER) {
					for(int y = 0; y <= panel.num.y; y++) {
						int ys = panel.getOriginY(y);  // Ｙ座標(共通)
						int xe = panel.getOriginX(panel.num.x); // Ｘ座標（終点）
						g.drawLine(panel.offset.x,ys,xe,ys); //線を引く
					}
				}
				// 外枠線を引く
				if((hasBorder & OUTER_BORDER) != NO_BORDER) {
					int off = panel.offset.x<panel.offset.y?panel.offset.x:panel.offset.y;
					int w = borderWidth<off?borderWidth:off;
					for(int i = 0; i < w; i++) {
						g.drawRect(panel.offset.x-i,panel.offset.y-i,
								panel.size.x*panel.num.x+2*i,panel.size.y*panel.num.y+2*i); //四角を描画
					}
				}
			} else { // 中央に引く
				//縦線を引く
				if((hasBorder & VIRTICAL_BORDER) != NO_BORDER) {
					for(int x = 0; x < panel.num.x; x++) {
						int xs = panel.getOriginX(x) + panel.size.x/2; // Ｘ座標(共通)
						int ye = panel.getOriginY(panel.num.y-1) + panel.size.y/2; // Ｙ座標（終点）
						g.drawLine(xs,panel.offset.y+panel.size.y/2,xs,ye); //線を引く
					}
				}
				//横線を引く
				if((hasBorder & HORIZONTAL_BORDER) != NO_BORDER) {
					for(int y = 0; y <= panel.num.y; y++) {
						int ys = panel.getOriginY(y) + panel.size.y/2; // Ｙ座標(共通)
						int xe = panel.getOriginX(panel.num.x-1) + panel.size.x/2; // Ｘ座標（終点）
						g.drawLine(panel.offset.x+panel.size.x/2,ys,xe,ys); //線を引く
					}
				}
				// 外枠線を引く
				if((hasBorder & OUTER_BORDER) != NO_BORDER) {
					int off = panel.offset.x<panel.offset.y?panel.offset.x:panel.offset.y;
					int w = borderWidth<off?borderWidth:off;
					for(int i = 0; i < w; i++) {
						g.drawRect(panel.offset.x+panel.size.x/2-i,panel.offset.y+panel.size.y/2-i,
								panel.size.x*(panel.num.x-1)+2*i,panel.size.y*(panel.num.y-1)+2*i); //四角を描画
					}
				}

			}
		}
	}

}
