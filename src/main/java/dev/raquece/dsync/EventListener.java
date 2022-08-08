package dev.raquece.dsync;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.advancement.AdvancementDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
        String msg = e.getDeathMessage();
        stdout.SendMessage(":skull:  " + msg);
    }

    @EventHandler
    public void onAdvancementDone(PlayerAdvancementDoneEvent e) {
        var rkey = e.getAdvancement().getKey().getKey().replace('/', '.');
        String key = "advancements." + rkey + ".title";
        var msg = adv_locales.get(key).getAsString();
        String player = e.getPlayer().getName();
        stdout.SendMessage(":orange_book:  " + player + " has completed an advancement! **[ " + msg + " ]**");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        String player = e.getPlayer().getName();
        stdout.SendMessage(":arrow_right:  " + player + " has connected");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        String player = e.getPlayer().getName();
        stdout.SendMessage(":arrow_left:  " + player + " has disconnected");
    }
}
