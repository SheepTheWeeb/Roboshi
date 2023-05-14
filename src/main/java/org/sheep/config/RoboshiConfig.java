package org.sheep.config;

import org.sheep.util.RoboshiConstant;

import java.io.IOException;
import java.util.Properties;

public class RoboshiConfig {
    private final Properties properties;

    public RoboshiConfig(String env) throws IOException {
        String resourceName = String.format(RoboshiConstant.PROPERTY_FILE_NAME, env);
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        this.properties = new Properties();
        properties.load(loader.getResourceAsStream(resourceName));
    }

    public String getDiscordBotToken() {
        return System.getProperty(RoboshiConstant.DISCORD_BOT_TOKEN_PROPERTY_KEY);
    }

    public String getComplimentAPIEndpoint() {
        return properties.getProperty(RoboshiConstant.COMPLIMENT_API_ENDPOINT_PROPERTY_KEY);
    }
}
