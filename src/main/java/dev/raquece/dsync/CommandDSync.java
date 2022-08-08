package dev.raquece.dsync;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandDSync {
    public CommandDSync(JavaPlugin plugin) {
        _plugin = plugin;
    }

    private JavaPlugin _plugin;

    public static void onStart(CommandSender sender) {
        if (!sender.isOp()) {
            sender.sendMessage("Insufficient permissions");
        }else{
            sender.sendMessage("Starting discord gateway");
        }
    }

    public static void onStop(CommandSender sender) {
        if (!sender.isOp()) {
            sender.sendMessage("Insufficient permissions");
        }else{
            sender.sendMessage("Starting discord gateway");
        }
    }
}
