package jp.fit.jc.it;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class GroupView extends PView{
	protected int groupMap[][];
	protected int ruleWidth = 3; // 枠線の太さ
	protected Color ruleColor = Color.gray;
	public GroupView(PPanel2 p) {
		super(p);
		groupMap = new int[p.num.y][p.num.x];
	}

	public int[][] getGroupMap() { return groupMap; }
	// グループマップを取り込む
	public void setGroupMap(int gmap[][]) {
		for(int y = 0;y<panel.num.y;y++) {
			for(int x = 0;x<panel.num.x;x++) {
				try { groupMap[y][x] = gmap[y][x]; } catch(Exception e) {} // エラーは無視する
			}
		}
	}
	public Color getRuleColor() { return ruleColor; }
	public void setRuleColor(Color c) { ruleColor = c; }

	public void setGroupArea(Point start,Point end, int c) {
		int sx = start.x < end.x ? start.x : end.x;
		int sy = start.y < end.y ? start.y : end.y;
		int ex = start.x > end.x ? start.x : end.x;
		int ey = start.y > end.y ? start.y : end.y;
		for(int y = sy; y <= ey; y++) {
			for(int x = sx; x <= ex; x++) {
				groupMap[y][x] = c;
			}
		}
	}
	public void clear() { fill(0); } // グループの全消去
	public void fill(int c) { // 塗りつぶす
		for(int y = 0; y <= panel.num.y; y++) {
			for(int x = 0; x <= panel.num.x; x++) {
				groupMap[y][x] = c;
			}
		}
	}


	@Override
	protected void drawView(Graphics g) {
		g.setColor(ruleColor);
		// 周囲を先に描画する
		g.fillRect(panel.offset.x - (ruleWidth/2), panel.offset.y - (ruleWidth/2), ruleWidth, panel.num.y*panel.size.y + ruleWidth);
		g.fillRect(panel.offset.x - (ruleWidth/2), panel.offset.y - (ruleWidth/2), panel.num.x*panel.size.x + ruleWidth, ruleWidth);
		g.fillRect(panel.offset.x - (ruleWidth/2), panel.offset.y + panel.num.y*panel.size.y - (ruleWidth/2),
				panel.num.x*panel.size.x + ruleWidth, ruleWidth);
		g.fillRect(panel.offset.x + panel.num.x*panel.size.x - (ruleWidth/2), panel.offset.y - (ruleWidth/2),
				ruleWidth, panel.num.y*panel.size.y + ruleWidth);

		for(int y = 0;y<groupMap.length;y++) {
			int sy = panel.getOriginY(y) - ruleWidth/2;
			for(int x = 0;x<groupMap[0].length;x++) {
				int sx = panel.getOriginX(x) - ruleWidth/2;
				// 横のチェック
				if(x>0) { // x==0の時は左がない
					if(groupMap[y][x-1]!=groupMap[y][x]) { // 左とグループが異なる
						g.fillRect(sx, sy, ruleWidth, panel.size.y+ruleWidth);
					}
				}
				// 縦のチェック
				if(y>0) { // y==0の時は上がない
					if(groupMap[y-1][x]!=groupMap[y][x]) { // 上とグループが異なる
						g.fillRect(sx, sy, panel.size.x+ruleWidth, ruleWidth);
					}
				}
			}
		}
	}
}
