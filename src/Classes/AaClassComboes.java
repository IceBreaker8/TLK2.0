package Classes;

import ClassGUI.AaClassHashMapChoosing;
import GameMechanics.ClimbingTool;
import GameMechanics.ManaSystem;
import GameMechanics.UltChargeMechanics;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;

public class AaClassComboes implements Listener {
    public static HashMap<UUID, Integer> clicks = new HashMap<>();

    public int getClicks(Player p) {
        if (clicks.containsKey(p.getUniqueId())) {
            return clicks.get(p.getUniqueId());
        }
        if (!clicks.containsKey(p.getUniqueId())) {
            return 0;
        }
        return 8;
    }

    @EventHandler
    public void onSwitchItemHeld(PlayerItemHeldEvent e) {
        Player p = e.getPlayer();
        if (e.getPreviousSlot() != e.getNewSlot()) {
            clicks.remove(p.getUniqueId());
            if (ClimbingTool.hookStatus.containsKey(p.getUniqueId())) {
                if (ClimbingTool.hookStatus.get(p.getUniqueId()) == 0) {
                    e.setCancelled(true);
                }
            }

        }

    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        if (item == null)
            return;
        ItemMeta im = e.getItem().getItemMeta();
        if (im == null)
            return;

        Action action = e.getAction();
        ItemStack stack = e.getItem();
        if (stack == null)
            return;
        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK))
            return;
        if (im.getLore() == null)
            return;

        if (!(stack.getItemMeta().hasDisplayName()))
            return;
        TlkClass cl = AaClassHashMapChoosing.clazz.get(p.getUniqueId());
        if (cl == null) return;
        if (stack.getItemMeta().getDisplayName().contains(cl.weaponName)) {
            e.setCancelled(true);
            if (Occultist.possessed.contains(p.getUniqueId())) {
                p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You are possessed!");

                return;
            }
            if (FrostBite.frozenNotUlt.contains(p.getUniqueId()) || FrostBite.frozen.contains(p.getUniqueId()) || Chronos.timeStopped.contains(p.getUniqueId())) {
                p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You can't cast spells while frozen!");
                return;
            }
            if (!clicks.containsKey(p.getUniqueId())) {
                clicks.put(p.getUniqueId(), 1);
            }
            AaClassActionBar a = new AaClassActionBar();
            ManaSystem ma = new ManaSystem();
            if (clicks.get(p.getUniqueId()) == 1) {
                a.send(p, ChatColor.RED + "     " + ChatColor.BOLD + "" + (int) (p.getHealth()) + "/20❤        " + ChatColor.GREEN + "R " + ChatColor.GRAY + "- ? - ?        " + ChatColor.AQUA + "" + ChatColor.BOLD + ma.getMana(p) + "/20⚙");
                p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 10, 1);
                clicks.put(p.getUniqueId(), 2);
                return;
            } else if (clicks.get(p.getUniqueId()) == 2) {
                a.send(p, ChatColor.RED + "     " + ChatColor.BOLD + "" + (int) (p.getHealth()) + "/20❤        " + ChatColor.GREEN + "R " + ChatColor.GRAY + "-" + ChatColor.GREEN + " R "
                        + ChatColor.GRAY + "- ?        " + ChatColor.AQUA + "" + ChatColor.BOLD + ma.getMana(p) + "/20⚙");
                p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 10, 1);
                clicks.put(p.getUniqueId(), 4);
                return;
            } else if (clicks.get(p.getUniqueId()) == 4) {
                a.send(p, ChatColor.RED + "     " + ChatColor.BOLD + "" + (int) (p.getHealth()) + "/20❤        " + ChatColor.GREEN + "R " + ChatColor.GRAY + "-" + ChatColor.GREEN + " R "
                        + ChatColor.GRAY + "-" + ChatColor.GREEN + " R        " + ChatColor.AQUA + "" + ChatColor.BOLD + ma.getMana(p) + "/20⚙");
                p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 10, 1);

                clicks.remove(p.getUniqueId());
                if (ma.getMana(p) < cl.Mana1) {
                    p.sendMessage(ChatColor.RED + "You don't have enough mana to cast this spell!");
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
                    return;
                }
                cl.useSpell1(p);


                return;
            } else if (clicks.get(p.getUniqueId()) == 3) {
                a.send(p, ChatColor.RED + "     " + ChatColor.BOLD + "" + (int) (p.getHealth()) + "/20❤        " + ChatColor.GREEN + "R " + ChatColor.GRAY + "-" + ChatColor.GREEN + " L "
                        + ChatColor.GRAY + "-" + ChatColor.GREEN + " R        " + ChatColor.AQUA + "" + ChatColor.BOLD + ma.getMana(p) + "/20⚙");
                p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 10, 1);

                clicks.remove(p.getUniqueId());
                if (ma.getMana(p) < cl.Mana2) {
                    p.sendMessage(ChatColor.RED + "You don't have enough mana to cast this spell!");
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
                    return;
                }
                cl.useSpell2(p);


                return;
            }
        }
    }


    @EventHandler
    public void ons(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        ItemStack item = e.getItem();
        if (item == null)
            return;
        ItemMeta im = e.getItem().getItemMeta();
        if (im == null)
            return;
        Action action = e.getAction();
        ItemStack stack = e.getItem();
        if (stack == null)
            return;
        if (!(action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK))
            return;
        if (im.getLore() == null)
            return;
        if (!(stack.getItemMeta().hasDisplayName()))
            return;
        TlkClass cl = AaClassHashMapChoosing.clazz.get(p.getUniqueId());
        if (cl == null) return;
        if (stack.getItemMeta().getDisplayName().contains(cl.weaponName)) {
            e.setCancelled(true);
            if (Occultist.possessed.contains(p.getUniqueId())) {
                p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You are possessed!");

                return;
            }
            if (FrostBite.frozenNotUlt.contains(p.getUniqueId()) || FrostBite.frozen.contains(p.getUniqueId()) || Chronos.timeStopped.contains(p.getUniqueId())) {
                p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You can't cast spells while frozen!");
                return;
            }
            if (!clicks.containsKey(p.getUniqueId())) {
                clicks.put(p.getUniqueId(), 1);
            }
            AaClassActionBar a = new AaClassActionBar();
            ManaSystem ma = new ManaSystem();
            if (clicks.get(p.getUniqueId()) == 2) {
                a.send(p, ChatColor.RED + "     " + ChatColor.BOLD + "" + (int) (p.getHealth()) + "/20❤        " + ChatColor.GREEN + "R " + ChatColor.GRAY + "-" + ChatColor.GREEN + " L "
                        + ChatColor.GRAY + "- ?        " + ChatColor.AQUA + "" + ChatColor.BOLD + ma.getMana(p) + "/20⚙");
                p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 10, 1);
                clicks.put(p.getUniqueId(), 3);
                return;
            } else if (clicks.get(p.getUniqueId()) == 3) {
                a.send(p, ChatColor.RED + "     " + ChatColor.BOLD + "" + (int) (p.getHealth()) + "/20❤        " + ChatColor.GREEN + "R " + ChatColor.GRAY + "-" + ChatColor.GREEN + " L "
                        + ChatColor.GRAY + "-" + ChatColor.GREEN + " L        " + ChatColor.AQUA + "" + ChatColor.BOLD + ma.getMana(p) + "/20⚙");
                p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 10, 1);

                clicks.remove(p.getUniqueId());
                if (ma.getMana(p) < cl.Mana3) {
                    p.sendMessage(ChatColor.RED + "You don't have enough mana to cast this spell!");
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
                    return;
                }
                cl.useSpell3(p);


                ///////
                return;
            } else if (clicks.get(p.getUniqueId()) == 4) {
                a.send(p, ChatColor.RED + "     " + ChatColor.BOLD + "" + (int) (p.getHealth()) + "/20❤        " + ChatColor.GREEN + "R " + ChatColor.GRAY + "-" + ChatColor.GREEN + " R "
                        + ChatColor.GRAY + "-" + ChatColor.GREEN + " L        " + ChatColor.AQUA + "" + ChatColor.BOLD + ma.getMana(p) + "/20⚙");

                p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 10, 1);
                clicks.remove(p.getUniqueId());
                UltChargeMechanics u = new UltChargeMechanics();
                if (!(u.canUlt(p))) {
                    p.sendMessage(ChatColor.RED + "Your ultimate isn't fully charged!");
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
                    return;
                }
                if (ma.getMana(p) < 20) {
                    p.sendMessage(ChatColor.RED + "You don't have enough mana to cast this spell!");
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
                    return;
                }
                cl.useUltimate(p);

                return;
            }

        }

    }

    @EventHandler
    public void onPlayerChargeMana(EntityDamageByEntityEvent e) {
        Entity victim = e.getEntity();
        Entity damager = e.getDamager();
        if (Occultist.possessed.contains(victim.getUniqueId())) {
            e.setDamage(0);
        }
        if (Occultist.possessed.contains(damager.getUniqueId())) {
            e.setCancelled(true);
        }
    }

}

