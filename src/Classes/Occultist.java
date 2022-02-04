package Classes;

import ClassGUI.AaClassSpellShowcase;
import ClassGUI.OccultistSpellInv;
import ClassUpgrades.SkillPointsCore;
import GameMechanics.ManaSystem;
import Main.Main;
import Teams.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.UUID;

public class Occultist extends TlkClass implements Listener {
    public Occultist() {
        //Class name
        className = "Occultist";
        classColor = ChatColor.DARK_RED;
        classSpec = classColor + "" + ChatColor.BOLD + "Occultist, The Eye Of Darkness" + ChatColor.WHITE + "" + ChatColor.BOLD;
        //Weapon name
        weaponName = "HexTotem";

        //Spell Names
        spell1Name = "Death Star";
        spell2Name = "Possession";
        spell3Name = "Intimidation";
        ultName = "Armageddon ";

        //Mana Cost
        Mana1 = 6;
        Mana2 = 14;
        Mana3 = 16;

        // Spell DMG
        spell1DMG = 3;  //death star explosion DMG
        spell2DMG = 6 * 20;  //possess Duration
        spell3DMG = 4;  //removing mana from players
        ultDMG = 5;     //damage per thunderstrike 4DMG * 5 times
    }

    ManaSystem mana = new ManaSystem();
    AaClassActionBar cAc = new AaClassActionBar();

