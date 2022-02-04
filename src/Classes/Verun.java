package Classes;

import ClassGUI.AaClassSpellShowcase;
import ClassGUI.RiftorSpellInv;
import ClassGUI.VerunSpellInv;
import ClassUpgrades.SkillPointsCore;
import Main.Main;
import Teams.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;


public class Verun extends TlkClass implements Listener {
    public Verun() {
        //Class name
        className = "Verun";
        classColor = ChatColor.GOLD;
        classSpec = classColor + "" + ChatColor.BOLD + "Verun, The Desert Scorpion" + ChatColor.WHITE + "" + ChatColor.BOLD;
        //Weapon name
        weaponName = "Scorpion Claw";

        //Spell Names
        spell1Name = "Sting";
        spell2Name = "Claw Grab";
        spell3Name = "Golden Skin";
        ultName = "Evolve";

        //Mana Cost
        Mana1 = 8;
        Mana2 = 12;
        Mana3 = 14;

        // Spell DMG
        spell1DMG = 6;  //sting damage
        spell2DMG = 4;  // claw grab damage
        spell3DMG = 20 * 4; // golden skin timer
        ultDMG = 20 * 6;     //evolve timer
    }

    AaClassActionBar cAc = new AaClassActionBar();

    @Override
    public void useSpell1(Player p) {
        removeMana(p, Mana1);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell1Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana1 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SILVERFISH_HURT, 5, 1);
        p.getWorld().spawnParticle(Particle.DRAGON_BREATH, p.getLocation(), 80, 0.4F, 0.8F, 0.4F, 0.1);
        double range = 3;
        p.setVelocity(p.getLocation().getDirection().multiply(1F));
        p.setVelocity(new Vector(p.getVelocity().getX(), 0.0D,
                p.getVelocity().getZ()));
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Entity entity : p.getWorld().getNearbyEntities(p.getLocation(), range, range, range)) {
                    if (entity.getType().isAlive() && entity != p) {
                        if (TeamManager.arentSameTeam(p, entity)) {
                            LivingEntity livE = (LivingEntity) entity;
                            livE.damage(spell1DMG + SkillPointsCore.getDMGValue(p), p);
                            livE.getWorld().spawnParticle(Particle.BLOCK_CRACK, livE.getLocation().add(0, 1, 0),
                                    400, new MaterialData(Material.GOLD_BLOCK));
                            livE.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 2, 1));
                            cancel();
                            return;
                        }
                    }
                }
            }
        }.runTaskLater(Main.plugin, 4);

    }

    public static HashMap<UUID, Entity> clawed = new HashMap<>();

    @Override
    public void useSpell2(Player p) {
        Vector direction = p.getLocation().getDirection().add(new Vector(0, 0.8, 0));
        Location claw = p.getLocation().add(direction.multiply(2F));
        if (countEntities(claw, p) == 0) {
            p.sendMessage(ChatColor.RED + "There are no enemies to grab!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeMana(p, Mana2);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell2Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana2 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");

        for (Entity entity : p.getWorld().getNearbyEntities(claw, 1.8, 1.8, 1.8)) {
            if (entity.getType().isAlive() && entity != p) {
                if (TeamManager.arentSameTeam(p, entity)) {
                    LivingEntity e = (LivingEntity) entity;
                    e.damage(spell2DMG + SkillPointsCore.getDMGValue(p), p);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SILVERFISH_HURT, 5, 2);
                    displayDashParticle(p, p.getLocation().clone().add(0, 1, 0), direction);
                    clawed.put(e.getUniqueId(), e);
                    freezePlayer(e, 20);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 6));
                    break;
                }
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Entity e : clawed.values()) {
                    e.setVelocity(p.getLocation().getDirection().multiply(2F));
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SILVERFISH_HURT, 5, 1);
                    e.getWorld().spawnParticle(Particle.DAMAGE_INDICATOR, e.getLocation(),
                            20, 0.8F, 0.8F, 0.8F, 0.1);
                }
                clawed.clear();
            }
        }.runTaskLater(Main.plugin, 20);
    }

    public Integer countEntities(Location loc, Player p) {
        int count = 0;
        for (Entity entity : loc.getWorld().getNearbyEntities(loc, 1.8, 1.8, 1.8)) {
            if (entity.getType().isAlive() && entity != p && TeamManager.arentSameTeam(p, entity)) {
                count += 1;
            }
        }
        return count;
    }

    public void displayDashParticle(LivingEntity p, Location start, Vector increment) {
        for (int x = 1; x < 4; x++) {
            increment.multiply(x / 4);
            Location tpLoc = start.add(start.getDirection());
            tpLoc.getWorld().spawnParticle(Particle.DRAGON_BREATH, tpLoc, 20, 0.1F, 0.1F, 0.1F, 0.01);

        }
    }

    public void freezePlayer(Entity e, Integer time) {
        Location loc = e.getLocation();
        FrostBite.frozenNotUlt.add(e.getUniqueId());
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                count += 1;
                e.teleport(loc);
                if (count == time) {
                    FrostBite.frozenNotUlt.remove(e.getUniqueId());
                    cancel();

                }

            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }

    public static HashMap<UUID, Boolean> goldArmor = new HashMap<>();

    @Override
    public void useSpell3(Player p) {
        if (goldArmor.containsKey(p.getUniqueId())) {
            p.sendMessage(ChatColor.RED + "You can't cast this spell twice at a time!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeMana(p, Mana3);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell3Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana3 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        goldArmor.put(p.getUniqueId(), true);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SILVERFISH_HURT, 5, 1);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, spell3DMG, 3));
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                p.getWorld().spawnParticle(Particle.BLOCK_CRACK, p.getLocation().add(0, 1.4, 0),
                        40, new MaterialData(Material.GOLD_BLOCK));
                count += 1;
                if (count == spell3DMG) {
                    goldArmor.clear();
                    cancel();
                    return;
                }

            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }

    @Override
    public void useUltimate(Player p) {
        removeMana(p, 20);
        removeUlt(p);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + ultName + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + 20 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        Mana1 -= 6;
        spell1DMG += 6;
        p.setGlowing(true);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, ultDMG, 4));
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, ultDMG, 80));
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (count % 40 == 0) {
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_IRONGOLEM_DEATH, 5, 2);

                }
                p.getWorld().spawnParticle(Particle.BLOCK_CRACK, p.getLocation().add(0, 1.4, 0),
                        40, new MaterialData(Material.DIAMOND_BLOCK));
                count += 1;
                if (count == ultDMG) {
                    p.setGlowing(false);
                    Mana1 += 6;
                    spell1DMG -= 6;
                    cancel();
                    return;
                }

            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (goldArmor.containsKey(e.getEntity().getUniqueId())) {
            e.setDamage((e.getDamage()) - (e.getDamage() * 90 / 100));
        }
    }

    @Override
    public AaClassSpellShowcase getClassInv() {
        return new VerunSpellInv();
    }
}
