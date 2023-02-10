import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j

public class GreetingBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String textFromUser = update.getMessage().getText();

            Long userId = update.getMessage().getChatId();
            String userFirstName = update.getMessage().getFrom().getFirstName();

            log.info("[{}, {}] : {}", userId, userFirstName, textFromUser);

            SendMessage sendMessage;

            if (textFromUser.equals("/start")) {
                sendMessage = SendMessage.builder()
                        .chatId(userId.toString())
                        .text("Привіт! Представся, будь ласка, або напиши ім'я того, " +
                                "для кого ти хочеш побачити привітання:)")
                        .build();
            }
            else {
                sendMessage = SendMessage.builder()
                        .chatId(userId.toString())
                        .text("Привіт, " + textFromUser + ". Раді тебе тут бачити!:)")
                        .build();
            }
            try {
                this.sendApiMethod(sendMessage);
            } catch (TelegramApiException e) {
                log.error("Exception when sending message: ", e);
            }
        } else {
            log.warn("Unexpected update from user");
        }
    }

    @Override
    public String getBotUsername() {
        return SecretData.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return SecretData.TOKEN;
    }
}