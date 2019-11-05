import java.awt.Image;
import javax.swing.JPanel;

class Enemy extends  Thing{//敵クラス
	Enemy(Image im,JPanel p){
		super(im,p);
	}

	//UFOから逃げる
	void move(int mx,int my) {
		if(x<mx) {//左右に逃げる
			x--;
			if(x<0) {x=0;}//画面の左端
		}else if(x> mx) {
			x++;
			if(x>panel.getWidth()) {x = panel.getWidth();}//右端
		}
		if(y<my) {//上下に逃げる
			y--;
			if(y<0) {y=0;}//画面の上端
		}else if(y>my) {
			y++;
			if(y>panel.getHeight()) {y=panel.getHeight();}//下端
		}

	}
}