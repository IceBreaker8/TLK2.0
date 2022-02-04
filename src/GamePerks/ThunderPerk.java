package GamePerks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;

public class ThunderPerk extends Perks {
    public ThunderPerk() {
        perk = ChatColor.YELLOW + "" + ChatColor.BOLD + "ThunderStrike";
        className = "ThunderPerk";
    }

    @Override
    public void activatePerk(Entity p) {
        p.getWorld().strikeLightningEffect(p.getLocation());

    }
}
