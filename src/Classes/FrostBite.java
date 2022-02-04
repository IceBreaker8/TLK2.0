package Classes;

import ClassGUI.AaClassSpellShowcase;
import ClassGUI.FrostBiteSpellInv;
import ClassUpgrades.SkillPointsCore;
import Main.Main;
import Teams.TeamManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class FrostBite extends TlkClass implements Listener {
    public FrostBite() {
        //Class name
        className = "FrostBite";
        classColor = ChatColor.AQUA;
        classSpec = classColor + "" + ChatColor.BOLD + "FrostBite, The Ice Bender" + ChatColor.WHITE + "" + ChatColor.BOLD;
        //Weapon name
        weaponName = "Ice Fragment";

        //Spell Names
        spell1Name = "Snow Shot";
        spell2Name = "Ice Spike";
        spell3Name = "Polar Rampage";
        ultName = "Blizzard";

        //Mana Cost
        Mana1 = 8;
        Mana2 = 12;
        Mana3 = 14;

        // Spell DMG
        spell1DMG = 4;  //snowshot damage + 1sec freeze
        spell2DMG = 6;  // ice spike damage + 2sec freeze
        spell3DMG = 3 * 20;  // polar bear time to despawn
        ultDMG = 4 * 20;     // Blizzard time
    }

    AaClassActionBar cAc = new AaClassActionBar();

    @Override
    public void useSpell1(Player p) {
        removeMana(p, Mana1);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + ChatColor.AQUA + "" + ChatColor.BOLD + spell1Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana1 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        p.getLocation().getWorld().playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 5, 1);
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
                p.getWorld().spawnParticle(Particle.SNOWBALL, loc, 40);
                for (Entity e : loc.getWorld().getEntities()) {
                    if (e.getLocation().distance(loc) < 1.4) {
                        if (!(e.equals(p)) && !(e instanceof Zombie)) {
                            if (TeamManager.arentSameTeam(p, e)) {
                                if (e.getType().isAlive()) {
                                    if (checkSpectator(e)) {
                                        LivingEntity b = (LivingEntity) e;
                                        b.damage(spell1DMG + SkillPointsCore.getDMGValue(p), p);
                                        freezePlayer(b, 20);
                                        p.getWorld().playSound(loc, Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 5, 1);
                                        p.getWorld().spawnParticle(Particle.SNOWBALL, loc, 400, 1F, 1F, 1F, 50);
                                        cancel();
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

    public static HashSet<UUID> frozenNotUlt = new HashSet<>();

    public void freezePlayer(Entity e, Integer time) {
        Location loc = e.getLocation();
        frozenNotUlt.add(e.getUniqueId());
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                count += 1;
                e.teleport(loc);
                if (count == time) {
                    frozenNotUlt.remove(e.getUniqueId());
                    cancel();

                }

            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }

    @Override
    public void useSpell2(Player p) {
        Entity entity = getTarget(p);
        if (!p.isOnGround()) {
            p.sendMessage(ChatColor.RED + "You need to be on ground to cast this spell!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }

        if (entity == null) {
            p.sendMessage(ChatColor.RED + "There are no enemies in your line of sight!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (!TeamManager.arentSameTeam(p, entity)) {
            p.sendMessage(ChatColor.RED + "There are no enemies in your line of sight!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (entity.getLocation().distance(p.getLocation()) > 12) {
            p.sendMessage(ChatColor.RED + "The target is too far..");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }

        if (!(entity instanceof Player)) {
            p.sendMessage(ChatColor.RED + "You can only cast this spell on enemy players!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (!checkSpectator(entity)) {
            p.sendMessage(ChatColor.RED + "There are no enemies in your line of sight!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeMana(p, Mana2);
        iceSpike(p, (Player) entity);

    }

    public static HashMap<Location, Location> restoreBlocks = new HashMap<>();

    private void iceSpike(Player p, Player target) {
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + ChatColor.AQUA + "" + ChatColor.BOLD + spell2Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana2 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        Zombie bat = p.getWorld().spawn(p.getLocation(), Zombie.class);
        bat.setInvulnerable(true);
        bat.setSilent(true);
        bat.setCollidable(false);
        bat.setBaby(true);
        bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999 * 20, 3));
        PetFollow(target, bat, 0.4F);
        new BukkitRunnable() {
            int time = 0;

            @Override
            public void run() {

                if (!(bat.getLocation().getBlock().getType().isSolid()) && !(bat.getLocation().getBlock().getType().equals(Material.WATER))) {
                    bat.getLocation().getBlock().setType(Material.ICE);
                    bat.getLocation().getWorld().playSound(bat.getLocation(), Sound.BLOCK_GLASS_BREAK, 5, 1);
                    restoreBlocks.put(bat.getLocation(), bat.getLocation());
                }
                time += 1;
                if (time == 30) {
                    bat.remove();
                    restoreBlocks();
                    cancel();
                    return;
                }
                if (target.getLocation().distance(bat.getLocation()) < 1.3) {
                    freezePlayer(target, 40);
                    buildIceSpike(target);
                    target.damage(spell2DMG + SkillPointsCore.getDMGValue(p), p);
                    target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 2, 145));
                    target.getWorld().playSound(target.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 5, 1);
                    bat.remove();
                    cancel();
                    return;
                }
            }


        }.runTaskTimer(Main.plugin, 0, 1);
    }

    private void buildIceSpike(Entity target) {
        Location loc = target.getLocation();
        loc.getWorld().spawnParticle(Particle.SNOWBALL, loc, 400, 1.3F, 1.3F, 1.3F, 50);
        loc.getBlock().setType(Material.ICE);
        Location loc1 = loc.clone().add(0, 1, 0);
        Location loc2 = loc.clone().add(0, 2, 0);
        Location loc3 = loc.clone().add(0, 3, 0);
        Location loc4 = loc.clone().add(1, 0, 0);
        Location loc5 = loc.clone().add(0, 0, 1);
        Location loc6 = loc.clone().add(0, 0, -1);
        Location loc7 = loc.clone().add(-1, 0, 0);
        // restore
        restoreBlocks.put(loc, loc);
        restoreBlocks.put(loc1, loc1);
        restoreBlocks.put(loc2, loc2);
        restoreBlocks.put(loc3, loc3);
        restoreBlocks.put(loc4, loc4);
        restoreBlocks.put(loc5, loc5);
        restoreBlocks.put(loc6, loc6);
        restoreBlocks.put(loc7, loc7);

        if (loc.getBlock().isEmpty()) {
            loc.getBlock().setType(Material.ICE);

        }

        if (loc1.getBlock().isEmpty()) {
            loc1.getBlock().setType(Material.ICE);

        }
        if (loc2.getBlock().isEmpty()) {
            loc2.getBlock().setType(Material.ICE);

        }
        if (loc3.getBlock().isEmpty()) {
            loc3.getBlock().setType(Material.ICE);

        }
        if (loc4.getBlock().isEmpty()) {
            loc4.getBlock().setType(Material.ICE);

        }
        if (loc5.getBlock().isEmpty()) {
            loc5.getBlock().setType(Material.ICE);

        }
        if (loc6.getBlock().isEmpty()) {
            loc6.getBlock().setType(Material.ICE);

        }
        if (loc7.getBlock().isEmpty()) {
            loc7.getBlock().setType(Material.ICE);

        }


        new BukkitRunnable() {
            @Override
            public void run() {
                restoreBlocks();
                restoreBlocks.clear();
            }
        }.runTaskLater(Main.plugin, 20 * 2);


    }

    public void restoreBlocks() {
        for (Location loc : restoreBlocks.values()) {
            loc.getBlock().setType(Material.AIR);
        }
    }

    public static HashSet<UUID> PolarBearEffect = new HashSet<>();

    @Override
    public void useSpell3(Player p) {
        if (PolarBearEffect.contains(p.getUniqueId())) {
            p.sendMessage(ChatColor.RED + "You can't cast this spell twice!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeMana(p, Mana3);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + ChatColor.AQUA + "" + ChatColor.BOLD + spell3Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana3 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        p.getWorld().spawnParticle(Particle.SNOWBALL, p.getLocation(), 400, 1F, 1F, 1F, 50);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_POLAR_BEAR_HURT, 1, 1);
        p.setInvulnerable(true);
        PolarBear snowman = p.getWorld().spawn(p.getLocation(), PolarBear.class);
        snowman.setMaxHealth(14);
        snowman.setHealth(14);
        snowman.setCollidable(false);
        snowman.setCanPickupItems(false);
        PolarBearEffect.add(p.getUniqueId());
        p.setInvulnerable(true);
        snowman.setInvulnerable(true);
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, spell3DMG, 3));
        for (Player player : p.getServer().getOnlinePlayers()) {
            player.hidePlayer(p);
        }
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                count += 1;
                snowman.teleport(p);
                if (count == spell3DMG || !(PolarBearEffect.contains(p.getUniqueId()))) {
                    for (Player player : p.getServer().getOnlinePlayers()) {
                        player.showPlayer(p);
                    }
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_POLAR_BEAR_DEATH, 1, 1);
                    p.getWorld().spawnParticle(Particle.SNOWBALL, p.getLocation(), 400, 1F, 1F, 1F, 50);
                    p.setInvulnerable(false);
                    for (PotionEffect effect : p.getActivePotionEffects())
                        p.removePotionEffect(effect.getType());
                    snowman.remove();
                    PolarBearEffect.clear();
                    cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        Entity entity = e.getEntity();
        if (damager instanceof Player && PolarBearEffect.contains(damager.getUniqueId())) {
            e.setDamage(e.getDamage() + 4 + SkillPointsCore.getDMGValue((Player) damager));
            Vector v = e.getDamager().getLocation().getDirection().multiply(3F);
            entity.setVelocity(v);
            PolarBearEffect.remove(damager.getUniqueId());
            damager.getWorld().playSound(damager.getLocation(), Sound.ENTITY_POLAR_BEAR_DEATH, 1, 1);
            damager.getWorld().spawnParticle(Particle.SNOWBALL, damager.getLocation(), 400, 1F, 1F, 1F, 50);


        }
        if (frozen.contains(damager.getUniqueId())) {
            e.setCancelled(true);
        }
        if (frozenNotUlt.contains(damager.getUniqueId())) {
            e.setCancelled(true);
        }
        if (Chronos.timeStopped.contains(damager.getUniqueId())) {
            e.setCancelled(true);
        }
    }

    public static HashSet<UUID> frozen = new HashSet<>();

    @Override
    public void useUltimate(Player p) {
        if (!p.isOnGround()) {
            p.sendMessage(ChatColor.RED + "You need to be on ground to cast this spell!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;

        }
        removeUlt(p);
        removeMana(p, 20);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + ChatColor.AQUA + "" + ChatColor.BOLD + ultName + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + 20 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        Location pLoc = p.getLocation();
        spawnSnowBlocks(p);
        new BukkitRunnable() {
            int time = 0;

            @Override
            public void run() {
                if (time == ultDMG) {
                    pLoc.getWorld().playSound(pLoc, Sound.BLOCK_GLASS_BREAK, 5, 1);
                    restoreSnowyBlocks();
                    frozen.clear();
                    cancel();
                    return;
                }
                for (Entity entity : p.getWorld().getNearbyEntities(pLoc, 4, 4, 4)) {
                    if (!(entity.equals(p)) && entity.getType().isAlive() &&
                            TeamManager.arentSameTeam(p, entity)) {
                        if (checkSpectator(entity)) {
                            LivingEntity b = (LivingEntity) entity;
                            if (time == 0 && !(frozen.contains(entity.getUniqueId())) && entity.isOnGround()) {
                                frozen.add(entity.getUniqueId());
                            }
                            if (time % 20 == 0 || time == 0) {
                                if (!b.isDead()) {
                                    if (!(entity instanceof PolarBear)) {
                                        freezePlayer(entity, 30);
                                    }
                                    Location eloc = b.getLocation().clone().add(0, 6, 0);
                                    eloc.getWorld().spawn(eloc, Snowball.class);
                                }
                            }
                        }
                    }
                }
                if (time % 20 == 0 || time == 0) {
                    p.getWorld().spawnParticle(Particle.SNOWBALL, pLoc, 800, 3F, 3F, 3F, 50);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 5, 1);

                }
                time += 1;

            }

        }.runTaskTimer(Main.plugin, 0, 1);

    }

    public static HashMap<Location, Material> restoreSnowBlocks = new HashMap<>();
    public static HashMap<Location, Byte> restoreSnowBlocksId = new HashMap<>();


    public ArrayList<Block> getBlocks(Block start, int radius) {
        ArrayList<Block> blocks = new ArrayList<Block>();
        for (double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++) {
            for (double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++) {
                for (double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++) {
                    Location loc = new Location(start.getWorld(), x, y, z);
                    if (loc.getBlock().getType().isSolid()) {
                        if (loc.getBlock().getY() == start.getY()) {
                            blocks.add(loc.getBlock());
                            restoreSnowBlocks.put(loc, loc.getBlock().getType());
                            restoreSnowBlocksId.put(loc, loc.getBlock().getData());
                        }
                    }
                }
            }
        }
        return blocks;
    }

    private void spawnSnowBlocks(Player p) {
        for (Block block : getBlocks(p.getLocation().clone().subtract(0, 1, 0).getBlock(), 4)) {
            block.setType(Material.SNOW_BLOCK);
        }
    }

    private void restoreSnowyBlocks() {
        for (Location loc : restoreSnowBlocks.keySet()) {
            loc.getBlock().setType(restoreSnowBlocks.get(loc));
            loc.getBlock().setData(restoreSnowBlocksId.get(loc));

        }
        restoreSnowBlocks.clear();
        restoreSnowBlocksId.clear();
    }

    @Override
    public AaClassSpellShowcase getClassInv() {
        return new FrostBiteSpellInv();
    }
}
