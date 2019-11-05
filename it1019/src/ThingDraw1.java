//Thing　クラスを使って、UFOを描画する。
//マウスと共に動き、クリックするとその位置が爆発する。

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ThingDraw1 extends JFrame{
	String imageNames[] = {"bou1.jpg","bou2.jpg","bou3.jpg"};
	public ThingDraw1(String title) {
		
		super(title);
		setSize(650,650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//終了ボタン
		setLocationByPlatform(true);
		setLayout(new BorderLayout());//画面下にボタンがおけるレイアウト
		
		
		Image im[];
		im = new Image[imageNames.length];
		for(int i=0;i<im.length;i++) {
			im[i] = new ImageIcon(imageNames[i]).getImage();
		}
		ThingPanel1 panel = new ThingPanel1(im,this);
		add(panel);
	}
	
	public static void main(String args[]) {
		ThingDraw1 frame = new ThingDraw1("クラスを作る");
		frame.setVisible(true);
	}
}

//
class ThingPanel1 extends JPanel implements MouseMotionListener,MouseListener{
	int width=650,height=650;
	Image image[];
	JFrame frame;
	Thing ufo;
	
	Thing blowup[];
	int num = 0;
	
	//
	ThingPanel1(Image im[],JFrame fr){
		super();
		image = im;
		frame = fr;
		//
		setMinimumSize(new Dimension(width,height));
		setPreferredSize(new Dimension(width,height));
		
		addMouseMotionListener(this);
		addMouseListener(this);
		
		setBackground(Color.black);
		ufo = new Thing(image[0],this);
		blowup = new Thing[100];
	}
	
	//
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//
		for(int i=0;i<num;i++) {
			blowup[i].draw(g);
		}
		ufo.draw(g);
	}
	
	//
	public void mouseMoved(MouseEvent e) {
		ufo.move(e.getX(),e.getY());
		frame.repaint();
	}
	//マウスをドラッグしたまま移動したときに呼び出されるメソッド
	public void mouseDragged(MouseEvent e) {}
	
	//マウスをクリックした時
	public void mouseClicked(MouseEvent e) {
		if(num<blowup.length) {
			blowup[num]=new Thing(image[1],this);
			blowup[num].setLocation(e.getX(),e.getY());
			num++;
			frame.repaint();
		}
	}
	//その他、何もしない
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent arg0) {}
}

