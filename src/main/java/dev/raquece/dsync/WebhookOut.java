package dev.raquece.dsync;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.logging.Logger;

public class WebhookOut {
    public WebhookOut(Logger logger, String url, JavaPlugin main) {
        this.url = url;
        this.logger = logger;
        this.main = main;
    }

    private String url;
    private Logger logger;
    private JavaPlugin main;

    public void SendMessage(String contents) {
        main.getServer().getScheduler().runTask(main, new Runnable() {
            @Override
            public void run() {
                HttpClient client = HttpClient.newHttpClient();
                var content = "{ \"content\": \"" + contents + "\" }";
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .setHeader("content-type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(content))
                        .build();

                try {
                    HttpResponse<String> response = client.send(request,
                            HttpResponse.BodyHandlers.ofString());
                } catch (Exception e) {
                    logger.severe("Failed to send webhook.");
                }
            }
        });
    }

    public void SendEmbedSync(String title, String description, int color) {
        String embed_json = "{ \"embeds\": [{\"title\": \"" + title + "\", \"description\": \"" + description + "\", \"color\": " + Integer.toString(color) + " }]}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .setHeader("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(embed_json))
                .build();

        try {
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.severe("Failed to send webhook.");
        }
    }

    public void SendEmbed(String title, String description, int color) {
        main.getServer().getScheduler().runTask(main, new Runnable() {
            @Override
            public void run() {
                String embed_json = "{ \"embeds\": [{\"title\": \"" + title + "\", \"description\": \"" + description + "\", \"color\": " + Integer.toString(color) + " }]}";

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .setHeader("content-type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(embed_json))
                        .build();

                try {
                    HttpResponse<String> response = client.send(request,
                            HttpResponse.BodyHandlers.ofString());
                } catch (Exception e) {
                    logger.severe("Failed to send webhook.");
                }
            }
        });
    }
}

