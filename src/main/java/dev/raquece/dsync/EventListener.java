package dev.raquece.dsync;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.Charset;

public class EventListener implements Listener {
    public EventListener(JavaPlugin main, WebhookOut stdout) {
        this.stdout = stdout;
        this.main = main;

        JsonParser parser = new JsonParser();
        adv_locales = parser.parse(
                new InputStreamReader(main.getResource("adv_locales.json"), Charset.forName("UTF-8")))
                .getAsJsonObject();
    }

    private WebhookOut stdout;
    private JavaPlugin main;

    private JsonObject adv_locales;

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        var msg = main.getConfig().getString("messages.player.death");
        msg = msg.replaceAll("[$]VICTIM", e.getEntity().getName());
        msg = msg.replaceAll("[$]MESSAGE", e.getDeathMessage());
        stdout.SendMessage(msg);
    }

    @EventHandler
    public void onAdvancementDone(PlayerAdvancementDoneEvent e) {
        var rkey = e.getAdvancement().getKey().getKey().replace('/', '.');
        String key = "advancements." + rkey + ".title";
        var title = adv_locales.get(key).getAsString();

        var msg = main.getConfig().getString("messages.player.advancement");
        msg = msg.replaceAll("[$]PLAYER", e.getPlayer().getName());
        msg = msg.replaceAll("[$]ADVANCEMENT", title);
        stdout.SendMessage(msg);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        var msg = main.getConfig().getString("messages.player.join");
        msg = msg.replaceAll("[$]PLAYER", e.getPlayer().getName());
        stdout.SendMessage(msg);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        var msg = main.getConfig().getString("messages.player.quit");
        msg = msg.replaceAll("[$]PLAYER", e.getPlayer().getName());
        stdout.SendMessage(msg);
    }
}
