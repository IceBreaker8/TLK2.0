package Classes;

import ClassGUI.AaClassHashMapChoosing;
import ClassGUI.AaClassSpellShowcase;
import ClassGUI.GluonSpellInv;
import ClassUpgrades.SkillPointsCore;
import Main.Main;
import Teams.TeamManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class Gluon extends TlkClass implements Listener {

    public Gluon() {
        //Class name
        className = "Gluon";
        classColor = ChatColor.WHITE;
        classSpec = classColor + "" + ChatColor.BOLD + "Gluon, The Gravity Controler" + ChatColor.WHITE + "" + ChatColor.BOLD;
        //Weapon name
        weaponName = "Gluon Link";

        //Spell Names
        spell1Name = "Attraction";
        spell2Name = "Gravity Shield";
        spell3Name = "Repulsion";
        ultName = "Revertion";

        //Mana Cost
        Mana1 = 10;
        Mana2 = 14;
        Mana3 = 10;

        // Spell DMG
        spell1DMG = 8;  // punch while airborne damage
        spell2DMG = 4 * 20;  // gravity shield duration
        spell3DMG = 6;  // landing damage
        ultDMG = 6 * 20;     // Revertion duration  ||
    }

    AaClassActionBar cAc = new AaClassActionBar();

    @Override
    public AaClassSpellShowcase getClassInv() {
        return new GluonSpellInv();
    }

    public static HashMap<LivingEntity, Boolean> gravPunch = new HashMap<>();

    @Override
    public void useSpell1(Player p) {
        if (cantUseSpellsWhileUlt.contains(p.getUniqueId())) {
            p.sendMessage(ChatColor.RED + "You can't cast spells now!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        Entity e = getTarget(p);
        if (e == null || !e.isValid() || !(e.getType().isAlive())) {
            p.sendMessage(ChatColor.RED + "There are no targets in your line of sight..");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (!TeamManager.arentSameTeam(p, e)) {
            p.sendMessage(ChatColor.RED + "There are no targets in your line of sight..");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (!(e instanceof Player)) {
            p.sendMessage(ChatColor.RED + "There are no targets in your line of sight..");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (gravPunch.containsKey(e)) {
            p.sendMessage(ChatColor.RED + "There can't cast this spell twice in a short period of time!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (e.getLocation().distance(p.getLocation()) > 16) {
            p.sendMessage(ChatColor.RED + "You target is too far away!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (e.getLocation().distance(p.getLocation()) < 4) {
            p.sendMessage(ChatColor.RED + "You target is too close!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (!checkSpectator(e)) {
            p.sendMessage(ChatColor.RED + "There are no targets in your line of sight..");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeMana(p, Mana1);
        gravPunch.put((LivingEntity) e, true);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell1Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana1 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        Vector direction = p.getLocation().subtract(e.getLocation()).toVector().add(new Vector(0, 4.4, 0)).normalize();
        direction.multiply(2.1F);
        e.getWorld().playSound(e.getLocation(), Sound.ITEM_FIRECHARGE_USE, 5, 1);
        e.setVelocity(direction);
        checkPunch(p, (LivingEntity) e);
    }

    private void checkPunch(Player p, LivingEntity target) {
        new BukkitRunnable() {
            @Override
            public void run() {
                target.getLocation().getWorld().spawnParticle(Particle.CLOUD, target.getLocation(), 8, 0.01F, 0.01F, 0.01F, 0.001);
                if (gravPunch.get(target).equals(false)) {
                    gravPunch.remove(target);
                    cancel();
                    return;
                }
                if (target.isOnGround()) {
                    gravPunch.remove(target);
                    cancel();
                    return;
                }
            }
        }.runTaskTimer(Main.plugin, 4, 1);
    }

    @EventHandler
    public void entityDamaged(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        Entity victim = e.getEntity();
        if (damager instanceof Player && victim.getType().isAlive()) {
            if (gravPunch.containsKey(victim) && AaClassHashMapChoosing.getClass((Player) damager).contains("Gluon")) {
                gravPunch.put((LivingEntity) victim, false);
                Vector launch = damager.getLocation().getDirection().normalize();
                launch.multiply(1.4F);
                victim.setVelocity(launch);
                e.setDamage(spell1DMG + SkillPointsCore.getDMGValue((Player) e.getDamager()));
                victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_IRONGOLEM_HURT, 5, 2);
                victim.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, victim.getLocation(), 100, 0.6F, 0.6F, 0.6F, 0.1);
            }
        }
        if (damager instanceof Player) {
            if (((Player) damager).getItemInHand().getType().equals(Material.STONE_AXE) ||
                    ((Player) damager).getItemInHand().getType().equals(Material.STONE_SPADE) ||
                    ((Player) damager).getItemInHand().getType().equals(Material.STONE_PICKAXE)
                    || ((Player) damager).getItemInHand().getType().equals(Material.IRON_PICKAXE) ||
                    ((Player) damager).getItemInHand().getType().equals(Material.GOLD_PICKAXE)) {
                if (victim instanceof Skeleton || victim instanceof Golem) {
                    damager.sendMessage(ChatColor.RED + "It looks like you can't damage this entity with that weapon..");
                    e.setCancelled(true);
                }
            }
        }
        if (damager instanceof Player) {
            if (((Player) damager).getItemInHand().getType().equals(Material.DIAMOND_AXE)) {
                e.setDamage(1);
            }
        }
    }

    @Override
    public void useSpell2(Player p) {
        if (cantUseSpellsWhileUlt.contains(p.getUniqueId())) {
            p.sendMessage(ChatColor.RED + "You can't cast spells now!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (!p.isOnGround()) {
            p.sendMessage(ChatColor.RED + "You need to be on ground to cast this spell!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeMana(p, Mana2);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell2Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana2 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        Location pLoc = p.getLocation();
        List<Location> cubeLoc = getHollowCube(pLoc.clone().add(-2, 0, 2), pLoc.clone().add(2, 4, -2), 0.25);
        for (Location loc : cubeLoc) {
            new BukkitRunnable() {
                int count = 0;

                @Override
                public void run() {
                    for (Player player : p.getWorld().getPlayers()) {
                        if (!TeamManager.arentSameTeam(p, player)) {
                            if (player.getLocation().distance(pLoc.clone().add(0, 2, 0)) <= 2.8) {
                                player.setInvulnerable(true);
                            } else if (player.getLocation().distance(pLoc.clone().add(0, 2, 0)) > 2.8) {
                                player.setInvulnerable(false);
                            }
                        }
                    }
                    if (count == spell2DMG) {
                        pLoc.getWorld().playSound(pLoc, Sound.ENTITY_SHULKER_SHOOT, 5, 2);
                        for (Player player : p.getWorld().getPlayers()) {
                            player.setInvulnerable(false);
                        }
                        cancel();
                        return;
                    }
                    if (count % 20 == 0 || count == 0) {
                        pLoc.getWorld().playSound(pLoc, Sound.ENTITY_SHULKER_SHOOT, 5, 1);
                    }
                    if (count % 10 == 0 || count == 0) {
                        loc.getWorld().spawnParticle(Particle.CLOUD, loc, 1, 0.0001F, 0.0001F, 0.0001F, 0.001);
                    }
                    count += 1;
                    if (count == spell2DMG) {
                        for (Player player : p.getWorld().getPlayers()) {
                            player.setInvulnerable(false);
                        }
                        cancel();
                    }
                }
            }.runTaskTimer(Main.plugin, 0, 1);
        }

    }

    public List<Location> getHollowCube(Location corner1, Location corner2, double particleDistance) {
        List<Location> result = new ArrayList<Location>();
        World world = corner1.getWorld();
        double minX = Math.min(corner1.getX(), corner2.getX());
        double minY = Math.min(corner1.getY(), corner2.getY());
        double minZ = Math.min(corner1.getZ(), corner2.getZ());
        double maxX = Math.max(corner1.getX(), corner2.getX());
        double maxY = Math.max(corner1.getY(), corner2.getY());
        double maxZ = Math.max(corner1.getZ(), corner2.getZ());

        for (double x = minX; x <= maxX; x += particleDistance) {
            for (double y = minY; y <= maxY; y += particleDistance) {
                for (double z = minZ; z <= maxZ; z += particleDistance) {
                    int components = 0;
                    if (x == minX || x == maxX) components++;
                    if (y == minY || y == maxY) components++;
                    if (z == minZ || z == maxZ) components++;
                    if (components >= 2) {
                        result.add(new Location(world, x, y, z));
                    }
                }
            }
        }

        return result;
    }

    @Override
    public void useSpell3(Player p) {
        if (cantUseSpellsWhileUlt.contains(p.getUniqueId())) {
            p.sendMessage(ChatColor.RED + "You can't cast spells now!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeMana(p, Mana3);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell3Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana3 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        p.setVelocity(p.getLocation().getDirection().multiply(1.8F));
        p.setVelocity(new Vector(p.getVelocity().getX(), 1.0D,
                p.getVelocity().getZ()));
        dmgOnLanding(p);
        p.getWorld().playSound(p.getLocation(), Sound.ITEM_FIRECHARGE_USE, 5, 1);


    }

    private void dmgOnLanding(Player p) {
        new BukkitRunnable() {
            int t = 0;

            @Override
            public void run() {
                t += 1;
                p.getLocation().getWorld().spawnParticle(Particle.CLOUD, p.getLocation(), 8, 0.01F, 0.01F, 0.01F, 0.001);
                if (!p.isOnline()) {
                    cancel();
                    return;
                }
                if (p.isOnGround() || (t == 20 * 8)) {
                    Material m = p.getLocation().clone().subtract(0, 1, 0).getBlock().getType();
                    p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FIREBALL_EXPLODE, 5, 1);
                    FallingBlock b0 = p.getWorld().spawnFallingBlock(p.getLocation(), m, (byte) 0);
                    FallingBlock b2 = p.getWorld().spawnFallingBlock(p.getLocation(), m, (byte) 0);
                    FallingBlock b3 = p.getWorld().spawnFallingBlock(p.getLocation(), m, (byte) 0);
                    FallingBlock b4 = p.getWorld().spawnFallingBlock(p.getLocation(), m, (byte) 0);
                    FallingBlock b5 = p.getWorld().spawnFallingBlock(p.getLocation(), m, (byte) 0);
                    FallingBlock b6 = p.getWorld().spawnFallingBlock(p.getLocation(), m, (byte) 0);
                    FallingBlock b7 = p.getWorld().spawnFallingBlock(p.getLocation(), m, (byte) 0);
                    FallingBlock b8 = p.getWorld().spawnFallingBlock(p.getLocation(), m, (byte) 0);
                    b0.setDropItem(false);
                    b2.setDropItem(false);
                    b3.setDropItem(false);
                    b4.setDropItem(false);
                    b5.setDropItem(false);
                    b6.setDropItem(false);
                    b7.setDropItem(false);
                    b8.setDropItem(false);
                    b0.setVelocity(new Vector(0, 0.5, 0.25));
                    b2.setVelocity(new Vector(0.25, 0.5, 0));
                    b3.setVelocity(new Vector(0.25, 0.5, 0.25));
                    b4.setVelocity(new Vector(0, 0.5, -0.25));
                    b5.setVelocity(new Vector(-0.25, 0.5, 0));
                    b6.setVelocity(new Vector(-0.25, 0.5, -0.25));
                    b7.setVelocity(new Vector(-0.25, 0.5, 0.25));
                    b8.setVelocity(new Vector(0.25, 0.5, -0.25));
                    Location loc = p.getLocation();
                    for (Entity e : loc.getWorld().getEntities()) {
                        if (e.getLocation().distance(loc) < 4) {
                            if (e instanceof Golem || e instanceof Skeleton
                                    || e instanceof Chicken || e instanceof Rabbit) {
                                LivingEntity b1 = (LivingEntity) e;
                                b1.damage(spell3DMG + SkillPointsCore.getDMGValue(p), p);
                                Vector direction = b1.getLocation().toVector().subtract(p.getLocation().toVector())
                                        .normalize();
                                direction.setX(direction.getX() * 0.7);
                                direction.setY(direction.getY() * 0.7 + 1);
                                direction.setZ(direction.getZ() * 0.7);
                                b1.setVelocity(direction);
                            }
                            if (e.getType().isAlive() && !(e.equals(p))) {
                                if (TeamManager.arentSameTeam(p, e)) {
                                    if (checkSpectator(e)) {
                                        LivingEntity b1 = (LivingEntity) e;
                                        b1.damage(spell3DMG + SkillPointsCore.getDMGValue(p), p);
                                        Vector direction = b1.getLocation().toVector().subtract(p.getLocation().toVector())
                                                .normalize();
                                        direction.setX(direction.getX() * 0.7);
                                        direction.setY(direction.getY() * 0.7 + 1);
                                        direction.setZ(direction.getZ() * 0.7);
                                        b1.setVelocity(direction);
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

    @EventHandler
    public void onFallingBlockLand(EntityChangeBlockEvent e) {
        e.setCancelled(true);
        return;
    }

    public static HashSet<UUID> cantUseSpellsWhileUlt = new HashSet<UUID>();

    @Override
    public void useUltimate(Player p) {
        removeMana(p, 20);
        removeUlt(p);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + ultName + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + 20 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        cantUseSpellsWhileUlt.add(p.getUniqueId());
        p.setInvulnerable(true);
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, ultDMG + 5, 128));
        new BukkitRunnable() {
            int t = 0;

            @Override
            public void run() {
                p.getLocation().getWorld().spawnParticle(Particle.CLOUD, p.getLocation(), 8, 0.01F, 0.01F, 0.01F, 0.001);
                if (t % 20 == 0 || t == 0) {
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 5, 1);
                    ArrayList blocksList = getBlocks(p.getLocation().clone().subtract(0, 1, 0).getBlock(), 14);
                    for (int i = 0; i < 6; i++) {
                        Location loc1 = randomBlock(blocksList).getLocation();
                        p.getLocation().getWorld().spawnParticle(Particle.CLOUD, p.getLocation(), 40, 0.5F, 0.5F, 0.5F, 1);
                        loc1.getWorld().spawnParticle(Particle.CLOUD, loc1, 40, 1F, 1F, 1F, 1);
                        Material material1 = loc1.getBlock().getType();
                        FallingBlock b1 = p.getWorld().spawnFallingBlock(loc1.clone().add(0, 1, 0), material1, (byte) 0);
                        b1.setDropItem(false);
                        b1.setVelocity(new Vector(0, 2, 0));
                    }
                    for (Entity e : p.getWorld().getEntities()) {
                        if (e.getLocation().distance(p.getLocation()) < 18) {
                            if (e.getType().isAlive() && !(e.equals(p)) && e instanceof Player) {
                                if (TeamManager.arentSameTeam(p, e)) {
                                    e.setVelocity(new Vector(0, 4, 0));
                                }
                            }
                        }
                    }
                }
                if (t >= ultDMG) {
                    p.setInvulnerable(false);
                    cantUseSpellsWhileUlt.remove(p.getUniqueId());
                    cancel();
                    return;
                }
                t += 1;
            }
        }.runTaskTimer(Main.plugin, 0, 1);

    }

    public Block randomBlock(ArrayList blocks) {
        Random random = new Random();
        int N = random.nextInt(blocks.size());
        Block block = (Block) blocks.get(N);
        return block;
    }

    public ArrayList<Block> getBlocks(Block start, int radius) {
        ArrayList<Block> blocks = new ArrayList<Block>();
        for (double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++) {
            for (double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++) {
                for (double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++) {
                    Location loc = new Location(start.getWorld(), x, y, z);
                    if (loc.getBlock().getType().isSolid()) {
                        if (loc.getBlock().getY() == start.getY()) {
                            blocks.add(loc.getBlock());
                        }
                    }
                }
            }
        }
        return blocks;
    }

    @EventHandler
    public void onPlayerFallDmg(EntityDamageEvent e) {
        if (e.getEntity() instanceof Skeleton) {
            e.setDamage((e.getDamage()) - (e.getDamage() * 40 / 100));
        }
        AaClassHashMapChoosing c = new AaClassHashMapChoosing();
        if (e.getEntity() instanceof Player) {
            if (!(c.getClass((Player) e.getEntity()) == null)) {
                if (c.getClass((Player) e.getEntity()).equals("Gluon") ||
                        c.getClass((Player) e.getEntity()).equals("Poseidon")) {
                    if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                        e.setCancelled(true);
                    }
                }
            }
        }

    }


}
