package org.sheep.model.command;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.sheep.config.RoboshiConfig;
import org.sheep.model.ComplimentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ComplimentCommand extends AbstractCommand {
    private static final String NAME = "compliment";
    private static final String DESCRIPTION = "Gives you a compliment";

    private final RoboshiConfig config;
    private final RestTemplate restTemplate;

    @Autowired
    public ComplimentCommand(RoboshiConfig config, RestTemplate restTemplate) {
        super(NAME, DESCRIPTION, true);
        this.config = config;
        this.restTemplate = restTemplate;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (!this.isEnabled()) {
            log.warn("PingCommand is disabled but still used");
            return;
        }
        event.reply(getCompliment()).queue();
    }

    /**
     * Fetches compliment from the compliment API
     *
     * @return compliment
     */
    private String getCompliment() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ComplimentResponse> response = restTemplate.exchange(
                    config.getComplimentAPIEndpoint(),
                    HttpMethod.GET,
                    entity,
                    ComplimentResponse.class
            );
            ComplimentResponse body = response.getBody();
            if (body != null && response.getStatusCode().is2xxSuccessful()) {
                return body.getCompliment();
            }
        } catch(RestClientResponseException | ResourceAccessException ex) {
            log.error("Error has occurred while fetching compliment: {}", ex.getMessage(), ex);
        }
        return "Could not fetch compliment from API.";
    }
}
