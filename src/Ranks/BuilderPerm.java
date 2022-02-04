package Ranks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class BuilderPerm implements Listener {

    @EventHandler
    public void onPlaceBlocks(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);
        RankingSystem r = new RankingSystem();
        if (r.getRank(p) != Rank.BUILDER) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);
        RankingSystem r = new RankingSystem();
        if (r.getRank(p) != Rank.BUILDER) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(PlayerBucketEmptyEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);
        RankingSystem r = new RankingSystem();
        if (r.getRank(p) != Rank.BUILDER) {
            e.setCancelled(true);
        }
    }


}