    @Override
    public void useSpell1(Player p) {
        removeMana(p, Mana1);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell1Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana1 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        p.getLocation().getWorld().playSound(p.getLocation(), Sound.ITEM_FIRECHARGE_USE, 5, 1);

        new BukkitRunnable() {
            double t = 0;
            Location loc = p.getLocation();
            Vector direction = loc.getDirection().normalize().multiply(2.0D);

            public void run() {
                t = t + 0.5;

                double x = direction.getX() * t;
                double y = direction.getY() * t + 1.5;
                double z = direction.getZ() * t;
                loc.add(x, y, z);
                p.getWorld().spawnParticle(Particle.FALLING_DUST, loc, 50, 0.2F, 0.2F, 0.2F,
                        1000);
                if (!loc.getBlock().isEmpty() && loc.getBlock().getType().isSolid()) {
                    targetFound(loc, p);
                    this.cancel();
                    return;
                }
                for (Entity e : loc.getWorld().getEntities()) {
                    if (e.getLocation().distance(loc) < 1.5) {
                        if (e != (p) && !(e instanceof Zombie)) {

                            if (e.getType().isAlive()) {
                                if (TeamManager.arentSameTeam(p, e)) {
                                    if (checkSpectator(e)) {
                                        LivingEntity b = (LivingEntity) e;
                                        b.damage(6 + SkillPointsCore.getDMGValue(p), p);
                                        p.getWorld().playSound(loc, Sound.ENTITY_WITHER_HURT, 5, 1);
                                        p.getWorld().spawnParticle(Particle.FALLING_DUST, loc, 300, 0.5F, 0.5F, 0.5F, 450);
                                        b.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2 * 20, 1));
                                        b.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 1 * 20, 1));
                                        this.cancel();
                                    }
                                }
                            }
                        }
                    }
                }
                loc.subtract(x, y, z);

                if (t > 20) {
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 0, 1);


    }


    public void targetFound(Location loc, Player p) {
        p.getWorld().playSound(loc, Sound.ENTITY_WITHER_HURT, 5, 1);
        p.getWorld().spawnParticle(Particle.FALLING_DUST, loc, 2500, 2F, 2F, 2F, 450);
        for (Entity e : loc.getWorld().getEntities()) {
            if (e.getLocation().distance(loc) < 4) {
                if (e.getType().isAlive() && !(e.equals(p)) && !(e instanceof Zombie)) {
                    if (TeamManager.arentSameTeam(p, e)) {
                        if (checkSpectator(e)) {
                            LivingEntity b1 = (LivingEntity) e;
                            b1.damage(spell1DMG + SkillPointsCore.getDMGValue(p), p);
                            b1.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2 * 20, 1));
                        }
                    }
                }

            }
        }

    }

    @Override
    public void useSpell2(Player p) {
        Entity entity = getTarget(p);
        if (entity == null || !(entity.getType().isAlive()) || !(entity.isValid())) {
            p.sendMessage(ChatColor.RED + "There are no targets to possess!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (entity instanceof Zombie || entity instanceof PolarBear || FrostBite.PolarBearEffect.contains(entity.getUniqueId())) {
            p.sendMessage(ChatColor.RED + "There are no targets to possess!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (!TeamManager.arentSameTeam(p, entity)) {
            p.sendMessage(ChatColor.RED + "There are no targets to possess!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (entity instanceof Wither || entity instanceof Golem || entity instanceof Skeleton) {
            p.sendMessage(ChatColor.RED + "There are no targets to possess!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (entity.getLocation().distance(p.getLocation()) > 4) {
            p.sendMessage(ChatColor.RED + "You need to get closer..");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeMana(p, Mana2);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_STARE, 5, 2);
        p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You possessed " + ChatColor.GRAY + getTarget(p).getName() + ChatColor.DARK_RED + "" + ChatColor.BOLD + " .. ");
        possess(entity, p);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + ChatColor.DARK_RED + "" + ChatColor.BOLD + spell2Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana2 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");

    }

    public static HashSet<UUID> possessed = new HashSet<UUID>();

    private void possess(Entity victim, Player occultist) {
        if (victim instanceof Player) {
            victim.sendMessage(ChatColor.DARK_RED + "You have been possessed!");
        }
        possessed.add(victim.getUniqueId());
        occultist.getWorld().playSound(victim.getLocation(), Sound.ENTITY_ENDERMEN_SCREAM, 5, 2);
        occultist.getWorld().spawnParticle(Particle.FALLING_DUST, occultist.getLocation(), 300, 0.5F, 0.5F, 1F, 450);
        occultist.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 6 * 20, 1));
        occultist.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 6 * 20, 145));
        occultist.teleport(victim);
        hidePlayers(occultist);
        occultist.setInvulnerable(true);
        LivingEntity e = (LivingEntity) victim;
        e.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 6 * 20, 1));
        e.setCollidable(false);
        new BukkitRunnable() {
            int t = 0;

            @Override
            public void run() {
                if (occultist.isDead()) {
                    e.setCollidable(false);
                    occultist.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 0, 0), true);
                    occultist.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 0, 0), true);
                    occultist.setInvulnerable(false);
                    showPlayers(occultist);
                    possessed.remove(victim.getUniqueId());
                    cancel();
                    return;
                }
                t += 1;
                victim.getLocation().setDirection(occultist.getLocation().getDirection());
                victim.getLocation().setPitch(occultist.getLocation().getPitch());
                victim.getLocation().setYaw(occultist.getLocation().getYaw());
                victim.getWorld().spawnParticle(Particle.BLOCK_CRACK, victim.getLocation().add(0, 0.2, 0), 20, new MaterialData(Material.REDSTONE_BLOCK));
                victim.teleport(occultist);
                if (t == spell2DMG || victim.isDead()) {
                    e.setCollidable(false);
                    occultist.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 0, 0), true);
                    occultist.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 0, 0), true);
                    occultist.setInvulnerable(false);
                    occultist.getWorld().playSound(occultist.getLocation(), Sound.ENTITY_ENDERMEN_SCREAM, 5, 2);
                    showPlayers(occultist);
                    possessed.remove(victim.getUniqueId());
                    occultist.getWorld().spawnParticle(Particle.FALLING_DUST, occultist.getLocation(), 300, 0.5F, 1F, 0.5F, 450);
                    cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }

    @Override
    public void useSpell3(Player p) {
        if (thereAreNoEntities(p, 10)) {
            p.sendMessage(ChatColor.RED + "There are no enemies nearby..");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeMana(p, Mana3);
        for (Entity entity : p.getWorld().getNearbyEntities(p.getLocation(), 10, 10, 10)) {
            if (!entity.equals(p) && entity.isValid() && entity.getType().isAlive() && !(entity instanceof Zombie)) {
                if (TeamManager.arentSameTeam(p, entity)) {
                    p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You removed " + ChatColor.AQUA + spell3DMG + " Mana" + ChatColor.DARK_RED + "" + ChatColor.BOLD + " from " + entity.getName() + "..");
                    displayParticles(p, entity);
                    if (entity instanceof Player) {
                        mana.setMana((Player) entity, mana.getMana(p) - spell3DMG);
                    }
                    cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + ChatColor.DARK_RED + "" + ChatColor.BOLD + spell3Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana3 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
                    entity.getWorld().spawnParticle(Particle.BLOCK_CRACK, entity.getLocation().add(0, 1, 0), 1000, new MaterialData(Material.REDSTONE_BLOCK));
                    entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ENDERMEN_SCREAM, 5, 1);
                }
            }
        }
    }

    public void displayParticles(Player p, Entity entity) {
        Zombie bat = p.getWorld().spawn(entity.getLocation(), Zombie.class);
        bat.setInvulnerable(true);
        bat.setCustomName(p.getName());
        bat.setCustomNameVisible(false);
        bat.setSilent(true);
        bat.setCollidable(false);
        bat.setBaby(true);
        bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999 * 20, 3));
        PetFollow(p, bat, 0.24F);
        new BukkitRunnable() {
            int t = 0;

            @Override
            public void run() {
                t += 1;
                if (t == (10 * 20)) {
                    bat.remove();
                    cancel();

                }
                if (bat.getLocation().distance(p.getLocation()) > 20) {
                    p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You lost a terrified soul..");
                    p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1, 1);
                    bat.remove();
                    cancel();
                }
                if (bat.isDead()) {
                    cancel();
                }
                if (!(bat.isDead())) {
                    bat.getWorld().spawnParticle(Particle.FALLING_DUST, bat.getLocation().add(0, 1.5, 0), 3, 0.01F, 0.01F, 0.01F, 450);
                }
                if (bat.getLocation().distance(p.getLocation()) < 1) {
                    if (p.getHealth() > p.getMaxHealth() - 2) {
                        p.setHealth(p.getMaxHealth());
                        p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You inhaled a terrified soul" + ChatColor.RED + "" + ChatColor.BOLD + " [+2HP]" + ChatColor.DARK_RED + "" + ChatColor.BOLD + "..");
                        bat.remove();
                        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_AMBIENT, 5, 2);
                        this.cancel();
                    }
                    if (p.getHealth() <= (p.getMaxHealth() - 2)) {
                        p.setHealth(p.getHealth() + 2);
                        p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You inhaled a terrified soul" + ChatColor.RED + "" + ChatColor.BOLD + " [+2HP]" + ChatColor.DARK_RED + "" + ChatColor.BOLD + "..");
                        bat.remove();
                        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_AMBIENT, 5, 2);
                        this.cancel();
                    }
                }
            }
        }.runTaskTimer(Main.plugin, 0, 1);

    }

    @Override
    public void useUltimate(Player p) {
        removeMana(p, 20);
        removeUlt(p);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + ChatColor.DARK_RED + "" + ChatColor.BOLD + ultName + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + 20 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        p.getWorld().setThundering(true);
        p.getWorld().setStorm(true);
        new BukkitRunnable() {
            int t = 0;

            @Override
            public void run() {
                t += 1;
                hurtPlayers(p);
                if (t == 5) {
                    p.getWorld().setStorm(false);
                    p.getWorld().setThundering(false);
                    cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 0, 20 * 3);
    }

    private void hurtPlayers(Player p) {
        for (Entity entity : p.getWorld().getNearbyEntities(p.getLocation(), 12, 12, 12)) {
            if (entity.getType().isAlive() && !entity.equals(null) && !(entity.equals(p))) {
                if (TeamManager.arentSameTeam(p, entity)) {
                    if (checkSpectator(entity)) {
                        entity.getWorld().strikeLightningEffect(entity.getLocation());
                        ((LivingEntity) entity).damage(ultDMG + SkillPointsCore.getDMGValue(p), p);
                        entity.getWorld().spawnParticle(Particle.BLOCK_CRACK, entity.getLocation().add(0, 1, 0), 1000, new MaterialData(Material.REDSTONE_BLOCK));
                        entity.getLocation().getWorld().playSound(entity.getLocation(), Sound.ENTITY_ENDERMEN_DEATH, 5, 2);
                        ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 1, 3));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDmgedByZombie(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof Player && e.getDamager() instanceof Zombie) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void rez(EntityResurrectEvent e) {
        e.setCancelled(true);
    }

    @Override
    public AaClassSpellShowcase getClassInv() {
        return new OccultistSpellInv();
    }
}
