import com.pengrad.telegrambot.request.SendAudio;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupTest {
    public static void main(String[] args) throws IOException {

      //Document doc = Jsoup.connect("https://www.hobobo.ru/audioskazki/sbornik-skazok-mp3/tri-porosenka-online/").get();
      Document doc = Jsoup.connect("https://nukadeti.ru/audioskazki/dlya-detej-3-let").get();
        //Document doc = Jsoup.connect("https://www.hobobo.ru/assets/uploads/audio/tri-porosenka.mp3").get();
       // System.out.println(doc.title());
//        el.select("a[href]") - finds links (a tags with href attributes)
//        el.select("a[href*=example.com]") - finds links pointing to example.com (loosely)

        Elements ssilka = doc.select("div.player-wrap"); //doc.select("a.jp-playlist-item-free")
       // String text=ssilka.text();
        String result="";
        for (Element a : ssilka) {
           // if (a.attr("data-files").equals("bratya_grimm_belosnezhka_i_sem_gnomov.mp3")){
                   // result=a.text();

      //  }
            result=ssilka.text();
            System.out.println(result);

    }
}
}
