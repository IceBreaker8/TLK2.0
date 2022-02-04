package Classes;

import ClassGUI.AaClassSpellShowcase;
import org.apache.commons.lang.ClassUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class TlkClass extends AaClassUtility {

////////////////////////////////////////////// TlkClass Specs //////////////////////////////////////////////////////////

    public String className;
    public String weaponName;
    public String classSpec;

    public String spell1Name;
    public int spell1DMG;

    public String spell2Name;
    public int spell2DMG;

    public String spell3Name;
    public int spell3DMG;

    public String ultName;
    public int ultDMG;

    public int Mana1;
    public int Mana2;
    public int Mana3;
    public ChatColor classColor;

/////////////////////////////////////////////// Class Inheritance //////////////////////////////////////////////////////

    public AaClassSpellShowcase getClassInv() {
        return null;
    }

//////////////////////////////////////////////// Spells ////////////////////////////////////////////////////////////////

    public void useSpell1(Player p) {
    }

    public void useSpell2(Player p) {
    }

    public void useSpell3(Player p) {
    }

    public void useUltimate(Player p) {
    }

}
