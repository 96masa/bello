import java.awt.Image;
import javax.swing.JPanel;

public class Central extends Thing{//敵クラス
	Central(Image im ,JPanel p){
		super(im,p);
	}//UFOから逃げる
	void move(int mx,int my) {

			if(x<mx) {//左右に逃げる
				x--;
				if(x<1) {x=325;y=325;}//画面の中央に再描画させる
			}else if(x>mx) {
				x++;
				if(x>650) {x=325;y=325;}//画面の中央に再描画させる
			}
			if(y<my) {//上下に逃げる
				y--;
				if(y<1) {y=325;x=325;}//画面の中央に再描画させる
			}else if(y>my) {
				y++;
				if(y>650) {y=325;x=325;}//画面の中央に再描画させる
			}

	}

}
