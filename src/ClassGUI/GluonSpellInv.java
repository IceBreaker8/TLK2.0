package ClassGUI;

import ClassUpgrades.SpellStringAdder;
import Classes.Gluon;
import ItemConstructorClass.ItemConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public class GluonSpellInv extends AaClassSpellShowcase implements Listener {


    @Override
    public Inventory getSpellGUI(Player player) {
        Inventory newInv = GluonInv(player);
        fillInvWithGlass(newInv, 26);
        return skillSet(newInv, player);
    }

    SpellStringAdder sp = new SpellStringAdder();

    public void openClassInv(Player p) {
        p.openInventory(GluonInv(p));
    }


    public ItemStack GluonWeapon() {
        ItemConstructor itC = new ItemConstructor();
        ArrayList<String> lore5 = new ArrayList<String>();
        lore5.add(ChatColor.GRAY + "A magnified gluon found in a");
        lore5.add(ChatColor.GRAY + "meteorite that attaches quarks");
        lore5.add(ChatColor.GRAY + "together.");
        lore5.add("");
        lore5.add(ChatColor.DARK_RED + "> Class Item <");
        ItemStack item5 = itC.MakeItem(ChatColor.WHITE + "" + ChatColor.BOLD + "Gluon Link", Material.END_ROD, lore5);
        return item5;
    }

    public Inventory GluonInv(HumanEntity p) {
        Gluon r = new Gluon();
        ItemConstructor itC = new ItemConstructor();
        Inventory OccGUI = Bukkit.getServer().createInventory(null, 9 * 4,
                ChatColor.WHITE + "" + ChatColor.BOLD + "Gluon Class");
        ///////////////////////////////////// Occultist class item //////////////////////////////////////////////////////
        fillInvWithGlass(OccGUI, 0);
        Player player = (Player) p;
        //death star spell
        ArrayList<String> lore1 = new ArrayList<String>();
        lore1.add("");
        lore1.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + r.Mana1);
        lore1.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Right-Right");
        lore1.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Creates a gravitational");
        lore1.add(ChatColor.WHITE + "pulse that pulls your targetted enemy towards");
        lore1.add(ChatColor.WHITE + "you. If the enemy is hit by you while airborne,");
        lore1.add(ChatColor.WHITE + "he will receive " + r.spell1DMG + sp.dmgAdder(player) + " DMG and get launched");
        lore1.add(ChatColor.WHITE + "in the direction you looking at.");
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta pm = (PotionMeta) potion.getItemMeta();
        pm.setBasePotionData(new PotionData(PotionType.WATER));
        potion.setItemMeta(pm);
        ItemStack item1 = itC.MakeItemByStack(ChatColor.WHITE + "" + ChatColor.BOLD + "First Spell : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.spell1Name, potion, lore1);
        OccGUI.setItem(10, item1);

        // possession spell
        ArrayList<String> lore2 = new ArrayList<String>();
        lore2.add("");
        lore2.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + r.Mana2);
        lore2.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Left-Right");
        lore2.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Create a gravity shield box");
        lore2.add(ChatColor.WHITE + "that makes allies invulnerable to damage inside");
        lore2.add(ChatColor.WHITE + "it for " + (int) (r.spell2DMG / 20) + " seconds.");
        ItemStack Hpotion = new ItemStack(Material.POTION);
        PotionMeta pm1 = (PotionMeta) Hpotion.getItemMeta();
        pm1.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
        Hpotion.setItemMeta(pm1);
        ItemStack item2 = itC.MakeItemByStack(ChatColor.WHITE + "" + ChatColor.BOLD + "Second Spell : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.spell2Name, Hpotion, lore2);
        OccGUI.setItem(11, item2);

        // intimidation spell
        ArrayList<String> lore3 = new ArrayList<String>();
        lore3.add("");
        lore3.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + r.Mana3);
        lore3.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Left-Left");
        lore3.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Launch yourself forward,");
        lore3.add(ChatColor.WHITE + "when you land, smash the ground , knocking back");
        lore3.add(ChatColor.WHITE + "and damaging nearby enemies for " + r.spell3DMG + sp.dmgAdder(player) + " DMG.");
        ItemStack Lpotion = new ItemStack(Material.POTION);
        PotionMeta pm2 = (PotionMeta) Lpotion.getItemMeta();
        pm2.setBasePotionData(new PotionData(PotionType.JUMP));
        Lpotion.setItemMeta(pm2);
        ItemStack item3 = itC.MakeItemByStack(ChatColor.WHITE + "" + ChatColor.BOLD + "Third Spell : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.spell3Name, Lpotion, lore3);
        OccGUI.setItem(12, item3);

        //Armageddon spell
        ArrayList<String> lore4 = new ArrayList<String>();
        lore4.add("");
        lore4.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + "20");
        lore4.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Right-Left");
        lore4.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Revert gravity around you");
        lore4.add(ChatColor.WHITE + "for " + (int) (r.ultDMG / 20) + " seconds, launching all nearby");
        lore4.add(ChatColor.WHITE + "enemies and blocks in the air.");
        lore4.add(ChatColor.WHITE + "You are immune to damage and can't jump");
        lore4.add(ChatColor.WHITE + "during this spell.");
        ItemStack Epotion = new ItemStack(Material.POTION);
        PotionMeta pm3 = (PotionMeta) Epotion.getItemMeta();
        pm3.setBasePotionData(new PotionData(PotionType.INVISIBILITY));
        Epotion.setItemMeta(pm3);
        ItemStack item4 = itC.MakeItemByStack(ChatColor.GOLD + "" + ChatColor.BOLD + "Ultimate : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.ultName, Epotion, lore4);
        OccGUI.setItem(13, item4);

        //Rift expansion spell
        OccGUI.setItem(15, GluonWeapon());

        completeGUI(OccGUI);
        // open inv
        return OccGUI;


    }

    @EventHandler
    public void classChoosingEvent(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        Player player = (Player) p;
        if (!e.getInventory().getTitle().contains("Gluon Class")) {
            return;
        }
        e.setCancelled(true);
        if (e.getCurrentItem() == null)
            return;
        if (!e.getCurrentItem().hasItemMeta())
            return;
        if (!e.getCurrentItem().getItemMeta().hasDisplayName())
            return;

        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Class Menu")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            AaClassChooseGUI c = new AaClassChooseGUI();
            c.openClassGUI(player);
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("CONFIRM")) {
            AaClassHashMapChoosing ch = new AaClassHashMapChoosing();
            ch.chooseClass(player, new Gluon());
            if (!p.getInventory().contains(GluonWeapon())) {
                p.getInventory().clear();
                p.getInventory().addItem(GluonWeapon());
            }
            return;
        }

    }
}
