//javaによるGUIプログラミング入門　Step3
//ボタンの生成と配置、ボタンを押したときの処理
import java.awt.event.*;//ボタン（イベント）の処理に必要
import javax.swing.*;//GUIを使う場合に必要

//extends および　implemets はJavaプログラミングで学習する。
public class FrameStep3 extends JFrame implements ActionListener {
    private static final long serialVersionUID =1L;
	JLabel label1;//ラベルをオブジェクト変数として定義
	JLabel label2;
	JButton button1;//３つのボタン：オブジェクト変数として定義
	JButton button2;//button2は正解と表示後終了するボタン
	JButton button3;//ここに書かれた変数はクラスのどこからでも使える
	//コンストラクタ
	FrameStep3(String title) {//title(引数）はタイトルバーに表示する文字列
		super(title);          //フレームを実際に作る
		setSize(450,250);     //フレームサイズの設定
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//[x]ボタンでフレームを閉じる
		setLocationByPlatform(true);//開始時の表示位置のOSに任せる
		setLayout(null);      //レイアウトなし

		//ラベルの生成と配置
		label1 = new JLabel("正しい円周率を押してみよう");
		label1.setBounds(10,10,290,30);
		add(label1);
		label2 = new JLabel("");//空白のラベルを生成
		label2.setBounds(350,150,100,30);//ボタンの配置
		add(label2);

		//ボタンの生成と配置
		button1 = new JButton("3.14594");
		button1.setBounds(10, 100, 120, 30);
		add(button1);
		button2 = new JButton("3.14159");
		button2.setBounds(160, 100, 120, 30);
		add(button2);
		button3 = new JButton("3.14432");
		button3.setBounds(310, 100, 120, 30);
		add(button3);

		//ボタンが押されたときのイベントをキャッチする
		button1.addActionListener(this);//thisはこのフレーム
		button2.addActionListener(this);
		button3.addActionListener(this);
	}

	public static void main(String[] args)  { //メインメソッド
		FrameStep3 frame = new FrameStep3("円周率の問題");
		frame.setVisible(true);
	}
	 //ボタンが押されたとき（イベント発生時）に呼び出されるメソッド
	public void actionPerformed(ActionEvent e) {
		//イベントから押されたボタンを得る。
		JButton b = (JButton)e.getSource();
		String s =b.getText();//ボタンのメッセージを得る
		System.out.println("["+s+"]がおされました"); //表示

		if (b==button2) {//ボタン２が押されたら正解と表示する
		    label2.setText("正解");
			System.out.println("終了して下さい");
		}
		else//ボタン２以外が押されたとき不正解と表示する
			label2.setText("不正解");
		}
	}




