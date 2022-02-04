package Classes;

import ClassGUI.AaClassSpellShowcase;
import ClassGUI.ItzalSpellInv;
import ClassGUI.SoldierSpellInv;
import ClassUpgrades.SkillPointsCore;
import Main.Main;
import Teams.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class Soldier extends TlkClass {
    public Soldier() {
        //Class name
        className = "Soldier";
        classColor = ChatColor.RED;
        classSpec = classColor + "" + ChatColor.BOLD + "Soldier, The Brave Fighter" + ChatColor.WHITE + "" + ChatColor.BOLD;
        //Weapon name
        weaponName = "Justice Axe";

        //Spell Names
        spell1Name = "Slash";
        spell2Name = "Strafe";
        spell3Name = "Strike";
        ultName = "Succor";

        //Mana Cost
        Mana1 = 10;
        Mana2 = 14;
        Mana3 = 14;

        // Spell DMG
        spell1DMG = 3;  // slash dmg 3*2
        spell2DMG = 7;  // strafe dmg on first land
        spell3DMG = 4; // strike dmg (+3 more on touch)
        ultDMG = 3;   //succor damage
    }

    AaClassActionBar cAc = new AaClassActionBar();

    private int hopCount = 5;

    @Override
    public void useSpell1(Player p) {
        removeMana(p, Mana1);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell1Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana1 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_IRONGOLEM_ATTACK, 5, 4);
        Location loc = p.getLocation().add(p.getLocation().getDirection().setY(0).normalize().multiply(3));
        slashAttack(loc, p);
    }

    private void slashAttack(Location loc, Player p) {
        Vector vect = p.getLocation().getDirection().setY(0.3).normalize().multiply(1.6);
        for (Entity entity : p.getWorld().getNearbyEntities(loc, 5, 5, 5)) {
            if (entity.getType().isAlive() && entity != p) {
                if (TeamManager.arentSameTeam(p, entity)) {
                    if (entity instanceof Zombie) continue;
                    LivingEntity livE = (LivingEntity) entity;
                    livE.damage(spell1DMG + SkillPointsCore.getDMGValue(p), p);
                    livE.getWorld().spawnParticle(Particle.FIREWORKS_SPARK,
                            livE.getLocation(), 80, 1, 1, 1, 0.1F);
                    livE.getWorld().playSound(livE.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 5, 4);
                    livE.setVelocity(new Vector(0, 0, 0));
                    hitAgain(livE, p, vect);
                }
            }
        }
    }

    private void hitAgain(LivingEntity e, Player p, Vector vector) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (e.isDead()) return;
                e.damage(spell1DMG, p);
                e.getWorld().spawnParticle(Particle.FIREWORKS_SPARK,
                        e.getLocation(), 80, 1, 1, 1, 0.1F);
                e.getWorld().playSound(e.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 5, 4);
                e.setVelocity(vector);
            }
        }.runTaskLater(Main.plugin, 8);
    }

    @Override
    public void useSpell2(Player p) {
        removeMana(p, Mana2);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell2Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana2 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 5, 2);
        p.setVelocity(p.getLocation().getDirection().setY(0.9F).multiply(0.8F));
        new BukkitRunnable() {
            int t = 0;

            @Override
            public void run() {
                if (!p.isOnline() || p.isDead()) {
                    cancel();
                    return;
                }
                if (p.isOnGround()) {
                    t = t + 1;
                    if (t == 1) {
                        groundParticlesDisplay(p, 120);
                        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5, 2);
                        for (Entity entity : p.getWorld().getNearbyEntities(p.getLocation(),
                                4, 4, 4)) {
                            if (entity.getType().isAlive() && entity != p) {
                                if (entity instanceof Zombie) continue;
                                if (TeamManager.arentSameTeam(p, entity)) {
                                    LivingEntity livE = (LivingEntity) entity;
                                    livE.damage(spell2DMG + SkillPointsCore.getDMGValue(p), p);
                                    livE.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 128));
                                    livE.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 128));
                                    livE.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20, 1));
                                    livE.setVelocity(p.getLocation().getDirection().setY(0.3).normalize().multiply(1.6));

                                }
                            }
                        }
                    } else {
                        groundParticlesDisplay(p, 80);
                    }
                    p.setVelocity(p.getLocation().getDirection().setY(0.7F).multiply(0.8F));
                    if (t == hopCount) {
                        cancel();
                    }
                }
            }

        }.runTaskTimer(Main.plugin, 5, 2);
    }

    private void groundParticlesDisplay(Player p, int number) {
        p.getWorld().spawnParticle(Particle.BLOCK_CRACK, p.getLocation(), number,
                new MaterialData(p.getLocation().clone().subtract(0, 1, 0).getBlock().getType()));
        p.getWorld().spawnParticle(Particle.BLOCK_CRACK, p.getLocation(), number,
                new MaterialData(p.getLocation().clone().subtract(0, 1, -1).getBlock().getType()));
        p.getWorld().spawnParticle(Particle.BLOCK_CRACK, p.getLocation(), number,
                new MaterialData(p.getLocation().clone().subtract(0, 1, 1).getBlock().getType()));
        p.getWorld().spawnParticle(Particle.BLOCK_CRACK, p.getLocation(), number,
                new MaterialData(p.getLocation().clone().subtract(-1, 1, 0).getBlock().getType()));
        p.getWorld().spawnParticle(Particle.BLOCK_CRACK, p.getLocation(), number,
                new MaterialData(p.getLocation().clone().subtract(1, 1, 0).getBlock().getType()));
    }

    @Override
    public void useSpell3(Player p) {
        removeMana(p, Mana3);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell3Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana3 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        Location loc = p.getLocation().add(p.getLocation().getDirection().multiply(8F));
        drawLine(p.getLocation().clone().add(0, 1.4, 0), loc.clone().add(0, 1.4, 0), 0.1, p);
    }

    public static ArrayList<UUID> canthitagain = new ArrayList<>();

    public void drawLine(Location point1, Location point2, double space, Player p) {
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_PISTON_EXTEND, 5, 3);
        World world = point1.getWorld();
        double distance = point1.distance(point2);
        Vector p1 = point1.toVector();
        Vector p2 = point2.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
        double length = 0;
        for (; length < distance; p1.add(vector)) {
            for (Entity entity : p.getWorld().getNearbyEntities(new Location(world, p1.getX(), p1.getY(), p1.getZ()),
                    1, 1, 1)) {
                if (entity.getType().isAlive() && entity != p) {
                    if (canthitagain.contains(entity.getUniqueId())) continue;
                    if (TeamManager.arentSameTeam(p, entity)) {
                        canthitagain.add(entity.getUniqueId());
                        entity.getWorld().spawnParticle(Particle.DRIP_LAVA, entity.getLocation(),
                                80, 0.8, 0.8, 0.8, 3);
                        entity.getWorld().playSound(entity.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 5, 3);
                        ((LivingEntity) entity).damage(spell3DMG + SkillPointsCore.getDMGValue(p), p);
                    }
                }
            }
            world.spawnParticle(Particle.BLOCK_CRACK, new Location(world, p1.getX(), p1.getY(), p1.getZ()), 2,
                    new MaterialData(Material.IRON_BLOCK));
            length += space;
        }
        if (canthitagain.size() == 0) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    p.setVelocity(p.getLocation().getDirection().setY(0.3).multiply(2F));
                }
            }.runTaskLater(Main.plugin, 10);
        } else {
            UUID id = getLastElement(canthitagain);
            canthitagain.clear();
            Entity e = getEntityByUniqueId(id);
            new BukkitRunnable() {
                @Override
                public void run() {
                    dmgEnemiesOnHop(p);
                    p.setVelocity(e.getLocation().clone().subtract(p.getLocation().clone()).toVector().setY(1.4).multiply(0.24));
                }
            }.runTaskLater(Main.plugin, 10);
        }
        canthitagain.clear();


    }

    public Entity getEntityByUniqueId(UUID uniqueId) {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getUniqueId().equals(uniqueId))
                    return entity;
            }
        }

        return null;
    }

    public static <T> T getLastElement(final Iterable<T> elements) {
        final Iterator<T> itr = elements.iterator();
        T lastElement = itr.next();

        while (itr.hasNext()) {
            lastElement = itr.next();
        }

        return lastElement;
    }

    public void dmgEnemiesOnHop(Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Entity entity : p.getWorld().getNearbyEntities(p.getLocation(), 1.2, 1.2, 1.2)) {
                    if (entity.getType().isAlive() && entity != p) {
                        if (entity instanceof Zombie) continue;
                        if (TeamManager.arentSameTeam(p, entity)) {
                            LivingEntity livE = (LivingEntity) entity;
                            livE.damage(spell3DMG + 3);
                            livE.setVelocity(p.getLocation().getDirection().multiply(4F));
                            livE.getWorld().playSound(livE.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 5, 3);
                        }
                    }
                }
                if (p.isOnGround()) {
                    cancel();
                    return;
                }
            }
        }.runTaskTimer(Main.plugin, 6, 4);
    }

    @Override
    public void useUltimate(Player p) {
        removeMana(p, 20);
        removeUlt(p);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + ultName + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + 20 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        shockwave(p);
    }

    public void shockwave(Player p) {
        for(Player players : p.getServer().getOnlinePlayers()){
            if(!TeamManager.arentSameTeam(p, players)){
                players.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 10, 0));
            }
        }
        Location loc = p.getLocation();
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 7, 3);
        new BukkitRunnable() {
            double t = 0;

            public void run() {
                t += 0.2;
                makeCircle(p, t, loc.clone().add(0, 1, 0));
                if ((int) t % 2 == 0) {
                    damageEntities(p, t, loc);
                }
                if ((int) t == 16) {
                    this.cancel();
                }

            }

        }.runTaskTimer(Main.plugin, 0, 1);
    }

    public void makeCircle(Player p, double radius, Location pLoc) {
        final int points = 36;
        final Location origin = pLoc;
        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;
            Location point = origin.clone().add(radius * Math.sin(angle), 0.0d, radius * Math.cos(angle));
            p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, point, 1, 0, 0, 0, 0);
            p.getWorld().spawnParticle(Particle.WATER_WAKE, point, 3, 0, 0, 0, 0);
        }
    }

    public void damageEntities(Player p, double radius, Location loc) {
        for (Entity entity : p.getWorld().getNearbyEntities(loc, radius, radius, radius)) {
            if (entity.getType().isAlive() && entity != p) {
                if (TeamManager.arentSameTeam(p, entity)) {
                    LivingEntity livE = (LivingEntity) entity;
                    livE.damage(ultDMG + SkillPointsCore.getDMGValue(p), p);
                    livE.setVelocity((livE.getLocation().clone())
                            .subtract(loc.clone()).toVector().multiply(0.04F).setY(0.4));
                }
            }
        }
    }

    @Override
    public AaClassSpellShowcase getClassInv() {
        return new SoldierSpellInv();
    }
}
