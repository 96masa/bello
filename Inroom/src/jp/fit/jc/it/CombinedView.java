package jp.fit.jc.it;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Hashtable;

public class CombinedView extends CharacterView {
	protected DrawItem drawChar;
	protected Hashtable<Character,DrawItem> drawMap; // 何をどのように描画するかのハッシュ

	public CombinedView(PPanel2 p) {
		super(p);
		drawMap = new Hashtable<Character,DrawItem>();
		drawChar = new DrawItem(panel) {
			@Override
			public void draw(Graphics g, int x, int y, char ch) {
				drawChar(g,x,y,ch); // x,y 位置の文字を表示
			}};
	}

	public void setDrawItem(char ch,DrawItem drawItem) {
		drawMap.put(ch, drawItem);
	}

	public void setDrawCharByString(String s) { // そのまま出力する文字たち
		for(int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			drawMap.put(ch, drawChar);
		}
	}

	// 描画メソッド
	@Override
	protected void drawView(Graphics g) {
		for(int y = 0; y < panel.num.y; y++) {
			for(int x = 0; x < panel.num.x; x++) {
				char ch = view[y][x];
				DrawItem item = drawMap.get(ch);
				if(item != null) item.draw(g, x, y, ch);
			}
		}
	}
	// 枠に文字を描くメソッド @Override
	public void drawChar(Graphics g,int px,int py,char c) {
		g.setFont(font);
		g.setColor(fontColor);

		int x = panel.getOriginX(px) + textMargin.x; // 座標値を計算する
		int y = panel.getOriginY(py) + (panel.size.y - textMargin.y);
		g.drawString(""+c, x, y);
	}

	// 枠を塗りつぶすクラス
	protected class DrawColor extends DrawItem {
		Color color;
		DrawColor(PPanel2 panel,Color c) {
			super(panel);
			color = c;
		}
		@Override
		public void draw(Graphics g, int px, int py, char ch) {
			int x = panel.getOriginX(px); // 座標値を計算する
			int y = panel.getOriginY(py);
			g.setColor(color); // 色をセットし
			g.fillRect(x, y, panel.size.x, panel.size.y); //　矩形に塗る
		}

	}
	public void setDrawColor(char ch, Color c) {
		drawMap.put(ch,new DrawColor(panel,c));
	}

	// イメージを描画するクラス
	protected class DrawImage extends DrawItem {
		static final int FREE = 0;
		static final int FIXED = 1;
		static final int CENTERED = 2;
		static final int CROSSED = 3;

		Image image;
		int type = FREE;
		DrawImage(PPanel2 panel,Image im) {
			super(panel);
			image = im;
		}
		DrawImage(PPanel2 panel,Image im,int t) {
			super(panel);
			image = im;
			type = t;
		}
		@Override
		public void draw(Graphics g, int px, int py, char ch) {
			int x = panel.getOriginX(px); // 座標値を計算する
			int y = panel.getOriginY(py);
			switch (type) {
			case FREE:
				g.drawImage(image,x,y,null); // イメージを描画(原寸)
				break;
			case FIXED:
				g.drawImage(image,x,y,panel.size.x,panel.size.y,null); // イメージを描画(原寸)
				break;
			case CENTERED:
				x += panel.size.x/2; x -= image.getWidth(null)/2;
				y += panel.size.y/2; y -= image.getHeight(null)/2;
				g.drawImage(image,x,y,null); // イメージを描画(原寸)
				break;
			case CROSSED:
				x -= image.getWidth(null)/2;
				y -= image.getHeight(null)/2;
				g.drawImage(image,x,y,null); // イメージを描画(原寸)
				break;
			}
		}

	}
	public void setDrawImage(char ch, Image im) {
		drawMap.put(ch,new DrawImage(panel,im));
	}

	public void setDrawFixedImage(char ch, Image im) {
		drawMap.put(ch,new DrawImage(panel,im,DrawImage.FIXED));
	}
}
