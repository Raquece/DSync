package dev.raquece.dsync;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends JavaPlugin {
    private WebhookOut stdout;

    private FileConfiguration config;

    private Logger logger;

    @Override
    public void onEnable() {
        getLogger().info("Fetching dependencies...");
        logger = getLogger();

        logger.info("Getting configuration...");
        if (getConfig() == null) {
            saveDefaultConfig();
        }
        config = getConfig();

        checkConfig();

        logger.info("Starting discord sync...");
        if (config.getString("webhook_url") == null || config.getString("webhook_url") == "null") {
            getLogger().severe("DSync configuration webhook token not set. Set a token and restart server to apply changes.");
            config.set("webhook_url", "null");
            saveConfig();
            stdout = new NullWebhook(logger);
        }else{
            stdout = new WebhookOut(logger, config.getString("webhook_url"), this);
        }

        logger.info("Registering event listeners...");
        getServer().getPluginManager().registerEvents(new EventListener(this, stdout), this);

        Pattern versionP = Pattern.compile("MC: [^)]*");
        Matcher m = versionP.matcher(getServer().getVersion());
        String ver = "";
        if (m.find()) {
            ver = m.group(0);
        }

        var title = config.getString("messages.server.started.title");
        var message = config.getString("messages.server.started.description");
        title = title.replaceAll("[$]VERSION", ver);
        message = message.replaceAll("[$]VERSION", ver);
        stdout.SendEmbed(title, message, config.getInt("messages.server.started.color"));
    }

    @Override
    public void onDisable() {
        if (config.getString("webhook_url") != null && config.getString("webhook_url") != "null") {
            var title = config.getString("messages.server.stopped.title");
            var message = config.getString("messages.server.stopped.description");
            title = title.replaceAll("[$]VERSION", getServer().getVersion());
            message = message.replaceAll("[$]VERSION", getServer().getVersion());
            stdout.SendEmbedSync(title, message, config.getInt("messages.server.stopped.color"));
        }
    }

    private void checkConfig() {
        if(config.getString("messages.player.join") == null) {
            config.set("messages.player.join", ":arrow_right:  $PLAYER has connected");
        }
        if(config.getString("messages.player_quit") == null) {
            config.set("messages.player.quit", ":arrow_left:  $PLAYER has disconnected");
        }
        if(config.getString("messages.player.advancement") == null) {
            config.set("messages.player.advancement", ":orange_book:  $PLAYER has completed an advancement! **[ $ADVANCEMENT ]**");
        }
        if(config.getString("messages.player.death") == null) {
            config.set("messages.player.death", ":skull:  $MESSAGE");
        }
        if(config.getString("messages.server.started.title") == null ||
                config.getString("messages.server.started.description") == null ||
                config.getString("messages.server.started.color") == null) {
            config.set("messages.server.started.title", ":green_circle:  Server Online");
            config.set("messages.server.started.description", "The server is now online.");
            config.set("messages.server.started.color", 3066993);
        }
        if(config.getString("messages.server.stopped.title") == null ||
                config.getString("messages.server.stopped.description") == null ||
                config.getString("messages.server.stopped.color") == null) {
            config.set("messages.server.stopped.title", ":red_circle:  Server Offline");
            config.set("messages.server.stopped.description", "The server is now offline.");
            config.set("messages.server.stopped.color", 10038562);
        }
    }
}
