package ClassUpgrades;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SkillPointsChecker extends SkillPointsObtainer {


    public static ChatColor canUpgrade(Player p, Integer checker, HashMap toUpgrade) {
        UpgradePurchase u = new UpgradePurchase();
        if (u.passedUpgradeLimits(p, toUpgrade)) {
            return ChatColor.DARK_RED;
        }
        if (getSkillPoints(p) >= checker) {
            return ChatColor.DARK_GREEN;
        }
        return ChatColor.DARK_RED;
    }

}
