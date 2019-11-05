//javaによるGUIプログラミング入門　Step1
//フレームの生成：フレームはウインドウの外枠の事

import javax.swing.*;//GUIを使う場合に必要

//フレームを扱うクラス、extends JFframe が必要（詳細はJavaプログラミングで！）
public class FrameStep1 extends JFrame {
	//コンストラクタ、ここで実際のフレームづくりを行う。
	FrameStep1(String title) {//title(引数）はタイトルバーに表示する文字列
		//JFrame クラスのコンストラクタ（フレームを実際に作る）を呼び出す
		super(title);
		//フレームサイズの設定
		setSize(300,200);
		//[x]ボタンが押されたらフレームを閉じる
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//開始時の表示位置のOSに任せる
		setLocationByPlatform(true);
	}
	
	//メインメソッド、フレームを作り、可視化する。
	public static void main(String[] args) {
		//フレームオブジェクトを生成する
		JFrame frame = new FrameStep1("ITエンジニア");
		//フレームの表示を開始する。
		frame.setVisible(true);
	}

}
