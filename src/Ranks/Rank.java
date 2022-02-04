package Ranks;

import org.bukkit.ChatColor;

public enum Rank {

    OWNER(ChatColor.AQUA + "[" + ChatColor.DARK_AQUA + "OWNER" + ChatColor.AQUA + "] " + ChatColor.AQUA),
    ADMIN(ChatColor.RED + "[" + ChatColor.DARK_RED + "ADMIN" + ChatColor.RED + "] " + ChatColor.RED),
    MOD(ChatColor.YELLOW + "[" + ChatColor.GOLD + "MOD" + ChatColor.YELLOW + "] " + ChatColor.YELLOW),
    YT(ChatColor.LIGHT_PURPLE + "[" + ChatColor.DARK_PURPLE + "YT" + ChatColor.LIGHT_PURPLE + "] " + ChatColor.LIGHT_PURPLE),
    BUILDER(ChatColor.GREEN + "[" + ChatColor.DARK_GREEN + "BUILDER" + ChatColor.GREEN + "] " + ChatColor.GREEN),
    MEMBER(ChatColor.GRAY + "[" + ChatColor.DARK_GRAY + "MEMBER" + ChatColor.GRAY + "] " + ChatColor.GRAY),
    CMD(ChatColor.WHITE + "[" + ChatColor.AQUA + "CMD" + ChatColor.WHITE + "] " + ChatColor.WHITE);
    public String prefix;

    Rank(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;

    }


}
