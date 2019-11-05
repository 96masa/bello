//税込み金額計算機、GridLayaoutを使って配置
//ファイル名:Zeikomi.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Zeikomi extends JFrame implements ActionListener{
	JTextField textField1;
	JTextField textField2;
	JButton keisan;
	
	//コンストラクタ
	Zeikomi(String title){
		super(title);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(480,200);
		setLocationByPlatform(true);
		
		//レイアウトは縦３×横２のグリッド
		setLayout(new GridLayout(3,2));
		JLabel label1=new JLabel("金額を入れてください");
		label1.setHorizontalAlignment(JLabel.RIGHT);//右寄せ
		label1.setVerticalAlignment(JLabel.BOTTM);//下寄せ
		textField1 = new JTextFieid(20);
		JLabel label2 =new JLabel("計算");
		label2.setHorizontalAlignment(JLabel.RIGHT);//右寄せ
		textField2 = new JTextField(20);
		
		keisan = new JBoutton("計算");//計算ボタン生成
		keisan.addActionListener(this);//押されたらこのフレームのイベントとする
		
		add(label1);//左上
		add(textField1);//左上
		add(label2);//左中
		add(textField2);//右中
		add(keisan);//左下
		
	}
	//メインルーチン
	public static void main(String args[]){
		Zenikomi frame =new Zeikomi("税込み計算");
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		JButton btn=(JButton)e.getSource();//ボタンを得る
		if(btn==keisan){//計算ボタンかチェックする。
			int kingaku = Integer.parseInt(textField1.getText());//金額を得る
			int komi = (int)(kingaku*1.08);//税込みを計算
			textField2.setText(""+komi);//税込み金額をセットする。
		}
	}
}
	
		