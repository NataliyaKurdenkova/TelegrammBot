import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendAudio;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyTelegramBot2 {

    public static void main(String[] args) throws IOException {

        //подключаемся к зарегистрированному боту по токену
        TelegramBot telegramBot = new TelegramBot(getTokenBot());

        //конектимся к сайту, с которого хотим спарсить инфу
        Document doc = Jsoup.connect("https://audioskazki-online.ru/popular").get();

        //определяемся с элементами
        Elements select = doc.select("li[data-title]");

        //создаем мапу для хранения инфы
        Map<String, String> map = new HashMap<>();

        //парсим в нее инфу с сайта - название сказок и сслки на аудио файлы
        for (Element element : select) {
            String s = element.getElementsByAttribute("data-title").toString();
            String v = element.childNode(1).toString();
            String s1 = s.split("data-title=\"")[1].split("\"")[0];
            String v1 = v.split("data-src=\"")[1].split("\"")[0];
            map.put(s1, v1);
        }
// заглушка для тестирования работы бота
//        map.put("Винни Пух", "https://woogy.ru/skazki/miln/1.mp3");
//        map.put("Айболит", "https://woogy.ru/skazki/chukovskiy/aibolit.mp3");
//        map.put("Курочка Ряба", "https://woogy.ru/skazki/russkie/kurochka-ryaba.mp3");
//        map.put("Сказка о рыбаке и рыбке", "https://woogy.ru/skazki/pushkin/skazka-o-rybake-i-rybke.mp3");
//        map.put("Колобок", "https://woogy.ru/skazki/russkie/kolobok.mp3");

//слушаем что нам пишет подключенный пользователь в чате
        telegramBot.setUpdatesListener(updates -> {
            updates.forEach(upd -> {
                //получаем идентификатор чата пользователя
                long chatId = upd.message().chat().id();
                //получаем сообщение которое нам пользователь прислал
                String incomeMessage = upd.message().text();

                String linkToAudio = null;
                //приветственное сообщение (обработка команд)
                if (incomeMessage.equals("/start") || incomeMessage.equals("/menu")) {
                    String result = "Привет " + upd.message().chat().username() + "\n" + "Меня зовут бот-сказочник. Какую сказку ты хочешь услышать?";
                    // приветственное фото
                    String photoPath = "https://forum.pushkino.org/uploads/post-51-1209587921.jpg";
                    SendPhoto photo = new SendPhoto(chatId, photoPath);
                    //отправляем фото
                    telegramBot.execute(photo);
                    SendMessage request = new SendMessage(chatId, result);

                    // отправляем сообщение
                    telegramBot.execute(request);

                    // создаем и выаодим меню
                    request = new SendMessage(chatId, "Меню:");

                    //кнопки
                    String[] buttons = new String[map.size()];


                    // записываем название кнопок названия сказок
                    int i = 0;
                    for (Map.Entry<String, String> mapKey : map.entrySet()) {
                        buttons[i] = mapKey.getKey();
                        i++;
                    }

                    // создаем само меню с названиями
                    Keyboard keyboard = null;
                    KeyboardButton[][] keyboardButtons = new KeyboardButton[map.size()][1];
                    int t = 0;
                    for (int j = 0; j < map.size(); j++) {
                        for (int k = 0; k < 1; k++) {
                            keyboardButtons[j][k] = new KeyboardButton(buttons[t]);
                            t++;
                        }
                    }
                    keyboard = new ReplyKeyboardMarkup(keyboardButtons);

                    request.replyMarkup(keyboard);
                    telegramBot.execute(request);

                } else {
                    // кнопка вызова меню
                    Keyboard keyboard = new ReplyKeyboardMarkup("/menu");
                    SendMessage requestMenu = new SendMessage(chatId, upd.message().chat().username() + ", если меню пропало набери /menu");
                    requestMenu.replyMarkup(keyboard);
                    telegramBot.execute(requestMenu);

                    //печатаем в консоль сообщение пользователя
                    System.out.println(incomeMessage);

                    for (Map.Entry<String, String> mapResult : map.entrySet()) {
                        if (mapResult.getKey().equals(incomeMessage)) {
                            linkToAudio = String.valueOf(mapResult.getValue());
                        }
                    }
                    try {
                            // отправка аудио в чат
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
// токен моего чатбота
    private static String getTokenBot() {
        return "5461332993:AAGQ7DPd7_oY6hvWF_1WpSzWsxIuyLhF-0c";
    }


}
