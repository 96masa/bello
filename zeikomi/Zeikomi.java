//�ō��݋��z�v�Z�@�AGridLayaout���g���Ĕz�u
//�t�@�C����:Zeikomi.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Zeikomi extends JFrame implements ActionListener{
	JTextField textField1;
	JTextField textField2;
	JButton keisan;
	
	//�R���X�g���N�^
	Zeikomi(String title){
		super(title);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(480,200);
		setLocationByPlatform(true);
		
		//���C�A�E�g�͏c�R�~���Q�̃O���b�h
		setLayout(new GridLayout(3,2));
		JLabel label1=new JLabel("���z�����Ă�������");
		label1.setHorizontalAlignment(JLabel.RIGHT);//�E��
		label1.setVerticalAlignment(JLabel.BOTTM);//����
		textField1 = new JTextFieid(20);
		JLabel label2 =new JLabel("�v�Z");
		label2.setHorizontalAlignment(JLabel.RIGHT);//�E��
		textField2 = new JTextField(20);
		
		keisan = new JBoutton("�v�Z");//�v�Z�{�^������
		keisan.addActionListener(this);//�����ꂽ�炱�̃t���[���̃C�x���g�Ƃ���
		
		add(label1);//����
		add(textField1);//����
		add(label2);//����
		add(textField2);//�E��
		add(keisan);//����
		
	}
	//���C�����[�`��
	public static void main(String args[]){
		Zenikomi frame =new Zeikomi("�ō��݌v�Z");
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		JButton btn=(JButton)e.getSource();//�{�^���𓾂�
		if(btn==keisan){//�v�Z�{�^�����`�F�b�N����B
			int kingaku = Integer.parseInt(textField1.getText());//���z�𓾂�
			int komi = (int)(kingaku*1.08);//�ō��݂��v�Z
			textField2.setText(""+komi);//�ō��݋��z���Z�b�g����B
		}
	}
}
	
		