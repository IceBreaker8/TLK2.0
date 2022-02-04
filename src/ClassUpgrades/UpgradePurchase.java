package ClassUpgrades;

import ClassGUI.AaClassHashMapChoosing;
import GameMechanics.ForgeryMech;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;

public class UpgradePurchase extends SkillPointsCore implements Listener {

    public static int dmgUpgradeLimit = 9;
    public static int manaUpgradeLimit = 3;
    public static int ultUpgradeLimit = 3;
    public static int healthUpgradeLimit = 10;
    public static int miningUpgradeLimit = 3;

    public boolean canPurchase(Player p, Integer upgradeReq) {
        if (getSkillPoints(p) < upgradeReq) {
            p.sendMessage(ChatColor.RED + "You still need more skill points for this upgrade!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return false;
        }
        return true;
    }

    public boolean passedUpgradeLimits(Player p, HashMap toUpgrade) {
        if (toUpgrade == Health) {
            if (getHpValue(p) >= healthUpgradeLimit) {
                return true;
            }
        }
        if (toUpgrade == Damage) {
            if (getDMGValue(p) >= dmgUpgradeLimit) {
                return true;
            }
        }
        if (toUpgrade == Mana) {
            if (getManaValue(p) >= (manaUpgradeLimit - 1)) {
                return true;
            }
        }
        if (toUpgrade == UltCharge) {
            if (getUltValue(p) >= (ultUpgradeLimit - 1)) {
                return true;
            }
        }
        if (toUpgrade == MiningLuck) {
            if (getMiningValue(p) >= miningUpgradeLimit) {
                return true;
            }
        }
        return false;
    }

    public String passedLimitString(Player p, HashMap hashMap) {
        if (passedUpgradeLimits(p, hashMap)) {
            return ChatColor.DARK_RED + " [Limit Reached]";
        }
        return "";
    }

    @EventHandler
    public void classChoosingEvent(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        if (!e.getInventory().getTitle().contains("Class")) {
            return;
        }
        if (e.getCurrentItem() == null)
            return;
        if (!e.getCurrentItem().hasItemMeta())
            return;
        if (!e.getCurrentItem().getItemMeta().hasDisplayName())
            return;
        e.setCancelled(true);
        AaClassHashMapChoosing c = new AaClassHashMapChoosing();
        Player player = (Player) p;
        AaClassHashMapChoosing choosing = new AaClassHashMapChoosing();
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Health")) {

            if (!ForgeryMech.diamondVerif.containsKey(p.getUniqueId())) {
                ((Player) p).playSound(p.getLocation(),Sound.BLOCK_ANVIL_PLACE , 5, 1);
                p.sendMessage(ChatColor.RED + "You need to upgrade your weapon with the diamond essence first!");
                return;
            }
            if (passedUpgradeLimits(player, Health)) {
                player.sendMessage(ChatColor.RED + "You have reached the upgrade limit for health!");
                player.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
                return;
            }
            if (canPurchase(player, healthReq)) {
                player.getOpenInventory().close();
                Health.put(player, getHpValue(player) + 1);
                p.setMaxHealth(20 + Health.get(player));
                removeSkillPoints(player, healthReq);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, 1);
                p.openInventory(choosing.getTlkClass(player).getClassInv().getSpellGUI(player));
                return;

            }
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Mana")) {
            if (!ForgeryMech.goldVerif.containsKey(p.getUniqueId())) {
                ((Player) p).playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 5, 1);
                p.sendMessage(ChatColor.RED + "You need to upgrade your weapon with the gold essence first!");
                return;
            }
            if (passedUpgradeLimits(player, Mana)) {
                player.sendMessage(ChatColor.RED + "You have reached the upgrade limit for mana!");
                player.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
                return;
            }
            if (canPurchase(player, manaReq)) {
                player.getOpenInventory().close();
                Mana.put(player, getManaValue(player) + 1);
                removeSkillPoints(player, manaReq);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, 1);
                p.openInventory(choosing.getTlkClass(player).getClassInv().getSpellGUI(player));
                return;

            }
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Spell Damage")) {
            if (!ForgeryMech.ironVerif.containsKey(p.getUniqueId())) {
                ((Player) p).playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 5, 1);
                p.sendMessage(ChatColor.RED + "You need to upgrade your weapon with the iron essence first!");
                return;
            }
            if (passedUpgradeLimits(player, Damage)) {
                player.sendMessage(ChatColor.RED + "You have reached the upgrade limit for spell damage!");
                player.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
                return;
            }
            if (canPurchase(player, dmgReq)) {
                player.getOpenInventory().close();
                Damage.put(player, getDMGValue(player) + 1);
                removeSkillPoints(player, dmgReq);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, 1);
                p.openInventory(choosing.getTlkClass(player).getClassInv().getSpellGUI(player));
                return;
            }
        }

        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Ult Charge")) {
            if (!ForgeryMech.diamondVerif.containsKey(p.getUniqueId())) {
                ((Player) p).playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 5, 1);
                p.sendMessage(ChatColor.RED + "You need to upgrade your weapon with the diamond essence first!");
                return;
            }
            if (passedUpgradeLimits(player, UltCharge)) {
                player.sendMessage(ChatColor.RED + "You have reached the upgrade limit for ult charge!");
                player.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
                return;
            }
            if (canPurchase(player, ultReq)) {
                player.getOpenInventory().close();
                removeSkillPoints(player, ultReq);
                UltCharge.put(player, getUltValue(player) + 1);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, 1);
                p.openInventory(choosing.getTlkClass(player).getClassInv().getSpellGUI(player));
                return;
            }
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Mining Luck")) {
            if (!ForgeryMech.ironVerif.containsKey(p.getUniqueId())) {
                ((Player) p).playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 5, 1);
                p.sendMessage(ChatColor.RED + "You need to upgrade your weapon with the iron essence first!");
                return;
            }
            if (passedUpgradeLimits(player, MiningLuck)) {
                player.sendMessage(ChatColor.RED + "You have reached the upgrade limit for mining luck!");
                player.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
                return;
            }
            if (canPurchase(player, miningReq)) {
                player.getOpenInventory().close();
                removeSkillPoints(player, miningReq);
                MiningLuck.put(player, getMiningValue(player) + 1);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, 1);
                p.openInventory(choosing.getTlkClass(player).getClassInv().getSpellGUI(player));
                return;
            }
        }
    }
}
