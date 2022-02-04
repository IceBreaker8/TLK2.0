package ClassGUI;

import ClassUpgrades.SkillPointsCore;
import Classes.TlkClass;
import ItemConstructorClass.ItemConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public abstract class AaClassSpellShowcase extends TlkClass {
    private ItemConstructor itC = new ItemConstructor();
    private ArrayList<String> lore = new ArrayList<>();

    public abstract Inventory getSpellGUI(Player p);

    void completeGUI(Inventory OccGUI) {
        OccGUI.setItem(27, itC.MakeItem(ChatColor.WHITE + "" + ChatColor.BOLD + "Class Menu", Material.ARROW, lore));
        OccGUI.setItem(35, itC.MakeItem(ChatColor.GREEN + "" + ChatColor.BOLD + "CONFIRM", Material.EMERALD_BLOCK, lore));
    }

    Inventory skillSet(Inventory newInv, Player p) {
        newInv.setItem(27, itC.MakeItemByStack(ChatColor.RED + "" + ChatColor.BOLD + "Spell Damage",
                new ItemStack(Material.CONCRETE, 1, (byte) 14),
                SkillPointsCore.addLore(p, ChatColor.RED + "> Click to add 1 Damage Point <",
                        SkillPointsCore.getDamage(p),
                        SkillPointsCore.dmgReq + " skill points", SkillPointsCore.dmgReq, SkillPointsCore.Damage
                )));
        newInv.setItem(29, itC.MakeItemByStack(ChatColor.AQUA + "" + ChatColor.BOLD + "Mana",
                new ItemStack(Material.CONCRETE, 1, (byte) 3),
                SkillPointsCore.addLore(p, ChatColor.AQUA + "> Click to add 1 Mana Point <",
                        SkillPointsCore.getManaString(p),
                        SkillPointsCore.manaReq + " skill points", SkillPointsCore.manaReq, SkillPointsCore.Mana
                )));
        newInv.setItem(31, itC.MakeItemByStack(ChatColor.GREEN + "" + ChatColor.BOLD + "Health",
                new ItemStack(Material.CONCRETE, 1, (byte) 5),
                SkillPointsCore.addLore(p, ChatColor.GREEN + "> Click to add 1 Health Point <",
                        SkillPointsCore.getHealth(p),
                        SkillPointsCore.healthReq + " skill points", SkillPointsCore.healthReq, SkillPointsCore.Health
                )));
        newInv.setItem(33, itC.MakeItemByStack(ChatColor.WHITE + "" + ChatColor.BOLD + "Ult Charge",
                new ItemStack(Material.CONCRETE, 1, (byte) 0),
                SkillPointsCore.addLore(p, ChatColor.WHITE + "> Click to add 1 Ult Charge Point <",
                        SkillPointsCore.getUltCharge(p),
                        SkillPointsCore.ultReq + " skill points", SkillPointsCore.ultReq, SkillPointsCore.UltCharge
                )));
        newInv.setItem(35, itC.MakeItemByStack(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Mining Luck",
                new ItemStack(Material.CONCRETE, 1, (byte) 10),
                SkillPointsCore.addLore(p, ChatColor.DARK_PURPLE + "> Click to add 1 Mining Luck Point <",
                        SkillPointsCore.getMiningLuck(p),
                        SkillPointsCore.miningReq + " skill points", SkillPointsCore.miningReq, SkillPointsCore.MiningLuck
                )));
        return newInv;
    }

    void fillInvWithGlass(Inventory inv, int start) {
        for (int count = start; count < 36; count++) {
            ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
            inv.setItem(count, i);
        }
    }

}
