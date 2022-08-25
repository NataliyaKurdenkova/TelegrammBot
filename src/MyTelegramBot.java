import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import org.jsoup.Jsoup;

// test_tel_bot - мой бот
public class MyTelegramBot {
    public static void main(String[] args){
       // System.out.println("test_tel_bot");
        // Create your bot passing the token received from @BotFather
        TelegramBot bot = new TelegramBot("5461332993:AAGQ7DPd7_oY6hvWF_1WpSzWsxIuyLhF-0c");
//id=1144656668
//https://github.com/public-apis/public-apis - бесплатные Api
        // будильник
     /*   while (true) {
            try {
                Thread.sleep(1000 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int hour = LocalDateTime.now().getHour();
            int minute = LocalDateTime.now().getMinute();
            if (hour == 6 || minute == 30) {
                SendMessage request = new SendMessage(211433463, "wake up");
                bot.execute(request);
            }
        }

*/
        bot.setUpdatesListener(updates -> {
            updates.forEach(upd -> {
                try {
                    System.out.println(upd);
                    long chatId = upd.message().chat().id();
                    String incomeMessage = upd.message().text();

                    //приветственное сообщение (обработка команд)
                    if(incomeMessage.equals("/start")){
                        String  result ="Hello";
                    }else {
                        //logica

                        //1. logic dollars kurs
                  /*  Document doc = Jsoup.connect("https://www.cbr.ru/scripts/XML_daily.asp?date_req=" + incomeMessage).get();
                    System.out.println(doc.title());
                    Elements valutes = doc.select("Valute");
                    String result = "";
                    for (Element valute : valutes) {
                        if (valute.attr("ID").equals("R01235")) {
                            result = valute.select("Value").text();
                            System.out.println(result);
                        }
                    }*/

                        //2. пользователь отправляет нам номер новости, а мы выводим ее
                    /*int number = Integer.parseInt(incomeMessage);
                    Document doc = Jsoup.connect("https://lenta.ru/rss").get();
                    int index = number - 1;
                    Element news = doc.select("item").get(index);
                    String category = news.select("category").text();
                    String title = news.select("title").text();
                    String link = news.select("link").text();
                    String description = news.select("description").text();
                    String result = category + "\n" + title + "\n" + description + "\n" + link;
*/



                        // по запросу вывод треллер  iTunes

                                    String movieName = incomeMessage;
                                    String jsonString = Jsoup.connect("https://itunes.apple.com/search?media=movie&term=" + movieName)
                                            .ignoreContentType(true)
                                            .execute()
                                            .body();
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    var jsonNode = objectMapper.readTree(jsonString);
                                    String result = jsonNode.get("results").get(0).get("previewUrl").asText();
                                    //send response
                                   // SendVideo request = new SendVideo(chatId, result);
                                  //  bot.execute(request);
                        SendMessage request = new SendMessage(chatId, result);
                        bot.execute(request);

// снимок с сайта nasa
                      /*  String date = incomeMessage; //date=2022-08-09
                        String jsonString = Jsoup.connect("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&date=" + date)
                                .ignoreContentType(true)
                                .execute()
                                .body();
                        ObjectMapper objectMapper = new ObjectMapper();
                        var jsonNode = objectMapper.readTree(jsonString);
                        String imageUrl = jsonNode.get("url").asText();
                        String explanation = jsonNode.get("explanation").asText();
                        String result = imageUrl + "\n" + explanation;
                        //send response
                        SendMessage request = new SendMessage(chatId, result);
                        bot.execute(request);
*/

                    }
                    //send response
                  // SendMessage request = new SendMessage(chatId, result);
                   // bot.execute(request);




                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });






// Send messages
       // long chatId = update.message().chat().id();
       // SendResponse response = bot.execute(new SendMessage(chatId, "Hello!"));




    }
}
