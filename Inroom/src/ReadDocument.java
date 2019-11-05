// XML を読み込むクラス
// XMLStepN.java と同じフォルダに置き、コンパイルしておく
// eclipse を使っている場合は同じプロジェクト内に置く
import javax.xml.parsers.*;
import org.w3c.dom.*;


public class ReadDocument {
	// read メソッドのみ準備している。uriはファイル名、もしくはURLでもOK
	public static Element read(String uri) {
		DocumentBuilder db;
		Document doc;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = db.parse(uri);
			return  doc.getDocumentElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

