package GameMenuInvGUIMechanics;

import BossesSpawners.AngelAndShamanBosses;
import BossesSpawners.GolemSpawner;
import BossesSpawners.TlkSpawn;
import ClassGUI.AaClassTable;
import GameMechanics.*;
import GameModeCore.GameModeStartPhase_I;
import GameModeCore.NexusDamage;
import Scoreboard.GameScoreboard;
import Scoreboard.PlayerScoreboard;
import Teams.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GameMenuMechs implements Listener {
    GameScoreboard sc = new GameScoreboard();
    PlayerScoreboard pJ = new PlayerScoreboard();

    public static Boolean activateClimb = false;

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        if (!e.getInventory().getTitle().contains(ChatColor.WHITE + "" + ChatColor.BOLD + "GameMenu")) {
            return;
        }
        if (e.getCurrentItem() == null)
            return;
        if (!e.getCurrentItem().hasItemMeta())
            return;
        if (!e.getCurrentItem().getItemMeta().hasDisplayName())
            return;
        e.setCancelled(true);
        TeamManager tm = new TeamManager();
        Player player = (Player) p;
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Shamans")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            tm.setTeam(player, "shamans");
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Angels")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            tm.setTeam(player, "angels");
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Remove Team")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            tm.removeTeams(player);
            tm.setTeamPrefix(player);
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Display Mana")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            ManaSystem m = new ManaSystem();
            m.startManaDisplay();
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Activate Bonfire")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            BonfireMech bonfireMech = new BonfireMech();
            bonfireMech.activateBonfires();
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Activate Smelter")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            SmelterMech smelterMech = new SmelterMech();
            smelterMech.activateSmelter();
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Activate Thirst")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            ThirstSystem thirstSystem = new ThirstSystem();
            thirstSystem.consumeWater();
            for (Player player1 : player.getServer().getOnlinePlayers()) {
                player1.setLevel(ThirstSystem.MaxThirstLevel);
            }
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Activate Scoreboard")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            sc.appearScoreboard();
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Delete Scoreboard")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            sc.deleteScoreboard();
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Activate Player Scoreboard")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            pJ.appearScoreboard();
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Delete Player Scoreboard")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            pJ.deleteScoreboard();
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Climbing System")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            activateClimb = true;
            ClimbingTool.grapplingHookRecipe();
            CorruptedCoreMech.addDiamondCore();
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Shooting Crystal")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            CrystalShooter c = new CrystalShooter();
            c.crystalTowerOn();
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Allow Class Table")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            AaClassTable.allowEnchTable = true;
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Spawn Shaman Boss")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            AngelAndShamanBosses a = new AngelAndShamanBosses();
            a.spawnShamanBoss();
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Spawn Angel Boss")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            AngelAndShamanBosses a = new AngelAndShamanBosses();
            a.spawnAngelBoss();
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Kill Bosses")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            AngelAndShamanBosses a = new AngelAndShamanBosses();
            a.killBosses();
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Flowers")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            /*Flowers f = new Flowers();
            f.addItems(player);*/
            //NameTag nameTag = new NameTag();
           // p.sendMessage("" + nameTag.hasTeam(player));
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Golem")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            GolemSpawner.golemTimer();
            TlkSpawn.tlkTimer();
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Start game")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            GameModeStartPhase_I gameModeStartPhase_i = new GameModeStartPhase_I();
            gameModeStartPhase_i.countdown();
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Restore Map")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            NexusDamage.restoreMap();
            return;
        }

    }
}
