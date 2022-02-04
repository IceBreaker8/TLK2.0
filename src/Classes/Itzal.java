package Classes;

import ClassGUI.AaClassSpellShowcase;
import ClassGUI.ItzalSpellInv;
import ClassUpgrades.SkillPointsCore;
import Main.Main;
import Teams.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Itzal extends TlkClass {
    public Itzal() {
        //Class name
        className = "Itzal";
        classColor = ChatColor.DARK_GRAY;
        classSpec = classColor + "" + ChatColor.BOLD + "Itzal, The Hidden Shadow" + ChatColor.WHITE + "" + ChatColor.BOLD;
        //Weapon name
        weaponName = "Void Shard";

        //Spell Names
        spell1Name = "Void Dash";
        spell2Name = "Shadow Walk";
        spell3Name = "Black Circle";
        ultName = "Shadow Stabs";

        //Mana Cost
        Mana1 = 10;
        Mana2 = 12;
        Mana3 = 14;

        // Spell DMG
        spell1DMG = 7;  //dash dmg
        spell2DMG = 20 * 4;  // shadow walk time
        spell3DMG = 20 * 3; // health regen 2hp per sec
        ultDMG = 16;     //stab dmg
    }

    private int dmg = 2;
    AaClassActionBar cAc = new AaClassActionBar();

    @Override
    public void useSpell1(Player p) {
        Location pLoc = p.getLocation();
        Vector direction = p.getLocation().getDirection();
        Location tpLoc = p.getLocation().add(p.getLocation().getDirection().multiply(16F));
        p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_BULLET_HIT, 5, 1);
        if (!tpLoc.getBlock().isEmpty()) {
            p.sendMessage(ChatColor.RED + "You can't dash into blocks..");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeMana(p, Mana1);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell1Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana1 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        displayDashParticle(p, pLoc, direction);
        p.teleport(tpLoc);

    }

    public void displayDashParticle(Player p, Location start, Vector increment) {
        for (int x = 1; x < 16; x++) {
            increment.multiply(x / 16);
            Location tpLoc = start.add(start.getDirection());
            for (Entity entity : p.getWorld().getNearbyEntities(start, 20, 20, 20)) {
                if (entity.getLocation().distance(tpLoc) < 2.2 && entity.getType().isAlive() && entity != p) {
                    if (TeamManager.arentSameTeam(p, entity)) {
                        if (checkSpectator(entity)) {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            livingEntity.damage(spell1DMG + SkillPointsCore.getDMGValue(p), p);
                        }
                    }
                }
            }
            tpLoc.getWorld().spawnParticle(Particle.SMOKE_NORMAL, tpLoc, 20, 0.1F, 0.1F, 0.1F, 0.01);

        }
    }

    public static HashMap<UUID, Boolean> invis = new HashMap<>();

    @Override
    public void useSpell2(Player p) {
        if (invis.containsKey(p.getUniqueId())) {
            p.sendMessage(ChatColor.RED + "You can't cast this spell twice!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeMana(p, Mana2);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell2Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana2 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 7, 1);
        p.getWorld().spawnParticle(Particle.SMOKE_LARGE, p.getLocation(), 200, 0.8F, 0.8F, 0.8F, 0.1);
        hidePlayers(p);
        invis.put(p.getUniqueId(), true);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, spell2DMG, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, spell2DMG, 8));
        new BukkitRunnable() {
            @Override
            public void run() {
                showPlayers(p);
                invis.remove(p.getUniqueId());
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 7, 2);
                p.spawnParticle(Particle.SMOKE_LARGE, p.getLocation(), 200, 0.8F, 0.8F, 0.8F, 0.1);
            }
        }.runTaskLater(Main.plugin, spell2DMG);
    }

    public void useSpell3(Player p) {
        removeMana(p, Mana3);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell3Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana3 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        int radius = 4;
        Location pLoc = p.getLocation();
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (count == spell3DMG) {
                    cancel();
                    return;
                }
                if (count % 20 == 0) {
                    healOrDmg(p, pLoc, radius);
                }
                for (Location loc : getCircle(pLoc.clone(), radius, 40)) {
                    loc.getWorld().spawnParticle(Particle.SMOKE_NORMAL,
                            loc, 1, 0.2F, 0.2F, 0.2F, 0.001);
                }

                count += 1;
            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }

    public void healOrDmg(Player p, Location center, int radius) {
        for (Entity entity : p.getWorld().getNearbyEntities(center, radius, radius, radius)) {
            if (entity instanceof Golem || entity instanceof Skeleton || entity instanceof Chicken || entity instanceof Rabbit)
                ((LivingEntity) entity).damage(dmg + SkillPointsCore.getDMGValue(p), p);
            if (entity.getType().isAlive() && entity instanceof Player) {
                Player player = (Player) entity;
                if (player.getLocation().distance(center.clone()) < radius) {
                    if (!TeamManager.arentSameTeam(p, player)) {
                        player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "You got 2 HP..");
                        healPlayer(player, dmg);
                        player.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 5, 2);
                    } else {
                        player.damage(dmg + SkillPointsCore.getDMGValue(p), p);
                    }
                }

            }
        }
    }


    public void healPlayer(Player p, int heal) {
        if (p.getHealth() <= (p.getMaxHealth() - heal)) {
            p.setHealth(p.getHealth() + heal);
        } else if (p.getHealth() > (p.getMaxHealth() - heal) && p.getHealth() < p.getMaxHealth()) {
            p.setHealth(p.getMaxHealth());
        }
    }

    public ArrayList<Location> getCircle(Location center, double radius, int amount) {
        World world = center.getWorld();
        double increment = (2 * Math.PI) / amount;
        ArrayList<Location> locations = new ArrayList<Location>();
        for (int i = 0; i < amount; i++) {
            double angle = i * increment;
            double x = center.getX() + (radius * Math.cos(angle));
            double z = center.getZ() + (radius * Math.sin(angle));
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }

    @Override
    public void useUltimate(Player p) {
        Location start = p.getLocation();
        shadowStab(p, start.clone());

    }

    private void shadowStab(Player p, Location start) {
        int radius = 16;
        ArrayList<Entity> targets = getNearbyTargets(start, p, radius);
        if (targets.isEmpty()) {
            p.sendMessage(ChatColor.RED + "There are no nearby entities to target!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeMana(p, 20);
        removeUlt(p);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + ultName + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + 20 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        int size = targets.size();
        p.setInvulnerable(true);
        hidePlayers(p);
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (count == size) {
                    p.teleport(start);
                    for (PotionEffect effect : p.getActivePotionEffects()) {
                        p.removePotionEffect(effect.getType());
                    }
                    showPlayers(p);
                    p.setInvulnerable(false);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 7, 1);
                    p.getWorld().spawnParticle(Particle.SMOKE_LARGE, p.getLocation(), 200, 0.8F, 0.8F, 0.8F, 0.1);
                    cancel();
                    return;
                }
                LivingEntity entity = (LivingEntity) targets.get(count);
                if (entity.getLocation().distance(start) < radius) {
                    if (entity.isValid()) {
                        Location targetLoc = entity.getLocation().subtract(entity.getLocation().getDirection().multiply(1F));
                        p.teleport(targetLoc);
                        entity.damage(ultDMG + SkillPointsCore.getDMGValue(p), p);
                        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 7, 1);
                        p.getWorld().spawnParticle(Particle.SMOKE_LARGE, p.getLocation(), 200, 0.8F, 0.8F, 0.8F, 0.1);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, spell2DMG, 9999));
                    }
                }
                count += 1;
            }

        }.runTaskTimer(Main.plugin, 0, 16);

    }

    public ArrayList<Entity> getNearbyTargets(Location start, Player p, int radius) {
        ArrayList<Entity> targets = new ArrayList<>();
        for (Entity entity : p.getWorld().getNearbyEntities(start, radius, radius, radius)) {
            if (entity.getType().isAlive() && entity != p && TeamManager.arentSameTeam(p, entity)) {
                targets.add(entity);
            }
        }
        return targets;
    }

    @Override
    public AaClassSpellShowcase getClassInv() {
        return new ItzalSpellInv();
    }
}