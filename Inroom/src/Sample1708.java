import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Sample1708 extends JFrame {

	/*// グループ分けデータ // この配列をデータを元に読み込む
	static int gmap[][] = {
			{1,1,2,3,3,4,4,4},
			{1,1,2,5,5,4,4,4},
			{6,7,2,8,8,9,9,9},
			{7,7,2,8,8,10,10,10},
			{11,11,12,12,13,10,10,10},
			{11,11,12,13,13,14,14,14},
			{11,11,12,13,13,14,15,15}
	};
	*/

	// XML から読むときは、下のmap0の定義をコメントアウトする
	/* static String[] map0 = {
			"1,1,2,3,3,4,4,4",
			"1,1,2,5,5,4,4,4",
			"6,7,2,8,8,9,9,9",
			"7,7,2,8,8,10,10,10",
			"11,11,12,12,13,10,10,10",
			"11,11,12,13,13,14,14,14",
			"11,11,12,13,13,14,15,15"};
	*/
	static String XMLFileName = "sample1709.xml"; // XMLファイルの名前

	public Sample1708(String XMLfile) {
		super(); // いつもの設定★タイトルはXMLから
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setLayout(new BorderLayout());
		setSize(300,300);

		Element doc = ReadDocument.read(XMLfile);

		// タイトルを取り出してセットする。
		NodeList nl = doc.getElementsByTagName("title");     // タイトルタグ部分を得る
		setTitle(nl.item(0).getFirstChild().getNodeValue()); // 中身を取り出しタイトルバーにセット

		//最初の問題を取り出す。
		Element item = (Element) doc.getElementsByTagName("item").item(0);
		String[] map0 = getStrings(item,"group"); // グループマップの取り出し

		int[][] gmap = convData(map0);
		Sample1708Panel panel = new Sample1708Panel(gmap[0].length,gmap.length,this); // パネルを作って配置
		add(panel,BorderLayout.CENTER);
		panel.setGroupMap(gmap); // データをセット
	}

	// 複数桁のデータを扱う：一括処理できるようにしたメソッド、エラー処理は無し
	int [][] convData(String[] map0) {
		String[][] map1 = new String[map0.length][]; // 2次元配列を準備、行数はmap0から得る。
		for(int i = 0; i < map0.length; i ++ ) { // 各行のデータそれぞれを操作
		    map1[i] = map0[i].split(",");  // コンマで分割し、各列データの配列を格納する。
		}
		int[][] map2 = new int[map1.length][map1[0].length]; // 2次元配列を準備
		for(int i = 0; i < map1.length; i ++ ) { // 各行のデータそれぞれを操作
		    for(int j = 0; j < map1[i].length; j ++ ) { // 各列のデータそれぞれを操作
		        try {
		        	map2[i][j] = Integer.parseInt(map1[i][j]);// 変換して代入
		        } catch(ArrayIndexOutOfBoundsException e) { // 配列データ無し
		        	map2[i][j] = 0; // 0はデータ無し
		        } catch(NumberFormatException e) { // 書式の誤り
		        	map2[i][j] = 0;
		        }
		    }
		}
		return map2;
	}
	// 問題や答えの文字列の並びを得る（答え合わせページに詳細）
	String[] getStrings(Element mondai,String tag) {
		String s[];
		NodeList nl = mondai.getElementsByTagName(tag);
		s = new String[nl.getLength()];
		for(int i = 0; i < nl.getLength() ;i++) {
			try {
				s[i] = nl.item(i).getFirstChild().getNodeValue();
			} catch(Exception e) {
				s[i] = "";
			}
		}
		return s;
	}
	// メインルーチン
	public static void main(String args[]) {
		Sample1708 frame = new Sample1709(XMLFileName);
		frame.setVisible(true);
	}
}
/* 以下はSample1708と同じ
class Sample1708Panel extends PPanel2 {
	GroupView gview; // グループで分割するビュー
	Sample1708Panel(int x, int y, JFrame f) {
		super(x,y,f);
		addPView(new BorderView(this, BorderView.FULL_BORDER,Color.black));
		gview = new GroupView(this);
		addPView(gview);
	}

	void setGroupMap(int[][] gmap) {
		gview.setGroupMap(gmap);
	}
}
*/
