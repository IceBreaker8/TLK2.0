package GameMechanics;

import ItemConstructorClass.ItemConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ForgeryMech implements Listener {

    @EventHandler
    public void onEnchTableClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() != Material.ANVIL) return;
            e.setCancelled(true);
            openForgeryGUI(p);
        }
    }


    void fillInvWithGlass(Inventory inv, int start) {
        for (int count = start; count < 45; count++) {
            ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
            inv.setItem(count, i);
        }
    }

    ItemConstructor itC = new ItemConstructor();

    public static HashMap<UUID, Integer> forgeryActivationSteps = new HashMap<>(); // 1 : lava bucket

    // 10   11   13   19   20   22   37   38   39
    public ItemStack addClassItem(Player p) {
        for (ItemStack item : p.getInventory().getContents()) {
            if (item == null) continue;
            if (!item.hasItemMeta()) continue;
            if (item.getItemMeta().hasLore()) {
                for (String lore : item.getItemMeta().getLore()) {
                    if (lore.contains(ChatColor.DARK_RED + "> Class Item <")) {
                        return item;
                    }
                }
            }
        }
        return null;
    }

    public Boolean getMagmaCreamVerif(Player p) {
        for (ItemStack item : p.getInventory().getContents()) {
            if (item == null) continue;
            if (!item.hasItemMeta()) continue;
            if (item.getItemMeta().hasDisplayName()) {
                if (item.getItemMeta().getDisplayName().contains("Lava Essence")) {
                    return true;
                }
            }

        }
        return false;
    }


    public void openForgeryGUI(Player p) {
        if (addClassItem(p) == null) {
            p.sendMessage(ChatColor.RED + "You need to choose a class first!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 5, 1);
            return;
        }
        Inventory forgery = Bukkit.getServer().createInventory(null, 9 * 5,
                ChatColor.RED + "" + ChatColor.BOLD + "Forgery");
        fillInvWithGlass(forgery, 0);
        ItemStack air = new ItemStack(Material.AIR);
        /////////////////////////////
        forgery.setItem(10, air); // CLASS ITEM
        forgery.setItem(11, air); // 1 empty
        forgery.setItem(15, addClassItem(p));
        forgery.setItem(19, air); // 3 empty
        forgery.setItem(20, air);
        /////////////////////////////
        forgery.setItem(16, air);
        forgery.setItem(24, air);
        forgery.setItem(25, air);
        /////////////////////////////
        forgery.setItem(10, addClassItem(p));
        ////////////////////////////
        if (!forgeryActivationSteps.containsKey(p.getUniqueId())) {
            forgery.setItem(13, itC.MakeItem(ChatColor.WHITE + "" +
                    ChatColor.BOLD + "Empty Bucket", Material.BUCKET, null));
        } else {
            forgery.setItem(13, itC.MakeItem(ChatColor.DARK_RED + "" +
                    ChatColor.BOLD + "Lava Bucket", Material.LAVA_BUCKET, null));
        }
        if (ironVerif.containsKey(p.getUniqueId())) {
            forgery.setItem(16, itC.MakeItem(ChatColor.DARK_GRAY + "" + ChatColor.BOLD
                    + "Iron Essence", Material.IRON_INGOT, null));
        }
        if (goldVerif.containsKey(p.getUniqueId())) {
            forgery.setItem(25, itC.MakeItem(ChatColor.GOLD + "" + ChatColor.BOLD
                    + "Gold Essence", Material.GOLD_INGOT, null));
        }
        if (diamondVerif.containsKey(p.getUniqueId())) {
            forgery.setItem(24, itC.MakeItem(ChatColor.AQUA + "" + ChatColor.BOLD
                    + "Diamond Essence", Material.DIAMOND, null));
        }
        forgery.setItem(22, itC.MakeItem(ChatColor.RED + "" + ChatColor.BOLD + "FORGE -->", Material.ANVIL, null));
        forgery.setItem(36, itC.MakeItem(ChatColor.GRAY + "" + ChatColor.BOLD +
                "+ add 1 Iron Block", Material.IRON_BLOCK, null));
        forgery.setItem(37, itC.MakeItem(ChatColor.GOLD + "" + ChatColor.BOLD +
                "+ add 1 Gold Block", Material.GOLD_BLOCK, null));
        forgery.setItem(38, itC.MakeItem(ChatColor.AQUA + "" + ChatColor.BOLD +
                "+ add 1 Diamond Block", Material.DIAMOND_BLOCK, null));
        forgery.setItem(40, itC.MakeItem(ChatColor.RED + "" + ChatColor.BOLD +
                "+ add 1 Lava Essence", Material.MAGMA_CREAM, null));
        forgery.setItem(42, itC.MakeItem(ChatColor.WHITE + "" + ChatColor.BOLD +
                "+ Glass Bottle", Material.GLASS_BOTTLE, null));
        forgery.setItem(44, itC.MakeItem(ChatColor.DARK_RED + "" + ChatColor.BOLD +
                "RESET", Material.REDSTONE_BLOCK, null));
        ///////////////////////////


        p.openInventory(forgery);

    }

    public static HashMap<UUID, Integer> ironCount = new HashMap<>();
    public static HashMap<UUID, Integer> goldCount = new HashMap<>();
    public static HashMap<UUID, Integer> diamondCount = new HashMap<>();

    public static HashMap<UUID, Integer> ironVerif = new HashMap<>();
    public static HashMap<UUID, Integer> goldVerif = new HashMap<>();
    public static HashMap<UUID, Integer> diamondVerif = new HashMap<>();

    @EventHandler
    public void classChoosingEvent(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        if (!e.getInventory().getTitle().contains("Forgery")) {
            return;
        }
        e.setCancelled(true);

        if (e.getCurrentItem() == null)
            return;
        if (!e.getCurrentItem().hasItemMeta())
            return;
        if (!e.getCurrentItem().getItemMeta().hasDisplayName())
            return;
        Player player = (Player) p;
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("RESET")) {
            resetGUI((Player) p, e.getClickedInventory());
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Glass Bottle")) {
            if (p.getInventory().containsAtLeast(new ItemStack(Material.SAND), 3)) {
                clearItem((Player) p, 3, Material.SAND);
                player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 5, 1);
                player.getInventory().addItem(new ItemStack(Material.GLASS_BOTTLE, 1));
            } else {
                ((Player) p).playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 5, 1);
                p.sendMessage(ChatColor.RED + "You need 3 sand blocks to make one glass bottle!");
                return;
            }
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("+ add 1 Lava Essence")) {
            if (forgeryActivationSteps.containsKey(p.getUniqueId())) {
                ((Player) p).playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 5, 1);
                p.sendMessage(ChatColor.RED + "You have already filled the bucket!");
                return;
            }
            if (getMagmaCreamVerif((Player) p)) {
                if (!forgeryActivationSteps.containsKey(p.getUniqueId())) {
                    clearItem((Player) p, 1, Material.MAGMA_CREAM);
                    e.getClickedInventory().setItem(13, itC.MakeItem(ChatColor.DARK_RED + "" +
                            ChatColor.BOLD + "Lava Bucket", Material.LAVA_BUCKET, null));
                    ((Player) p).playSound(p.getLocation(), Sound.ITEM_BUCKET_FILL_LAVA, 5, 1);
                    forgeryActivationSteps.put(p.getUniqueId(), 1);

                }
            } else {
                ((Player) p).playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 5, 1);
                p.sendMessage(ChatColor.RED + "You don't have a Lava Essence!");
                return;
            }
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("+ add 1 Iron Block")) {
            if (verifyEmptyItemLocs(e.getClickedInventory())) {
                return;
            }
            if (p.getInventory().containsAtLeast(new ItemStack(Material.IRON_BLOCK), 1)) {
                ((Player) p).playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_IRON, 5, 1);
                clearItem((Player) p, 1, Material.IRON_BLOCK);
                addOneToHashMap((Player) p, ironCount);
                addBlockToForgeryGUI(e.getClickedInventory(), Material.IRON_BLOCK);
            } else {
                ((Player) p).playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 5, 1);
                p.sendMessage(ChatColor.RED + "You don't have an iron block!");
                return;
            }

        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("+ add 1 Gold Block")) {
            if (verifyEmptyItemLocs(e.getClickedInventory())) {
                return;
            }
            if (p.getInventory().containsAtLeast(new ItemStack(Material.GOLD_BLOCK), 1)) {
                ((Player) p).playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_IRON, 5, 1);
                clearItem((Player) p, 1, Material.GOLD_BLOCK);
                addOneToHashMap((Player) p, goldCount);
                addBlockToForgeryGUI(e.getClickedInventory(), Material.GOLD_BLOCK);
            } else {
                ((Player) p).playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 5, 1);
                p.sendMessage(ChatColor.RED + "You don't have a gold block!");
                return;
            }
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("+ add 1 Diamond Block")) {
            if (verifyEmptyItemLocs(e.getClickedInventory())) {
                return;
            }
            if (p.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND_BLOCK), 1)) {
                ((Player) p).playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_IRON, 5, 1);
                clearItem((Player) p, 1, Material.DIAMOND_BLOCK);
                addOneToHashMap((Player) p, diamondCount);
                addBlockToForgeryGUI(e.getClickedInventory(), Material.DIAMOND_BLOCK);
            } else {
                ((Player) p).playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 5, 1);
                p.sendMessage(ChatColor.RED + "You don't have a diamond block!");
                return;
            }
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("FORGE -->")) {
            if (!forgeryActivationSteps.containsKey(p.getUniqueId())) {
                p.sendMessage(ChatColor.RED + "You need to get a lava source in order to forge!");
                ((Player) p).playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 5, 1);
                return;
            }
            if (forgeShape(e.getClickedInventory(), (Player) p) == 1) {
                if (ironVerif.containsKey(p.getUniqueId())) {
                    p.sendMessage(ChatColor.RED + "You have already forged the iron essence!");
                    ((Player) p).playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 5, 1);
                    return;
                }
                p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 5, 1);
                ironVerif.put(p.getUniqueId(), 1);
                e.getClickedInventory().setItem(16, itC.MakeItem(ChatColor.DARK_GRAY + "" + ChatColor.BOLD
                        + "Iron Essence", Material.IRON_INGOT, null));
                removeMap((Player) p);
                removeBlock(e.getClickedInventory());
                putEnchantedItem(e.getClickedInventory(), (Player) p);

                return;
            } else if (forgeShape(e.getClickedInventory(), (Player) p) == 2) {
                if (goldVerif.containsKey(p.getUniqueId())) {
                    p.sendMessage(ChatColor.RED + "You have already forged the gold essence!");
                    ((Player) p).playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 5, 1);
                    return;
                }
                p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 5, 1);
                goldVerif.put(p.getUniqueId(), 1);
                e.getClickedInventory().setItem(25, itC.MakeItem(ChatColor.GOLD + "" + ChatColor.BOLD
                        + "Gold Essence", Material.GOLD_INGOT, null));
                removeBlock(e.getClickedInventory());
                removeMap((Player) p);
                putEnchantedItem(e.getClickedInventory(), (Player) p);
                return;
            } else if (forgeShape(e.getClickedInventory(), (Player) p) == 3) {
                if (diamondVerif.containsKey(p.getUniqueId())) {
                    p.sendMessage(ChatColor.RED + "You have already forged the diamond essence!");
                    ((Player) p).playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 5, 1);
                    return;
                }
                p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 5, 1);
                diamondVerif.put(p.getUniqueId(), 1);
                e.getClickedInventory().setItem(24, itC.MakeItem(ChatColor.AQUA + "" + ChatColor.BOLD
                        + "Diamond Essence", Material.DIAMOND, null));
                removeBlock(e.getClickedInventory());
                removeMap((Player) p);
                putEnchantedItem(e.getClickedInventory(), (Player) p);
                return;
            } else {
                p.sendMessage(ChatColor.RED + "You can't execute an unknown forge sequence!");
                ((Player) p).playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 5, 1);
            }
        }

    }

    public void removeMap(Player p) {
        ironCount.remove(p.getUniqueId());
        goldCount.remove(p.getUniqueId());
        diamondCount.remove(p.getUniqueId());
    }

    private void putEnchantedItem(Inventory inv, Player p) {
        inv.setItem(15, addClassItem(p));
    }

    private void removeBlock(Inventory inv) {
        ItemStack air = new ItemStack(Material.AIR);
        inv.setItem(11, air); // 1 empty
        inv.setItem(20, air); // 2 empty
        inv.setItem(19, air); // 3 empty
    }

    private int forgeShape(Inventory forgery, Player p) {
        if (ironCount.containsKey(p.getUniqueId())) {
            if (ironCount.get(p.getUniqueId()) == 2 &&
                    !goldCount.containsKey(p.getUniqueId()) &&
                    !diamondCount.containsKey(p.getUniqueId())) {
                return 1;
            }
        }
        if (goldCount.containsKey(p.getUniqueId())) {
            if (goldCount.get(p.getUniqueId()) == 2 &&
                    !ironCount.containsKey(p.getUniqueId()) &&
                    !diamondCount.containsKey(p.getUniqueId())) {
                return 2;
            }
        }
        if (diamondCount.containsKey(p.getUniqueId())) {
            if (diamondCount.get(p.getUniqueId()) == 1 &&
                    !ironCount.containsKey(p.getUniqueId()) &&
                    !goldCount.containsKey(p.getUniqueId())) {
                return 3;
            }
        }
        return 0;
    }

    public boolean verifyEmptyItemLocs(Inventory forgery) {
        if ((forgery.getItem(11) != null) && (forgery.getItem(20) != null) && (forgery.getItem(19) != null)) {
            return true;
        }
        return false;
    }

    public void addOneToHashMap(Player p, HashMap map) {
        if (!map.containsKey(p.getUniqueId())) {
            map.put(p.getUniqueId(), 1);
            return;
        }
        map.put(p.getUniqueId(), ((int) map.get(p.getUniqueId()) + 1));
    }

    public void resetGUI(Player p, Inventory inv) {
        ItemStack air = new ItemStack(Material.AIR);
        inv.setItem(11, air); // 1 empty
        inv.setItem(20, air); // 2 empty
        inv.setItem(19, air); // 3 empty
        backItems(p);
    }

    public void backItems(Player p) {
        if (ironCount.containsKey(p.getUniqueId())) {
            for (int i = 0; i < ironCount.get(p.getUniqueId()); i++) {
                p.getInventory().addItem(new ItemStack(Material.IRON_BLOCK));
            }
            ironCount.remove(p.getUniqueId());
        }
        if (goldCount.containsKey(p.getUniqueId())) {
            for (int i = 0; i < goldCount.get(p.getUniqueId()); i++) {
                p.getInventory().addItem(new ItemStack(Material.GOLD_BLOCK));
            }
            goldCount.remove(p.getUniqueId());

        }
        if (diamondCount.containsKey(p.getUniqueId())) {
            for (int i = 0; i < diamondCount.get(p.getUniqueId()); i++) {
                p.getInventory().addItem(new ItemStack(Material.DIAMOND_BLOCK));
            }
            diamondCount.remove(p.getUniqueId());

        }
    }

    public void addBlockToForgeryGUI(Inventory inv, Material block) {
        ItemStack item = new ItemStack(block, 1);
        if (inv.getItem(11) == null) {
            inv.setItem(11, item);
        } else if (inv.getItem(20) == null) {
            inv.setItem(20, item);
        } else if (inv.getItem(19) == null) {
            inv.setItem(19, item);
        }
    }

    public boolean clearItem(Player player, int count, Material mat) {
        Map<Integer, ? extends ItemStack> ammo = player.getInventory().all(mat);

        int found = 0;
        for (ItemStack stack : ammo.values())
            found += stack.getAmount();
        if (count > found)
            return false;

        for (Integer index : ammo.keySet()) {
            ItemStack stack = ammo.get(index);

            int removed = Math.min(count, stack.getAmount());
            count -= removed;

            if (stack.getAmount() == removed)
                player.getInventory().setItem(index, null);
            else
                stack.setAmount(stack.getAmount() - removed);

            if (count <= 0)
                break;
        }

        player.updateInventory();
        return true;
    }

    @EventHandler
    public void onPlayerClose(InventoryCloseEvent e) {
        if (e.getInventory().getTitle().contains("Forgery")) {
            resetGUI((Player) e.getPlayer(), e.getInventory());
        }
    }
}