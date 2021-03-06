import java.awt.Image;
import java.awt.Point;
import java.util.Random;

import javax.swing.JPanel;

class Enemy extends Thing{
	int count =0;//移動タイミングのカウンタ
	Random rand;//乱数発生器
	Point dir;
	Enemy(Image im,JPanel p){
		super(im,p);//Thingのコンストラクタを呼び出す
		rand = new Random();//乱数発生器をセット
		dir = new Point(rand.nextInt(3)-1,rand.nextInt(3)-1);//敵の初期動作
		setLocation(rand.nextInt(panel.getWidth()),
				rand.nextInt(panel.getHeight()));//乱数で初期値をセット
	}


	void move(int mx,int my) {//move()をオーバーライドして、動作を変える
		x+=dir.x;
		y+=dir.y;

		count++;
		if(count>=10) {//10回以上呼び出されたかチェック
			dir.x=rand.nextInt(3)-1;
			dir.y=rand.nextInt(3)-1;
			count=0;//0にリセット
		}
	}
}
