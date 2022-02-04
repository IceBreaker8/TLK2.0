package GamePerks;

import org.bukkit.entity.Entity;


public abstract class Perks {
    public String perk;
    public String className;

    public abstract void activatePerk(Entity p);
}
