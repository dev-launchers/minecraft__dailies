package devlaunchers.dailies.dailyworlds;

import devlaunchers.dailies.Dailies;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;

public class DailyWorldListener implements Listener {

    private boolean isDailyWorld(World world) {
        return world.getName().startsWith("daily-world-");
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (isDailyWorld(world)) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Dailies.getInstance(), () -> {
                teleportPlayerToSpawn(player);
                clearPlayerInventory(player);
                setPlayerHealthFull(player);
                setPlayerFoodLevelFull(player);
                insertCompassIntoPlayerInventory(player);
                setPlayerActionBar(player);
            }, 20);
        }
    }

    private void teleportPlayerToSpawn(Player player) {
        player.teleport(player.getWorld().getSpawnLocation());
    }

    private void clearPlayerInventory(Player player) {
        player.getInventory().setContents(new ItemStack[0]);
    }

    private void setPlayerHealthFull(Player player) {
        System.out.println("SETTING MAX HEALTH");
        double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        player.setHealth(maxHealth);
    }

    private void setPlayerFoodLevelFull(Player player) {
        System.out.println("SETTING MAX FOOD");
        player.setFoodLevel(20);
    }

    private void insertCompassIntoPlayerInventory(Player player) {
        player.getInventory().addItem(new ItemStack(Material.COMPASS));
    }

    private void setPlayerActionBar(Player player) {
        player.sendActionBar("STRING");
        //player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("TESTING ACTION BAR"));
    }
}