package devlaunchers.dailies.playerdatahandler;

import devlaunchers.dailies.Dailies;
import devlaunchers.dailies.Dailies;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// TODO: Make saved inventories determined by World also

public class PlayerDataHandler {

    private void createDataFolder() {
        System.out.println("Creating Data Folder");

        // Creates the plugins data folder "ByteEconomy", if it doesn't exist yet
        JavaPlugin plugin = Dailies.getInstance();
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
    }

    private String getPlayerFilePath(Player player) {
        UUID uuid = player.getUniqueId();
        return uuid + ".yml";
    }

    private File getPlayerFile(Player player) {
        System.out.println(getPlayerFilePath(player));
        return new File(Dailies.getInstance().getDataFolder(), getPlayerFilePath(player));
    }

    private FileConfiguration getPlayerFileConfig(Player player) {
        File file = new File(Dailies.getInstance().getDataFolder(), getPlayerFilePath(player));
        return YamlConfiguration.loadConfiguration(file);
    }

    public ItemStack[] removeItemAtIndex(Inventory inventory, int index) {
        ItemStack[] itemStacks = inventory.getContents();
        itemStacks[index] = new ItemStack(Material.AIR);

        return itemStacks;
    }

    public void savePlayerInventory(Player player) {
        System.out.println("PlayerDataHandler - SAVING PLAYER INVENTORY");


        createDataFolder();

        File playerFile = getPlayerFile(player);
        FileConfiguration configData = getPlayerFileConfig(player);

        // Remove null entries in PlayerInventory
        ItemStack[] playerInventory = player.getInventory().getContents();
        List<ItemStack> trimmedInventory = new ArrayList<ItemStack>();
        for (ItemStack itemStack : playerInventory) {
            if (itemStack != null)
                trimmedInventory.add(itemStack);
        }

        configData.set(getPlayerFilePath(player), trimmedInventory);

        try {
            configData.save(playerFile);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ItemStack> loadPlayerInventory(Player player) {
        System.out.println("PlayerDataHandler - LOADING PLAYER INVENTORY");

        FileConfiguration configData = getPlayerFileConfig(player);

        List contentsList =  configData.getList(getPlayerFilePath(player));
        if (contentsList != null) {
            return new ArrayList<ItemStack>(contentsList);
        }

        return new ArrayList<ItemStack>();
    }
}
