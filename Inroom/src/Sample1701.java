import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jp.fit.jc.it.BorderView;
import jp.fit.jc.it.CharacterView;
import jp.fit.jc.it.ColorView;
import jp.fit.jc.it.PPanel2;
public class Sample1701 extends JFrame implements ActionListener {
	Sample1701Panel panel;
	JButton[] buttons;     // 削除と番号ボタンの配列

	public Sample1701(String s) {
		super(s); // いつもの設定
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setLayout(new BorderLayout());
		setSize(400,350);

		panel = new Sample1701Panel(6,6,this); // パネルを作って配置
		add(panel,BorderLayout.CENTER);

		buttons = new JButton[10]; // 10個のボタンを準備
		JPanel bpanel = new JPanel(); // ボタンを貼り付けるパネル
		bpanel.setLayout(new GridLayout(2,5)); // グリッドレイアウトでボタンを並べる

		buttons[0] = new JButton("削除");   // 削除ボタンを作り
		buttons[0].addActionListener(this); // アクションリスナを装備し
		bpanel.add(buttons[0]);              // パネルに張り付ける

		for(int i = 1; i <= 9; i++) {
			buttons[i] = new JButton(""+i);   // 番号ボタンを作り
			buttons[i].addActionListener(this); // アクションリスナを装備し
			bpanel.add(buttons[i]);              // パネルに張り付ける
		}
		add(bpanel,BorderLayout.SOUTH); // ボタンパネルを張り付ける
	}

	public static void main(String[] args) {
		Sample1701 frame = new Sample1701("数字(文字)入力のサンプル");
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource(); // 押されたボタンを得る。
		if(btn == buttons[0]) { // 削除ボタン
			panel.setLetter('\0'); // ナル文字をセットする。
		} else { //数字(文字)ボタン、他にもボタンがある場合は最後に持ってくる
			String text = btn.getText(); // ボタンの文字を得る
			panel.setLetter(text.charAt(0)); // ボタンの文字の最初の1つをセット
		}
	}


}

class Sample1701Panel extends PPanel2 implements MouseListener {
	CharacterView cview;   // 文字を表示するビュー
	ColorView     colview;  // 文字を入力する色つきユニットを出すためのビュー
	Point cur;			   // 文字を入力する位置

	Sample1701Panel(int x, int y,JFrame f ) {
		super(x,y,f);
		colview = new ColorView(this);       // 色つきユニット(一番下)
		addPView(colview);
		cview = new CharacterView(this);     // 文字ビュー(真ん中)
		addPView(cview);
		addPView(new BorderView(this,BorderView.FULL_BORDER,Color.black)); // 罫線を表示する(一番上)
		cur = null;
		addMouseListener(this); // マウスリスナを装備する。
	}

	// 指定されたユニットに数字(文字)をセットする。
	void setLetter(Character c) {
		if(cur != null) { // ユニット指定があるかどうか調べる
			cview.setCharacter(cur.x, cur.y, c); // そこにセット
			frame.repaint();//再描画
		}
	}

	public void mouseClicked(MouseEvent e) {
		if(cur != null) {  // 既に設定されているユニットがある
			colview.setColor(cur.x, cur.y, null); // ユニットの色をなくす(透明)
			cur = null;
		}
		cur = getUnit(e); // マウス位置のユニットを得る。ナル値の時は枠外をクリックしている。
		if(cur != null) colview.setColor(cur.x, cur.y, Color.pink); // マウス位置のユニットに色を塗る
		frame.repaint(); // 再描画
	}
	public void mousePressed(MouseEvent e) {	}
	public void mouseReleased(MouseEvent e) {	}
	public void mouseEntered(MouseEvent e) {	}
	public void mouseExited(MouseEvent e) {	}

}