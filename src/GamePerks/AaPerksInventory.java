package GamePerks;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class AaPerksInventory implements Listener {
    public ArrayList<String> getLore(Player p, Perks perks) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        if (!playerPerk.containsKey(p.getUniqueId())) {
            lore.add(ChatColor.RED + "INACTIVE NOW");
            return lore;
        }
        if (playerPerk.get(p.getUniqueId()).toString().contains(perks.className)) {
            lore.add(ChatColor.GREEN + "ACTIVE NOW");
            return lore;
        } else {
            lore.add(ChatColor.RED + "INACTIVE NOW");
            return lore;
        }
    }

    public void openInv(Player p) {
        Inventory inv = Bukkit.getServer().createInventory(null, 9 * 1,
                ChatColor.WHITE + "" + ChatColor.BOLD + "Death Perks");
        ItemConstructor it = new ItemConstructor();

        // burp
        inv.setItem(0, it.MakeItem(new BurpPerk().perk, Material.RAW_FISH, getLore(p, new BurpPerk())));

        //wolf howl
        inv.setItem(1, it.MakeItem(new WolfPerk().perk, Material.BONE, getLore(p, new WolfPerk())));
        //Villager Death
        inv.setItem(2, it.MakeItem(new VillagerDeath().perk, Material.EMERALD, getLore(p, new VillagerDeath())));
        //Thunder strike
        inv.setItem(3, it.MakeItem(new ThunderPerk().perk, Material.BLAZE_ROD, getLore(p, new ThunderPerk())));
        //blaze Death
        inv.setItem(4, it.MakeItem(new BlazePerk().perk, Material.BLAZE_POWDER, getLore(p, new BlazePerk())));
        //shadow
        inv.setItem(5, it.MakeItem(new ShadowPerk().perk, Material.COAL, getLore(p, new ShadowPerk())));
        //RIP
        inv.setItem(7, it.MakeItem(new RipPerks().perk, Material.TNT, getLore(p, new RipPerks())));
        //guardian
        inv.setItem(6, it.MakeItem(new GuardianPerk().perk, Material.PRISMARINE_SHARD, getLore(p, new GuardianPerk())));
        //da last chikken
        inv.setItem(8, it.MakeItem(new DlcPerk().perk, Material.EGG, getLore(p, new DlcPerk())));

        p.openInventory(inv);

    }

    public static HashMap<UUID, Perks> playerPerk = new HashMap<>();

    @EventHandler
    public void perkInv(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        Player player = (Player) p;
        if (!e.getInventory().getTitle().contains("Death Perks")) {
            return;
        }
        e.setCancelled(true);
        if (e.getCurrentItem() == null)
            return;
        if (!e.getCurrentItem().hasItemMeta())
            return;
        if (!e.getCurrentItem().getItemMeta().hasDisplayName())
            return;
        player.getOpenInventory().close();
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Burp")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
            playerPerk.put(player.getUniqueId(), new BurpPerk());
            openInv(player);
            player.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "You have activated the " + new BurpPerk().perk + ChatColor.GRAY + "" + ChatColor.BOLD + " perk!");
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Wolf Howl")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
            playerPerk.put(player.getUniqueId(), new WolfPerk());
            openInv(player);
            player.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "You have activated the " + new WolfPerk().perk + ChatColor.GRAY + "" + ChatColor.BOLD + " perk!");
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Villager Death")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
            playerPerk.put(player.getUniqueId(), new VillagerDeath());
            openInv(player);
            player.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "You have activated the " + new VillagerDeath().perk + ChatColor.GRAY + "" + ChatColor.BOLD + " perk!");
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Blaze Death")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
            playerPerk.put(player.getUniqueId(), new BlazePerk());
            openInv(player);
            player.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "You have activated the " + new BlazePerk().perk + ChatColor.GRAY + "" + ChatColor.BOLD + " perk!");
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("ThunderStrike")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
            playerPerk.put(player.getUniqueId(), new ThunderPerk());
            openInv(player);
            player.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "You have activated the " + new ThunderPerk().perk + ChatColor.GRAY + "" + ChatColor.BOLD + " perk!");
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Guardian")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
            playerPerk.put(player.getUniqueId(), new GuardianPerk());
            openInv(player);
            player.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "You have activated the " + new GuardianPerk().perk + ChatColor.GRAY + "" + ChatColor.BOLD + " perk!");
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Shadow")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
            playerPerk.put(player.getUniqueId(), new ShadowPerk());
            openInv(player);
            player.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "You have activated the " + new ShadowPerk().perk + ChatColor.GRAY + "" + ChatColor.BOLD + " perk!");
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("RIP")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
            playerPerk.put(player.getUniqueId(), new RipPerks());
            openInv(player);
            player.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "You have activated the " + new RipPerks().perk + ChatColor.GRAY + "" + ChatColor.BOLD + " perk!");
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Da Last Chikken")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
            playerPerk.put(player.getUniqueId(), new DlcPerk());
            openInv(player);
            player.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "You have activated the " + new DlcPerk().perk + ChatColor.GRAY + "" + ChatColor.BOLD + " perk!");
            return;
        }


    }
}
