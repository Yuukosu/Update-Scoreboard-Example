package net.yuukosu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GamePlayer {

    @Getter
    private final Player player;
    @Getter
    private final GameScoreboard scoreboard;
    @Setter
    @Getter
    private int coins;

    public GamePlayer(Player player) {
        this.player = player;
        this.coins = 0;
        this.scoreboard = new GameScoreboard(this);
    }

    public void init() {
        // スコアボードの作成
        this.scoreboard.initScoreboard();

        // 1 tick ごとにスコアボードを更新する
        new BukkitRunnable() {

            @Override
            public void run() {
                // プレイヤーリストにプレイヤーのUUIDが存在していたらスコアボードを更新する
                if (Test.getPlayers().containsKey(GamePlayer.this.player.getUniqueId())) {
                    GamePlayer.this.scoreboard.updateScoreboard();
                    return;
                }

                this.cancel();
            }
        }.runTaskTimer(Test.getInstance(), 0L, 1L);
    }
}
