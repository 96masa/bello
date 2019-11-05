//BMI計算機、GridLayoutを使って表示
//ファイル名：BMI.java

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;


public class BMI extends JFrame implements ActionListener {
	JTextField textField1;
	JTextField textField2;
	JTextField textField3;
	JTextField textField4;
	JButton keisan;//計算ボタンを生成
	JButton risou;//りそう
	JButton reset;

	//コンストラクタ
	BMI(String title){
		super(title);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(480,200);
		setLocationByPlatform(true);

		//レイアウトは縦4×横2のグリッド
		setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,2));
		JLabel label1 =new JLabel("身長を入れて下さい(cm)");
		label1.setHorizontalAlignment(JLabel.RIGHT);//右寄せ
		label1.setVerticalAlignment(JLabel.CENTER);//中央寄せ
		textField1=new JTextField(20);
		JLabel label2 =new JLabel("体重を入れて下さい(kg)");
		label2.setHorizontalAlignment(JLabel.RIGHT);//右寄せ
		label2.setVerticalAlignment(JLabel.CENTER);//中央寄せ
		textField2=new JTextField(20);
		JLabel label3 =new JLabel("BMI");
		label3.setHorizontalAlignment(JLabel.RIGHT);//右寄せ
		label3.setVerticalAlignment(JLabel.CENTER);//中央寄せ
		textField3=new JTextField(20);
		JLabel label4 =new JLabel("理想体重(kg)");
		label4.setHorizontalAlignment(JLabel.RIGHT);//右寄せ
		label4.setVerticalAlignment(JLabel.CENTER);//中央寄せ
		textField4=new JTextField(20);

		panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(label3);
        panel.add(textField3);
        panel.add(label4);
        panel.add(textField4);
		add(panel,BorderLayout.CENTER);

		//レイアウトは縦1×横3のグリッド
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1,3));
		keisan =new JButton("計算");//計算ボタンを生成
		keisan.addActionListener(this);
		risou =new JButton("理想計算");//理想計算ボタンを生成
		risou.addActionListener(this);
		reset =new JButton("リセット");//リセットボタンを生成
		reset.addActionListener(this);

		panel2.add(keisan);
		panel2.add(risou);
		panel2.add(reset);
		add(panel2,BorderLayout.SOUTH);
	}

	public static void main(String args[]) {
		BMI frame = new BMI ("税込み");
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		JButton btn= (JButton)e.getSource();
		float m = Float.parseFloat(textField1.getText());
		float h =m/100;
		if(btn==keisan) {//計算ボタンのとき
			float w = Float.parseFloat(textField2.getText());
			float bmi = w/(h*h);//BMI値を計算する
			textField3.setText(""+bmi);//出力
		}
		if(btn==risou) {//理想計算ボタンのとき
			float r=h*h*22;//理想計算をする
			textField4.setText(""+r);
		}
		if(btn==reset){
			textField1.setText("");//空白をセットする
			textField2.setText("");
			textField3.setText("");
			textField4.setText("");
		}
	}
}
