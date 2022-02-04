package Classes;

import ClassGUI.AaClassSpellShowcase;
import ClassGUI.PoseidonSpellInv;
import ClassGUI.VerunSpellInv;
import ClassUpgrades.SkillPointsCore;
import Main.Main;
import Teams.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Poseidon extends TlkClass {
    public Poseidon() {
        //Class name
        className = "Poseidon";
        classColor = ChatColor.BLUE;
        classSpec = classColor + "" + ChatColor.BOLD + "Poseidon, The Protector Of Seas" + ChatColor.WHITE + "" + ChatColor.BOLD;
        //Weapon name
        weaponName = "Ocean Shard";

        //Spell Names
        spell1Name = "Water Splash";
        spell2Name = "Holy Water";
        spell3Name = "Flop";
        ultName = "Temple Boulder";

        //Mana Cost
        Mana1 = 10;
        Mana2 = 12;
        Mana3 = 14;

        // Spell DMG
        spell1DMG = 7;   //splash dmg
        spell2DMG = 6;   // heal value
        spell3DMG = 3;   //damage on flop land
        ultDMG = 12; // boulder damage
    }

    AaClassActionBar cAc = new AaClassActionBar();

    @Override
    public void useSpell1(Player p) {
        Entity entity = getTarget(p);
        if (entity == null) {
            p.sendMessage(ChatColor.RED + "There are no enemies in your line of sight!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (!entity.getType().isAlive() || entity instanceof Zombie) {
            p.sendMessage(ChatColor.RED + "There are no enemies in your line of sight!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (!TeamManager.arentSameTeam(p, entity)) {
            p.sendMessage(ChatColor.RED + "There are no enemies in your line of sight!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (entity.getLocation().distance(p.getLocation()) > 20) {
            p.sendMessage(ChatColor.RED + "The target is too far away!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeMana(p, Mana1);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell1Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana1 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        entity.getWorld().spawnParticle(Particle.WATER_SPLASH,
                entity.getLocation().clone().subtract(0, 1, 0), 6000, 1F, 1F, 1F, 0.1);
        entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_GENERIC_SPLASH, 5, 1);
        LivingEntity livE = (LivingEntity) entity;
        livE.damage(spell1DMG + SkillPointsCore.getDMGValue(p), p);
        entity.setVelocity(new Vector(0, 1, 0));
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                count += 1;
                entity.getWorld().spawnParticle(Particle.WATER_WAKE, entity.getLocation()
                        , 20, 0.5F, 0.5F, 0.5F, 0.1);
                if (entity.isOnGround() || !entity.isValid() || count == (20 * 4)) {
                    cancel();
                    return;
                }
            }
        }.runTaskTimer(Main.plugin, 2, 1);
    }

    @Override
    public void useSpell2(Player p) {
        removeMana(p, Mana2);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell2Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana2 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        for (Entity entity : p.getWorld().getNearbyEntities(p.getLocation(), 6, 6, 6)) {
            if (entity.getType().isAlive() && entity instanceof Player) {
                if (!TeamManager.arentSameTeam(p, entity)) {
                    LivingEntity livE = (LivingEntity) entity;
                    healPlayer(livE, spell2DMG);
                    livE.getWorld().spawnParticle(Particle.WATER_SPLASH,
                            entity.getLocation().clone().add(0, 0.6, 0), 1000, 0.5F, 0.5F, 0.5F, 0.1);
                    livE.getWorld().playSound(livE.getLocation(), Sound.ENTITY_GENERIC_SPLASH, 5, 1);
                }
            }
        }
        p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "You healed your nearby allies for " + ChatColor.RED +
                "" + ChatColor.BOLD + spell2DMG + " HP" + ChatColor.BLUE + "" + ChatColor.BOLD + ".");

    }

    public void healPlayer(LivingEntity p, Integer heal) {
        if ((p.getHealth() + heal) > p.getMaxHealth()) {
            p.setHealth(p.getMaxHealth());
            return;
        }
        p.setHealth(p.getHealth() + heal);
    }

    @Override
    public void useSpell3(Player p) {
        removeMana(p, Mana3);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell3Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana3 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        p.setVelocity(new Vector(0, 1, 0));
        guardianDeath(p);
        new BukkitRunnable() {
            double t = 0;

            @Override
            public void run() {
                if (!p.isOnline() || p.isDead()) {
                    cancel();
                    return;
                }
                if (p.isOnGround()) {
                    damageNearbyEntities(p);
                    t = t + 1;
                    p.setVelocity(p.getLocation().getDirection().add(new Vector(0, 0.5, 0)).multiply(1.5f));
                    guardianDeath(p);
                }
                if (p.isOnGround() && t == 2) {
                    damageNearbyEntities(p);
                    guardianDeath(p);
                    this.cancel();

                }

            }

        }.runTaskTimer(Main.plugin, 5, 2);
    }

    public void damageNearbyEntities(Player p) {
        for (Entity entity : p.getWorld().getNearbyEntities(p.getLocation(), 3, 3, 3)) {
            if (entity.getType().isAlive() && entity != p) {
                if (TeamManager.arentSameTeam(p, entity)) {
                    ((LivingEntity) entity).damage(spell3DMG + SkillPointsCore.getDMGValue(p), p);
                }
            }
        }
    }

    public void guardianDeath(Player p) {
        p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_FLOP, 7, 1);
        p.getLocation().getWorld().spawnParticle(Particle.WATER_SPLASH, p.getLocation(), 1000, 1F, 2F, 1F, 0.01);
        Location loc = p.getLocation();
        Item b1 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.PRISMARINE_SHARD, 1));
        Item b2 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.PRISMARINE_SHARD, 1));
        Item b3 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.PRISMARINE_SHARD, 1));
        Item b4 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.PRISMARINE_SHARD, 1));
        Item b5 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.PRISMARINE_SHARD, 1));
        Item b6 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.PRISMARINE_SHARD, 1));
        Item b7 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.PRISMARINE_SHARD, 1));
        Item b8 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.PRISMARINE_SHARD, 1));
        Item b9 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.PRISMARINE_SHARD, 1));
        ////
        b1.setPickupDelay(20 * 9999);
        b2.setPickupDelay(20 * 9999);
        b3.setPickupDelay(20 * 9999);
        b4.setPickupDelay(20 * 9999);
        b5.setPickupDelay(20 * 9999);
        b6.setPickupDelay(20 * 9999);
        b7.setPickupDelay(20 * 9999);
        b8.setPickupDelay(20 * 9999);
        b9.setPickupDelay(20 * 9999);
        ////
        b1.setVelocity(new Vector(0, 0.2, 0.1));
        b2.setVelocity(new Vector(0.1, 0.2, 0));
        b3.setVelocity(new Vector(0.1, 0.2, 0.1));
        b4.setVelocity(new Vector(0, 0.2, -0.1));
        b5.setVelocity(new Vector(-0.1, 0.2, 0));
        b6.setVelocity(new Vector(-0.1, 0.2, -0.1));
        b7.setVelocity(new Vector(-0.1, 0.2, 0.1));
        b8.setVelocity(new Vector(0.1, 0.2, -0.1));
        b9.setVelocity(new Vector(0.1, 0.2, -0.1));
        new BukkitRunnable() {

            @Override
            public void run() {
                b1.remove();
                b2.remove();
                b3.remove();
                b4.remove();
                b5.remove();
                b6.remove();
                b7.remove();
                b8.remove();
                b9.remove();
            }

        }.runTaskLater(Main.plugin, 30);
    }

    @Override
    public void useUltimate(Player p) {
        removeUlt(p);
        removeMana(p, 20);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + ultName + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + 20 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        p.spawnParticle(Particle.MOB_APPEARANCE, p.getLocation(), 1, 0.1F, 0.1F, 0.1F, 6);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 5, 1);
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (count == 3) {
                    cancel();
                    return;
                }
                count += 1;
                exSpell(p);
            }
        }.runTaskTimer(Main.plugin, 30, 14);

    }

    public void exSpell(Player p) {
        FallingBlock b1 = p.getWorld().spawnFallingBlock(p.getLocation().add(0, 1.5, 0), Material.PRISMARINE, (byte) 0);
        b1.setVelocity(p.getEyeLocation().getDirection().multiply(2.0F));
        b1.setDropItem(false);
        b1.getWorld().playSound(b1.getLocation(), Sound.WEATHER_RAIN_ABOVE, 10, 1);
        new BukkitRunnable() {
            double t = 0;

            public void run() {
                t = t + 1;
                b1.getLocation().getWorld().spawnParticle(Particle.WATER_SPLASH, b1.getLocation(), 250, 0.2F, 0.2F,
                        0.2F, 100);
                for (Entity e : b1.getLocation().getWorld().getEntities()) {
                    if (e.getLocation().distance(b1.getLocation()) < 2.4) {
                        if (e != (p)) {
                            if (!(e instanceof Zombie)) {
                                if (e.getType().isAlive()) {
                                    if (TeamManager.arentSameTeam(p, e)) {
                                        LivingEntity b = (LivingEntity) e;
                                        Location locb = b1.getLocation();
                                        b.damage(3, p);
                                        targetFound(b, p, locb);
                                        b1.remove();
                                        cancel();
                                    }
                                }
                            }
                        }
                    }
                }
                if (t == 50) {
                    b1.remove();
                    this.cancel();
                }
                if (b1.isOnGround()) {
                    b1.remove();
                    this.cancel();
                }
            }

        }.runTaskTimer(Main.plugin, 0, 2);
    }

    @SuppressWarnings("deprecation")
    public void targetFound(Damageable b, Player p, Location locb) {
        p.getLocation().getWorld().playSound(locb, Sound.ENTITY_ENDERDRAGON_FIREBALL_EXPLODE, 5, 1);
        locb.getWorld().spawnParticle(Particle.WATER_DROP, locb, 1000, 2F, 2F, 2F, 0.1);
        FallingBlock b1 = p.getWorld().spawnFallingBlock(locb, Material.PRISMARINE, (byte) 0);
        FallingBlock b2 = p.getWorld().spawnFallingBlock(locb, Material.PRISMARINE, (byte) 0);
        FallingBlock b3 = p.getWorld().spawnFallingBlock(locb, Material.PRISMARINE, (byte) 0);
        FallingBlock b4 = p.getWorld().spawnFallingBlock(locb, Material.PRISMARINE, (byte) 0);
        FallingBlock b5 = p.getWorld().spawnFallingBlock(locb, Material.PRISMARINE, (byte) 0);
        FallingBlock b6 = p.getWorld().spawnFallingBlock(locb, Material.PRISMARINE, (byte) 0);
        FallingBlock b7 = p.getWorld().spawnFallingBlock(locb, Material.PRISMARINE, (byte) 0);
        FallingBlock b8 = p.getWorld().spawnFallingBlock(locb, Material.PRISMARINE, (byte) 0);
        b1.setDropItem(false);
        b2.setDropItem(false);
        b3.setDropItem(false);
        b4.setDropItem(false);
        b5.setDropItem(false);
        b6.setDropItem(false);
        b7.setDropItem(false);
        b8.setDropItem(false);
        b1.setVelocity(new Vector(0, 0.5, 0.25));
        b2.setVelocity(new Vector(0.25, 0.5, 0));
        b3.setVelocity(new Vector(0.25, 0.5, 0.25));
        b4.setVelocity(new Vector(0, 0.5, -0.25));
        b5.setVelocity(new Vector(-0.25, 0.5, 0));
        b6.setVelocity(new Vector(-0.25, 0.5, -0.25));
        b7.setVelocity(new Vector(-0.25, 0.5, 0.25));
        b8.setVelocity(new Vector(0.25, 0.5, -0.25));
        Vector direction = b.getLocation().toVector().subtract(p.getLocation().toVector()).normalize();
        direction.setX(direction.getX() * 1);
        direction.setY(direction.getY() * 1 + 0.5);
        direction.setZ(direction.getZ() * 1);
        b.setVelocity(direction);
        Location loc = b.getLocation();
        b.getWorld().spawnParticle(Particle.WATER_BUBBLE, loc, 500, 2F, 2F, 2F, 50);
        for (Entity e : loc.getWorld().getEntities()) {
            if (e.getLocation().distance(loc) < 3) {
                if (e.getType().isAlive() && !(e.equals(p))) {
                    if (!(e instanceof Zombie)) {
                        if (TeamManager.arentSameTeam(p, e)) {
                            LivingEntity b21 = (LivingEntity) e;
                            b21.damage(ultDMG + SkillPointsCore.getDMGValue(p), p);
                        }
                    }
                }
            }
        }
    }

    @Override
    public AaClassSpellShowcase getClassInv() {
        return new PoseidonSpellInv();
    }
}
