import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

//イメージが描画できるパネルのクラス
class ArrayPanel extends JPanel implements MouseMotionListener,MouseListener{
	int width=650,height=650;//パネルの幅と高さ
	Image image[];//イメージの配列
	XMLObj1 frame;//親フレームを保持（再描画時に必要）
	Thing ufo;//UFOイメージ
	int x = -100,y=-100;//UFOを描画する位置（初期値は画面外）
	Thing obj[];//色々なものの入る配列
	Thing obj2[];//色々なものの入る配列2
	Random rand;
	String XMLFileName = "XMLSample.xml";

	//パネルを作るコンストラクタ。引数は描画したいイメージ
	ArrayPanel(Image im[],XMLObj1 fr){
		super();//通常のパネルを作る。
		image =im;//イメージを保持
		frame =fr;//親フレームを保持
		//パネルの大きさをセット
		setMinimumSize(new Dimension(width,height));
		setPreferredSize(new Dimension(width,height));

		addMouseMotionListener(this);//マウスモーションリスナーを装着
		addMouseListener(this);//マウスリスナーを装着
		Element doc = ReadDocument.read(XMLFileName);
		NodeList nl = doc.getElementsByTagName("suu");//<suu>タグをすべて得る
		NodeList nl2 = doc.getElementsByTagName("suu2");//<suu>タグをすべて得る
		String a=nl.item(0).getFirstChild().getNodeValue();
		String b=nl2.item(0).getFirstChild().getNodeValue();

		ufo = new Thing(image[0],this);//UFOをセット
		obj = new Thing[Integer.parseInt(a)];//敵や爆発マーク
		obj2 = new Thing[Integer.parseInt(b)];//敵や爆発マーク
		rand = new Random();//乱数発生器



		nl = doc.getElementsByTagName("suu");
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
