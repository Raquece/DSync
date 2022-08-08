package dev.raquece.dsync;

import java.util.logging.Logger;

public class NullWebhook extends WebhookOut {
    public NullWebhook(Logger logger) {
        super(logger, "null", null);
    }

    @Override
    public void SendMessage(String contents) {
        return;
    }

    @Override
    public void SendEmbed(String title, String description, int color) {
        return;
    }
}
