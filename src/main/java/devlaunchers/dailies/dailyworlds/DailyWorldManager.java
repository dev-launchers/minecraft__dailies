package devlaunchers.dailies.dailyworlds;

import devlaunchers.dailies.Dailies;
import devlaunchers.dailies.NPCSpawner;
import devlaunchers.dailies.multiversehandler.MultiverseHelper;
import devlaunchers.dailies.Dailies;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.ArrayList;

// TODO: Clean up alert reminder chat code stuff - it's bad
// TODO: Display time left in world in action bar, or use boss health boss bar - or make progress bar in action bar

// TODO: Should people be able to bring entities (Pets, villagers, etc) from the daily world?
// TODO: Daily streaks!

/*
 "this is my new favorite Science & Technology stream" - TheVibeCurator 2021
 */


// TODO: Possible daily game modes
// - Easter egg hunts
// - Protecting a certain starting item while hunting for another one
// - Trophy hunting (stats above entity head)
// - Treasure hunt ()
// - Huge towers you have to scale

// TODO: [IDEA] "Jaily Dim" chases you around and tries to jail you

public class DailyWorldManager {

    private static final int TOTAL_WORLD_LIFETIME = 60*5; // In seconds
    private static final int[] WARNING_INTERVALS = new int[]{ // In seconds
            TOTAL_WORLD_LIFETIME/2,
            TOTAL_WORLD_LIFETIME-60,
            TOTAL_WORLD_LIFETIME-30,
            TOTAL_WORLD_LIFETIME-10,
            TOTAL_WORLD_LIFETIME-5,
            TOTAL_WORLD_LIFETIME-4,
            TOTAL_WORLD_LIFETIME-3,
            TOTAL_WORLD_LIFETIME-2,
            TOTAL_WORLD_LIFETIME-1
    };

    private static ArrayList<String> activeDaily = new ArrayList<String>();

    public static void init() {
        /*
        // World creation task
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Dailies.getInstance(), () -> {
            buildNewDailyWorld();
        }, 0, 20*TOTAL_WORLD_LIFETIME);
        */
    }

    public static void buildNewDailyWorld(Player player) {

        System.out.println("{{{{{--------------------------}}}}}");
        System.out.println("{{{{{BUILDING A NEW DAILY WORLD}}}}}");
        System.out.println("{{{{{--------------------------}}}}}");

        new NPCSpawner().spawnDailyWorldSendingNpc(player);


        /*
        // Setup world deletion warning alerts
        for (int intervalAmount : WARNING_INTERVALS) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Dailies.getInstance(), () -> {
                System.out.println("Sending reminder message");
                World dailyWorld = Bukkit.getServer().getWorld(DAILY_WORLD_NAME);
                for (Player worldPlayer : dailyWorld.getPlayers()) {
                    player.sendMessage("World destruction in "+(TOTAL_WORLD_LIFETIME-intervalAmount)+" seconds!");
                }
            }, 20*intervalAmount);
        }
        */
    }

    public static String getPlayerDailyWorldName(Player player) {
        return "daily-world-"+player.getName();
    }

    public static void createDailyWorld(Player player, Runnable callback) {
        MultiverseHelper.createWorld(getPlayerDailyWorldName(player), () -> {
            callback.run();
        });
    }

    public static void deleteDailyWorld(Player player, Runnable callback) {
        // Tear down old daily-world, create new one
        MultiverseHelper.deleteWorld(getPlayerDailyWorldName(player), () -> {
            callback.run();
        });
    }


}
