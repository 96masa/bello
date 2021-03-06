import java.awt.Image;
import java.util.Random;

import javax.swing.JPanel;

//Thing を拡張してNeburaクラスを作る（別ファイルにしておいたほうがよい）
class Nebura extends Thing{
	int count = 0;//移動タイミングのカウンタ
	Random rand;//乱数発生器
	Nebura(Image im,JPanel p){
		super(im,p);//Thingのコンストラクタを呼び出す
		rand = new Random();//乱数発生器をセット
		setLocation(rand.nextInt(panel.getWidth()),
				rand.nextInt(panel.getHeight()));//乱数で初期値をセット
	}

	void move(int mx,int my) {//move()をオーバーライドして、動作を変える
		count++;
		if(count>=50) {//50回以上呼び出されたかチェック
			x = rand.nextInt(panel.getWidth());//乱数で座標値を変える
			y = rand.nextInt(panel.getHeight());//乱数で座標値を変える
			count = 0;//0にリセット
		}
	}
}