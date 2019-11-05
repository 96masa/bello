package jp.fit.jc.it;
import java.awt.Graphics;

public abstract class DrawItem {
	public PPanel2 panel;
	public DrawItem(PPanel2 p) {panel = p;}
	abstract public void draw(Graphics g,int x,int y,char ch);
}
