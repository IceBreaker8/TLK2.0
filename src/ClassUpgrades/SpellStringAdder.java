package ClassUpgrades;

import ClassGUI.AaClassHashMapChoosing;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static ClassUpgrades.SkillPointsCore.getDMGValue;

public class SpellStringAdder {

    AaClassHashMapChoosing player = new AaClassHashMapChoosing();

    public String dmgAdder(Player p) {
        if (getDMGValue(p) > 0) {
            return player.getTlkClass(p).classColor + " + (" +
                    ChatColor.GOLD + getDMGValue(p) +
                    player.getTlkClass(p).classColor + ")" + ChatColor.WHITE;
        }
        return "";
    }
}
