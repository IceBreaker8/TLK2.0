package Classes;

import ClassGUI.AaClassHashMapChoosing;
import ClassGUI.AaClassSpellShowcase;
import ClassGUI.AlpSpellInv;
import ClassUpgrades.SkillPointsCore;
import GameMechanics.ManaSystem;
import Main.Main;
import Teams.TeamManager;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.GenericAttributes;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.UUID;

import static org.bukkit.entity.Horse.Color.WHITE;

public class Alp extends TlkClass implements Listener {
    public Alp() {
        //Class name
        className = "Alp";
        classColor = ChatColor.DARK_AQUA;
        classSpec = classColor + "" + ChatColor.BOLD + "Alp, The Sharp Edge" + ChatColor.WHITE + "" + ChatColor.BOLD;
        //Weapon name
        weaponName = "Ancient Sword";

        //Spell Names
        spell1Name = "Dagger Throw";
        spell2Name = "Sudden Lunge";
        spell3Name = "Horse Ride";
        ultName = "War Cry";

        //Mana Cost
        Mana1 = 8;
        Mana2 = 12;
        Mana3 = 16;

        // Spell DMG
        spell1DMG = 7;  //dagger dmg
        spell2DMG = 10;  // lunge dmg
        spell3DMG = 20 * 4;  //horse ride time
        ultDMG = 6 * 20;     //rage time
    }

    private Integer addon = 4;
    ManaSystem mana = new ManaSystem();
    AaClassActionBar cAc = new AaClassActionBar();

    public void buffSpell() {
        spell1DMG += addon;
        spell2DMG += addon;
    }

    public void resetSpells() {
        spell1DMG -= addon;
        spell2DMG -= addon;
    }

    @Override
    public void useSpell1(Player p) {
        removeMana(p, Mana1);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell1Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana1 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
        Location loc = p.getLocation().add(0, 1.2, 0);
        Vector direction = loc.getDirection().normalize();
        direction.multiply(1.4F);
        Item sword = p.getWorld().dropItem(loc, item);
        sword.setCustomName(p.getName() + "'s dagger");
        sword.setCustomNameVisible(false);
        sword.setPickupDelay(20 * 9999);
        sword.setVelocity(direction.multiply(1F));
        sword.setGravity(false);
        sword.getWorld().playSound(sword.getLocation(), Sound.ITEM_FIRECHARGE_USE, 5, 1);
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                for (Entity e : p.getLocation().getWorld().getEntities()) {
                    if (e.getLocation().distance(sword.getLocation()) < 1.2) {
                        if (e != (p)) {
                            if (e.getType().isAlive() && !(e instanceof Zombie)) {
                                if (TeamManager.arentSameTeam(p, e)) {
                                    if (checkSpectator(e)) {
                                        sword.remove();
                                        LivingEntity b = (LivingEntity) e;
                                        b.damage(spell1DMG + SkillPointsCore.getDMGValue(p), p);
                                        sword.getWorld().playSound(sword.getLocation(), Sound.ENTITY_PLAYER_HURT, 5, 2);
                                        sword.getWorld().spawnParticle(Particle.BLOCK_CRACK, sword.getLocation().clone().add(0, 1, 0), 100, new MaterialData(Material.REDSTONE_BLOCK));
                                        cancel();
                                    }
                                }
                            }
                        }
                    }
                }
                sword.getWorld().spawnParticle(Particle.CRIT, sword.getLocation(), 1, 0.1F, 0.1F, 0.1F, 0.1); //Amount
                count += 1;
                if (count == 20 * 4) {
                    sword.remove();
                    cancel();
                }
                if (sword.isOnGround()) {
                    sword.getWorld().spawnParticle(Particle.BLOCK_CRACK, sword.getLocation(), 100, new MaterialData(sword.getLocation().clone().subtract(0, 1, 0).getBlock().getType()));
                    sword.getWorld().playSound(sword.getLocation(), Sound.ENTITY_IRONGOLEM_HURT, 5, 2);
                    sword.setVelocity(direction.multiply(0F));
                    waitForPickUp(sword);
                    cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        if (e.getItem().getName().contains("dagger") && !(AaClassHashMapChoosing.getTlkClass(e.getPlayer()).className == className)) {
            e.setCancelled(true);
            return;
        }
        if (e.getItem().getName().contains(e.getPlayer().getName() + "'s dagger") && AaClassHashMapChoosing.getTlkClass(e.getPlayer()).className == className) {
            e.setCancelled(true);
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
            e.getPlayer().sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "> You repicked your thrown dagger! <");
            e.getItem().remove();
            mana.setMana(e.getPlayer(), mana.getMana(e.getPlayer()) + Mana1);
            return;
        }
    }

