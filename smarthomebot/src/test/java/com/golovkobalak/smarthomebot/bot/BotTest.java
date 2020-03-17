package com.golovkobalak.smarthomebot.bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootTest
class BotTest {
    /*@Autowired
    private HomeBot bot;*/

    @BeforeEach
    private void setUp() {

    }

    @Test
    void onUpdateReceived() throws TelegramApiException, InterruptedException {
        ApiContextInitializer.init();
        String result = "a";
        synchronized (result){
            result.wait(10000);
        }
        TelegramBotsApi botsApi = new TelegramBotsApi();
        HomeBot bot = new HomeBot();
        botsApi.registerBot(bot);
        synchronized (result) {
            result.wait();
        }
    }
}