package GameModeCore;

import BossesSpawners.AngelAndShamanBosses;
import BossesSpawners.GolemSpawner;
import BossesSpawners.TlkSpawn;
import ClassGUI.AaClassTable;
import GameMechanics.*;
import GameMenuInvGUIMechanics.GameMenuMechs;
import Scoreboard.GameScoreboard;
import Teams.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class GameModeStartPhase_II {

    World w = Bukkit.getWorld("world");
    Location tlk2Map = new Location(w, 6, 74, 40);
    Location lobby = new Location(w, 61, 43, -1220);
    private Location shamanSpawn = new Location(w, 339, 72, -11);
    private Location angelSpawn = new Location(w, -118, 73, 1);


    public void initiateTeleportSequence() {
        activateMechs();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule mobGriefing false");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule naturalRegeneration false");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule keepInventory true");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule announceAdvancements false");
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            GameScoreboard g = new GameScoreboard();
            g.appearScoreboard();
            p.setInvulnerable(false);
            for (PotionEffect effect : p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }
            if (TeamManager.getTeam(p) != null) {
                if (TeamManager.getTeam(p) != null) {
                    if (TeamManager.getTeam(p) == "shamans") {
                        p.teleport(shamanSpawn);
                        p.setGameMode(GameMode.SURVIVAL);
                        p.setInvulnerable(false);
                        TeamManager.setTeamPrefix(p);
                    }
                    if (TeamManager.getTeam(p) == "angels") {
                        p.teleport(angelSpawn);
                        p.setGameMode(GameMode.SURVIVAL);
                        p.setInvulnerable(false);
                        TeamManager.setTeamPrefix(p);
                    }

                }
            } else {
                p.teleport(shamanSpawn);
                p.setGameMode(GameMode.SPECTATOR);
            }
        }
    }

    public void activateMechs() {
        CorruptedCoreMech.addDiamondCore();
        ManaSystem m = new ManaSystem();
        m.startManaDisplay();
        BonfireMech bonfireMech = new BonfireMech();
        bonfireMech.activateBonfires();
        SmelterMech smelterMech = new SmelterMech();
        smelterMech.activateSmelter();
        ThirstSystem thirstSystem = new ThirstSystem();
        thirstSystem.consumeWater();
        for (Player player1 : Bukkit.getServer().getOnlinePlayers()) {
            player1.setLevel(ThirstSystem.MaxThirstLevel);
            player1.setInvulnerable(false);
        }
        GameMenuMechs.activateClimb = true;
        ClimbingTool.grapplingHookRecipe();
        CrystalShooter c = new CrystalShooter();
        c.crystalTowerOn();
        AaClassTable.allowEnchTable = true;
        AngelAndShamanBosses a = new AngelAndShamanBosses();
        a.spawnShamanBoss();
        a.spawnAngelBoss();
        GolemSpawner.golemTimer();
        TlkSpawn.tlkTimer();
    }
}
