package devlaunchers.dailies;

import devlaunchers.dailies.commands.DailyCommand;
import devlaunchers.dailies.dailyworlds.DailyNpcListener;
import devlaunchers.dailies.dailyworlds.DailyWorldListener;
import devlaunchers.dailies.dailyworlds.DailyWorldManager;
import devlaunchers.dailies.commands.InventoryLoadCommand;
import devlaunchers.dailies.commands.InventorySaveCommand;
import org.bukkit.plugin.java.JavaPlugin;

// TODONE: Make every player have their own daily world
// TODONE: Make NPC spawn which takes players to their own daily worlds
// TODONE: Make Daily Kim spawn right away
// TODONE: Change Daily Kim's name after world is loaded
// TODONE: Clear inventory when people go to Daily Worlds (Multiverse Inventories conflict?!)
// TODONE: Kill Daily Kim after being alive for a certain amount of time
// TODONE: Make it so NPCs don't spawn on top of trees

// TODONE: Make it so NPCs can't spawn in wood or other stuff
// TODONE: Make it so NPCs can spawn in caves

// TODONE: Fix error color

// TODO: Make all NPCS invincible

// TODO: Make it so NPCs can't spawn on top of air

// TODO: Have NPCs communicate how to use the Daily system through fun dialog


// TODO: Timed deletion of player daily worlds
// TODO: Limit players to only creating one daily instance per day


// TODO: Change /daily to be activated from an item (or some other mechanic)
//   - Spawn a temporary item somewhere near the player and give them a message in chat leading them to it?
//   - Spawn on player's bed?
//   - Insert directly into player inventory (poop out of them if inventory is full? Or delay and wait X time until notifying again)
//   - Allow them to click a substring in a chat message which gives them the item/instantly spawns their Daily Helper

// ------------------------------------------



// TODO: (do later) Daily worlds are broken? Not deleting, or something?
// TODO: (do later) Maybe have one daily world and give people time bound access to a specific location-based entry point
// TODO: (do later) Secure inventory clearing (can probably be abused)

public final class Dailies extends JavaPlugin {

    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        // Plugin startup logic
        this.getCommand("saveInventory").setExecutor(new InventorySaveCommand());
        this.getCommand("loadInventory").setExecutor(new InventoryLoadCommand());
        this.getCommand("daily").setExecutor(new DailyCommand());

        getServer().getPluginManager().registerEvents(new DailyWorldListener(), this);
        getServer().getPluginManager().registerEvents(new DailyNpcListener(), this);

        DailyWorldManager.init();
    }

    public static JavaPlugin getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
