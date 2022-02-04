package ClassUpgrades;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class SkillPointsObtainer {


    public static HashMap<UUID, Integer> skillPoints = new HashMap<>();

    public static void addSkillPoints(Player p, Integer sp) {
        if (skillPoints.get(p.getUniqueId()) == null) {
            skillPoints.put(p.getUniqueId(), sp);
            return;
        }
        skillPoints.put(p.getUniqueId(), skillPoints.get(p.getUniqueId()) + sp);
    }

    public static Integer getSkillPoints(Player p) {
        if (SkillPointsObtainer.skillPoints.get(p.getUniqueId()) == null) {
            SkillPointsObtainer.skillPoints.put(p.getUniqueId(), 0);
            return 0;
        }
        return SkillPointsObtainer.skillPoints.get(p.getUniqueId());
    }

    public void removeSkillPoints(Player p, Integer sp) {
        if (skillPoints.get(p.getUniqueId()) == null) {
            skillPoints.put(p.getUniqueId(), 0);
            return;
        }
        skillPoints.put(p.getUniqueId(), skillPoints.get(p.getUniqueId()) - sp);
    }

}
