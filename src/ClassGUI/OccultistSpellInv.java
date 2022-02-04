package ClassGUI;

import ClassUpgrades.SpellStringAdder;
import Classes.Occultist;
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

public class OccultistSpellInv extends AaClassSpellShowcase implements Listener {

    @Override
    public Inventory getSpellGUI(Player player) {
        Inventory newInv = OccInv(player);
        fillInvWithGlass(newInv, 26);
        return skillSet(newInv, player);
    }

    SpellStringAdder sp = new SpellStringAdder();

    public void openClassInv(Player p) {
        p.openInventory(OccInv(p));
    }

    public ItemStack OccultistWeapon() {
        ItemConstructor itC = new ItemConstructor();
        ArrayList<String> lore5 = new ArrayList<String>();
        lore5.add(ChatColor.GRAY + "This weapon is usually used in voodoo");
        lore5.add(ChatColor.GRAY + "rituals.");
        lore5.add("");
        lore5.add(ChatColor.DARK_RED + "> Class Item <");
        ItemStack item5 = itC.MakeItem(ChatColor.DARK_RED + "" + ChatColor.BOLD + "HexTotem", Material.TOTEM, lore5);
        return item5;
    }

    public Inventory OccInv(HumanEntity p) {
        Occultist r = new Occultist();
        ItemConstructor itC = new ItemConstructor();
        Inventory OccGUI = Bukkit.getServer().createInventory(null, 9 * 4,
                ChatColor.DARK_RED + "" + ChatColor.BOLD + "Occultist Class");
        ///////////////////////////////////// Occultist class item //////////////////////////////////////////////////////
        fillInvWithGlass(OccGUI, 0);
        Player player = (Player) p;

        //death star spell
        ArrayList<String> lore1 = new ArrayList<String>();
        lore1.add("");
        lore1.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + r.Mana1);
        lore1.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Right-Right");
        lore1.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Fire an orb in your direction,");
        lore1.add(ChatColor.WHITE + "if it hits a player, he gets withered, darkened ");
        lore1.add(ChatColor.WHITE + "for 2 seconds and damaged for 6 " + sp.dmgAdder(player) + " DMG ; else if it");
        lore1.add(ChatColor.WHITE + "hits a block, cause an explosion of dark matter ");
        lore1.add(ChatColor.WHITE + "dealing " + r.spell1DMG + " damage to nearby players. ");
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
        lore2.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Possess an enemy and");
        lore2.add(ChatColor.WHITE + "control him for " + (int) (r.spell2DMG / 20) + " seconds.");
        lore2.add(ChatColor.WHITE + "Possessed enemies won't be able to cast spells,");
        lore2.add(ChatColor.WHITE + "and are invulnerable.");

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
        lore3.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Intimidate enemies by");
        lore3.add(ChatColor.WHITE + "inhaling their fear using your terrifying scream, ");
        lore3.add(ChatColor.WHITE + "removing " + r.spell3DMG + " mana from each ");
        lore3.add(ChatColor.WHITE + "enemy, restoring 2 hp for each enemies.");
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
        lore4.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Summon the old dark souls");
        lore4.add(ChatColor.WHITE + "from the skies to hunt players in a radius of");
        lore4.add(ChatColor.WHITE + "12 blocks, summoning thunder strikes each 3");
        lore4.add(ChatColor.WHITE + "seconds for 15 seconds , dealing " + r.ultDMG + sp.dmgAdder(player) + " damage");
        lore4.add(ChatColor.WHITE + "to enemies , affecting him with darkness");
        lore4.add(ChatColor.WHITE + "for 1 second.");
        ItemStack Epotion = new ItemStack(Material.POTION);
        PotionMeta pm3 = (PotionMeta) Epotion.getItemMeta();
        pm3.setBasePotionData(new PotionData(PotionType.INVISIBILITY));
        Epotion.setItemMeta(pm3);
        ItemStack item4 = itC.MakeItemByStack(ChatColor.GOLD + "" + ChatColor.BOLD + "Ultimate : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.ultName, Epotion, lore4);
        OccGUI.setItem(13, item4);

        //Rift expansion spell
        OccGUI.setItem(15, OccultistWeapon());
        completeGUI(OccGUI);
        // open inv
        return OccGUI;


    }

    @EventHandler
    public void classChoosingEvent(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        Player player = (Player) p;
        if (!e.getInventory().getTitle().contains("Occultist Class")) {
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
            ch.chooseClass(player, new Occultist());
            if (!p.getInventory().contains(OccultistWeapon())) {
                p.getInventory().clear();
                p.getInventory().addItem(OccultistWeapon());
            }
            return;
        }

    }

}
