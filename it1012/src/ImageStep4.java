//グラフィッククスを描画する画面に画像を表示する
//ボタンに画像を張り付ける場合。
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ImageStep4 extends JFrame implements ActionListener {
	ImagePanel4 panel ;//イメージ用のパネルを定義
	JButton button;//上ボタンを定義
	JButton button1;//下ボタンを定義
	JButton button2;//左ボタンを定義
	JButton button3;//右ボタンを定義
	JButton button4;//右下ボタンを定義
	JButton button5;//左上ボタンを定義
	JButton button6;//右上ボタンを定義
	JButton button7;//左下ボタンを定義
	JButton button8;//スピードupボタンを定義
	JButton button9;//スピードdownボタンを定義

	public ImageStep4(String title) {
		super(title);
		setSize(650,700);//フレームサイズの設定
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//終了ボタン
		setLocationByPlatform(true);
		setLayout(new BorderLayout());//画面下にボタンがおけるレイアウト

		//ファイルからイメージを読み込む　ファイル名を指定
		Image im = new ImageIcon("cello-2830670__340.jpg").getImage();
		panel = new ImagePanel4 (im);//パネルを作る
		add(panel,BorderLayout.CENTER);//パネルを中央に配置

		//ボタンに画像を張り付ける
		JPanel panel2 =new JPanel();
		panel2.setLayout(new GridLayout(2,5));//レイアウトは縦2×5横のグリッド
		button = new JButton(new ImageIcon("ue.jpg"));
		button.addActionListener(this);
		button1=new JButton(new ImageIcon("sita.jpg"));
		button1.addActionListener(this);
		button2=new JButton(new ImageIcon("hidari.jpg"));
		button2.addActionListener(this);
		button3=new JButton(new ImageIcon("migi.jpg"));
		button3.addActionListener(this);
		button4 = new JButton(new ImageIcon("migisita.jpg"));
		button4.addActionListener(this);
		button5=new JButton(new ImageIcon("hidariue.jpg"));
		button5.addActionListener(this);
		button6=new JButton(new ImageIcon("migiue.jpg"));
		button6.addActionListener(this);
		button7=new JButton(new ImageIcon("hidarisita.jpg"));
		button7.addActionListener(this);
		button8=new JButton(new ImageIcon("up.jpg"));
		button8.addActionListener(this);
		button9=new JButton(new ImageIcon("down.jpg"));
		button9.addActionListener(this);

		panel2.add(button);
		panel2.add(button1);
		panel2.add(button2);
		panel2.add(button3);
		panel2.add(button4);
		panel2.add(button5);
		panel2.add(button6);
		panel2.add(button7);
		panel2.add(button8);
		panel2.add(button9);
		add(panel2,BorderLayout.SOUTH);//下に配置する。

	}
	//メインルーチン
	public static void main(String[] args)  {
		ImageStep4 frame = new ImageStep4("貼り付け+移動＋ボタンに画像+スピードup,down+はみ出さない");
		frame.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		JButton b =(JButton)e.getSource();
		if(b==button) {            //上ボタンが押された。
			panel.moveImage();     //イメージを移動させる
			repaint();             //再描画
		}
		if(b==button1) {           //下ボタンが押された。
			panel.moveImage1();
			repaint();
		}
		if(b==button2) {           //左ボタンが押された。
			panel.moveImage2();
			repaint();
		}
		if(b==button3) {           //右ボタンが押された。
			panel.moveImage3();
			repaint();
		}
		if(b==button4) {           //右下ボタンが押された。
			panel.moveImage4();
			repaint();
		}
		if(b==button5) {           //左上ボタンが押された。
			panel.moveImage5();
			repaint();
		}
		if(b==button6) {           //右上ボタンが押された。
			panel.moveImage6();
			repaint();
		}
		if(b==button7) {           //左下ボタンが押された。
			panel.moveImage7();
			repaint();
		}
		if(b==button8) {           //スピードupボタンが押された。
			panel.moveImage8();
			repaint();
		}
		if(b==button9) {           //スピードdownボタンが押された。
			panel.moveImage9();
			repaint();
		}
	}
}
//イメージが描画できるパネルのクラス
class ImagePanel4 extends JPanel {
	int width=450,height=300;//パネルの幅と高さ
	Image image;//描画するイメージ
	int x=10,y=10,s=0;//画像の初期位置を（10,10）にセット、変数ｓの初期値を０にする

