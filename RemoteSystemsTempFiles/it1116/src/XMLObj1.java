import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import org.w3c.dom.*;
public class XMLObj1 extends JFrame {
	String XMLFileName = "XMLSample.xml";
	public XMLObj1() {
		super();
		setSize(650,650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setLayout(new FlowLayout());//レイアウトはnull以外

		Element doc = ReadDocument.read(XMLFileName);

		//タイトルを取り出してセットする
		NodeList nl = doc.getElementsByTagName("title");//<title>タグをすべて得る
		setTitle(nl.item(0).getFirstChild().getNodeValue());

		//画像ファイル名をXMLから得て画像を配列にセットする
		Image im[];
		nl = doc.getElementsByTagName("image");
		im = new Image[nl.getLength()];
		for(int i=0;i<im.length;i++) {
			Node n = nl.item(i);
			String file = n.getFirstChild().getNodeValue();
			im[i] = new ImageIcon(file).getImage();
		}
		ArrayPanel panel =new ArrayPanel(im,this);//パネルを作る
		add(panel);//パネルを配置
	}

	//メインルーチン
	public static void main(String args[]) {
		XMLObj1 frame = new XMLObj1();//
		frame.setVisible(true);
	}
}






