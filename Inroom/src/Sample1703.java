import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;

import javax.swing.JFrame;

import jp.fit.jc.it.BorderView;
import jp.fit.jc.it.CharacterView;
import jp.fit.jc.it.PPanel2;
import jp.fit.jc.it.StringView;

public class Sample1703 extends JFrame {

	static String[] mainStr = {"123456","","ABCDEF","","あいうえお"};

	Sample1703Panel panel;

	public Sample1703(String s) {
		super(s); // いつもの設定
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setLayout(new BorderLayout());
		setSize(650,350);

		panel = new Sample1703Panel(6,6,this); // パネルを作って配置
		add(panel,BorderLayout.CENTER);

		// 文字を入れてい見る
		panel.setMain(mainStr);

		panel.setS1(0, 0, "数字");
		panel.setS1(0, 2, "alphabet");
		panel.setS2(0, 4, "ひらがな");
		panel.setS1(0, 5, "①");  // 丸付き数字は非推奨
	}

	public static void main(String[] args) {
		Sample1703 frame = new Sample1703("縦横入力のサンプル");
		frame.setVisible(true);
	}
}

class Sample1703Panel extends PPanel2  {
	CharacterView cview;   // 文字を表示するビュー
	StringView    s1view, s2view;  // 上下に小さな
	Point cur;			   // 文字を入力する位置

	Sample1703Panel(int x, int y,JFrame f ) {
		super(x,y,f);
		setUnitSize(100,32); // ちょっと横長
		cview = new CharacterView(this); // 大きな文字のビュー（１文字）
		addPView(cview);

		s1view = new StringView(this,StringView.SMALL_NW); // 左上に小さな文字
		addPView(s1view);

		s2view = new StringView(this,StringView.SMALL_SW); // 左下に小さな文字
		addPView(s2view);

		addPView(new BorderView(this,BorderView.FULL_BORDER,Color.gray));
	}

	void setMain(String[] s) {   // 大きな文字Viewを文字列で埋める
		cview.setCharByString(s);
	}

	void setS1(int x, int y,String s) { // 小さな文字(上)の1項目をセットする
		s1view.setString(x,y,s);
	}

	void setS2(int x, int y,String s) { // 小さな文字(下)の1項目をセットする
		s2view.setString(x,y,s);
	}
}