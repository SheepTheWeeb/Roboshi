package org.sheep.scheduler;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.sheep.config.SpotifyConfig;
import org.sheep.gateway.SpotifyGateway;
import org.sheep.model.response.spotify.SpotifyItem;
import org.sheep.model.response.spotify.SpotifyListing;
import org.sheep.service.CacheService;
import org.sheep.util.RoboshiConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@ConditionalOnProperty("spotify.scheduler.enabled")
public class SpotifyScheduler {
    // PlaylistIds from: Ascending Schep, Ultimate Schep, Revival Schep, DnB Party Party and Chilly with Billy
    private final List<String> playlistIDList = Arrays.asList("70yjpM5AnpdJuZCUdS3BFq", "3MKfHx7Hjof6dumov0X9Ar",
            "6XpviM4AyjMkutYcamIvhe", "0pTDtGa8KU7e6pjCzCnraZ", "12GUKJxNC0Li5jAMaaVsph");

    @Autowired
    private JDA jda;
    @Autowired
    private SpotifyConfig spotifyConfig;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CacheService<String> authCache;
    @Autowired
    private Random random;

    @Scheduled(cron = "0 0 0 * * *")
    public void songOfTheDayScheduler() {
        log.info("Song of the day scheduler started");
        String token = getCachedAuthToken();
        String randomPlaylistID = playlistIDList.get(random.nextInt(playlistIDList.size()));
        int offset = random.nextInt(NumberUtils.toInt(spotifyConfig.getTracksMaxOffset(), 500));
        SpotifyListing<SpotifyItem> playlist = SpotifyGateway.getTracks(randomPlaylistID, offset, token, restTemplate, spotifyConfig);
        if (playlist == null || playlist.getItems() == null) {
            log.error("Playlist response was null, for playlistID {}, offset: {}", randomPlaylistID, offset);
            return;
        }
        if (playlist.getItems().isEmpty()) {
            offset = random.nextInt(playlist.getTotal());
            playlist = SpotifyGateway.getTracks(randomPlaylistID, offset, token, restTemplate, spotifyConfig);
        }

        if (playlist != null && !playlist.getItems().isEmpty()) {
            String songOfTheDay = String.format("https://open.spotify.com/track/%s", playlist.getItems().get(0).getTrack().getId());
            sendDiscordMessage("Liedje van de dag: \n" + songOfTheDay);
        }
    }

    private String getCachedAuthToken() {
        String token = authCache.get(RoboshiConstant.SPOTIFY_AUTH_TOKEN);
        if (token == null) {
            token = SpotifyGateway.getAuthToken(restTemplate, spotifyConfig);
            authCache.add(RoboshiConstant.SPOTIFY_AUTH_TOKEN, token);
        }
        return token;
    }

    private void sendDiscordMessage(String msg) {
        String guildId = StringUtils.defaultString(spotifyConfig.getGuild());
        Guild guild = jda.getGuildById(guildId);
        if (guild == null) {
            log.error("Spotify Scheduler Guild not found: {}", guildId);
            return;
        }

        String channelId = StringUtils.defaultString(spotifyConfig.getChannel());
        TextChannel channel = guild.getTextChannelById(channelId);
        if (channel == null) {
            log.error("Spotify Scheduler Channel not found: {}", channelId);
            return;
        }
        channel.sendMessage(msg).queue();
    }
}
