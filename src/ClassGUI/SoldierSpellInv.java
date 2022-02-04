package ClassGUI;

import ClassUpgrades.SpellStringAdder;
import Classes.Soldier;
import Classes.Verun;
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

public class SoldierSpellInv extends AaClassSpellShowcase implements Listener {
    @Override
    public Inventory getSpellGUI(Player player) {
        Inventory newInv = soldierInv(player);
        fillInvWithGlass(newInv, 26);
        return skillSet(newInv, player);
    }

    public void openClassInv(Player p) {
        p.openInventory(soldierInv(p));
    }

    Soldier r = new Soldier();
    SpellStringAdder sp = new SpellStringAdder();

    public ItemStack soldierWeapon() {
        ItemConstructor itC = new ItemConstructor();
        ArrayList<String> lore5 = new ArrayList<String>();
        lore5.add(ChatColor.GRAY + "" + ChatColor.MAGIC + "A class made by Selvut, a ");
        lore5.add(ChatColor.GRAY + "" + ChatColor.MAGIC + "A class made by Selvut,");
        lore5.add(ChatColor.GRAY + "" + ChatColor.MAGIC + "A class made by Selvut, ");
        lore5.add("");
        lore5.add(ChatColor.DARK_RED + "> Class Item <");
        ItemStack item = itC.MakeItem(r.classColor + "" + ChatColor.BOLD + r.weaponName, Material.DIAMOND_AXE, lore5);
        return item;
    }

    public Inventory soldierInv(HumanEntity p) {
        ItemConstructor itC = new ItemConstructor();
        Inventory OccGUI = Bukkit.getServer().createInventory(null, 9 * 4,
                r.classColor + "" + ChatColor.BOLD + "Soldier Class");
        ///////////////////////////////////// Occultist class item //////////////////////////////////////////////////////
        fillInvWithGlass(OccGUI, 0);
        Player player = (Player) p;
        //death star spell
        ArrayList<String> lore1 = new ArrayList<String>();
        lore1.add("");
        lore1.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + r.Mana1);
        lore1.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Right-Right");
        lore1.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Deal " + r.spell1DMG + " damage and zero knockback");
        lore1.add(ChatColor.WHITE + "to enemies within a 5 block radius of a point set 3 blocks");
        lore1.add(ChatColor.WHITE + "in front of the caster. Enemies that are hit by the initial strike");
        lore1.add(ChatColor.WHITE + "will then be hit again for " + r.spell1DMG + sp.dmgAdder(player) + " damage 0.4 seconds afterwards,");
        lore1.add(ChatColor.WHITE + "taking 5 blocks worth of knockback.");
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
        lore2.add(ChatColor.RED + "Description: " + ChatColor.WHITE + " Jump forward 5 blocks and up 2,");
        lore2.add(ChatColor.WHITE + "then after hitting a block, make short 3 block hops in your");
        lore2.add(ChatColor.WHITE + "direction, 6 times.");
        lore2.add(ChatColor.WHITE + "After you land from the first jump, a 4 block radius around ");
        lore2.add(ChatColor.WHITE + "where you landed will take " + r.spell2DMG + sp.dmgAdder(player) + " damage and receive Weakness, slowness,");
        lore2.add(ChatColor.WHITE + "and be unable to jump for 1 second, as well as being knocked back 4 blocks. ");

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
        lore3.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Hit enemies within a one block radius of");
        lore3.add(ChatColor.WHITE + "a line of ‘unraveling’ and piercing particles 8 blocks,at");
        lore3.add(ChatColor.WHITE + "at 2 blocks per 0.1 seconds for " + r.spell3DMG + " damage.");
        lore3.add(ChatColor.WHITE + "0.5 seconds after casting, the player lunges 8 blocks in");
        lore3.add(ChatColor.WHITE + "the direction of the enemy affected last(straight forward");
        lore3.add(ChatColor.WHITE + "on a miss),, dealing " + (r.spell3DMG + 3) + sp.dmgAdder(player) + " damage and inflicting 8 blocks");
        lore3.add(ChatColor.WHITE + "of knockback to any enemy contacted.");
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
        lore4.add(ChatColor.RED + "Description: " + ChatColor.WHITE + " Create a slowly expanding,");
        lore4.add(ChatColor.WHITE + "1-block tall shockwave originating from where you cast.");
        lore4.add(ChatColor.WHITE + "Expands by 1 block in all directions every 0.5 seconds for");
        lore4.add(ChatColor.WHITE + "4 seconds. Deals " + r.ultDMG + sp.dmgAdder(player) + " damage per hit and knocks back enemies");
        lore4.add(ChatColor.WHITE + "caught in the shockwave by 2 blocks, while giving allies");
        lore4.add(ChatColor.WHITE + "who touch the wave strength for 10 seconds.");
        ItemStack Epotion = new ItemStack(Material.POTION);
        PotionMeta pm3 = (PotionMeta) Epotion.getItemMeta();
        pm3.setBasePotionData(new PotionData(PotionType.INVISIBILITY));
        Epotion.setItemMeta(pm3);
        ItemStack item4 = itC.MakeItemByStack(ChatColor.GOLD + "" + ChatColor.BOLD + "Ultimate : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.ultName, Epotion, lore4);
        OccGUI.setItem(13, item4);

        OccGUI.setItem(15, soldierWeapon());
        completeGUI(OccGUI);
        // open inv
        return OccGUI;


    }

    @EventHandler
    public void classChoosingEvent(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        Player player = (Player) p;
        if (!e.getInventory().getTitle().contains("Soldier Class")) {
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
            ch.chooseClass(player, new Soldier());
            if (!p.getInventory().contains(soldierWeapon())) {
                p.getInventory().clear();
                p.getInventory().addItem(soldierWeapon());
            }
            return;
        }
    }
}
