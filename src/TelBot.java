import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

public class TelBot {
    public static void main(String[] args){
       // System.out.println("test_tel_bot");
        // Create your bot passing the token received from @BotFather
        TelegramBot bot = new TelegramBot("5461332993:AAGQ7DPd7_oY6hvWF_1WpSzWsxIuyLhF-0c");

// Register for updates
        bot.setUpdatesListener(updates -> {
           updates.forEach(upd->{
               System.out.println(upd);
               long chatId = upd.message().chat().id();
              //входное сообщение
               String incomeMessage=upd.message().text();
               //имя
               String senderName=upd.message().from().firstName();
               // приветствие
               String message="Привет! "+incomeMessage + senderName;
               SendMessage request = new SendMessage(chatId, message);
               bot.execute(request);
           });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

// Send messages
       // long chatId = update.message().chat().id();
       // SendResponse response = bot.execute(new SendMessage(chatId, "Hello!"));
    }
}
