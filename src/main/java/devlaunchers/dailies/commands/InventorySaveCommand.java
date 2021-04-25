package devlaunchers.dailies.commands;

import devlaunchers.dailies.playerdatahandler.PlayerDataHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InventorySaveCommand implements CommandExecutor {


    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;


            PlayerDataHandler playerDataHandler = new PlayerDataHandler();
            playerDataHandler.savePlayerInventory(player);
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
