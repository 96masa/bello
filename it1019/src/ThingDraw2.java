//クラスの継承を使ってThingクラスから、Neburaクラスを作る
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class ThingDraw2 extends JFrame {
	String imageNames[]= {"s_ufo.png","s_ufo1.jpg","s_ufo2.jpg","s_ufo3.jpg","s_ufo4.jpg"};
	public ThingDraw2(String title) {
		super(title);
		setSize(650,650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//終了ボタン
		setLocationByPlatform(true);
		setLayout(new FlowLayout());//レイアウトはnull以外

		Image im[];//ファイルからイメージを読み込む
		im=new Image[imageNames.length];
		for(int i=0;i<im.length;i++) {
			im[i]= new ImageIcon(imageNames[i]).getImage();
		}
		ThingPanel2 panel = new ThingPanel2 (im,this);//パネルを作る
		add(panel);
	}
	//メインルーチン
	public static void main(String args[]) {
		ThingDraw2 frame = new ThingDraw2("クラス継承");
		frame.setVisible(true);
	}
}


//イメージが描画できるパネルのクラス
class ThingPanel2 extends JPanel implements MouseMotionListener,MouseListener{
	int width=650,height=650;//パネルの幅と高さ
	Image image[];//イメージの配列
	JFrame frame;//親フレームを保持（再描画時に必要）
	Thing ufo;//UFOイメージ
	Thing enemy;//敵１
	Thing enemy1;//敵2
	Thing blowup[];//爆発イメージ

	//パネルを作るコンストラクタ
	ThingPanel2(Image im[],JFrame fr){
		super();//通常のパネルを作る
		image=im;//イメージを保持
		frame=fr;//親フレームを保持
		//
		setMinimumSize(new Dimension(width,height));
		setPreferredSize(new Dimension(width,height));//マウスリスナーを装着

		addMouseMotionListener(this);//マウスモーションリスナーを装着
		addMouseListener(this);//マウスリスナーを装着

		setBackground(Color.black);//背景を黒くする。
		ufo = new Thing(image[0],this);//ものUFOをセット
		enemy = new Enemy(image[3],this);//敵をセット
		enemy1 = new Enemy(image[4],this);//敵１をセット
		blowup = new Thing[100];//エイリアンが入る配列確保
	}

	//幅と高さを返す
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	//実際の描画を行うメソッド。必要な時に自動的に呼び出される。
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//爆発イメージを描画
		for(int i=0;i<blowup.length;i++) {
			if(blowup[i]!=null) {
				blowup[i].draw(g);
			}
		}
		if(enemy != null) {//消滅している場合あり
			enemy.draw(g);
		}
		if(enemy1 != null) {
			enemy1.draw(g);
		}
		if(ufo != null) {//消滅している場合あり
			ufo.draw(g);
		}
	}
	//マウスの移動を検出した時に呼び出されるメソッド
	public void mouseMoved(MouseEvent e) {
		if(ufo != null)ufo.move(e.getX(),e.getY());//マウス位置にUFOを動かす
		if(enemy != null)enemy.move(e.getX(),e.getY());//敵も動かす%座標値は意味なし
		if(enemy1 != null)enemy1.move(e.getX(),e.getY());//敵1も動かす%座標値は意味なし
		frame.repaint();//再描画する
	}
	//マウスをドラッグしたまま移動した時に呼び出されるメソッド
	public void mouseDragged(MouseEvent e) {}
	//マウスをクリックした時
	public void mouseClicked(MouseEvent e) {
		for(int i=0;i<blowup.length;i++) {
			if(blowup[i] == null) {//UFOマーク無し
				if(ufo.hit(enemy)) {//UFOが敵の上にいる（敵を撃破）
					blowup[i] = new Thing(image[2],this);//新しいUFOを生成
					blowup[i].setLocation(enemy.getX(),enemy.getY());//敵位置に配置
					enemy = null;//敵は消滅
				}else {
					blowup[i] = new Thing(image[1],this);//新しいエイリアンを生成
					blowup[i].setLocation(e.getX(),e.getY());//マウスの位置に配置
				}
				if(ufo.hit(enemy1)) {//UFOが敵1の上にいる（敵を撃破）
					blowup[i] = new Thing(image[2],this);//新しいUFOを生成
					blowup[i].setLocation(enemy1.getX(),enemy1.getY());//敵位置に配置
					enemy1 = null;
				}else {
					blowup[i] = new Thing(image[1],this);//新しいエイリアンを生成
					blowup[i].setLocation(e.getX(),e.getY());//マウスの位置に配置
				}
				frame.repaint();//再描画
				break;//for文を抜け出す
			}
		}
	}
	public void mouseEntered(MouseEvent e) {//消えているものを出す
		if(enemy==null) {
			enemy=new Nebura(image[3],this);//新敵生成
		}
		if(enemy1==null) {
			enemy1=new Nebura(image[4],this);//新敵1生成
		}
		if(ufo == null) {//UFOが消滅していたら出す
			ufo= new Thing(image[0],this);
			ufo.move(e.getX(),e.getY());
		}
		frame.repaint();//再描画
	}
	public void mouseExited(MouseEvent e) {//UFOを消す
		ufo = null;
		frame.repaint();
	}
	//その他、何もしない
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}

