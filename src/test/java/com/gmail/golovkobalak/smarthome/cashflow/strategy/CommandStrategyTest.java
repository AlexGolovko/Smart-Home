package com.gmail.golovkobalak.smarthome.cashflow.strategy;

import com.gmail.golovkobalak.smarthome.cashflow.command.CashBotCommand;
import com.gmail.golovkobalak.smarthome.cashflow.repo.ChatRepo;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CommandStrategyTest {

    public static final String CLEAR = "/clear";
    @Spy
    @InjectMocks
    CommandStrategy commandStrategy;
    @Mock
    Update update;
    @Mock
    Message message;
    @Mock
    Chat chat;
    @Mock
    private ChatRepo chatRepo;
    @Mock
    Map<String, CashBotCommand> commandMap;


    @Test
    void testClearCommand() {
        doReturn(message).when(update).message();
        doReturn(chat).when(message).chat();
        doReturn(1L).when(chat).id();
        doReturn(CLEAR).when(message).text();
        doReturn(Optional.of(new Chat())).when(chatRepo).findFirstByChatId(ArgumentMatchers.eq(1L));
        commandStrategy.process(update);
        verify(commandMap).get(CLEAR);
    }

    @Test
    void testMessCommand() {
        doReturn(message).when(update).message();
        doReturn(chat).when(message).chat();
        doReturn(1L).when(chat).id();
        doReturn("/clear@LouieHolovkoBot").when(message).text();
        doReturn(Optional.of(new Chat())).when(chatRepo).findFirstByChatId(ArgumentMatchers.eq(1L));
        commandStrategy.process(update);
        verify(commandMap).get(CLEAR);
    }
}