package de.mcmdev.scoreboardlib.api;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class BaseScoreboard {

    private Player holder;
    private Scoreboard scoreboard;
    private Objective objective;

    public BaseScoreboard(Player holder) {
        this.holder = holder;
        this.scoreboard = getScoreboard();
        this.objective = loadObjective();
    }

    private Scoreboard getScoreboard()  {
       return holder.getScoreboard();
    }

    private Objective loadObjective()    {
        Objective objective;
        if(scoreboard.getObjective("baseboard") == null)    {
            objective = scoreboard.registerNewObjective("baseboard", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            System.out.println("setobj");
        }
        objective = scoreboard.getObjective("baseboard");
        return objective;
    }

    public BaseScoreboard setDisplayName(String displayName)  {
        objective.setDisplayName(displayName);
        return this;
    }

    public BaseScoreboard setScore(String entryName, int value)   {
        objective.getScore(entryName).setScore(value);
        return this;
    }

    public BaseScoreboard removeEntry(String entryName)   {
        scoreboard.resetScores(entryName);
        return this;
    }

    public BaseScoreboard removeEntry(int value)   {
        scoreboard.getEntries().stream()
                .map(s -> objective.getScore(s))
                .filter(score -> score.isScoreSet() && score.getScore() == value)
                .forEach(score -> score.getScoreboard().resetScores(score.getEntry()));
        return this;
    }


}
