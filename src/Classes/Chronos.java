package Classes;

import ClassGUI.AaClassSpellShowcase;
import ClassGUI.ChronosSpellInv;
import ClassUpgrades.SkillPointsCore;
import GameMechanics.ManaSystem;
import Main.Main;
import Teams.TeamManager;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class Chronos extends TlkClass {
    public Chronos() {
        //Class name
        className = "Chronos";
        classColor = ChatColor.YELLOW;
        classSpec = classColor + "" + ChatColor.BOLD + "Chronos, The Master Of Time" + ChatColor.WHITE + "" + ChatColor.BOLD;
        //Weapon name
        weaponName = "Ancient Clock";

        //Spell Names
        spell1Name = "Clock Wind";
        spell2Name = "Time Freeze";
        spell3Name = "Recall";
        ultName = "Time Decay";

        //Mana Cost
        Mana1 = 8;
        Mana2 = 14;
        Mana3 = 14;

        // Spell DMG
        spell1DMG = 3;  //clock wind DMG each clock
        spell2DMG = 3 * 20;  // freeze time
        spell3DMG = 8;  //time travel duration in seconds
        ultDMG = 6 * 20;     //time decay duration
    }

    ManaSystem mana = new ManaSystem();
    AaClassActionBar cAc = new AaClassActionBar();

    @Override
    public void useSpell1(Player p) {
        removeMana(p, Mana1);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + ChatColor.YELLOW + "" + ChatColor.BOLD + spell1Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana1 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (count == 0) {
                    launchItem(p);
                }
                if (count == 1) {
                    launchItem(p);

                }
                if (count == 2) {
                    launchItem(p);

                }
                if (count == 3) {
                    cancel();
                }
                count += 1;
            }
        }.runTaskTimer(Main.plugin, 0, 20);

    }

    public void launchItem(Player p) {
        ItemStack watch = new ItemStack(Material.WATCH, 1);
        Location loc = p.getLocation().add(0, 1.5, 0);
        Vector direction = loc.getDirection().normalize();
        direction.multiply(1.4F);
        Item clock = p.getWorld().dropItem(loc, watch);
        clock.setCustomName(p.getName() + "'s clock");
        clock.setCustomNameVisible(false);
        clock.setPickupDelay(20 * 9999);
        clock.setVelocity(direction.multiply(2.6F));
        clock.getWorld().playSound(clock.getLocation(), Sound.ITEM_FIRECHARGE_USE, 5, 1);
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                for (Entity e : p.getLocation().getWorld().getEntities()) {
                    if (e.getLocation().distance(clock.getLocation()) < 2) {
                        if (e != (p)) {
                            if (e.getType().isAlive() && !(e instanceof Zombie)) {
                                if (TeamManager.arentSameTeam(p, e)) {
                                    if (checkSpectator(e)) {
                                        clock.remove();
                                        LivingEntity b = (LivingEntity) e;
                                        b.damage(spell1DMG + SkillPointsCore.getDMGValue(p), p);
                                        clock.getWorld().playSound(clock.getLocation(), Sound.ENTITY_IRONGOLEM_HURT, 5, 2);
                                        b.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 10, 0));
                                        cancel();
                                    }
                                }
                            }
                        }
                    }
                }
                clock.getWorld().spawnParticle(Particle.REDSTONE, clock.getLocation(), 0, 1, 1, 0, 1); //Amount
                count += 1;
                if (count == 20 * 6 || clock.isOnGround()) {
                    clock.remove();
                    cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }

    public static HashMap<LivingEntity, Location> AI = new HashMap<>();


    @Override
    public void useSpell2(Player p) {
        if (thereAreNoEntities(p, 4)) {
            p.sendMessage(ChatColor.RED + "There are no nearby enemies..");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeMana(p, Mana2);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + ChatColor.YELLOW + "" + ChatColor.BOLD + spell2Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana2 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        for (Entity entity : p.getWorld().getNearbyEntities(p.getLocation(), 4, 4, 4)) {
            if (entity.getType().isAlive() && !entity.equals(p) && !(entity instanceof Player) && !(entity instanceof Wither)) {
                if (TeamManager.arentSameTeam(p, entity)) {
                    LivingEntity d = (LivingEntity) entity;
                    d.getWorld().spawnParticle(Particle.SMOKE_LARGE, d.getLocation(), 100, 0.5F, 0.5F, 0.5F, 0.001);
                    d.setAI(false);
                    d.setInvulnerable(true);
                    d.setCollidable(false);
                    d.getWorld().playSound(d.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 5, 1);
                    AI.put(d, d.getLocation());
                }
            }
        }
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.getLocation().distance(p.getLocation()) <= 4) {
                if (player.equals(p)) continue;
                if (!TeamManager.arentSameTeam(p, player)) continue;
                if (checkSpectator(player)) {
                    player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation(), 100, 0.5F, 0.5F, 0.5F, 0.001);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 5, 1);
                    player.setInvulnerable(true);
                    AI.put(player, player.getLocation());
                    freezePlayer();
                }
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for (LivingEntity l : AI.keySet()) {
                    l.setAI(true);
                    l.setInvulnerable(false);
                    l.setCollidable(true);
                    l.getWorld().playSound(l.getLocation(), Sound.ENTITY_IRONGOLEM_HURT, 5, 1);

                }
                AI.clear();
                cancel();

            }
        }.runTaskLater(Main.plugin, spell2DMG);


    }

    public static HashSet<UUID> timeStopped = new HashSet<UUID>();

    public void freezePlayer() {

        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                for (LivingEntity l : AI.keySet()) {
                    l.teleport(AI.get(l));
                    if (!(timeStopped.contains(l.getUniqueId()))) {
                        timeStopped.add(l.getUniqueId());
                    }
                }
                count += 1;
                if (count > spell2DMG) {
                    timeStopped.clear();
                    cancel();

                }

            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }


    public static HashMap<UUID, Location> recallLoc = new HashMap<>();
    public static HashMap<UUID, Double> hpLoc = new HashMap<>();
    public static HashMap<UUID, Integer> manaLoc = new HashMap<>();

    @Override
    public void useSpell3(Player p) {
        if (!p.isOnGround()) {
            p.sendMessage(ChatColor.RED + "You need to be on ground to cast this spell.");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (recallLoc.containsKey(p.getUniqueId())) {
            p.sendMessage(ChatColor.RED + "You can't use this spell twice at a time!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        hpLoc.put(p.getUniqueId(), p.getHealth());
        manaLoc.put(p.getUniqueId(), mana.getMana(p));
        removeMana(p, Mana3);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + ChatColor.YELLOW + "" + ChatColor.BOLD + spell3Name + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + Mana3 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        p.playSound(p.getLocation(), Sound.ENTITY_IRONGOLEM_HURT, 5, 2);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 4, 144));
        Location loc = p.getLocation();
        recallLoc.put(p.getUniqueId(), loc);
        p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You will travel back in " + ChatColor.WHITE + spell3DMG + ChatColor.YELLOW + "" + ChatColor.BOLD + " seconds..");
        new BukkitRunnable() {
            int time = 0;

            @Override
            public void run() {
                time += 1;
                if (p.isDead()) {
                    p.sendMessage("rip");
                    recallLoc.remove(p.getUniqueId());
                    hpLoc.remove(p.getUniqueId());
                    manaLoc.remove(p.getUniqueId());
                    cancel();
                    return;
                }
                if (time == spell3DMG) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 4, 144));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10, 144));
                    p.teleport(recallLoc.get(p.getUniqueId()));
                    recallLoc.remove(p.getUniqueId());
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Recalled successfully.. ");
                    p.playSound(p.getLocation(), Sound.ENTITY_IRONGOLEM_HURT, 5, 2);
                    p.setHealth(hpLoc.get(p.getUniqueId()));
                    mana.setMana(p, manaLoc.get(p.getUniqueId()));
                    hpLoc.remove(p.getUniqueId());
                    cancel();
                    return;
                }
                p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
                p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You will travel back in " + ChatColor.WHITE + (spell3DMG - time) + ChatColor.YELLOW + "" + ChatColor.BOLD + " seconds..");


            }
        }.runTaskTimer(Main.plugin, 20, 20);

    }

    public static HashMap<Location, Material> blocksToChange = new HashMap<>();
    public static HashMap<Location, Byte> dataToChange = new HashMap<>();
    public static HashMap<UUID, Long> verify = new HashMap<>();

    @Override
    public void useUltimate(Player p) {
        if (verify.containsKey(p.getUniqueId())) {
            p.sendMessage(ChatColor.RED + "You already have that spell active!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        if (!p.isOnGround()) {
            p.sendMessage(ChatColor.RED + "You need to be on ground to cast this spell.");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            return;
        }
        removeUlt(p);
        removeMana(p, 20);
        cAc.send(p, ChatColor.WHITE + "" + ChatColor.BOLD + "You have casted " + ChatColor.YELLOW + "" + ChatColor.BOLD + ultName + ChatColor.WHITE + "" + ChatColor.BOLD + " [-" + ChatColor.AQUA + "" + ChatColor.BOLD + 20 + ChatColor.WHITE + "" + ChatColor.BOLD + "]");
        long t = p.getWorld().getTime();
        verify.put(p.getUniqueId(), t);
        regenAllies(p, p.getLocation(), t);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 5, 1);
        Location ploc = p.getLocation().subtract(0, 1, 0); //Granite


        //Blocks loc:
        Location loc1 = p.getLocation().subtract(1, 1, 0); //Dorite
        Location loc2 = p.getLocation().subtract(2, 1, 0); // Moss stone
        Location loc3 = p.getLocation().subtract(0, 1, -1); //Diorite
        Location loc4 = p.getLocation().subtract(0, 1, -2); //Granite
        Location loc5 = p.getLocation().subtract(-1, 1, 0); //Dorite
        Location loc6 = p.getLocation().subtract(-2, 1, 0); //Granite
        Location loc7 = p.getLocation().subtract(0, 1, 1);  //mossy stone
        Location loc8 = p.getLocation().subtract(0, 1, 2);  // cobble
        Location loc9 = p.getLocation().subtract(1, 1, -1); //soul sand
        Location loc10 = p.getLocation().subtract(1, 1, -2);//Dorite
        Location loc11 = p.getLocation().subtract(2, 1, -1);//cobble
        Location loc12 = p.getLocation().subtract(2, 1, -2); //grass block
        Location loc13 = p.getLocation().subtract(1, 1, 1);  //Granite
        Location loc14 = p.getLocation().subtract(1, 1, 2);  //Grass
        Location loc15 = p.getLocation().subtract(2, 1, 1);  //Grass
        Location loc16 = p.getLocation().subtract(2, 1, 2);  //Dorite
        Location loc17 = p.getLocation().subtract(-1, 1, -1); //Grass
        Location loc18 = p.getLocation().subtract(-1, 1, -2); //Dorite
        Location loc19 = p.getLocation().subtract(-2, 1, -1); //Mossy
        Location loc20 = p.getLocation().subtract(-2, 1, -2); //Grass
        Location loc21 = p.getLocation().subtract(-1, 1, 1);   //soul sand
        Location loc22 = p.getLocation().subtract(-1, 1, 2);   //Granite
        Location loc23 = p.getLocation().subtract(-2, 1, 1);   //Grass block
        Location loc24 = p.getLocation().subtract(-2, 1, 2);   //Moss stone
        //Block setting:
        blocksToChange.put(ploc, ploc.getBlock().getType());
        dataToChange.put(ploc, ploc.getBlock().getData());

        blocksToChange.put(loc1, loc1.getBlock().getType());
        dataToChange.put(loc1, loc1.getBlock().getData());


        blocksToChange.put(loc2, loc2.getBlock().getType());
        dataToChange.put(loc2, loc2.getBlock().getData());


        blocksToChange.put(loc3, loc3.getBlock().getType());
        dataToChange.put(loc3, loc3.getBlock().getData());

        blocksToChange.put(loc4, loc4.getBlock().getType());
        dataToChange.put(loc4, loc4.getBlock().getData());

        blocksToChange.put(loc5, loc5.getBlock().getType());
        dataToChange.put(loc5, loc5.getBlock().getData());

        blocksToChange.put(loc6, loc6.getBlock().getType());
        dataToChange.put(loc6, loc6.getBlock().getData());

        blocksToChange.put(loc7, loc7.getBlock().getType());
        dataToChange.put(loc7, loc7.getBlock().getData());

        blocksToChange.put(loc8, loc8.getBlock().getType());
        dataToChange.put(loc8, loc8.getBlock().getData());

        blocksToChange.put(loc9, loc9.getBlock().getType());
        dataToChange.put(loc9, loc9.getBlock().getData());

        blocksToChange.put(loc10, loc10.getBlock().getType());
        dataToChange.put(loc10, loc10.getBlock().getData());

        blocksToChange.put(loc11, loc11.getBlock().getType());
        dataToChange.put(loc11, loc11.getBlock().getData());

        blocksToChange.put(loc12, loc12.getBlock().getType());
        dataToChange.put(loc12, loc12.getBlock().getData());

        blocksToChange.put(loc13, loc13.getBlock().getType());
        dataToChange.put(loc13, loc13.getBlock().getData());

        blocksToChange.put(loc14, loc14.getBlock().getType());
        dataToChange.put(loc14, loc14.getBlock().getData());

        blocksToChange.put(loc15, loc15.getBlock().getType());
        dataToChange.put(loc15, loc15.getBlock().getData());

        blocksToChange.put(loc16, loc16.getBlock().getType());
        dataToChange.put(loc16, loc16.getBlock().getData());

        blocksToChange.put(loc17, loc17.getBlock().getType());
        dataToChange.put(loc17, loc17.getBlock().getData());

        blocksToChange.put(loc18, loc18.getBlock().getType());
        dataToChange.put(loc18, loc18.getBlock().getData());

        blocksToChange.put(loc19, loc19.getBlock().getType());
        dataToChange.put(loc19, loc19.getBlock().getData());

        blocksToChange.put(loc20, loc20.getBlock().getType());
        dataToChange.put(loc20, loc20.getBlock().getData());

        blocksToChange.put(loc21, loc21.getBlock().getType());
        dataToChange.put(loc21, loc21.getBlock().getData());

        blocksToChange.put(loc22, loc22.getBlock().getType());
        dataToChange.put(loc22, loc22.getBlock().getData());

        blocksToChange.put(loc23, loc23.getBlock().getType());
        dataToChange.put(loc23, loc23.getBlock().getData());

        blocksToChange.put(loc24, loc24.getBlock().getType());
        dataToChange.put(loc24, loc24.getBlock().getData());

        if (ploc.getBlock().getType().isSolid() && blockIsValid(ploc.getBlock())) {
            ploc.getBlock().setType(Material.STONE);
            ploc.getBlock().setData((byte) 1);
        }
        if (loc1.getBlock().getType().isSolid() && blockIsValid(loc1.getBlock())) {
            loc1.getBlock().setType(Material.STONE);
            loc1.getBlock().setData((byte) 3);
        }
        if (loc2.getBlock().getType().isSolid() && blockIsValid(loc2.getBlock())) {
            loc2.getBlock().setType(Material.MOSSY_COBBLESTONE);
        }
        if (loc3.getBlock().getType().isSolid() && blockIsValid(loc3.getBlock())) {
            loc3.getBlock().setType(Material.STONE);
            loc3.getBlock().setData((byte) 3);
        }
        if (loc4.getBlock().getType().isSolid() && blockIsValid(loc4.getBlock())) {
            loc4.getBlock().setType(Material.STONE);
            loc4.getBlock().setData((byte) 1);
        }
        if (loc5.getBlock().getType().isSolid() && blockIsValid(loc5.getBlock())) {
            loc5.getBlock().setType(Material.STONE);
            loc5.getBlock().setData((byte) 3);
        }
        if (loc6.getBlock().getType().isSolid() && blockIsValid(loc6.getBlock())) {
            loc6.getBlock().setType(Material.STONE);
            loc6.getBlock().setData((byte) 1);
        }
        if (loc7.getBlock().getType().isSolid() && blockIsValid(loc7.getBlock())) {
            loc7.getBlock().setType(Material.MOSSY_COBBLESTONE);
        }
        if (loc8.getBlock().getType().isSolid() && blockIsValid(loc8.getBlock())) {
            loc8.getBlock().setType(Material.COBBLESTONE);
        }
        if (loc9.getBlock().getType().isSolid() && blockIsValid(loc9.getBlock())) {
            loc9.getBlock().setType(Material.SOUL_SAND);
        }
        if (loc10.getBlock().getType().isSolid() && blockIsValid(loc10.getBlock())) {
            loc10.getBlock().setType(Material.STONE);
            loc10.getBlock().setData((byte) 3);
        }
        if (loc11.getBlock().getType().isSolid() && blockIsValid(loc11.getBlock())) {
            loc11.getBlock().setType(Material.COBBLESTONE);
        }
        if (loc12.getBlock().getType().isSolid() && blockIsValid(loc12.getBlock())) {
            loc12.getBlock().setType(Material.GRASS);
        }
        if (loc13.getBlock().getType().isSolid() && blockIsValid(loc13.getBlock())) {
            loc13.getBlock().setType(Material.STONE);
            loc13.getBlock().setData((byte) 1);
        }
        if (loc14.getBlock().getType().isSolid() && blockIsValid(loc14.getBlock())) {
            loc14.getBlock().setType(Material.GRASS);
        }
        if (loc15.getBlock().getType().isSolid() && blockIsValid(loc15.getBlock())) {
            loc15.getBlock().setType(Material.GRASS);
        }
        if (loc16.getBlock().getType().isSolid() && blockIsValid(loc16.getBlock())) {
            loc16.getBlock().setType(Material.STONE);
            loc16.getBlock().setData((byte) 3);
        }
        if (loc17.getBlock().getType().isSolid() && blockIsValid(loc17.getBlock())) {
            loc17.getBlock().setType(Material.GRASS);
        }
        if (loc18.getBlock().getType().isSolid() && blockIsValid(loc18.getBlock())) {
            loc18.getBlock().setType(Material.STONE);
            loc18.getBlock().setData((byte) 3);
        }
        if (loc19.getBlock().getType().isSolid() && blockIsValid(loc19.getBlock())) {
            loc19.getBlock().setType(Material.MOSSY_COBBLESTONE);
        }
        if (loc20.getBlock().getType().isSolid() && blockIsValid(loc20.getBlock())) {
            loc20.getBlock().setType(Material.GRASS);
        }
        if (loc21.getBlock().getType().isSolid() && blockIsValid(loc21.getBlock())) {
            loc21.getBlock().setType(Material.SOUL_SAND);
        }
        if (loc22.getBlock().getType().isSolid() && blockIsValid(loc22.getBlock())) {
            loc22.getBlock().setType(Material.STONE);
            loc22.getBlock().setData((byte) 1);
        }
        if (loc23.getBlock().getType().isSolid() && blockIsValid(loc23.getBlock())) {
            loc23.getBlock().setType(Material.GRASS);
        }
        if (loc24.getBlock().getType().isSolid() && blockIsValid(loc24.getBlock())) {
            loc24.getBlock().setType(Material.MOSSY_COBBLESTONE);
        }

        //return blocks
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Location loc : blocksToChange.keySet()) {
                    loc.getBlock().setType(blocksToChange.get(loc));
                    loc.getBlock().setData(dataToChange.get(loc));
                }
                p.getWorld().setTime(t);
                blocksToChange.clear();
                dataToChange.clear();
                verify.clear();
            }
        }.runTaskLater(Main.plugin, ultDMG + 10);
    }

    private void regenAllies(Player p, Location loc, long time) {
        new BukkitRunnable() {
            int t = 0;
            int count = 0;

            @Override
            public void run() {
                if ((t % 20) == 0 || t == 0) {
                    p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc, 1000, 2F, 2F, 2F, 0.001);
                    loc.getWorld().playSound(loc, Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 5, 1);
                    generateSound(loc);
                }
                count += 150;
                if (t == ultDMG) {
                    p.getWorld().playSound(loc, Sound.ENTITY_WITHER_BREAK_BLOCK, 5, 1);
                    p.getWorld().setTime(verify.get(p.getUniqueId()));
                    cancel();
                }
                p.getWorld().setTime(time + count);


                t += 1;
                for (Entity entity : p.getWorld().getNearbyEntities(loc, 3, 3, 3)) {
                    if (entity.equals(p) && entity.getType().isAlive()) {
                        LivingEntity d = (LivingEntity) entity;
                        d.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 3, 4));
                        d.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 3, 2));
                    } else if (entity.getType().isAlive() && !(entity.equals(p))) {
                        LivingEntity d = (LivingEntity) entity;
                        d.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 4, 4));
                    }
                }


            }
        }.runTaskTimer(Main.plugin, 0, 1);

    }

    public void generateSound(Location loc) {
        new BukkitRunnable() {
            @Override
            public void run() {
                loc.getWorld().playSound(loc, Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 5, 2);
            }
        }.runTaskLater(Main.plugin, 10);

    }

    private boolean blockIsValid(Block b) {
        if (b.getType().equals(Material.LOG) || b.getType().equals(Material.LOG_2) || b.getType().equals(Material.GRASS) || b.getType().equals(Material.WOOD) || b.getType().equals(Material.WOOL) || b.getType().equals(Material.GLASS) || b.getType().equals(Material.STONE) || b.getType().equals(Material.DIRT)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public AaClassSpellShowcase getClassInv() {
        return new ChronosSpellInv();
    }
}
