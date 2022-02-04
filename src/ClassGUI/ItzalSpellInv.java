package ClassGUI;

import ClassUpgrades.NBTEditor;
import ClassUpgrades.SpellStringAdder;
import Classes.Alp;
import Classes.Itzal;
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

public class ItzalSpellInv extends AaClassSpellShowcase implements Listener {
    @Override
    public Inventory getSpellGUI(Player player) {
        Inventory newInv = itzalInv(player);
        fillInvWithGlass(newInv, 26);
        return skillSet(newInv, player);
    }

    public void openClassInv(Player p) {
        p.openInventory(itzalInv(p));
    }

    Itzal r = new Itzal();
    SpellStringAdder sp = new SpellStringAdder();

    public ItemStack itzalWeapon() {
        ItemConstructor itC = new ItemConstructor();
        ArrayList<String> lore5 = new ArrayList<String>();
        lore5.add(ChatColor.GRAY + "A void shard accidentally found ");
        lore5.add(ChatColor.GRAY + "by Riftors during their expedition");
        lore5.add(ChatColor.GRAY + "to the void dimension.");
        lore5.add("");
        lore5.add(ChatColor.DARK_RED + "> Class Item <");
        ItemStack item = itC.MakeItem(r.classColor + "" + ChatColor.BOLD + r.weaponName, Material.FIREWORK_CHARGE, lore5);
        return item;
    }

    public Inventory itzalInv(HumanEntity p) {
        ItemConstructor itC = new ItemConstructor();
        Inventory OccGUI = Bukkit.getServer().createInventory(null, 9 * 4,
                r.classColor + "" + ChatColor.BOLD + "Itzal Class");
        ///////////////////////////////////// Occultist class item //////////////////////////////////////////////////////
        fillInvWithGlass(OccGUI, 0);
        Player player = (Player) p;
        //death star spell
        ArrayList<String> lore1 = new ArrayList<String>();
        lore1.add("");
        lore1.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + r.Mana1);
        lore1.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Right-Right");
        lore1.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Dash 16 blocks forward");
        lore1.add(ChatColor.WHITE + "dealing " + r.spell1DMG + sp.dmgAdder(player) + " DMG to all enemies contacted.");
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta pm = (PotionMeta) potion.getItemMeta();
        pm.setBasePotionData(new PotionData(PotionType.WATER));
        potion.setItemMeta(pm);
        ItemStack item1 = itC.MakeItemByStack(ChatColor.WHITE + "" + ChatColor.BOLD + "First Spell : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.spell1Name, potion, lore1);
        OccGUI.setItem(10, item1);

        // possession spell
        ArrayList<String> lore2 = new ArrayList<>();
        lore2.add("");
        lore2.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + r.Mana2);
        lore2.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Left-Right");
        lore2.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Throw a smoke bomb");
        lore2.add(ChatColor.WHITE + "underneath you, giving you speed and");
        lore2.add(ChatColor.WHITE + "invisibility for " + (int) (r.spell2DMG / 20) + " seconds.");
        ItemStack Hpotion = new ItemStack(Material.POTION);
        PotionMeta pm1 = (PotionMeta) Hpotion.getItemMeta();
        pm1.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
        Hpotion.setItemMeta(pm1);
        ItemStack item2 = itC.MakeItemByStack(ChatColor.WHITE + "" + ChatColor.BOLD + "Second Spell : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.spell2Name, Hpotion, lore2);
        OccGUI.setItem(11, item2);

        // intimidation spell
        ArrayList<String> lore3 = new ArrayList<>();
        lore3.add("");
        lore3.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + r.Mana3);
        lore3.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Left-Left");
        lore3.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Summon a black circle in your");
        lore3.add(ChatColor.WHITE + "location for " + (int) (r.spell3DMG / 20) + " seconds, all allies inside it");
        lore3.add(ChatColor.WHITE + "will get their wounds healed for 3 health ");
        lore3.add(ChatColor.WHITE + "points each second, and enemies will get");
        lore3.add(ChatColor.WHITE + "3" + sp.dmgAdder(player) + " DMG per second.");
        ItemStack Lpotion = new ItemStack(Material.POTION);
        PotionMeta pm2 = (PotionMeta) Lpotion.getItemMeta();
        pm2.setBasePotionData(new PotionData(PotionType.JUMP));
        Lpotion.setItemMeta(pm2);
        ItemStack item3 = itC.MakeItemByStack(ChatColor.WHITE + "" + ChatColor.BOLD + "Third Spell : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.spell3Name, Lpotion, lore3);
        OccGUI.setItem(12, item3);

        //Armageddon spell
        ArrayList<String> lore4 = new ArrayList<>();
        lore4.add("");
        lore4.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + "20");
        lore4.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Right-Left");
        lore4.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Target all nearby enemies");
        lore4.add(ChatColor.WHITE + "in a radius of 20 blocks of your location");
        lore4.add(ChatColor.WHITE + "and perform a finisher on each of them.");
        lore4.add(ChatColor.WHITE + "Brutally teleport behind each target and");
        lore4.add(ChatColor.WHITE + "stab them with your dagger, each stab");
        lore4.add(ChatColor.WHITE + "deals " + r.ultDMG + sp.dmgAdder(player) + " DMG.");
        ItemStack Epotion = new ItemStack(Material.POTION);
        PotionMeta pm3 = (PotionMeta) Epotion.getItemMeta();
        pm3.setBasePotionData(new PotionData(PotionType.INVISIBILITY));
        Epotion.setItemMeta(pm3);
        ItemStack item4 = itC.MakeItemByStack(ChatColor.GOLD + "" + ChatColor.BOLD + "Ultimate : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.ultName, Epotion, lore4);
        OccGUI.setItem(13, item4);

        OccGUI.setItem(15, itzalWeapon());
        completeGUI(OccGUI);
        // open inv
        return OccGUI;


    }

    @EventHandler
    public void classChoosingEvent(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        Player player = (Player) p;
        if (!e.getInventory().getTitle().contains("Itzal Class")) {
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
            ch.chooseClass(player, new Itzal());
            if (!p.getInventory().contains(itzalWeapon())) {
                p.getInventory().clear();
                p.getInventory().addItem(itzalWeapon());
            }
            return;
        }
    }
}
