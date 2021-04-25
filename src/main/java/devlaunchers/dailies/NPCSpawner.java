package devlaunchers.dailies;

import devlaunchers.dailies.dailyworlds.DailyWorldManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.Arrays;

public class NPCSpawner {

    private static final String MAIN_WORLD_NAME = "world";

    public NPCSpawner() {

    }

    private static Block getHighestBlockAtWithExclusions(World world, double x, double z) {
        ArrayList<Material> blacklist = new ArrayList<Material>(Arrays.asList(
                Material.AIR,
                Material.ACACIA_LEAVES,
                Material.BIRCH_LEAVES,
                Material.DARK_OAK_LEAVES,
                Material.JUNGLE_LEAVES,
                Material.OAK_LEAVES,
                Material.SPRUCE_LEAVES,
                Material.ACACIA_LOG,
                Material.BIRCH_LOG,
                Material.DARK_OAK_LOG,
                Material.JUNGLE_LOG,
                Material.OAK_LOG,
                Material.SPRUCE_LOG
        ));

        System.out.println("BEFORE WHILE!");

        Location location = world.getHighestBlockAt((int)x, (int)z).getLocation();
        while(blacklist.contains( location.getBlock().getType()))  {
            System.out.println("MOVING DOWN FOUND THE THING!!!!");
            location.setY(location.getY()-1);
        }

        return location.getBlock();
    }

    private static boolean checkIsValidNPCSpawnLocation(Location location) {
        Location upperLocation = location.clone().add(0, 1, 0);
        if (location.getBlock().getType() == Material.AIR && upperLocation.getBlock().getType() == Material.AIR)
            return true;
        return false;
    }

    private static Location determineNPCSpawnLocation(Player player) {
        Location location = player.getLocation();
        for (int x=-1; x<2; x++) {
            for (int z=-1; z<2; z++) {
                // center where player is standing
                if (x == 0 && z==0) continue;

                Location checkLocation = location.clone().add(x,0,z);
                if (checkIsValidNPCSpawnLocation(checkLocation)) {
                    return checkLocation;
                }
            }
        }
        return null;
    }

    // TODO: Consolidate this duplicated logic
    public static Entity spawnDailyWorldSendingNpc(Player player) {
        World mainWorld = Bukkit.getServer().getWorld(MAIN_WORLD_NAME);

        Location location = player.getLocation();
        location = determineNPCSpawnLocation(player);
        if (location != null) {
            Villager dailyNpc = (Villager) mainWorld.spawnEntity(location, EntityType.VILLAGER);
            dailyNpc.setAI(false);
            dailyNpc.setProfession(Villager.Profession.FARMER);
            dailyNpc.setCustomName("Creating World...\n" + player.getName() + "'s Daily Kim");

            DailyWorldManager.deleteDailyWorld(player, () -> {
                DailyWorldManager.createDailyWorld(player, () -> {
                    dailyNpc.setCustomName(player.getName() + "'s Daily Kim");
                    spawnNpcInDailyWorld(player);

                    Bukkit.getScheduler().scheduleSyncDelayedTask(Dailies.getInstance(), () -> {
                        dailyNpc.remove();
                    }, 20 * 60 * 1);
                });
            });
            return dailyNpc;
        }
        else {
            player.sendMessage(ChatColor.RED+"Could not find a valid place to spawn your Daily NPC...");
            return null;
        }
    }



    public static Entity spawnNpcInDailyWorld(Player player) {
        World dailyWorld = Bukkit.getServer().getWorld(DailyWorldManager.getPlayerDailyWorldName(player));
        Location spawnLocation = dailyWorld.getSpawnLocation();
        spawnLocation.add(1,0,0);

        Location location = getHighestBlockAtWithExclusions(dailyWorld, spawnLocation.getX(), spawnLocation.getZ()).getLocation();
        location.add(0,1,0);
        System.out.println("SPAWN LOCATION: "+location.toString());
        LivingEntity dailyNpc = (LivingEntity) dailyWorld.spawnEntity(location, EntityType.VILLAGER);
        dailyNpc.setAI(false);
        dailyNpc.setCustomName("Daily Jim");

        return dailyNpc;
    }

    public static Entity spawnNpcInMainWorld(Player player) {
        World mainWorld = Bukkit.getServer().getWorld(MAIN_WORLD_NAME);

        Location location = player.getLocation();
        location = determineNPCSpawnLocation(player);
        if (location != null) {
            LivingEntity dailyNpc = (LivingEntity) mainWorld.spawnEntity(location, EntityType.VILLAGER);
            dailyNpc.setAI(false);
            dailyNpc.setCustomName("Daily Tim");

            return dailyNpc;
        }
        else {
            player.sendMessage(ChatColor.RED+"Could not find a valid place to spawn your Daily NPC...");
            return null;
        }
    }

}
