import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Random.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class ThingDraw2Improvement extends JFrame{
	String imageNames[] = {};//星雲のイメージ

	public ThingDraw2Improvement(String title) {
		super(title);
		setSize(650,650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//終了ボタン
		setLocationByPlatform(true);
		setLayout(new FlowLayout());

		Image im[];
		im = new Image[imageNames.length];
		for(int i =0;i<im.length;i++) {
			im[i] = new ImageIcon(imageNames[i]).getImage();
		}
		ThingPanel2Improvement panel = new ThingPanel2Improvement(im,this);
		add(panel);
	}

	public static void main(String args[]) {
		ThingDraw2Improvement frame =new ThingDraw2Improvement("クラスを派生させる");
		frame.repaint();
	}
}
class Nebura extends Thing {
	int count =0;
	Random rand;
	Nebura(Image im,JPanel p){
		super(im,p);
		rand =new Random();
		setLocation(rand.nextInt(panel.getWidth()),
				rand.nextInt(panel.getHeight()));
	}

	void move (int mx,int my) {
		count++;
		if(count>=50) {
			x=rand.nextInt(panel.getWidth());
			y= rand.nextInt(panel.getHeight());
			count=0;
		}
	}
}


class ThingPanel2Improvement extends JPanel implements MouseMotionListener,MouseListener{
	int width=650,height=650;
	Image image[];
	JFrame frame;
	Thing ufo;
	Thing enemy;
	Thing blowup[];


	ThingPanel2Improvement(Image im[],JFrame fr){
		super();
		image =im;
		frame= fr;

		setMinimumSize(new Dimension(width,height));
		setPreferredSize(new Dimension(width,height));

		addMouseMotionListener(this);
		addMouseListener(this);
		setBackground(Color.black);
		ufo = new Thing(image[0],this);
		enemy =new Nebura(image[3],this);
		blowup =new Thing[100];
	}


	public int getwidth() {return width;}
	public int geheight() {return height;}

	public void paintCompornet(Graphics g) {
		super.paintComponent(g);

		for(int i=0;i<blowup.length;i++) {
			if(blowup[i]!=null) {
				blowup[i].draw(g);
			}
		}
		if(enemy != null) {
			enemy.draw(g);
		}
		if(ufo != null) {
			ufo.draw(g);
		}
	}
	public void mouseMoved(MouseEvent e) {
		if(ufo != null) ufo.move(e. getX(),e.getY());
		if(enemy != null) enemy.move(e. getX(),e.getY());
		frame.repaint();
	}

	public void mouseDragged(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {
		for(int i =0;i<blowup.length;i++) {
			if(blowup[i] ==null) {
				if(ufo.hit(enemy)) {
				blowup[i]=new Thing(image[2],this);
				blowup[i].setLocation(enemy.getX(),enemy.getY());
				enemy=null;
			}else {
				blowup[i]=new Thing(image[1],this);
				blowup[i].setLocation(e.getX(),e.getY());
			}
			frame.repaint();
			break;
		}
	}
}
	public void mouseEntered(MouseEvent e) {
		if(enemy ==null) {
			enemy=new Nebura(image[3],this);
		}
		if(ufo ==null) {
			ufo =new Thing(image[0],this);
			ufo.move(e.getX(),e.getY());
		}
		frame.repaint();
	}
	public void mouseExited(MouseEvent e) {
		ufo=null;
		frame.repaint();

	}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}