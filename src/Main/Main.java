package Main;

import BossesDrops.BossesDrops;
import BossesSpawners.MobCustomNameChanger;
import BreweryMech.BreweryGUI;
import BreweryMech.FlowerSearchGUI;
import BreweryMech.PotionEffects;
import ChatCMD.Messaging;
import ChatCMD.RankMessageChat;
import ClassGUI.*;
import ClassUpgrades.SkillPointPerKill;
import ClassUpgrades.UpgradePurchase;
import Classes.*;
import Commands.*;
import GameMechanics.*;
import GameMenuInvGUIMechanics.GameMenuMechs;
import GameMenuInvGUIMechanics.GmItemRightClick;
import GameModeCore.DeathMessages;
import GameModeCore.NexusDamage;
import GamePerks.AaPerksInventory;
import GamePerks.AaPlayerDeathPerkActivate;
import Ranks.BuilderPerm;
import Ranks.RanksCommands;
import Scoreboard.TLKScoreboardManager;
import Teams.TeamChoose;
import Teams.TeamFire;
import Teams.TeamPickOnJoin;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main plugin;

    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        getServer().getLogger().info("TLK2.0 plugin loaded succesfully!");
        registerCommands();
        registerEvent();


    }

    public void onDisable() {
        getServer().getLogger().info("TLK2.0 plugin unloaded succesfully!");
        saveConfig();
        plugin = null;

    }

    public void registerCommands() {
        DeathPerks ap = new DeathPerks();
        getCommand("msg").setExecutor(new Messaging());
        getCommand("rank").setExecutor(new RanksCommands());
        getCommand("gm").setExecutor(new GameMenuCMD());
        getCommand("deathperks").setExecutor(new DeathPerks());
        getCommand("dp").setExecutor(new DeathPerks());
        getCommand("spells").setExecutor(new ClassSpellsAndSkillUpgrades());
        getCommand("sp").setExecutor(new ClassSpellsAndSkillUpgrades());
        getCommand("jabsay").setExecutor(new ConsoleCommands());
        getCommand("wizsay").setExecutor(new ConsoleCommands());
        getCommand("ethsay").setExecutor(new ConsoleCommands());
        getCommand("kirdowsay").setExecutor(new ConsoleCommands());
        getCommand("icesay").setExecutor(new ConsoleCommands());
        getCommand("build").setExecutor(new ConsoleCommands());
        getCommand("start").setExecutor(new ConsoleCommands());
        getCommand("afk").setExecutor(new AfkCmd());
        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("shout").setExecutor(new ShoutCmd());
        getCommand("recall").setExecutor(new Recalling());
        getCommand("med").setExecutor(new Meditation());




    }

    public void registerEvent() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new RankMessageChat(), this);
        pm.registerEvents(new GameMenuMechs(), this);
        pm.registerEvents(new GmItemRightClick(), this);
        pm.registerEvents(new AaClassTable(), this);
        pm.registerEvents(new AaClassChooseGUI(), this);
        pm.registerEvents(new RiftorSpellInv(), this);
        pm.registerEvents(new BonfireMech(), this);
        pm.registerEvents(new OccultistSpellInv(), this);
        pm.registerEvents(new ChronosSpellInv(), this);
        pm.registerEvents(new FrostBiteSpellInv(), this);
        pm.registerEvents(new GluonSpellInv(), this);
        pm.registerEvents(new FloraSpellInv(), this);
        pm.registerEvents(new AlpSpellInv(), this);
        pm.registerEvents(new ItzalSpellInv(), this);
        pm.registerEvents(new VerunSpellInv(), this);
        pm.registerEvents(new AaClassComboes(), this);
        pm.registerEvents(new Riftor(), this);
        pm.registerEvents(new Occultist(), this);
        pm.registerEvents(new FrostBite(), this);
        pm.registerEvents(new Gluon(), this);
        pm.registerEvents(new Flora(), this);
        pm.registerEvents(new Alp(), this);
        pm.registerEvents(new Verun(), this);
        pm.registerEvents(new ManaSystem(), this);
        pm.registerEvents(new UltChargeMechanics(), this);
        pm.registerEvents(new StopTNTDamage(), this);
        pm.registerEvents(new RespawnMech(), this);
        pm.registerEvents(new AaPerksInventory(), this);
        pm.registerEvents(new AaPlayerDeathPerkActivate(), this);
        pm.registerEvents(new TeamPickOnJoin(), this);
        pm.registerEvents(new SmelterMech(), this);
        pm.registerEvents(new ThirstSystem(), this);
        pm.registerEvents(new ClassItemDrop(), this);
        pm.registerEvents(new UpgradePurchase(), this);
        pm.registerEvents(new SkillPointPerKill(), this);
        pm.registerEvents(new TLKScoreboardManager(), this);
        pm.registerEvents(new BuilderPerm(), this);
        pm.registerEvents(new PoseidonSpellInv(), this);
        pm.registerEvents(new ConsoleCommands(), this);
        pm.registerEvents(new WaterWell(), this);
        pm.registerEvents(new AfkCmd(), this);
        pm.registerEvents(new ClimbingTool(), this);
        pm.registerEvents(new CrystalCore(), this);
        pm.registerEvents(new ForgeryMech(), this);
        pm.registerEvents(new BossesDrops(), this);
        pm.registerEvents(new MobCustomNameChanger(), this);
        pm.registerEvents(new BreweryGUI(), this);
        pm.registerEvents(new FlowerSearchGUI(), this);
        pm.registerEvents(new PotionEffects(), this);
        pm.registerEvents(new SoldierSpellInv(), this);
        pm.registerEvents(new NexusDamage(), this);
        pm.registerEvents(new TeamFire(), this);
        pm.registerEvents(new FishingEvent(), this);
        pm.registerEvents(new WaterDamage(), this);
        pm.registerEvents(new DisableWeapons(), this);
        pm.registerEvents(new OreMineRespawn(), this);
        pm.registerEvents(new TeamChoose(), this);
        pm.registerEvents(new DeathMessages(), this);
        pm.registerEvents(new PlayerRegenInSpawn(), this);
        pm.registerEvents(new CorruptedCoreMech(), this);
        pm.registerEvents(new RecipesPages(), this);










    }
}

