package net.yuukosu;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Test extends JavaPlugin {

    @Getter
    private static Test instance;
    @Getter
    private static final Map<UUID, GamePlayer> players = new HashMap<>();

    @Override
    public void onEnable() {
        this.init();
    }

    private void init() {
        Test.instance = this;

        // コマンドの登録
        CraftServer server = (CraftServer) Bukkit.getServer();
        server.getCommandMap().register("Test", new CoincCommand());

        // イベントリスナーの登録
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
    }
}
