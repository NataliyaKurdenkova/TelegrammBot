import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendAudio;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;

import java.util.HashMap;
import java.util.Map;

public class MyTelegramBot2 {

    public static void main(String[] args) {

        TelegramBot telegramBot = new TelegramBot(getTokenBot());

//        Document doc = null;
//        try {
//            doc = Jsoup.connect("https://audioskazki-online.ru/popular").get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Map<String, String> map = new HashMap<>();
        map.put("Винни Пух", "https://woogy.ru/skazki/miln/1.mp3");
        map.put("Айболит", "https://woogy.ru/skazki/chukovskiy/aibolit.mp3");
        map.put("Курочка Ряба", "https://woogy.ru/skazki/russkie/kurochka-ryaba.mp3");
        map.put("Сказка о рыбаке и рыбке", "https://woogy.ru/skazki/pushkin/skazka-o-rybake-i-rybke.mp3");
        map.put("Колобок", "https://woogy.ru/skazki/russkie/kolobok.mp3");


        telegramBot.setUpdatesListener(updates -> {
            updates.forEach(upd -> {
                long chatId = upd.message().chat().id();
                String incomeMessage = upd.message().text();
                String linkToAudio = null;
                //приветственное сообщение (обработка команд)
                if (incomeMessage.equals("/start") || incomeMessage.equals("/menu")) {
                    String result = "Привет " + upd.message().chat().username() + "\n" + "Меня зовут бот-сказочник. Какую сказку ты хочешь услышать?";
                    // приветственное фото
                    String photoPath = "https://forum.pushkino.org/uploads/post-51-1209587921.jpg";
                    SendPhoto photo = new SendPhoto(chatId, photoPath);
                    telegramBot.execute(photo);
                    SendMessage request = new SendMessage(chatId, result);

                    telegramBot.execute(request);
                    request = new SendMessage(chatId, "Меню:");

                    String[] buttons = new String[map.size()];
                    int i = 0;
                    for (Map.Entry<String, String> mapKey : map.entrySet()) {
                        buttons[i] = mapKey.getKey();
                        i++;
                    }

                    request.replyMarkup(new ReplyKeyboardMarkup(buttons));
                    telegramBot.execute(request);

                } else {

                    System.out.println(incomeMessage);

                    for (Map.Entry<String, String> mapResult : map.entrySet()) {
                        if (mapResult.getKey().equals(incomeMessage)) {
                            linkToAudio = String.valueOf(mapResult.getValue());
                        }
                    }
                    try {


                        if (!linkToAudio.isEmpty()) {
                            SendAudio audio = new SendAudio(chatId, linkToAudio);
                            telegramBot.execute(audio);
                        } else {
                            SendMessage request = new SendMessage(chatId, upd.message().chat().username() + ", если меню пропало набери /menu");
                            telegramBot.execute(request);
                        }

                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private static String getTokenBot() {
        return "5461332993:AAGQ7DPd7_oY6hvWF_1WpSzWsxIuyLhF-0c";
    }


}
