package de.mcmdev.scoreboardlib.commands;

import de.mcmdev.scoreboardlib.ScoreboardLib;
import de.mcmdev.scoreboardlib.api.BaseScoreboard;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Map;

public class CommandScoreboard implements CommandExecutor {

    private ScoreboardLib plugin;

    public CommandScoreboard(ScoreboardLib plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player)    {
            Player player = (Player) sender;
            if(player.hasPermission("scoreboard.manage"))   {
                if(args.length > 0) {
                    String subcommand = args[0];
                    switch (subcommand) {

                        case "title":
                            if(args.length >= 2)    {
                                for(Map.Entry<Player, BaseScoreboard> entry : plugin.baseScoreboardMap.entrySet()) {
                                    entry.getValue().setDisplayName(ChatColor.translateAlternateColorCodes('&', String.join(" ", Arrays.copyOfRange(args, 1, args.length))));
                                }
                                player.sendMessage("§eScoreboard §8➤ §eTitel wurde gesetzt");
                            }   else    {
                                player.sendMessage("§cError §8➤ §cUngenügend Argumente");
                                sendHelp(player);
                            }
                            break;
                        case "set":
                            if(args.length >= 3)    {
                                if(NumberUtils.isNumber(args[1]))   {
                                    int num = Integer.parseInt(args[1]);
                                    for(Map.Entry<Player, BaseScoreboard> entry : plugin.baseScoreboardMap.entrySet()) {
                                        entry.getValue().setScore(ChatColor.translateAlternateColorCodes('&', String.join(" ", Arrays.copyOfRange(args, 2, args.length))), num);
                                    }
                                    player.sendMessage("§eScoreboard §8➤ §eEintrag wurde gesetzt");
                                }
                            }   else    {
                                player.sendMessage("§cError §8➤ §cUngenügend Argumente");
                                sendHelp(player);
                            }
                            break;
                        case "reset":
                            if(args.length == 2)    {
                                boolean byScore = false;
                                if(NumberUtils.isNumber(args[1]))   {
                                    byScore = true;
                                }
                                for(Map.Entry<Player, BaseScoreboard> entry : plugin.baseScoreboardMap.entrySet()) {
                                    if(byScore) {
                                        entry.getValue().removeEntry(Integer.parseInt(args[1]));
                                    }   else    {
                                        entry.getValue().removeEntry(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
                                    }
                                }
                                player.sendMessage("§eScoreboard §8➤ §eEintrag wurde entfernt");
                            }   else    {
                                player.sendMessage("§cError §8➤ §cUngenügend Argumente");
                                sendHelp(player);
                            }
                            break;


                    }
                }   else    {
                    player.sendMessage("§cError §8➤ §cUngenügend Argumente");
                    sendHelp(player);
                }
            }   else    {
                player.sendMessage("§cError §8➤ §cDu darfst diesen Befehl nicht benutzen");
            }
        }
        return true;
    }

    private void sendHelp(Player player)    {
        player.sendMessage("§8§m                §e Scoreboard §8§m                ");
        player.sendMessage("§eScoreboard §8➤ §e/sb title [Title] - Setzt den Titel");
        player.sendMessage("§eScoreboard §8➤ §e/sb set [Value] [Name] - Setzt einen Score");
        player.sendMessage("§eScoreboard §8➤ §e/sb reset [Name] - Entfernt einen Score");
    }
}
