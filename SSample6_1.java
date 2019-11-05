import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Container;
import java.awt.BorderLayout;

class SSample6_1 extends JFrame{
  public static void main(String args[]){
    SSample6_1 frame = new SSample6_1("ƒ^ƒCƒgƒ‹");
    frame.setVisible(true);
  }

  SSample6_1(String title){
    setTitle(title);
    setBounds(0, 0, 1080,750);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel p = new JPanel();

    ImageIcon icon1 = new ImageIcon("ue.jpg");
    ImageIcon icon2 = new ImageIcon("bou1.jpg");

    JLabel label1 = new JLabel(icon1);

    JLabel label2 = new JLabel();
    label2.setIcon(icon2);

    p.add(label1);
    p.add(label2);

    Container contentPane = getContentPane();
    contentPane.add(p, BorderLayout.CENTER);
  }
}