	//パネルを作るコンストラクタ。引数は描画したいイメージ
	ImagePanel4(Image im) {
		super();//通常のパネルを作る
		image = im;//イメージを保持
		//パネルの大きさをセット
		setMinimumSize(new Dimension(width,height));
		setPreferredSize(new Dimension(width,height));
	}

	//実査の描画を行うメソッド。必要なときに自動的に呼び出される。
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, x, y, this);
	}

	//イメージを移動させるメソッド
	public void moveImage() {
		if(s>=-9) {        //変数sが-9以上のときに実行される
			y+=-10-s;      //y座標を10-ｓずつ上に動かす
		}else {            //-9よりも小さくなるとき
			s=-9;          //最低速度にする
		}
		if(y<1) {          //y座標が１未満のとき実行される
			y=300;         //y座標を300にセットする
		}
	}
	public void moveImage1() {
		if(s>=-9) {
			y+=10+s;       //y座標を10+ｓずつ下に動かす
		}else {
			s=-9;
		}
		if(y>300) {        //ｙ座標が300より大きいとき実行する
			y=0;           //y座標を0にセットする
		}
	}
	public void moveImage2() {
		if(s>=-9) {
			x+=-10-s;      //x座標を10-ｓずつ左に動かす
		}else {
			s=-9;
		}
		if(x<1) {          //x座標が１未満のとき実行する
			x=300;         //x座標を300にセットする
		}
	}
	public void moveImage3() {
		if(s>=-9) {
			x+=10+s;       //x座標を10+ｓずつ右に動かす
		}else {
			s=-9;
		}
		if(x>300) {        //x座標が300より大きいとき実行する
			x=0;           //x座標を0にセットする
		}
	}
	public void moveImage4() {
		if(s>=-9) {
			x+=10+s;       //x、ｙ座標を10+ｓずつ右下に動かす
			y+=10+s;
		}else {
			s=-9;
		}
		if(x>300 || y>300) {//x座標が300より大きいまたはｙ座標が300より大きいとき実行する
			x=0;            //x,y座標を0にセットする
			y=0;
		}
	}
	public void moveImage5() {
		if(s>=-9) {
			x+=-10-s;       //x,y座標を10-ｓずつ左上に動かす
			y+=-10-s;
		}else {
			s=-9;
		}
		if(x<0|| y<0) {     //x座標が0未満またはｙ座標が0未満のとき実行する
			x=300;          //x,ｙ座標を300にセットする
			y=300;
		}
	}
	public void moveImage6() {
		if(s>=-9) {
			x+=10+s;        //x座標を10+ｓずつ、y座標を10-ｓずつ右上に動かす
			y+=-10-s;
		}else {
			s=-9;
		}
		if(x>300||y<0) {    //x座標が300より大きいまたはｙ座標が0未満のとき実行する
			x=0;            //x座標を0にセット
			y=300;          //y座標を300にセット
		}
	}
	public void moveImage7() {
		if(s>=-9){
			x+=-10-s;       //x座標を10-sずつ、ｙ座標を10+ｓずつ左下に動かす
			y+=10+s;
		}else {
			s=-9;
		}
		if(x<0||y>300) {    //x座標が0未満またはy座標が300より大きいとき実行する
			x=300;          //x座標を300にセット
			y=0;            //y座標を0にセット
		}
	}
	public void moveImage8() {
		s+=3;               //sを3ずつ増やす
	}
	public void moveImage9() {
		s-=3;               //sを3ずつ減らす
	}
}