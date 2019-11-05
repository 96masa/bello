package jp.fit.jc.it;
import java.awt.Graphics;

public abstract class PView {
	protected PPanel2 panel; // このビューが存在するパネル
	public PView(PPanel2 p) {
		panel = p;
	}
	abstract protected void drawView(Graphics g);
	protected void changeUnitSize() {} // 枠の大きさ変更に備える：特殊な処理が必要なビューはオーバーライドする。
}