    public void waitForPickUp(Item sword) {
        sword.setPickupDelay(0);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (sword.isValid()) {
                    sword.getWorld().spawnParticle(Particle.BLOCK_CRACK, sword.getLocation(), 100, new MaterialData(sword.getLocation().clone().subtract(0, 1, 0).getBlock().getType()));
                    sword.remove();
                }
            }
        }.runTaskLater(Main.plugin, 20 * 4);
    }


    @Override
    public void useSpell2(Player p) {
        if (!p.isOnGround()) {
            p.sendMessage(ChatColor.RED + "You need to be on ground to cast this spell!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeMana(p, Mana2);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell2Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana2 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        p.setVelocity(p.getLocation().getDirection().multiply(0.6F));
        p.setVelocity(new Vector(p.getVelocity().getX(), 0.6D,
                p.getVelocity().getZ()));
        p.playSound(p.getLocation(), Sound.ENTITY_IRONGOLEM_HURT, 5, 1);
        p.getLocation().getWorld().spawnParticle(Particle.CLOUD, p.getLocation(), 40, 0.4F, 0.4F, 0.4F, 1);
        dmgOnLanding(p);

    }

    private void dmgOnLanding(Player p) {
        new BukkitRunnable() {
            int t = 0;

            @Override
            public void run() {
                if (!p.isOnline()) {
                    cancel();
                    return;
                }
                if (p.isOnGround()) {
                    p.getWorld().spawnParticle(Particle.BLOCK_CRACK, p.getLocation(), 100,
                            new MaterialData(p.getLocation().clone().subtract(0, 1, 0).getBlock().getType()));
                    p.getWorld().spawnParticle(Particle.BLOCK_CRACK, p.getLocation(), 100,
                            new MaterialData(p.getLocation().clone().subtract(0, 1, -1).getBlock().getType()));
                    p.getWorld().spawnParticle(Particle.BLOCK_CRACK, p.getLocation(), 100,
                            new MaterialData(p.getLocation().clone().subtract(0, 1, 1).getBlock().getType()));
                    p.getWorld().spawnParticle(Particle.BLOCK_CRACK, p.getLocation(), 100,
                            new MaterialData(p.getLocation().clone().subtract(-1, 1, 0).getBlock().getType()));
                    p.getWorld().spawnParticle(Particle.BLOCK_CRACK, p.getLocation(), 100,
                            new MaterialData(p.getLocation().clone().subtract(1, 1, 0).getBlock().getType()));
                    p.playSound(p.getLocation(), Sound.ENTITY_IRONGOLEM_HURT, 5, 2);
                    Location loc = p.getLocation();
                    for (Entity e : loc.getWorld().getEntities()) {
                        if (e.getLocation().distance(loc) < 1.6) {
                            if (e.getType().isAlive() && !(e.equals(p))) {
                                if (TeamManager.arentSameTeam(p, e)) {
                                    if (checkSpectator(e)) {
                                        LivingEntity b1 = (LivingEntity) e;
                                        b1.damage(spell2DMG + SkillPointsCore.getDMGValue(p), p);
                                        b1.getWorld().spawnParticle(Particle.BLOCK_CRACK,
                                                b1.getLocation().clone().add(0, 1, 0), 200,
                                                new MaterialData(Material.REDSTONE_BLOCK));
                                    }
                                }
                            }
                        }
                    }
                    cancel();
                    return;
                }
            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }

    public static HashSet<UUID> cantRideAgain = new HashSet<>();

    @Override
    public void useSpell3(Player p) {
        if (cantRideAgain.contains(p.getUniqueId())) return;
        removeMana(p, Mana3);
        cantRideAgain.add(p.getUniqueId());
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell3Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana3 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        p.getLocation().getWorld().spawnParticle(Particle.CLOUD, p.getLocation(), 100, 0.4F, 0.8F, 0.4F, 1);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_HORSE_ANGRY, 5, 1);
        Horse horse = p.getWorld().spawn(p.getLocation(), Horse.class);
        horse.setInvulnerable(true);
        horse.setColor(WHITE);
        horse.setAdult();
        horse.setStyle(Horse.Style.WHITE);
        horse.setCollidable(false);
        horse.setBreed(false);
        horse.setPassenger(p);
        horse.setTamed(true);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        horse.getInventory().setArmor(new ItemStack(Material.DIAMOND_BARDING));
        ((EntityLiving) ((CraftEntity) horse).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.3F);
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                count += 1;
                if (count > spell3DMG) {
                    cantRideAgain.clear();
                    horse.removePassenger(p);
                    horse.remove();
                    p.getLocation().getWorld().spawnParticle(Particle.CLOUD, p.getLocation(), 100, 0.4F, 0.8F, 0.4F, 1);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_HORSE_GALLOP, 5, 1);
                    cancel();
                    return;
                }
                if (!p.isOnline()) {
                    cantRideAgain.clear();
                    horse.remove();
                    cancel();
                    return;
                }

            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }

    public static HashSet<UUID> bloodyEffect = new HashSet<>();

    @Override
    public void useUltimate(Player p) {
        if (!p.isOnGround()) {
            p.sendMessage(ChatColor.RED + "You need to be on ground to cast this spell!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        buffSpell();
        removeMana(p, 20);
        removeUlt(p);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + ultName + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + 20 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 5, 1);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, ultDMG, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, ultDMG, 0));
        bloodyEffect.add(p.getUniqueId());
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (count == ultDMG) {
                    bloodyEffect.remove(p.getUniqueId());
                    resetSpells();
                    cancel();
                }
                count += 1;
                p.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, p.getLocation().clone().add(0, 1.6, 0), 1, 0.32F, 0.2F, 0.32F, 0.1);
                p.getWorld().spawnParticle(Particle.BLOCK_CRACK,
                        p.getLocation(), 20,
                        new MaterialData(Material.REDSTONE_BLOCK));
                if (count % 40 == 0 && count < (ultDMG - 10)) {
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 5, 1);
                }
            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }


    @EventHandler
    public void onPlayerExitHorse(VehicleExitEvent e) {
        Vehicle horse = e.getVehicle();
        if (e.getExited() instanceof Player) {
            if (AaClassHashMapChoosing.getTlkClass((Player) e.getExited()) != null) {
                if (AaClassHashMapChoosing.getTlkClass((Player) e.getExited()).className == className) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBloodEffect(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (bloodyEffect.contains(p.getUniqueId())) {
                Entity entity = e.getEntity();
                entity.getWorld().spawnParticle(Particle.BLOCK_CRACK, entity.getLocation().clone().add(0, 1, 0), 80, new MaterialData(Material.REDSTONE_BLOCK));
            }
        }
    }

    @Override
    public AaClassSpellShowcase getClassInv() {
        return new AlpSpellInv();
    }
}
