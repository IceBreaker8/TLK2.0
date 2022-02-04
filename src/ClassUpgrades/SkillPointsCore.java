package ClassUpgrades;

import ItemConstructorClass.ItemConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class SkillPointsCore extends SkillPointsObtainer {
    public static HashMap<Player, Integer> Damage = new HashMap<>();
    public static HashMap<Player, Integer> Mana = new HashMap<>();
    public static HashMap<Player, Integer> Health = new HashMap<>();
    public static HashMap<Player, Integer> UltCharge = new HashMap<>();
    public static HashMap<Player, Integer> MiningLuck = new HashMap<>();
    ItemConstructor itC = new ItemConstructor();

    public static int   dmgReq = 2;
    public static int manaReq = 10;
    public static int ultReq = 10;
    public static int healthReq = 3;
    public static int miningReq = 1;

    public static ArrayList<String> addLore(Player p, String string1, String string2, String string3, Integer toUpgrade, HashMap u) {
        ArrayList<String> lore = new ArrayList<>();
        UpgradePurchase upgradePurchase = new UpgradePurchase();
        lore.add(string2);
        lore.add("");
        lore.add(string1);
        lore.add(SkillPointsChecker.canUpgrade(p, toUpgrade, u) + "Requires " + string3 + upgradePurchase.passedLimitString(p, u));
        return lore;
    }

    public static int getDMGValue(Player p) {
        if (!Damage.containsKey(p)) {
            Damage.put(p, 0);
            return 0;
        }
        return Damage.get(p);
    }

    public static int getHpValue(Player p) {
        if (!Health.containsKey(p)) {
            Health.put(p, 0);
            return 0;
        }
        return Health.get(p);
    }

    public static int getManaValue(Player p) {
        if (!Mana.containsKey(p)) {
            Mana.put(p, 0);
            return 0;
        }
        return Mana.get(p);
    }

    public static int getUltValue(Player p) {
        if (!UltCharge.containsKey(p)) {
            UltCharge.put(p, 0);
            return 0;
        }
        return UltCharge.get(p);
    }

    public static int getMiningValue(Player p) {
        if (!MiningLuck.containsKey(p)) {
            MiningLuck.put(p, 0);
            return 0;
        }
        return MiningLuck.get(p);
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static String getDamage(Player p) {
        return ChatColor.WHITE + "Spell Damage: " + ChatColor.GRAY + "Default " + ChatColor.RED + "+ (" + ChatColor.GOLD + getDMGValue(p) + ChatColor.RED + ")";
    }

    public static String getManaString(Player p) {
        return ChatColor.WHITE + "Mana: " + ChatColor.GRAY + "1 " + ChatColor.AQUA + "+ (" + ChatColor.GOLD + ChatColor.GOLD + getManaValue(p) + ChatColor.AQUA + ")";
    }

    public static String getHealth(Player p) {
        return ChatColor.WHITE + "Health: " + ChatColor.GRAY + "20 " + ChatColor.GREEN + "+ (" + ChatColor.GOLD + getHpValue(p) + ChatColor.GREEN + ")";
    }


    public static String getUltCharge(Player p) {
        return ChatColor.WHITE + "Ult Charge: " + ChatColor.GRAY + "1 " + ChatColor.WHITE + "+ (" + ChatColor.GOLD + getUltValue(p) + ChatColor.WHITE + ")";
    }

    public static String getMiningLuck(Player p) {
        return ChatColor.WHITE + "Mining Luck: " + ChatColor.GRAY + "Default " + ChatColor.DARK_PURPLE + "+ (" + ChatColor.GOLD + getMiningValue(p) + ChatColor.DARK_PURPLE + ")";
    }

}