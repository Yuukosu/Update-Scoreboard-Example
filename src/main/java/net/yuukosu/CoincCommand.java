package net.yuukosu;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoincCommand extends Command {

    public CoincCommand() {
        super("coinc");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (Test.getPlayers().containsKey(player.getUniqueId())) {
                GamePlayer gamePlayer = Test.getPlayers().get(player.getUniqueId());
                gamePlayer.setCoins(gamePlayer.getCoins() + 1);

                player.sendMessage("§aAdded §61 §acoin!");
            }

            return true;
        }

        sender.sendMessage("§cPlayer only command.");
        return true;
    }
}
