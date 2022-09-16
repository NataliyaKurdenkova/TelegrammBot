import com.pengrad.telegrambot.request.SendAudio;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
import java.util.Map;

public class JsoupTest {
    public static void main(String[] args) throws IOException {

        Document doc = Jsoup.connect("https://audioskazki-online.ru/popular").get();
//        System.out.println(doc.title());
//        el.select("a[href]")
//        el.select("a[href*=example.com]")
//        el.select("body > div");
//        el.select("body > div.content"); comtent - это класс

        Elements select = doc.select("li[data-title]");
        System.out.println(select);
        Map<String, String> map = new HashMap<>();
        for (Element element : select) {
            String s = element.getElementsByAttribute("data-title").toString();
            String v = element.childNode(1).toString();
            System.out.println(v);
            String s1 = s.split("data-title=\"")[1].split("\"")[0];
            String v1 = v.split("data-src=\"")[1].split("\"")[0];
            map.put(s1, v1);
        }
        System.out.println("");

    }
}
