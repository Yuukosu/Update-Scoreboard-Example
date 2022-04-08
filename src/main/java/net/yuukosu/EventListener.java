package net.yuukosu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        GamePlayer gamePlayer = new GamePlayer(player);
        gamePlayer.init();
        Test.getPlayers().put(player.getUniqueId(), gamePlayer);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Test.getPlayers().remove(player.getUniqueId());
    }
}
