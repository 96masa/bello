import java.awt.Image;

import javax.swing.JPanel;
//爆発マーククラス：消滅する
class Explode extends  Thing{
	int count = 200;//消滅するまでのカウンタ、０になったら消滅する
	Explode(Image im,JPanel p){
		super(im,p);
	}

	//爆発マークは動かない、カウントが0になったら消滅する
	void move(int mx,int my) {
		if(count <=0) {//0以下になると消滅する
			setLocation(-hWidth,-hHeight);//画面の外の出す
		}else {
			count--;//一つ減らす
		}
	}

}