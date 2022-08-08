package dev.raquece.dsync;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

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

        stdout.SendEmbed(":green_circle:  Server Online", "The server is now online.", 3066993);
    }

    @Override
    public void onDisable() {
        stdout.SendEmbedSync(":red_circle:  Server Offline", "The server is now offline.", 10038562);
    }
}
