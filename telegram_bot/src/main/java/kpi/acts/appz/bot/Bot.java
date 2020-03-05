package kpi.acts.appz.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;

public abstract class Bot extends TelegramLongPollingBot {
    private final String token, botName;
    private static BotSession botSession = null ;
    protected Bot(String token, String botName){
        this.token = token;
        this.botName = botName;
    }

    public static void runBot(Bot newBot) {
        try {
            botSession =  new TelegramBotsApi().registerBot(newBot);
        } catch (TelegramApiException e) {
            newBot.processTheException(e);
        } finally {
            if(botSession!= null) {
                botSession.stop();
            }
        }
    }

    public Message sendTextMessage(Message messageFrom, String text){
        try {
            SendMessage send = new SendMessage().setChatId(messageFrom.getChatId());

            send.setText(text.trim());
            return execute(send);
        } catch (Exception e) {
            processTheException(e);
            return null;
        }
    }

    public void botStop() {
        botSession.stop();
    }

   protected abstract void processTheException(Exception e);

    @Override
    public final String getBotUsername() {
        return botName;
    }

    @Override
    public final String getBotToken() {
        return token;
    }
}
