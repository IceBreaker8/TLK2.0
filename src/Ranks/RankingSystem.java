package Ranks;

import Main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class RankingSystem {
    public FileConfiguration config = Main.plugin.getConfig();

    public void setRank(Player player, Rank rank) {
        config.set("Rank." + player.getUniqueId(), rank.toString());
    }

    public Rank getRank(Player player) {
        String val = config.getString("Rank." + player.getUniqueId());
        return (val == null ? Rank.MEMBER : Rank.valueOf(val));
    }

    /*public boolean hasRank(Player player, Rank rank) {
        return (getRank(player).compareTo(rank) >= 0);
    }
*/
}
