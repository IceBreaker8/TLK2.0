package GameMechanics;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class StopTNTDamage implements Listener {

    @EventHandler
    public void onTnt(EntityExplodeEvent e) {
        if (e.getEntity().getType() == EntityType.PRIMED_TNT) {
            e.blockList().clear();
        }
    }
}
