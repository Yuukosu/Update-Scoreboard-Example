package net.yuukosu;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard;

public class GameScoreboard {

    @Getter
    private final GamePlayer playerManager;
    @Setter
    @Getter
    private ScoreboardScore[] newScores;
    private ScoreboardScore[] oldScores;
    protected final PlayerConnection connection;
    protected final Scoreboard scoreboard;
    protected final ScoreboardObjective objective;

    public GameScoreboard(GamePlayer playerManager) {
        this.playerManager = playerManager;
        this.connection = ((CraftPlayer) this.playerManager.getPlayer()).getHandle().playerConnection;
        this.scoreboard = ((CraftScoreboard) Bukkit.getScoreboardManager().getNewScoreboard()).getHandle();
        this.objective = this.scoreboard.registerObjective("SIDEBAR", IScoreboardCriteria.b);
    }

    private ScoreboardScore createScore(String message, int slot) {
        ScoreboardScore score = new ScoreboardScore(this.scoreboard, this.objective, message);
        score.setScore(slot);

        return score;
    }

    public void initScoreboard() {
        this.objective.setDisplayName("§e§lSCOREBOARD");
        PacketPlayOutScoreboardObjective packet1 = new PacketPlayOutScoreboardObjective(this.objective, 0);

        this.connection.sendPacket(packet1);
    }

    public void updateScoreboard() {
        this.newScores = new ScoreboardScore[]{
                this.playerManager.getPlayer().isSneaking() ? this.createScore("§cYou are now sneaking!", 8) : null,
                this.createScore("  ", 7),
                this.createScore("Kills (Dummy): §a0", 6),
                this.createScore("Wins (Dummy): §a0", 5),
                this.createScore(" ", 4),
                this.createScore("Coins: §6" + String.format("%,d", this.playerManager.getCoins()), 3),
                this.createScore("", 2),
                this.createScore("§ewww.example.com", 1)
        };

        PacketPlayOutScoreboardObjective packet1 = new PacketPlayOutScoreboardObjective(this.objective, 2);
        PacketPlayOutScoreboardDisplayObjective packet2 = new PacketPlayOutScoreboardDisplayObjective(1, this.objective);

        for (int i = 0; i < this.newScores.length; i++) {
            String newName = this.newScores[i] != null ? this.newScores[i].getPlayerName() : null;

            if (this.oldScores != null && this.oldScores.length > i) {
                String oldName = this.oldScores[i] != null ? this.oldScores[i].getPlayerName() : null;

                if ((newName == null || !newName.equals(oldName)) && oldName != null) {
                    PacketPlayOutScoreboardScore packet = new PacketPlayOutScoreboardScore(oldName, this.objective);
                    this.connection.sendPacket(packet);
                    this.connection.sendPacket(packet1);
                }
            }

            if (newName != null) {
                PacketPlayOutScoreboardScore packet3 = new PacketPlayOutScoreboardScore(this.newScores[i]);
                this.connection.sendPacket(packet3);
            }
        }

        this.connection.sendPacket(packet1);
        this.connection.sendPacket(packet2);

        this.oldScores = this.newScores;
    }
}
