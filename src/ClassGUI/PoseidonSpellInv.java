package ClassGUI;

import ClassUpgrades.SpellStringAdder;
import Classes.Poseidon;
import Classes.Itzal;
import Classes.Poseidon;
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

public class PoseidonSpellInv extends AaClassSpellShowcase implements Listener {
    @Override
    public Inventory getSpellGUI(Player player) {
        Inventory newInv = guardianInv(player);
        fillInvWithGlass(newInv, 26);
        return skillSet(newInv, player);
    }

    public void openClassInv(Player p) {
        p.openInventory(guardianInv(p));
    }

    Poseidon r = new Poseidon();
    SpellStringAdder sp = new SpellStringAdder();

    public ItemStack GuardianWeapon() {
        ItemConstructor itC = new ItemConstructor();
        ArrayList<String> lore5 = new ArrayList<String>();
        lore5.add(ChatColor.GRAY + "This prismarine shard was found");
        lore5.add(ChatColor.GRAY + "in a temple under the deep ocean");
        lore5.add(ChatColor.GRAY + "located south the island.");
        lore5.add("");
        lore5.add(ChatColor.DARK_RED + "> Class Item <");
        ItemStack item = itC.MakeItem(r.classColor + "" + ChatColor.BOLD + r.weaponName, Material.PRISMARINE_SHARD, lore5);
        return item;
    }

    public Inventory guardianInv(HumanEntity p) {
        ItemConstructor itC = new ItemConstructor();
        Inventory OccGUI = Bukkit.getServer().createInventory(null, 9 * 4,
                r.classColor + "" + ChatColor.BOLD + "Poseidon Class");
        ///////////////////////////////////// Occultist class item //////////////////////////////////////////////////////
        fillInvWithGlass(OccGUI, 0);
        Player player = (Player) p;
        //death star spell
        ArrayList<String> lore1 = new ArrayList<String>();
        lore1.add("");
        lore1.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + r.Mana1);
        lore1.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Right-Right");
        lore1.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Target an enemy and");
        lore1.add(ChatColor.WHITE + "summons a water spash under him that ");
        lore1.add(ChatColor.WHITE + "knocks him in the air, dealing " + r.spell1DMG + sp.dmgAdder(player) + " DMG.");
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
        lore2.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Splash all nearby");
        lore2.add(ChatColor.WHITE + "allies with your holy water and heal");
        lore2.add(ChatColor.WHITE + "them with " + r.spell2DMG + " HP.");
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
        lore3.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Flop three times");
        lore3.add(ChatColor.WHITE + "in the direction you are looking, damaging");
        lore3.add(ChatColor.WHITE + "all nearby enemies with " + r.spell3DMG + sp.dmgAdder(player) + " DMG on each");
        lore3.add(ChatColor.WHITE + "landing.");

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
        lore4.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Call the mighty");
        lore4.add(ChatColor.WHITE + "elder guardian from the ocean temple to");
        lore4.add(ChatColor.WHITE + "throw three devastating prismarine boulders");
        lore4.add(ChatColor.WHITE + "each second.");
        lore4.add(ChatColor.WHITE + "Each boulder, on enemy contact, explodes");
        lore4.add(ChatColor.WHITE + "and deals " + r.ultDMG + sp.dmgAdder(player) + " DMG to all nearby enemies.");
        ItemStack Epotion = new ItemStack(Material.POTION);
        PotionMeta pm3 = (PotionMeta) Epotion.getItemMeta();
        pm3.setBasePotionData(new PotionData(PotionType.INVISIBILITY));
        Epotion.setItemMeta(pm3);
        ItemStack item4 = itC.MakeItemByStack(ChatColor.GOLD + "" + ChatColor.BOLD + "Ultimate : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.ultName, Epotion, lore4);
        OccGUI.setItem(13, item4);

        OccGUI.setItem(15, GuardianWeapon());
        completeGUI(OccGUI);
        // open inv
        return OccGUI;


    }

    @EventHandler
    public void classChoosingEvent(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        Player player = (Player) p;
        if (!e.getInventory().getTitle().contains("Poseidon Class")) {
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
            ch.chooseClass(player, new Poseidon());
            if (!p.getInventory().contains(GuardianWeapon())) {
                p.getInventory().clear();
                p.getInventory().addItem(GuardianWeapon());
            }
            return;
        }
    }
}
