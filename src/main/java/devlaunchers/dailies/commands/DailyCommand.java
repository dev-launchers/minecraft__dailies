package devlaunchers.dailies.commands;

import devlaunchers.dailies.dailyworlds.DailyWorldManager;
import devlaunchers.dailies.playerdatahandler.PlayerDataHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DailyCommand implements CommandExecutor {
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            player.sendMessage("Creating your daily world now. Please wait.....");

            DailyWorldManager.buildNewDailyWorld(player);
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
