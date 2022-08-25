import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Audio;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendAudio;
import com.pengrad.telegrambot.request.SendMediaGroup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.util.ArrayList;

public class MyTelegramBot2 {

    public static void main(String[] args) {

        TelegramBot telegramBot = new TelegramBot(getTokenBot());


        telegramBot.setUpdatesListener(updates -> {
            updates.forEach(upd -> {
                long chatId = upd.message().chat().id();
                String incomeMessage = upd.message().text();

                //приветственное сообщение (обработка команд)
                if (incomeMessage.equals("/start")) {
                    String result = "Привет " + upd.message().chat().username() + "\n" + "Меня зовут бот-сказочник. Какую сказку ты хочешь услышать?";
                    String photoPath = "https://forum.pushkino.org/uploads/post-51-1209587921.jpg";
                    SendPhoto photo = new SendPhoto(chatId, photoPath);
                    SendMessage request = new SendMessage(chatId, result);
                    telegramBot.execute(photo);
                    telegramBot.execute(request);
                    sendMenu(telegramBot, chatId, "Меню:");


                } else {
                    if (incomeMessage.equals("Три поросенка")) {
                        Document doc = null;
                        try {
                            doc = Jsoup.connect("https://www.hobobo.ru/audioskazki/sbornik-skazok-mp3/tri-porosenka-online/").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Elements text = doc.select("audio");

                        //<a class="jp-playlist-item-free" href="https://www.hobobo.ru/assets/uploads/audio/tri-porosenka.mp3" tabindex="-1" download="">

                       String result = text.attr("src");
                        //String result="https://www.hobobo.ru/assets/uploads/audio/tri-porosenka.mp3";
                        SendAudio audio=new SendAudio(chatId,result);
                        telegramBot.execute(audio);

                    }


                    //logica
                }

            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }


    private static String getTokenBot() {
        return "5461332993:AAGQ7DPd7_oY6hvWF_1WpSzWsxIuyLhF-0c";
    }

    //меню
    private static synchronized void getMenu() {
//        InlineKeyboardMarkup inlineKeyboardMarkup=new InlineKeyboardMarkup(
//                new InlineKeyboardButton[]{
//                      new InlineKeyboardButton("Колобок").url("view-source:https://nukadeti.ru/audioskazki/kolobok"),
//                      new InlineKeyboardButton("Маша и медведь").url("https://nukadeti.ru/audioskazki/masha_i_medved")
//
//                }


    }

    private static void sendMenu(TelegramBot bot, long chatId, String message) {
        SendMessage request = new SendMessage(chatId, message);
        String[] buttons = new String[3];
        buttons[0] = "Три поросенка";
        buttons[1] = "Маша и медведь";
        buttons[2] = "Морозко";

        request.replyMarkup(new ReplyKeyboardMarkup(buttons));
        bot.execute(request);
    }


}
