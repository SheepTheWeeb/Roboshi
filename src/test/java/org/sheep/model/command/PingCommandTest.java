package org.sheep.model.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PingCommandTest {

    static PingCommand pingCommand;

    @BeforeAll
    static void setup() {
        pingCommand = new PingCommand();
    }

    @Test
    void givenInteractionEvent_whenPingCommandExecute_thenQueueReply() {
        // given
        SlashCommandInteractionEvent eventMock = mock();
        ReplyCallbackAction callback = mock();
        RestAction restAction = mock();
        InteractionHook hook = mock();

        when(eventMock.reply(anyString())).thenReturn(callback);
        when(callback.setEphemeral(anyBoolean())).thenReturn(callback);
        when(callback.flatMap(any())).thenReturn(restAction);
        doNothing().when(restAction).queue();
        when(eventMock.getHook()).thenReturn(hook);
        when(hook.editOriginalFormat(anyString())).thenReturn(null);

        // when
        pingCommand.execute(eventMock);

        // then
        verify(restAction, times(1)).queue();
    }
}
