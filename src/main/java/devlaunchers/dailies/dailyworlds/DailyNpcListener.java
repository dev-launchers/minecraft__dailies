package devlaunchers.dailies.dailyworlds;

import devlaunchers.dailies.Dailies;
import devlaunchers.dailies.NPCSpawner;
import devlaunchers.dailies.playerdatahandler.PlayerDataHandler;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

// TODO: Better way to identify daily NPCs
// TODO: Maybe make items explode out of Daily Tim

public class DailyNpcListener implements Listener {
    @EventHandler
    public void onNpcRightClick(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        if (entity instanceof NPC) {
            if (entity.getCustomName().contains("Daily Kim")) {
                e.setCancelled(true);
                onDailyKimInteraction(e);
            }
            else{
                switch (entity.getCustomName()) {
                    case "Daily Jim":
                        e.setCancelled(true);
                        onDailyJimInteraction(e);
                        break;
                    case "Daily Tim":
                        e.setCancelled(true);
                        onDailyTimInteraction(e);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void onDailyKimInteraction(PlayerInteractEntityEvent e) {
        Server server = Bukkit.getServer();
        Player player = e.getPlayer();
        Entity entity = e.getRightClicked();

        String entityName = entity.getName();
        String dailyWorldName = "daily-world-"+entityName.split("'")[0];

        // Teleport player to main world
        server.dispatchCommand(server.getConsoleSender(), "mv tp "+player.getName()+" "+dailyWorldName);
    }

    private void onDailyJimInteraction(PlayerInteractEntityEvent e) {
        Server server = Bukkit.getServer();
        Player player = e.getPlayer();

        // Save player inventory
        PlayerDataHandler playerDataHandler = new PlayerDataHandler();
        playerDataHandler.savePlayerInventory(e.getPlayer());

        // Teleport player to main world
        server.dispatchCommand(server.getConsoleSender(), "mv tp "+player.getName()+" world");

        Bukkit.getScheduler().scheduleSyncDelayedTask(Dailies.getInstance(), () -> {
            new NPCSpawner().spawnNpcInMainWorld(player);
        }, 20*1);
    }

    private void onDailyTimInteraction(PlayerInteractEntityEvent e) {
        Server server = Bukkit.getServer();
        Player player = e.getPlayer();
        World world = player.getWorld();

        // Load player inventory
        PlayerDataHandler playerDataHandler = new PlayerDataHandler();
        ArrayList<ItemStack> playerInventoryStacks = playerDataHandler.loadPlayerInventory(player);
        ItemStack[] contents = new ItemStack[playerInventoryStacks.size()];
        contents = playerInventoryStacks.toArray(contents);
        HashMap<Integer, ItemStack> itemsNotAdded = player.getInventory().addItem(contents);
        // Poop out items on player that weren't added
        for(ItemStack itemStack : itemsNotAdded.values()) {
            if (itemStack != null)
                world.dropItem(player.getLocation(), itemStack);
        }

        // Play an effect and remove Daily Tim
        LivingEntity entity = (LivingEntity)e.getRightClicked();
        for (int i=0; i<10; i++) {
            Location smokeLocation = entity.getLocation();
            world.playEffect(new Location(
                    world,
                    smokeLocation.getX()+(Math.random()-.5),
                    smokeLocation.getY()+(Math.random()-.5),
                    smokeLocation.getZ()+(Math.random()-.5)
            ), Effect.SMOKE, 1);
        }
        //world.spawnEntity(entity.getLocation(), EntityType.FIREWORK);
        entity.remove();
    }
}
