package de.mcmdev.scoreboardlib;

import de.mcmdev.scoreboardlib.api.BaseScoreboard;
import de.mcmdev.scoreboardlib.commands.CommandScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class ScoreboardLib extends JavaPlugin {


    //TODO: Optimize Saving
    public HashMap<Player, BaseScoreboard> baseScoreboardMap = new HashMap<>();

    @Override
    public void onEnable() {

        getCommand("sb").setExecutor(new CommandScoreboard(this));

        getServer().getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void onJoin(PlayerJoinEvent event)   {
                if(!baseScoreboardMap.containsKey(event.getPlayer()))    {
                    baseScoreboardMap.put(event.getPlayer(), new BaseScoreboard(event.getPlayer()));
                }
            }

            @EventHandler
            public void onQuit(PlayerQuitEvent event)   {
                baseScoreboardMap.remove(event.getPlayer());
            }

        }, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
