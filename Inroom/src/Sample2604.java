import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

import jp.fit.jc.it.BorderView;
import jp.fit.jc.it.GroupView;
import jp.fit.jc.it.PPanel2;

public class Sample2604 extends JFrame {

	// グループ分けデータ
	static int gmap[][] = {
			{1,1,2,3,4},
			{5,5,2,3,4},
			{6,7,7,8,9},
			{6,10,10,8,9},
			{6,11,11,8,9},
	};

	public Sample2604(String title) {
		super(title); // いつもの設定
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setLayout(new BorderLayout());
		setSize(300,300);

		Sample2604Panel panel = new Sample2604Panel(gmap[0].length,gmap.length,this); // パネルを作って配置
		add(panel,BorderLayout.CENTER);
		panel.setGroupMap(gmap); // データをセット
	}
	// メインルーチン
	public static void main(String args[]) {
		Sample2604 frame = new Sample2604("領域分割のサンプル");
		frame.setVisible(true);
	}
}

class Sample2604Panel extends PPanel2 {
	GroupView gview; // グループで分割するビュー
	Sample2604Panel(int x, int y, JFrame f) {
		super(x,y,f);
		addPView(new BorderView(this, BorderView.FULL_BORDER,Color.black));
		gview = new GroupView(this);
		addPView(gview);
	}

	void setGroupMap(int[][] gmap) {
		gview.setGroupMap(gmap);
	}
}