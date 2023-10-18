package uz.predict.service;

import org.springframework.beans.factory.annotation.Autowired;
import uz.predict.config.BotConfig;
import uz.predict.pages.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.predict.step.Step;
import uz.predict.step.StepService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig config;
    private final PageService mainPage;

    @Autowired
    private StepService stepService;

    public TelegramBot(BotConfig config, PageService mainPage) {
        this.config = config;

        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "Boshlash"));

        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error during setting bot's command list: {}", e.getMessage());
        }

        this.mainPage = mainPage;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.hasText()) {
                String text = message.getText();

                if (text.equals("/start")) {
                    sendMessage(message.getChatId(), "Welcome, " + message.getFrom().getFirstName() + " " + message.getFrom().getLastName() + "!");

                    if (!mainPage.isLoggedIn()) {
                        // If not logged in
                        mainPage.init();

                        sendMessage(message.getChatId(), "Enter your telegram phone number: ");
                        stepService.createStep(message.getChatId(), Step.STARTED);
                    }

                    // Already logged in


                }

                Step lastStep = stepService.getLastStep(message.getChatId());

                switch (lastStep) {
                    case Step.STARTED -> {
                        mainPage.fillPhoneNumber(message.getText());

                        stepService.createStep(message.getChatId(), Step.PHONE_NUMBER_ENTERED);
                        break;
                    }
                    case Step.PHONE_NUMBER_ENTERED -> {
                        mainPage.fillAuthCode(message.getText());

                        stepService.createStep(message.getChatId(), Step.AUTH_CODE_ENTERED);
                        break;
                    }

                    case AUTH_CODE_ENTERED -> {

                        break;
                    }
                }

            }
        }
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();

        message.setChatId(chatId);
        message.setText(textToSend);
        message.enableHtml(true);
        try {
            execute(message);
        } catch (TelegramApiException e) {

        }
    }
}