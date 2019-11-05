/*「もの」を表すクラス
 * UFOや星雲を表すクラスのスーパークラス
 */

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class Thing {
	Image image;
	int x;
	int y;
	int hWidth;
	int hHeight;
	int radius;
	JPanel panel;
	Thing(Image im,JPanel p){
		image = im;
		panel = p;
		//半径計算用
		hWidth = image.getWidth(null)/2;
		hHeight = image.getHeight(null)/2;
		//三平方の定理で半径計算
		radius = (int)Math.sqrt((double)(hWidth*hWidth + hHeight*hHeight));
		setLocation(-100,-100);
	}
	 int getX() {return x;}
	 int getY() {return y;}
	 int getWidth() {return image.getWidth(null);}
	 int getHeight() {return image.getHeight(null);}//物の高さを返す
	 
	 void draw(Graphics g) {
		 g.drawImage(image,x-hWidth,y-hHeight,null);
	 }
	 
	 void setLocation(int mx,int my) {
		 x=mx;
		 y=my;
	 }
	 
	 //このクラスではmove()はsetLocation()と同じ
	 void move(int mx,int my) {
		 setLocation(mx,my);
	 }
	 
	 void setRadius(int r) {
		 radius=r;
	 }
	 
	 boolean hit(Thing t) {
		 //三平方の定理を使い判定。平方根演算は時間がかかるので２乗したまま比較
		 int length = radius + t.radius;
		 int dx = x-t.x;
		 int dy = y-t.y;
		 return(length*length >dx*dx + dy*dy);
	 }
}
