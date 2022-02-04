package Classes;

import ClassGUI.AaClassSpellShowcase;
import ClassGUI.RiftorSpellInv;
import ClassUpgrades.SkillPointsCore;
import Main.Main;
import Teams.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class Riftor extends TlkClass implements Listener {
    public Riftor() {
        //Class name
        className = "Riftor";
        classColor = ChatColor.DARK_PURPLE;
        classSpec = classColor + "" + ChatColor.BOLD + "Riftor, The Dimension Breacher" + ChatColor.WHITE + "" + ChatColor.BOLD;
        //Weapon name
        weaponName = "RiftMaker";

        //Spell Names
        spell1Name = "Switch";
        spell2Name = "Bidimentional Projectiles";
        spell3Name = "Rift Walk";
        ultName = "Rift Expansion";

        //Mana Cost
        Mana1 = 10;
        Mana2 = 12;
        Mana3 = 14;

        // Spell DMG
        spell1DMG = 6; //switch dmg
        spell2DMG = 5;  //projectile DMG
        spell3DMG = 20 * 20;  //portals duration
        ultDMG = 0;
    }

    AaClassActionBar cAc = new AaClassActionBar();

    @Override
    public void useSpell1(Player p) {
        Location pLoc = p.getLocation();
        Entity ee = getTarget(p);
        if (ee == null || !(ee.isValid()) || !(ee.getType().isAlive())) {
            p.sendMessage(ChatColor.RED + "There are no targets in your line of sight..");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (!TeamManager.arentSameTeam(p, ee)) {
            p.sendMessage(ChatColor.RED + "There are no targets in your line of sight..");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (ee instanceof Zombie || ee instanceof Wither || ee instanceof Skeleton || ee instanceof Golem) {
            p.sendMessage(ChatColor.RED + "You can't use Switch on these mobs!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        Location eloc = getTarget(p).getLocation();
        if (eloc.distance(pLoc) > 14) {
            p.sendMessage(ChatColor.RED + "Target is too far away to switch..");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 6, 1);
            return;
        } else {
            removeMana(p, Mana1);
            ((LivingEntity) ee).damage(spell1DMG + SkillPointsCore.getDMGValue(p), p);
            cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + spell1Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana1 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
            //TODO remove mana
            p.teleport(eloc);
            ee.teleport(pLoc);
            pLoc.getWorld().playSound(p.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 5, 3);
            eloc.getWorld().playSound(eloc, Sound.BLOCK_END_PORTAL_SPAWN, 5, 3);
            pLoc.getWorld().spawnParticle(Particle.FALLING_DUST, pLoc, 1000, 1F, 1F, 1F,
                    1000);
            eloc.getWorld().spawnParticle(Particle.FALLING_DUST, eloc, 1000, 1F, 1F, 1F,
                    1000);
            LivingEntity liv = (LivingEntity) ee;
            liv.damage(3, p);
            liv.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3 * 20, 5));

        }


    }


    @Override
    public void useSpell2(Player p) {
        Entity e = getTarget(p);
        if (e == null || !e.getType().isAlive()) {
            p.sendMessage(ChatColor.RED + "There are no targets in light of sight");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 6, 1);
            return;
        }
        if (e instanceof Zombie) {
            p.sendMessage(ChatColor.RED + "There are no targets in light of sight");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 6, 1);
            return;
        }
        if (!TeamManager.arentSameTeam(p, e)) {
            p.sendMessage(ChatColor.RED + "There are no targets in your line of sight..");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (e.getLocation().distance(p.getLocation()) > 10) {
            p.sendMessage(ChatColor.RED + "Target is too far..");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeMana(p, Mana2);
        Vector direction = p.getLocation().getDirection();
        direction.multiply(8);
        direction.add(new Vector(0, 10, 0));
        Location oneBlockAway = p.getLocation().add(getRightHeadDirection(p).add(new Vector(0, 10, 0).add(p.getLocation().getDirection().multiply(10))));
        Location SecondBlock = p.getLocation().add(getLeftHeadDirection(p).add(new Vector(0, 10, 0).add(p.getLocation().getDirection().multiply(10))));
        Location oneBlockAway1 = p.getLocation().add(getRightHeadDirection(p).add(new Vector(0, 9, 0).add(p.getLocation().getDirection().multiply(10))));
        Location SecondBlock1 = p.getLocation().add(getLeftHeadDirection(p).add(new Vector(0, 9, 0).add(p.getLocation().getDirection().multiply(10))));
        if (SecondBlock.getBlock().getType().isSolid() || SecondBlock1.getBlock().getType().isSolid() || oneBlockAway1.getBlock().getType().isSolid() || oneBlockAway.getBlock().getType().isSolid()) {
            p.sendMessage(ChatColor.RED + "Something is blocking the rifts path..");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 6, 1);
            return;
        }
        ((LivingEntity) e).damage(spell2DMG * 2 + SkillPointsCore.getDMGValue(p), p);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + spell2Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana2 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        SecondBlock.getBlock().setType(Material.END_GATEWAY);
        oneBlockAway.getBlock().setType(Material.END_GATEWAY);
        SecondBlock1.getBlock().setType(Material.END_GATEWAY);
        oneBlockAway1.getBlock().setType(Material.END_GATEWAY);
        new BukkitRunnable() {
            @Override
            public void run() {
                Location Sb = SecondBlock.add(0, 1, 0);
                Sb.getBlock().setType(Material.AIR);
                Location Sn = oneBlockAway.add(0, 1, 0);
                Sn.getBlock().setType(Material.AIR);
                SecondBlock1.getBlock().setType(Material.AIR);
                oneBlockAway1.getBlock().setType(Material.AIR);
            }
        }.runTaskLater(Main.plugin, 20 * 2);
        new BukkitRunnable() {
            @Override
            public void run() {
                SecondBlock.getWorld().playSound(SecondBlock, Sound.BLOCK_END_PORTAL_SPAWN, 5, 2);
                p.getWorld().playSound(p.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 5, 2);
                Vector v = e.getLocation().subtract(SecondBlock).toVector().normalize();
                Vector v1 = e.getLocation().subtract(oneBlockAway).toVector().normalize();
                Fireball fireball = p.getWorld().spawn(SecondBlock.add(0, -1, 0), Fireball.class);
                Fireball fireball2 = p.getWorld().spawn(oneBlockAway.add(0, -1, 0), Fireball.class);
                fireball.setIsIncendiary(false);
                fireball.setGravity(false);
                fireball.setVelocity(v.multiply(4F));
                fireball2.setIsIncendiary(false);
                fireball2.setGravity(false);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        fireball2.setVelocity(v1.multiply(4F));

                    }
                }.runTaskLater(Main.plugin, 4);

                SecondBlock.getWorld().spawnParticle(Particle.SPELL_WITCH, SecondBlock, 100, 1F, 1F, 1F,
                        1000);
                SecondBlock1.getWorld().spawnParticle(Particle.SPELL_WITCH, SecondBlock1, 100, 1F, 0.5F, 1F,
                        1000);
                oneBlockAway.getWorld().spawnParticle(Particle.SPELL_WITCH, oneBlockAway, 100, 1F, 1F, 1F,
                        1000);
                oneBlockAway1.getWorld().spawnParticle(Particle.SPELL_WITCH, oneBlockAway1, 100, 1F, 1F, 1F,
                        1000);
            }
        }.runTaskLater(Main.plugin, 4);


    }

    public static Vector getRightHeadDirection(Player player) {
        Vector direction = player.getLocation().getDirection().normalize();
        return new Vector(-direction.getZ(), 0.0, direction.getX()).normalize();
    }

    public static Vector getLeftHeadDirection(Player player) {
        Vector direction = player.getLocation().getDirection().normalize();
        return new Vector(direction.getZ(), 0.0, -direction.getX()).normalize();
    }

    public static HashMap<UUID, Integer> portalCount = new HashMap<>();
    public static HashMap<UUID, Location> entrance = new HashMap<>();
    public static HashMap<UUID, Location> exit = new HashMap<>();
    public static HashMap<UUID, Boolean> verify = new HashMap<>();


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void useSpell3(Player p) {
        if (!portalCount.isEmpty() && portalCount.get(p.getUniqueId()) == 2) {
            p.sendMessage(ChatColor.RED + "You have already opened two rifts!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (!portalCount.isEmpty() && portalCount.get(p.getUniqueId()) == 1) {
            Vector direction = p.getLocation().getDirection();
            direction.multiply(2.5);
            Location loc1 = p.getLocation().add(direction).add(0, 1, 0);
            Location loc2 = p.getLocation().add(direction).add(0, 2, 0);
            Location loc3 = p.getLocation().add(direction).add(0, 0, 0);

            if (loc2.getBlock().isEmpty() && loc1.getBlock().isEmpty()) {
                removeMana(p, Mana3);
                portalCount.put(p.getUniqueId(), 2);
                loc2.getBlock().setType(Material.END_GATEWAY);
                loc1.getBlock().setType(Material.END_GATEWAY);
                cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + spell3Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana3 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");

                //TODO MANA
                p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ENDERCHEST_OPEN, 5, 1);
                exit.put(p.getUniqueId(), loc1);
                new BukkitRunnable() {
                    int t = 0;

                    @Override
                    public void run() {
                        if (playerIsNearPortal(p, loc1, loc3)) {
                            if (entrance.containsKey(p.getUniqueId())) {
                                if (!verify.containsKey(p.getUniqueId())) {
                                    p.teleport(entrance.get(p.getUniqueId()));
                                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 6, 1);
                                    p.getWorld().spawnParticle(Particle.SPELL_WITCH, loc1, 100, 1F, 1F, 1F,
                                            1000);
                                    Vector direction3 = p.getLocation().getDirection();
                                    direction3.multiply(0.4);
                                    p.setVelocity(direction3);
                                    verify.put(p.getUniqueId(), true);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            verify.remove(p.getUniqueId());
                                        }
                                    }.runTaskLater(Main.plugin, 40);
                                }
                            }
                        }
                        t = t + 1;
                        if (t == 400 || !portalCount.containsKey(p.getUniqueId())) {
                            loc1.getBlock().setType(Material.AIR);
                            loc2.getBlock().setType(Material.AIR);
                            portalCount.remove(p.getUniqueId());
                            if (entrance.containsKey(p.getUniqueId()) && exit.containsKey(p.getUniqueId())) {
                                entrance.remove(p.getUniqueId());
                                exit.remove(p.getUniqueId());
                            }
                            cancel();
                            return;
                        }
                    }
                }.runTaskTimer(Main.plugin, 0, 1);

                return;
            } else {
                p.sendMessage(ChatColor.RED + "Something is blocking the second rift path..");
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 6, 1);
                return;
            }
        }
        if (!portalCount.containsKey(p.getUniqueId())) {
            Vector direction = p.getLocation().getDirection();
            direction.multiply(2.5);
            Location loc1 = p.getLocation().add(direction).add(0, 1, 0);
            Location loc2 = p.getLocation().add(direction).add(0, 2, 0);
            Location loc3 = p.getLocation().add(direction).add(0, 0, 0);

            if (loc2.getBlock().isEmpty() && loc1.getBlock().isEmpty()) {
                loc2.getBlock().setType(Material.END_GATEWAY);
                loc1.getBlock().setType(Material.END_GATEWAY);
                p.playSound(p.getLocation(), Sound.BLOCK_ENDERCHEST_OPEN, 6, 1);
                entrance.put(p.getUniqueId(), loc1);
                portalCount.put(p.getUniqueId(), 1);
                p.sendMessage(ChatColor.DARK_PURPLE + "Rift closing in 20 seconds..");
                new BukkitRunnable() {
                    int t = 0;

                    @Override
                    public void run() {
                        if (playerIsNearPortal(p, loc1, loc3)) {
                            if (exit.containsKey(p.getUniqueId())) {
                                if (!verify.containsKey(p.getUniqueId())) {
                                    p.teleport(exit.get(p.getUniqueId()));
                                    p.getWorld().spawnParticle(Particle.SPELL_WITCH, loc1, 100, 1F, 1F, 1F,
                                            1000);
                                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 6, 1);
                                    Vector direction3 = p.getLocation().getDirection();
                                    direction3.multiply(0.4);
                                    p.setVelocity(direction3);
                                    //TODO EFFECTS
                                    verify.put(p.getUniqueId(), true);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            verify.remove(p.getUniqueId());
                                        }
                                    }.runTaskLater(Main.plugin, 40);

                                }
                            }

                        }
                        t = t + 1;
                        if (t == spell3DMG) {
                            loc1.getBlock().setType(Material.AIR);
                            loc2.getBlock().setType(Material.AIR);
                            if (portalCount.containsKey(p.getUniqueId())) {
                                p.sendMessage(ChatColor.DARK_PURPLE + "The rifts are now closed..");
                                p.playSound(p.getLocation(), Sound.BLOCK_ENDERCHEST_CLOSE, 6, 1);
                                if (entrance.containsKey(p.getUniqueId()) && exit.containsKey(p.getUniqueId())) {
                                    entrance.remove(p.getUniqueId());
                                    exit.remove(p.getUniqueId());
                                }
                                portalCount.remove(p.getUniqueId());
                            }
                            cancel();
                            return;
                        }
                        if (t % 20 == 0) {
                            p.sendMessage(ChatColor.DARK_PURPLE + "Rift closing in " + (int) (20 - (t / 20)) + " seconds..");
                        }


                    }
                }.runTaskTimer(Main.plugin, 0, 1);
            } else {
                p.sendMessage(ChatColor.RED + "Something is blocking the first rift path..");
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 6, 1);
                return;
            }
        }

    }

    private boolean playerIsNearPortal(Player p, Location loc1, Location loc3) {
        if ((p.getLocation().distance(loc3) < 1) || (p.getLocation().distance(loc1) < 1)) {
            return true;
        }
        return false;
    }

    public static HashMap<Integer, Location> cleanPortal = new HashMap<>();
    public static HashMap<Integer, Location> launchMap = new HashMap<>();
    public static HashMap<Integer, Vector> launchVector = new HashMap<>();


    @Override
    public void useUltimate(Player p) {
        if (pathEmpty(p)) {
            p.sendMessage(ChatColor.RED + "Something is blocking the rift path..");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 6, 1);
            return;
        }
        removeMana(p, 20);
        removeUlt(p);
        Location pLoc = p.getLocation();
        launchMap.put(0, pLoc);
        cleanPortal.put(0, pLoc);
        p.getWorld().playSound(cleanPortal.get(0), Sound.BLOCK_END_PORTAL_SPAWN, 15, 1);

        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 6; i++) {
                Location loc1 = p.getLocation().add(j - 3, 12, i - 3);
                loc1.getBlock().setType(Material.END_GATEWAY);
                if (i == 3 && j == 3) {
                    Vector vector = loc1.subtract(launchMap.get(0)).toVector().normalize();
                    launchVector.put(0, vector);
                }
            }
        }
        int count = 0;
        for (int j = -1; j < 7; j++) {
            for (int i = -1; i < 7; i++) {
                count += 1;
                Location loc1 = p.getLocation().add(j - 3, 12, i - 3);
                cleanPortal.put(count, loc1);
                p.getWorld().spawnParticle(Particle.SPELL_WITCH, loc1, 100, 1F, 1F, 1F,
                        1000);
                if (!(loc1.getBlock().getType() == Material.END_GATEWAY)) {
                    loc1.getBlock().setType(Material.ENDER_STONE);

                }
            }
        }
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + ultName + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + 20 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");

        launchEnemies(p);
        // Clean dat shiz boi
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                for (int j = -1; j < 7; j++) {
                    for (int i = -1; i < 7; i++) {
                        count += 1;
                        cleanPortal.get(count).getBlock().setType(Material.AIR);
                        p.getWorld().spawnParticle(Particle.SPELL_WITCH, cleanPortal.get(count), 100, 1F, 1F, 1F,
                                1000);


                    }
                }
                p.playSound(launchMap.get(0), Sound.BLOCK_ENDERCHEST_CLOSE, 10, 2);
                launchMap.clear();
                cleanPortal.clear();
                launchVector.clear();
            }
        }.runTaskLater(Main.plugin, 20 * 8);
        // TODO effects


    }

    public void launchEnemies(Player p) {

        new BukkitRunnable() {
            int t = 0;

            @Override
            public void run() {
                t += 1;


                for (Entity entity : p.getWorld().getNearbyEntities(launchMap.get(0), 9, 9, 9)) {
                    if (!entity.equals(null) && !entity.equals(p) && entity.getType().isAlive() &&
                            !(entity instanceof Zombie) && TeamManager.arentSameTeam(p, entity)) {
                        ((LivingEntity) entity).damage(0.1, p);
                        entity.setVelocity(launchVector.get(0));
                        p.getWorld().spawnParticle(Particle.SPELL_WITCH, entity.getLocation(), 100, 1F, 1F, 1F,
                                1000);
                        if (t == 5) {
                            p.playSound(entity.getLocation(), Sound.ITEM_FIRECHARGE_USE, 10, 2);


                        }
                    }
                }

                if (t == 5) {
                    p.getWorld().playSound(launchMap.get(0), Sound.BLOCK_END_PORTAL_SPAWN, 15, 1);
                    p.playSound(p.getLocation(), Sound.ITEM_FIRECHARGE_USE, 10, 2);


                }
                if (t == 8) {
                    cancel();
                }


            }
        }.runTaskTimer(Main.plugin, 0, 16);
    }

    public boolean pathEmpty(Player p) {
        for (int j = -1; j < 7; j++) {
            for (int i = -1; i < 7; i++) {
                Location loc1 = p.getLocation().add(j - 3, 12, i - 3);
                if (!(loc1.getBlock().isEmpty())) {
                    return true;

                }
            }
        }
        return false;
    }

    @Override
    public AaClassSpellShowcase getClassInv() {
        return new RiftorSpellInv();
    }

}
