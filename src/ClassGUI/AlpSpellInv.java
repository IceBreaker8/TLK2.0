package ClassGUI;

import ClassUpgrades.NBTEditor;
import ClassUpgrades.SpellStringAdder;
import Classes.Alp;
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

public class AlpSpellInv extends AaClassSpellShowcase implements Listener {

    @Override
    public Inventory getSpellGUI(Player player) {
        Inventory newInv = alpInv(player);
        fillInvWithGlass(newInv, 26);
        return skillSet(newInv, player);
    }

    public void openClassInv(Player p) {
        p.openInventory(alpInv(p));
    }

    Alp r = new Alp();
    SpellStringAdder sp = new SpellStringAdder();

    public ItemStack alpWeapon() {
        ItemConstructor itC = new ItemConstructor();
        ArrayList<String> lore5 = new ArrayList<String>();
        lore5.add(ChatColor.GRAY + "This ancient white sword,");
        lore5.add(ChatColor.GRAY + "forged from pure iron and");
        lore5.add(ChatColor.GRAY + "white dragon tears, glows");
        lore5.add(ChatColor.GRAY + "in the hands of its holder.");
        lore5.add("");
        lore5.add(ChatColor.DARK_RED + "> Class Item <");
        ItemStack item = itC.MakeItem(r.classColor + "" + ChatColor.BOLD + "Ancient Sword", Material.IRON_SWORD, lore5);
        item = NBTEditor.setItemTag(item, "generic.attackDamage", "AttributeModifiers", null, "AttributeName");
        item = NBTEditor.setItemTag(item, "generic.attackDamage", "AttributeModifiers", 0, "Name");
        return item;
    }

    public Inventory alpInv(HumanEntity p) {
        ItemConstructor itC = new ItemConstructor();
        Inventory OccGUI = Bukkit.getServer().createInventory(null, 9 * 4,
                r.classColor + "" + ChatColor.BOLD + "Alp Class");
        ///////////////////////////////////// Occultist class item //////////////////////////////////////////////////////
        fillInvWithGlass(OccGUI, 0);
        Player player = (Player) p;
        //death star spell
        ArrayList<String> lore1 = new ArrayList<String>();
        lore1.add("");
        lore1.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + r.Mana1);
        lore1.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Right-Right");
        lore1.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Throw your dagger");
        lore1.add(ChatColor.WHITE + "straight forward, if it hits an enemy, it deals");
        lore1.add(ChatColor.WHITE + "" + r.spell1DMG + sp.dmgAdder(player) + " DMG, else if it hits the ground, you can repick");
        lore1.add(ChatColor.WHITE + "it again to restore your lost mana.");
        lore1.add(ChatColor.WHITE + "You must repick your thrown dagger quickly before");
        lore1.add(ChatColor.WHITE + "it disappears.");
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
        lore2.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Lunge towards your next");
        lore2.add(ChatColor.WHITE + "predicted location, dealing " + r.spell2DMG + sp.dmgAdder(player) + " DMG to all");
        lore2.add(ChatColor.WHITE + "enemies contacted when you land.");
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
        lore3.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Call your white horse to");
        lore3.add(ChatColor.WHITE + "carry you off the battlefield for " + (int) (r.spell3DMG / 20) + " seconds.");
        lore3.add(ChatColor.WHITE + "The white horse is invulnerable to damage.");
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
        lore4.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Unleash your rage, increasing");
        lore4.add(ChatColor.WHITE + "your speed and damage for " + (int) (r.ultDMG / 20) + " seconds,");
        lore4.add(ChatColor.WHITE + "drastically damaging enemies with your");
        lore4.add(ChatColor.WHITE + "sharpened sword.");
        lore4.add(ChatColor.WHITE + "You are resistant to damage during rage.");
        ItemStack Epotion = new ItemStack(Material.POTION);
        PotionMeta pm3 = (PotionMeta) Epotion.getItemMeta();
        pm3.setBasePotionData(new PotionData(PotionType.INVISIBILITY));
        Epotion.setItemMeta(pm3);
        ItemStack item4 = itC.MakeItemByStack(ChatColor.GOLD + "" + ChatColor.BOLD + "Ultimate : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.ultName, Epotion, lore4);
        OccGUI.setItem(13, item4);

        OccGUI.setItem(15, alpWeapon());
        completeGUI(OccGUI);
        // open inv
        return OccGUI;


    }

    @EventHandler
    public void classChoosingEvent(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        Player player = (Player) p;
        if (!e.getInventory().getTitle().contains("Alp Class")) {
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
            ch.chooseClass(player, new Alp());
            if (!p.getInventory().contains(alpWeapon())) {
                p.getInventory().clear();
                p.getInventory().addItem(alpWeapon());
            }
            return;
        }
    }
}

