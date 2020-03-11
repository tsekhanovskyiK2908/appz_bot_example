package kpi.acts.appz.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;

public abstract class Bot extends TelegramLongPollingBot {
    private final String token, botName;
    private static BotSession botSession = null;
    protected static boolean botIsRunning;

    protected Bot(String token, String botName){
        this.token = token;
        this.botName = botName;
    }

    public static void runBot(Bot newBot) {
        try {
            botSession =  new TelegramBotsApi().registerBot(newBot);
            botIsRunning = botSession.isRunning();
        } catch (TelegramApiException e) {
            newBot.processTheException(e);
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

    protected void botStop() {
        botSession.stop();
        botIsRunning = botSession.isRunning();
    }

    protected void botRestart() {
        botSession.start();
        botIsRunning = botSession.isRunning();
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
