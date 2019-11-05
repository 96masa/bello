import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class ArrayObj extends JFrame {
	String imageNames[] = {"s_rodosin.jpg","s_rodohason.jpg","s_tubasa.jpg"
			,"s_onoda.jpg","s_look.jpg","s_lookearo.jpg","s_s-works.jpg"};//敵を画像

	public ArrayObj(String title) {
		super(title);
		setSize(650,650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setLayout(new FlowLayout());//レイアウトはnull以外

		//ファイルからイメージを読み込むファイル名を指定する
		Image im[];
		im = new Image[imageNames.length];
		for(int i=0;i<im.length;i++) {
			im[i] = new ImageIcon(imageNames[i]).getImage();
		}
		ArrayPanel panel =new ArrayPanel(im,this);//パネルを作る
		add(panel);//パネルを配置
	}
	//メインルーチン
	public static void main(String args[]) {
		ArrayObj frame = new ArrayObj("配列とオブジェクト");
		frame.setVisible(true);
	}
}


//イメージが描画できるパネルのクラス
class ArrayPanel extends JPanel implements MouseMotionListener,MouseListener{
	int width=650,height=650;//パネルの幅と高さ
	Image image[];//イメージの配列
	JFrame frame;//親フレームを保持（再描画時に必要）
	Thing ufo;//UFOイメージ
	int x = -100,y=-100;//UFOを描画する位置（初期値は画面外）
	Thing obj[];//色々なものの入る配列
	Thing obj2[];//色々なものの入る配列2
	int objNumber;
	Random rand;
	//パネルを作るコンストラクタ。引数は描画したいイメージ
	ArrayPanel(Image im[],JFrame fr){
		super();//通常のパネルを作る。
		image =im;//イメージを保持
		frame =fr;//親フレームを保持
		//パネルの大きさをセット
		setMinimumSize(new Dimension(width,height));
		setPreferredSize(new Dimension(width,height));

		addMouseMotionListener(this);//マウスモーションリスナーを装着
		addMouseListener(this);//マウスリスナーを装着

		ufo = new Thing(image[1],this);//UFOをセット
		obj = new Thing[10];//敵や爆発マーク
		obj2 = new Thing[5];//敵や爆発マーク
		rand = new Random();//乱数発生器

		for(int i=0;i<obj.length;i++) {
			int r =rand.nextInt(4);//敵四体
			if(r<3) {
				obj[i]=new  Nebura(image[3+r],this);//敵を生成
			}else {
				obj[i]= new Enemy(image[6],this);//敵を生成
				obj[i].setLocation(rand.nextInt(getWidth()),
						rand.nextInt(getHeight()));//乱数で初期値をセット
			}
		}
		for(int j=0;j<obj2.length;j++) {
			int r =rand.nextInt(4);//敵四体
			if(r<3) {
				obj[j]=new  Central(image[3+r],this);//敵を生成
			}else {
				obj[j]= new Central(image[6],this);//敵を生成
				obj[j].setLocation(rand.nextInt(getWidth()),
						rand.nextInt(getHeight()));//乱数で初期値をセット
			}
		}
	}

	//幅と高さを返す
	public int getWidth() {return width;}
	public int getHeight() {return height;}

	//実際の描画を行うメソッド。必要な時に自動的に呼び出される。
	public void paintComponent(Graphics g) {
		super.paintComponent(g);//描画のデフォルト動作
		for(int i=0;i<obj.length;i++) {
			if(obj[i]!=null) {
				obj[i].draw(g);//何かを描画
			}
		}
		ufo.draw(g);//UFOを描画
		for(int i=0;i<obj2.length;i++) {
			if(obj2[i]!=null) {
				obj2[i].draw(g);//何かを描画
			}
		}

	}

	//マウスの移動を検出した時に呼びアされるメソッド
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();//マウスのX座標を得る
		int y = e.getY();//マウスのY座標を得る
		for(int i=0;i<obj.length;i++) {//何かを動かす
			if(obj[i]!=null) {
				if(obj[i].getY()<0) {//消滅した
					obj[i] =null;//消えてしまう
				}else {
					obj[i].move(x,y);//何かを動かす
				}
			}
		}
		ufo.move(x,y);//ufoを動かす
		frame.repaint();//再描画する
	}
	//マウスをドラッグしたまま移動したときに呼び出されるメソッド
	public void mouseDragged(MouseEvent e) {}//今回は何もしない

	//マウスをクリックした時
	public void mouseClicked(MouseEvent e) {
		int flag =0;//再描画する必要があるかどうかのフラグ
		for(int i=0;i<obj.length;i++) {
			if(obj[i]==null) {//消滅している
				//何もしない
			}else if(ufo.hit(obj[i])) {//上にのっている
				if(obj[i] instanceof Nebura) {//敵？
					Thing p=new Explode(image[1],this);//爆発１を作成
					p.setLocation(obj[i].getX(), obj[i].getY());//敵の位置に配置
					obj[i]=p;//爆発マークで置き換える
					flag++;
				}else if(obj[i] instanceof Enemy) {//敵？
					Thing p=new Explode(image[2],this);//爆発１を作成
					p.setLocation(obj[i].getX(), obj[i].getY());//敵の位置に配置
					obj[i]=p;//爆発マークで置き換える
					flag++;
				}
			}
		}
		for (int j=0;j<obj2.length;j++) {
			if(obj2[j]==null) {//消滅している
				//何もしない
			}else if(ufo.hit(obj2[j])) {//上にのっている
				if(obj2[j] instanceof Central) {//敵？
					Thing pi=new Explode(image[3],this);//爆発１を作成
					pi.setLocation(obj2[j].getX(),obj2[j].getY());//敵の位置に配置
					obj2[j]=pi;//爆発マークで置き換える
					flag++;
				}else if(obj2[j] instanceof Central) {//敵？
					Thing pi=new Explode(image[4],this);//爆発１を作成
					pi.setLocation(obj[j].getX(), obj[j].getY());//敵の位置に配置
					obj[j]=pi;//爆発マークで置き換える
					flag++;
				}	
			}
		}
	}



	//その他
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}