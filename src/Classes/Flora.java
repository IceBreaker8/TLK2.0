package Classes;

import ClassGUI.AaClassHashMapChoosing;
import ClassGUI.AaClassSpellShowcase;
import ClassGUI.FloraSpellInv;
import ClassUpgrades.SkillPointsCore;
import GameMechanics.UltChargeMechanics;
import Main.Main;
import Teams.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class Flora extends TlkClass implements Listener {

    public Flora() {
        //Class name
        className = "Flora";
        classColor = ChatColor.GREEN;
        classSpec = classColor + "" + ChatColor.BOLD + "Flora, The Goddess Of Nature" + ChatColor.WHITE + "" + ChatColor.BOLD;
        //Weapon name
        weaponName = "Tree Essence";

        //Spell Names
        spell1Name = "Root Star";
        spell2Name = "Healing Parrot";
        spell3Name = "Life Essence";
        ultName = "Resurrection";

        //Mana Cost
        Mana1 = 9;
        Mana2 = 16;
        Mana3 = 14;

        // Spell DMG
        spell1DMG = 5;  // star damage
        spell2DMG = 3;  // heal per second for 4 seconds (8 total hp)
        spell3DMG = 8;  // hp heal
        ultDMG = 18;     // Resurrection range
    }

    AaClassActionBar cAc = new AaClassActionBar();

    @Override
    public AaClassSpellShowcase getClassInv() {
        return new FloraSpellInv();
    }

    @Override
    public void useSpell1(Player p) {
        Entity entity = getTarget(p);
        if (entity == null) {
            p.sendMessage(ChatColor.RED + "There are no targets in your line of sight!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (!entity.getType().isAlive() || entity instanceof Parrot || entity instanceof Zombie) {
            p.sendMessage(ChatColor.RED + "There are no targets in your line of sight!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (!TeamManager.arentSameTeam(p, entity)) {
            p.sendMessage(ChatColor.RED + "There are no targets in your line of sight!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (entity.getLocation().distance(p.getLocation()) > 16) {
            p.sendMessage(ChatColor.RED + "The target is too far..");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (!checkSpectator(entity)){
            p.sendMessage(ChatColor.RED + "There are no targets in your line of sight!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeMana(p, Mana1);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell1Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana1 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        LivingEntity livEnt = (LivingEntity) entity;
        livEnt.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1 * 20, -128));
        livEnt.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1 * 20, 180));
        livEnt.getWorld().spawnParticle(Particle.TOTEM, livEnt.getLocation(), 300, 0.1F, 0.1F, 0.1F, 1);
        livEnt.getWorld().playSound(livEnt.getLocation(), Sound.ITEM_TOTEM_USE, 5, 2);
        greenStar(p, livEnt);
    }

    private void greenStar(Player p, LivingEntity livEnt) {
        Location livLoc = livEnt.getLocation();
        new BukkitRunnable() {
            int t = 20;

            @Override
            public void run() {
                Location loc = livLoc.clone().add(0, t, 0);
                livLoc.getWorld().spawnParticle(Particle.TOTEM, loc, 20, 0.1F, 0.1F, 0.1F, 0.1);

                if (t == 0 || loc.getBlock().getType().isSolid()) {
                    loc.getWorld().playSound(loc, Sound.ENTITY_ENDERDRAGON_FIREBALL_EXPLODE, 5, 1);
                    loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, loc, 10, 0.1F, 0.1F, 0.1F, 1);
                    damageEntities(p, loc);
                    cancel();
                }
                t -= 1;
            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }

    private void damageEntities(Player p, Location loc) {
        for (Entity entity : p.getWorld().getNearbyEntities(loc, 3, 3, 3)) {
            if (entity.getType().isAlive() && entity != p) {
                if (TeamManager.arentSameTeam(p, entity)) {
                    LivingEntity ent = (LivingEntity) entity;
                    ent.damage(spell1DMG + SkillPointsCore.getDMGValue(p), p);
                }
            }
        }

    }

    @Override
    public void useSpell2(Player p) {
        if (p.getLocation().getY() > 94) {
            p.sendMessage(ChatColor.RED + "You can't fly under pressure!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 5, 1);
            return;
        }
        removeMana(p, Mana2);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell2Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana2 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        p.setVelocity(new Vector(0, 1, 0));
        Parrot parrot = p.getWorld().spawn(p.getLocation(), Parrot.class);
        parrot.setInvulnerable(true);
        followPlayer(p, parrot);
        hidePlayers(p);
        p.getWorld().spawnParticle(Particle.TOTEM, p.getLocation(), 200, 0.1F, 0.1F, 0.1F, 1);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 5, 1);
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, spell2DMG * 20, 5));
        healNearbyAllies(p, 10);
    }

    private void healNearbyAllies(Player p, Integer range) {
        new BukkitRunnable() {
            int t = 0;

            @Override
            public void run() {
                if (t == 4) {
                    cancel();
                    return;
                }
                for (Entity e : p.getWorld().getNearbyEntities(p.getLocation(), range, range, range)) {
                    if (e.getType().isAlive() && e instanceof Player) {
                        if (!TeamManager.arentSameTeam(p, e)) {
                            Player livE = (Player) e;
                            if (livE.getHealth() <= (livE.getMaxHealth() - spell2DMG)) {
                                livE.getWorld().playSound(livE.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 5, 1);
                                livE.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You received " + ChatColor.RED + ChatColor.BOLD + spell2DMG + " HP" + ChatColor.GREEN + "" + ChatColor.BOLD + " from " + ChatColor.WHITE + "" + ChatColor.BOLD + p.getName() + ChatColor.GREEN + "" + ChatColor.BOLD + " !");
                                livE.setHealth(livE.getHealth() + spell2DMG);
                                p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You healed " + livE.getName() + " !");
                                livE.getWorld().spawnParticle(Particle.TOTEM, livE.getLocation(), 200, 0.001F, 0.001F, 0.001F, 1);

                            } else if (livE.getHealth() > (livE.getMaxHealth() - spell2DMG) && livE.getHealth() < livE.getMaxHealth()) {
                                livE.setHealth(livE.getMaxHealth());
                                livE.getWorld().playSound(livE.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 5, 1);
                                p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You healed " + livE.getName() + " !");
                                livE.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You received " + ChatColor.RED + ChatColor.BOLD + spell2DMG + " HP" + ChatColor.GREEN + "" + ChatColor.BOLD + " from " + ChatColor.WHITE + "" + ChatColor.BOLD + p.getName() + ChatColor.GREEN + "" + ChatColor.BOLD + " !");
                                livE.getWorld().spawnParticle(Particle.TOTEM, livE.getLocation(), 200, 0.001F, 0.001F, 0.001F, 1);

                            }
                        }
                    }
                }
                t += 1;
            }
        }.runTaskTimer(Main.plugin, 0, 20);

    }

    private void followPlayer(Player p, Entity entity) {
        p.setAllowFlight(true);
        p.setFlying(true);
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                count += 1;
                if (count == (spell2DMG * 20)) {
                    if (p.isOnline()) {
                        showPlayers(p);
                    }
                    p.getWorld().spawnParticle(Particle.TOTEM, p.getLocation(), 200, 0.1F, 0.1F, 0.1F, 1);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 5, 1);
                    entity.remove();
                    p.setAllowFlight(false);
                    p.setFlying(false);
                    cancel();
                }
                entity.teleport(p);
            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }

    @Override
    public void useSpell3(Player p) {
        Entity entity = getTarget(p);
        if (entity == null) {
            p.sendMessage(ChatColor.RED + "There are no targets in your line of sight!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (!entity.getType().isAlive() || entity instanceof Parrot) {
            p.sendMessage(ChatColor.RED + "There are no targets in your line of sight!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (!(entity instanceof Player) && !(entity.equals(p))) {
            p.sendMessage(ChatColor.RED + "There are no targets in your line of sight!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (TeamManager.arentSameTeam(p, entity)) {
            p.sendMessage(ChatColor.RED + "There are no targets in your line of sight!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (entity.getLocation().distance(p.getLocation()) > 16) {
            p.sendMessage(ChatColor.RED + "The target is too far..");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (((LivingEntity) entity).getHealth() == ((LivingEntity) entity).getMaxHealth()) {
            p.sendMessage(ChatColor.RED + "That player is already at full hp!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeMana(p, Mana3);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + spell3Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana3 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        Zombie bat = p.getWorld().spawn(p.getLocation().clone().add(0, 0.75, 0), Zombie.class);
        bat.setInvulnerable(true);
        bat.setCustomName(p.getName());
        bat.setCustomNameVisible(false);
        bat.setSilent(true);
        bat.setCollidable(false);
        bat.setBaby(true);
        PetFollow((Player) entity, bat, 0.6F);
        bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999 * 20, 3));
        displayParticlesAndHeal(bat, (Player) entity, p);
    }

    private void displayParticlesAndHeal(Entity e, Player p, Player healer) {
        p.getWorld().playSound(p.getLocation(), Sound.ITEM_FIRECHARGE_USE, 2, 1);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!p.isOnline()) {
                    e.remove();
                    cancel();
                    return;
                }
                Location loc = e.getLocation();
                loc.getWorld().spawnParticle(Particle.TOTEM, loc.clone().add(0, 1, 0), 16, 0.1F, 0.1F, 0.1F, 0.1);
                if (loc.distance(p.getLocation()) < 1.4) {
                    if (p.getHealth() <= (p.getMaxHealth() - spell3DMG)) {
                        p.getWorld().playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 5, 1);
                        p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You received " + ChatColor.RED + ChatColor.BOLD + spell3DMG + " HP" + ChatColor.GREEN + "" + ChatColor.BOLD + " from " + ChatColor.WHITE + "" + ChatColor.BOLD + healer.getName() + ChatColor.GREEN + "" + ChatColor.BOLD + " !");
                        p.setHealth(p.getHealth() + spell3DMG);
                        healer.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You healed " + p.getName() + " !");
                        p.getWorld().spawnParticle(Particle.TOTEM, p.getLocation(), 200, 0.001F, 0.001F, 0.001F, 1);


                    } else if (p.getHealth() > (p.getMaxHealth() - spell3DMG) && p.getHealth() < p.getMaxHealth()) {
                        p.setHealth(p.getMaxHealth());
                        p.getWorld().playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 5, 1);
                        healer.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You healed " + p.getName() + " !");
                        p.getWorld().spawnParticle(Particle.TOTEM, p.getLocation(), 200, 0.001F, 0.001F, 0.001F, 1);
                        p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You received " + ChatColor.RED + ChatColor.BOLD + spell3DMG + " HP" + ChatColor.GREEN + "" + ChatColor.BOLD + " from " + ChatColor.WHITE + "" + ChatColor.BOLD + healer.getName() + ChatColor.GREEN + "" + ChatColor.BOLD + " !");
                    } else if (p.getHealth() == p.getMaxHealth()) {
                        p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You already have full health!");
                    }
                    e.remove();
                    cancel();
                    return;

                }
            }
        }.runTaskTimer(Main.plugin, 0, 1);

    }

    public static HashMap<Player, Location> revive = new HashMap<>();
    public static HashSet<UUID> rez = new HashSet<UUID>();

    @Override
    public void useUltimate(Player p) {
        if (revive.isEmpty() || (countDead(p) == 0)) {
            p.sendMessage(ChatColor.RED + "There are no nearby dead allies to revive!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        respawnSoul(p);

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        AaClassHashMapChoosing c = new AaClassHashMapChoosing();

        if (!c.getClass(e.getPlayer()).equals("Flora")) {
            return;
        }
        UltChargeMechanics u = new UltChargeMechanics();
        if (revive.containsKey(e.getPlayer())) {
            return;
        }
        if (u.getUlt(e.getPlayer()) < 100) {
            return;
        }
        int count = countDead(e.getPlayer());
        if (revive.isEmpty()) {
            e.getPlayer().sendTitle(null, null, 0, 0, 0);
            return;
        }
        if (count == 0) {
            e.getPlayer().sendTitle(null, null, 0, 0, 0);
            return;
        }
        if (!revive.containsKey(e.getPlayer()) && c.getClass(e.getPlayer()).equals("Flora")) {
            e.getPlayer().sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + count, " Ally To Revive!", 0, 20 * 3, 20);
        }
    }

    private void respawnSoul(Player p) {
        int count = 0;
        for (Player player : revive.keySet()) {
            if (TeamManager.arentSameTeam(p, player)) continue;
            if (p.getLocation().distance(revive.get(player)) < ultDMG) {
                if (count == 0) {
                    cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + classColor + "" + ChatColor.BOLD + ultName + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + 20 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
                    removeMana(p, 20);
                    removeUlt(p);
                }
                count += 1;
                rez.add(player.getUniqueId());
                p.sendTitle(null, null, 0, 0, 0);
            }
        }
        count = 0;
    }

    private int countDead(Player rezer) {
        int t = 0;
        for (Player p : revive.keySet()) {
            if (rezer.getLocation().distance(revive.get(p)) < ultDMG) {
                if (TeamManager.arentSameTeam(rezer, p)) continue;
                t += 1;
            }
        }
        return t;
    }
}
